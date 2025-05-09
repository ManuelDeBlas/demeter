<script>
  import ElementoEnLista from "@/components/ElementoEnLista.vue";
  import { useExpedientesStore, getTipoSolicitud } from "@/stores/expedientes";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { mapState, mapActions } from "pinia";
  import { getListadoConfig } from "@/router/listadoConfig";
  import { get } from "@/stores/api-service.js";

  export default {
    name: "FormularioExpedienteView",
    components: { ElementoEnLista },
    data() {
      return {
        editando: true,
        config: getListadoConfig("solicitudes"),
        seleccionSolicitud: "",
        antiguoListadoSolicitudes: [],
        nuevoListadoSolicitudes: [],
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
      solicitudesDisponibles() {
        return this.solicitudes.filter(
          (s) =>
            !this.nuevoListadoSolicitudes.some(
              (ns) => ns._links.self.href === s._links.self.href
            ) &&
            s.estado === "PENDIENTE_EVALUACION" &&
            s.tipoSolicitud === this.expedienteAbierto.tipoSolicitud
        );
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
      eliminarSolicitudDelNuevoListado(solicitud) {
        this.nuevoListadoSolicitudes = this.nuevoListadoSolicitudes.filter(
          (s) => s._links.self.href !== solicitud._links.self.href
        );
      },
      enviarFormulario() {
        // if (this.editando) {
        //   // this.editarElemento(this.expedienteAbierto);
        // } else {
        //   this.anadirElemento(this.expedienteAbierto);
        // }
        console.log(this.nuevoListadoSolicitudes);
        console.log(this.antiguoListadoSolicitudes);
        // Envío de los cambios realizados a la API
        this.antiguoListadoSolicitudes
          .filter(
            (s) =>
              !this.nuevoListadoSolicitudes.some(
                (ns) => ns._links.self.href === s._links.self.href
              )
          )
          .forEach((s) => {
            console.log(s);
            this.eliminarSolicitudDeExpediente(s);
          });
        this.nuevoListadoSolicitudes
          .filter(
            (s) =>
              !this.antiguoListadoSolicitudes.some(
                (ns) => ns._links.self.href === s._links.self.href
              )
          )
          .forEach((s) => {
            this.agregarSolicitudAExpediente(s);
          });
        this.expedienteAbierto.solicitudes = this.nuevoListadoSolicitudes;
        this.$router.push({ path: "/listado/expedientes" });
      },
      eliminarExpediente() {
        this.eliminarElemento(this.expedienteAbierto._links.self.href);
        this.$router.push({ path: "/listado/expedientes" });
      },
      descartarCambios() {
        this.$router.push({ path: "/listado/expedientes" });
      },
    },
    async created() {
      try {
        // Descargar de la API las solicitudes del expediente abierto
        const resSolicitudes = await get(
          useExpedientesStore().elementoAbierto._links.solicitudes.href
        );
        const solicitudes =
          resSolicitudes.data._embedded[
            getTipoSolicitud(
              useExpedientesStore().elementoAbierto.tipoSolicitud
            )
          ] || [];

        // Asignar las solicitudes al expediente abierto
        useExpedientesStore().elementoAbierto.solicitudes = solicitudes;

        // Crear una copia independiente para antiguoListadoSolicitudes
        this.antiguoListadoSolicitudes = [...solicitudes];

        // Asignar directamente las solicitudes a nuevoListadoSolicitudes
        this.nuevoListadoSolicitudes = [...solicitudes];
      } catch (error) {
        console.error("Error al cargar las solicitudes:", error);
      }
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
              v-for="solicitud in solicitudesDisponibles"
              :key="solicitud._links.self.href"
              :value="solicitud"
            >
              {{ solicitud.nombreUco }}
            </option>
          </select>
          <button
            type="button"
            class="btn btn-primary mb-2"
            :disabled="!seleccionSolicitud"
            @click="nuevoListadoSolicitudes.push(seleccionSolicitud)"
          >
            Añadir solicitud
          </button>
          <ul>
            <div
              v-for="solicitud in nuevoListadoSolicitudes"
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
                @click="eliminarSolicitudDelNuevoListado(solicitud)"
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
          <button
            v-if="editando"
            type="button"
            @click="descartarCambios"
            class="btn btn-danger"
          >
            Descartar cambios
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
