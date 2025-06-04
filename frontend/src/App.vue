<script>
  import Cabecera from "@/components/navegacion/Cabecera.vue";
  import ProgressSpinner from "primevue/progressspinner";
  import Footer from "@/components/navegacion/Footer.vue";
  import { mapActions } from "pinia";
  import { useExpedientesStore } from "@/stores/expedientes.js";
  import { useReservistasStore } from "@/stores/reservistas.js";
  import { useSolicitudesStore } from "@/stores/solicitudes.js";

  export default {
    components: { Cabecera, ProgressSpinner, Footer },
    async mounted() {
      const expedientes = useExpedientesStore();
      const reservistas = useReservistasStore();
      const solicitudes = useSolicitudesStore();

      await Promise.all([
        expedientes.cargarElementos(),
        reservistas.cargarElementos(),
        solicitudes.cargarElementos(),
      ]);
      await Promise.all([
        expedientes.cargarSolicitudesEnExpedienteAlIniciar(),
        reservistas.crearListadoSolicitudes(),
        solicitudes.cargarReservistaEnSolicitudAlIniciar(),
      ]);
    },
  };
</script>
<template>
  <div class="app-container">
    <Cabecera></Cabecera>
    <main>
      <router-view></router-view>
    </main>
    <footer></footer>
  </div>
</template>

<style>
  .app-container {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  .fl {
    display: flex;
    flex-direction: row;
  }

  main {
    flex: 1;
  }

  .layout {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  footer {
    background-color: #f8f9fa;
    color: #333;
  }

  .colorPrincipal {
    color: #fff;
    background-color: #393621;
  }
  .colorPrincipal:hover {
    color: #7c732f;
  }
  .colorSecundario {
    color: #c4bd86;
  }
  .clolorSecundario:hover {
    color: #979163;
  }
  .spinnerColor {
    color: #c4bd86;
  }
  .fuenteEtiqueta {
    font: 1em sans-serif;
  }
  .colorFuenteFooter {
    color: #fff;
  }
  .otrosFuente {
    color: #000;
  }
  .fondofooter {
    background-color: #393621;
  }
  .fondo {
    background-color: #393621;
  }
  .colorAmarillo {
    background-color: #ffdd00;
  }
</style>
