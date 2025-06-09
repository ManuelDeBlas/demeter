<script>
  import { ESTADOS_EXPEDIENTE } from "@/constants/app";
  import { formatearAtributoEnElFrontend } from "@/utils/utils";
  import SolicitudEnFormularioExpediente from "@/components/componentes-en-formulario/SolicitudEnFormularioExpediente.vue";
  import { useExpedientesStore } from "@/stores/expedientes";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { mapState, mapActions } from "pinia";
  import { cargarTodaLaApi } from "@/utils/utils";

  export default {
    name: "FormularioExpedienteView",
    components: { SolicitudEnFormularioExpediente },
    data() {
      return {
        editando: true,
        consultando: useExpedientesStore().consultando,
        mensajeModal: "",
        mostrarModal: false,
        seleccionSolicitud: "",
        antiguoListadoSolicitudes: [],
        nuevoListadoSolicitudes: [],
        ESTADOS_EXPEDIENTE: ESTADOS_EXPEDIENTE,
      };
    },
    computed: {
      expedienteAbierto() {
        let expediente = useExpedientesStore().elementoAbierto;
        if (expediente === null) {
          expediente = {
            numeroExpediente: "",
            estado: "ABIERTO",
            solicitudes: [],
          };
          this.editando = false;
        }
        return expediente;
      },
      solicitudesDisponibles() {
        // TODO poner solo las solicitudes del mismo año
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
      formatearAtributoEnElFrontend,
      ...mapActions(useExpedientesStore, [
        "anadirElemento",
        "editarExpediente",
        "eliminarElemento",
        "agregarSolicitudAExpediente",
        "eliminarSolicitudDeExpediente",
      ]),
      eliminarSolicitudDelNuevoListado(solicitud) {
        this.nuevoListadoSolicitudes = this.nuevoListadoSolicitudes.filter(
          (s) => s._links.self.href !== solicitud._links.self.href
        );
      },
      async enviarFormulario() {
        if (this.editando) {
          const respuesta = await this.editarExpediente(this.expedienteAbierto);
          console.log("Respuesta de edición:", respuesta);
          this.mensajeModal = respuesta
        } else {
          const respuesta = await this.anadirElemento(this.expedienteAbierto);
          this.mensajeModal = respuesta
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
        await cargarTodaLaApi();
        this.mostrarModal = true;
      },
      eliminarExpediente() {
        this.eliminarElemento(this.expedienteAbierto._links.self.href);
        this.$router.push({ path: "/listado/expedientes" });
      },
      volverAlListado() {
        this.$router.push({ path: "/listado/expedientes" });
      },
      cerrarModal() {
        this.mostrarModal = false;
        useExpedientesStore().elementoAbierto = null;
        useExpedientesStore().consultando = false;
        this.$router.push({ path: "/listado/expedientes" });
      },
    },
    created() {
      // Crear una copia independiente para antiguoListadoSolicitudes
      this.antiguoListadoSolicitudes = [...this.expedienteAbierto.solicitudes];

      // Asignar directamente las solicitudes a nuevoListadoSolicitudes
      this.nuevoListadoSolicitudes = [...this.expedienteAbierto.solicitudes];
    },
  };
</script>

<template>
  <h2 v-if="!editando">Añadir expediente</h2>
  <h2 v-if="editando && consultando">Consultar expediente</h2>
  <h2 v-if="editando && !consultando">Modificar expediente</h2>
  <form @submit.prevent="enviarFormulario">
    <div class="mb-3">
      <label class="form-label">Número expediente</label>
      <input
        v-model="expedienteAbierto.numeroExpediente"
        type="text"
        class="form-control w-50 mx-auto"
        :disabled="editando || consultando"
        pattern="^T64(PS|EX|FC)A(ENE|FEB|MAR|ABR|MAY|JUN|JUL|AGO|SEP|OCT|NOV|DIC)([0-9]{2})[1-9][0-9]*$"
        title="Formato: T64 seguido de PS, EX o FC, luego A, el mes (ej. ENE), el año (25) y el número de expediente que corresponda (ej. 1). Ejemplo completo: T64PSAENE251"
        required
      />
    </div>
    <div class="mb-3">
      <label class="form-label">Estado</label>
      <select
        v-model="expedienteAbierto.estado"
        class="form-control w-50 mx-auto"
        :disabled="!editando || consultando"
      >
        <option
          v-for="estado in ESTADOS_EXPEDIENTE"
          :key="estado"
          :value="estado"
        >
          {{ formatearAtributoEnElFrontend(estado) }}
        </option>
      </select>
    </div>
    <div class="mb-3">
      <label class="form-label">Solicitudes</label>
      <div v-if="editando && !consultando">
        <select v-model="seleccionSolicitud" class="border p-2 rounded mb-4">
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
      </div>
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
            v-if="editando && !consultando"
            type="button"
            class="btn btn-danger ms-3"
            @click="eliminarSolicitudDelNuevoListado(solicitud)"
          >
            Eliminar solicitud
          </button>
        </div>
      </ul>
    </div>

    <div class="d-flex justify-content-end gap-2">
      <div v-if="!editando">
        <button type="button" class="btn btn-light" @click="volverAlListado">
          Cancelar cambios
        </button>
        <button type="submit" class="btn btn-success">Añadir</button>
      </div>
      <div v-if="editando && consultando">
        <button type="button" class="btn btn-success" @click="volverAlListado">
          Volver al listado
        </button>
      </div>
      <div v-if="editando && !consultando">
        <button type="submit" class="btn btn-light" @click="volverAlListado">
          Cancelar cambios
        </button>
        <button
          type="button"
          @click="eliminarExpediente"
          class="btn btn-danger ms-2"
        >
          Eliminar expediente
        </button>
        <button type="submit" class="btn btn-success ms-2">
          Guardar cambios
        </button>
      </div>
    </div>
  </form>

  <div
    v-if="mostrarModal"
    class="modal fade show"
    tabindex="-1"
    style="display: block; background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Información</h5>
          <button type="button" class="btn-close" @click="cerrarModal"></button>
        </div>
        <div class="modal-body">
          <p>{{ mensajeModal }}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="cerrarModal">
            Cerrar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
