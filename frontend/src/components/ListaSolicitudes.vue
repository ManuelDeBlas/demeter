<script>
  import { mapState, mapActions } from "pinia";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import SolicitudEnLista from "./SolicitudEnLista.vue";

  export default {
    components: {
      SolicitudEnLista,
    },
    computed: {
      ...mapState(useSolicitudesStore, ["solicitudes"]),
    },

    methods: {
      ...mapActions(useSolicitudesStore, [
        "anadirSolicitudStore",
        "eliminarSolicitudStore",
        "modificarEstadoSolicitudStore",
      ]),
      abrirFormularioSolicitud() {
        // TODO enviar al router que abra el componente FormularioSolicitud
      },
      eliminarSolicitud(solicitud) {
        this.eliminarSolicitudStore(solicitud._links.self.href);
      },
      modificarEstadoSolicitud(solicitud) {
        this.modificarEstadoSolicitudStore(solicitud);
      },
      editarSolicitud(solicitud) {
        // TODO enviar al router que abra el componente FormularioSolicitud
        // y le pase la solicitud a editar
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
    <ul>
      <div
        v-for="solicitud in solicitudes"
        :key="solicitud._links.self.href"
        class="mb-3"
      >
        <solicitud-en-lista :solicitud="solicitud" @eliminar-solicitud="eliminarSolicitud"></solicitud-en-lista>
      </div>
    </ul>
  </div>
</template>
