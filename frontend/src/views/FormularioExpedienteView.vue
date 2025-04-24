<script>
  import ElementoEnLista from "@/components/ElementoEnLista.vue";
  import { useExpedientesStore } from "@/stores/expedientes";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { mapState, mapActions } from "pinia";
  import { getListadoConfig } from "@/router/listadoConfig";

  export default {
    name: "FormularioExpedienteView",
    components: { ElementoEnLista },
    data() {
      return {
        editando: true,
        config: getListadoConfig("solicitudes"),
        seleccionSolicitud: "",
      };
    },
    computed: {
      expedienteAbierto() {
        let expediente = useExpedientesStore().elementoAbierto;
        if (expediente === null) {
          expediente = {
            numeroExpediente: "",
            estado: "",
            solicitudes: [],
          };
          this.editando = false;
        }
        return expediente;
      },
      ...mapState(useSolicitudesStore, { solicitudes: "elementos" }),
    },
    methods: {
      ...mapActions(useExpedientesStore, [
        "anadirElemento",
        "editarElemento",
        "eliminarElemento",
        "agregarSolicitudAExpediente",
        "eliminarSolicitudDeExpediente",
      ]),
      enviarFormulario() {
        if (this.editando) {
          this.editarElemento(this.expedienteAbierto);
        } else {
          this.anadirElemento(this.expedienteAbierto);
        }
        this.$router.push({ path: "/listado/expedientes" });
      },
      eliminarExpediente() {
        this.eliminarElemento(this.expedienteAbierto._links.self.href);
        this.$router.push({ path: "/listado/expedientes" });
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">
      <h2 v-if="editando">Editar expediente</h2>
      <h2 v-else>Crear nuevo expediente</h2>
    </div>
    <div class="card-body">
      <form @submit.prevent="enviarFormulario">
        <div class="mb-3">
          <label class="form-label">Número expediente:</label>
          <input
            v-model="expedienteAbierto.numeroExpediente"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Estado:</label>
          <input
            v-model="expedienteAbierto.estado"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Solicitudes: </label>
          <select v-model="seleccionSolicitud" class="border p-2 rounded mb-4">
            <option value="">Seleccionar</option>
            <option
              v-for="solicitud in solicitudes.filter((s) => s.estado === 'Aceptada sin expediente')"
              :key="solicitud._links.self.href"
              :value="solicitud"
            >
              {{ solicitud.nombreUco }}
            </option>
          </select>
          <button
            type="button"
            class="btn btn-primary mb-2"
            @click="agregarSolicitudAExpediente(seleccionSolicitud)"
          >
            Añadir solicitud
          </button>
          <ul>
            <div
              v-for="solicitud in expedienteAbierto.solicitudes"
              :key="solicitud._links.self.href"
              class="d-flex align-items-center justify-content-between mb-3"
            >
              <elemento-en-lista
                :tipoListado="'solicitudes'"
                :config="config"
                :elemento="solicitud"
                class="flex-grow-1"
              ></elemento-en-lista>
              <button
                type="button"
                class="btn btn-danger ms-3"
                @click="eliminarSolicitudDeExpediente(solicitud)"
              >
                Eliminar solicitud
              </button>
            </div>
          </ul>
        </div>

        <div class="d-flex justify-content-between">
          <button v-if="editando" type="submit" class="btn btn-primary">
            Guardar cambios
          </button>
          <button v-else type="submit" class="btn btn-success">
            Crear expediente
          </button>
          <button
            v-if="editando"
            type="button"
            @click="eliminarExpediente"
            class="btn btn-danger"
          >
            Eliminar expediente
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
