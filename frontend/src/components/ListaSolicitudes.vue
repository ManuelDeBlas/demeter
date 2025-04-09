<script>
  import { mapState, mapActions } from "pinia";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import SolicitudEnLista from "./SolicitudEnLista.vue";

  export default {
    components: {
      SolicitudEnLista,
    },
    data() {
      return {
        estados: [
          "Pendiente de evaluación",
          "Aceptada pendiente de publicación",
          "Aceptada publicada",
          "Rechazada",
        ],
        filtroEstado: "",
      };
    },
    computed: {
      ...mapState(useSolicitudesStore, ["solicitudes"]),
      solicitudesFiltradas() {
        if (this.filtroEstado) {
          return this.solicitudes.filter(
            (solicitud) => solicitud.estado === this.filtroEstado
          );
        }
        return this.solicitudes;
      },
    },

    methods: {
      ...mapActions(useSolicitudesStore, [
        "anadirSolicitudStore",
        "modificarEstadoSolicitudStore",
        "editarSolicitudStore"
      ]),
      abrirFormularioSolicitud() {
        // TODO enviar al router que abra el componente FormularioSolicitud
      },
      modificarEstadoSolicitud(solicitud) {
        this.modificarEstadoSolicitudStore(solicitud);
      },
      editarSolicitud(solicitud) {
        this.editarSolicitudStore(solicitud);
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">LISTA SOLICITUDES</h1>
    <button
      type="button"
      class="btn btn-success mb-3"
      @click="abrirFormularioSolicitud"
    >
      Añadir
    </button>
    <label class="block mb-2 font-bold">Filtrar por estado:</label>
    <select v-model="filtroEstado" class="border p-2 rounded mb-4">
      <option value="">Todas</option>
      <option v-for="estado in estados" :key="estado" :value="estado">
        {{ estado }}
      </option>
    </select>
    <ul>
      <div
        v-for="solicitud in solicitudesFiltradas"
        :key="solicitud._links.self.href"
        class="mb-3"
      >
        <solicitud-en-lista
          :solicitud="solicitud"
          @editar-solicitud="editarSolicitud"
          @modificar-estado-solicitud="modificarEstadoSolicitud"
        ></solicitud-en-lista>
      </div>
    </ul>
  </div>
</template>
