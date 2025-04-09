<script>
  import { mapState, mapActions } from "pinia";
  import { useSolicitudesStore } from "@/stores/solicitudes";

  export default {
    computed: {
      ...mapState(useSolicitudesStore, ["solicitudes"]),
    },

    methods: {
      ...mapActions(useSolicitudesStore, [
        "anadirSolicitud",
        "eliminarSolicitud",
        "modificarEstadoSolicitud",
      ]),
      abrirFormularioSolicitud() {
        // TODO enviar al router que abra el componente FormularioSolicitud
      }
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
        <div>{{ solicitud }}</div>
      </div>
    </ul>
  </div>
</template>
