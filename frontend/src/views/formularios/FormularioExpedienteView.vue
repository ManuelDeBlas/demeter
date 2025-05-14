<script>
  import ElementoEnLista from "@/components/ElementoEnLista.vue";
  import { useExpedientesStore } from "@/stores/expedientes";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { mapState, mapActions } from "pinia";

  export default {
    name: "FormularioExpedienteView",
    components: { ElementoEnLista },
    data() {
      return {
        editando: true,
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
            tipoSolicitud: "",
            estado: "ABIERTO",
            solicitudes: [],
          };
          this.editando = false;
        }
        return expediente;
      },
      solicitudesDisponibles() {
        return useSolicitudesStore().elementos.filter(
          (s) =>
            !this.expedienteAbierto.solicitudes.some(
              (es) => es._links.self.href === s._links.self.href
            ) &&
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
        if (this.editando) {
          // this.editarElemento(this.expedienteAbierto);
        } else {
          this.anadirElemento(this.expedienteAbierto);
        }
        // Envío de los cambios realizados a la API
        this.antiguoListadoSolicitudes
          .filter(
            (s) =>
              !this.nuevoListadoSolicitudes.some(
                (ns) => ns._links.self.href === s._links.self.href
              )
          )
          .forEach((s) => {
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
      // Crear una copia independiente para antiguoListadoSolicitudes
      this.antiguoListadoSolicitudes = [...this.expedienteAbierto.solicitudes];

      // Asignar directamente las solicitudes a nuevoListadoSolicitudes
      this.nuevoListadoSolicitudes = [...this.expedienteAbierto.solicitudes];
      (this.expedienteAbierto.solicitudes);
    },
  };
</script>

<template>
  <div
    class="formulario-con-fondo d-flex justify-content-center align-items-center"
  >
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
              :disabled="editando"
              pattern="^T64(PS|EX|FC)A(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)25[1-9][0-9]*$"
              title="Formato: T64 seguido de PS, EX o FC, luego A, el mes (ej. ENE), el año (25) y el número de expediente que corresponda (ej. 1). Ejemplo completo: T64PSAENE251"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Tipo de Solicitud:</label>
            <select
              v-model="expedienteAbierto.tipoSolicitud"
              class="form-select w-50 mx-auto"
              :disabled="editando"
            >
              <option value="FC">Formación continuada</option>
              <option value="EX">Activación ampliada</option>
              <option value="PS">Prestación servicios unidad</option>
            </select>
          </div>
          <div class="mb-3">
            <label class="form-label">Estado:</label>
            <input
              v-model="expedienteAbierto.estado"
              type="text"
              class="form-control w-50 mx-auto"
              :disabled="!editando"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Solicitudes: </label>
            <select
              v-model="seleccionSolicitud"
              class="border p-2 rounded mb-4"
            >
              <option value="">Seleccionar</option>
              <option
                v-for="solicitud in solicitudesDisponibles"
                :key="solicitud._links.self.href"
                :value="solicitud"
              >
                DNI: {{ solicitud.reservista.dni }} , Inicio:
                {{ solicitud.fechaInicio }} , Fin: {{ solicitud.fechaFin }}
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
                <solicitud-en-formulario-expediente
                  :solicitud="solicitud"
                  class="flex-grow-1"
                ></solicitud-en-formulario-expediente>
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
  </div>
</template>
