<script>
  import NavBar from "@/components/navegacion/NavBar.vue";
  import { mapActions } from "pinia";
  import { useExpedientesStore } from "@/stores/expedientes.js";
  import { usePocsStore } from "@/stores/pocs.js";
  import { useReservistasStore } from "@/stores/reservistas.js";
  import { useSolicitudesStore } from "@/stores/solicitudes.js";

  export default {
    components: { NavBar },
    async mounted() {
      const expedientes = useExpedientesStore();
      const pocs = usePocsStore();
      const reservistas = useReservistasStore();
      const solicitudes = useSolicitudesStore();

      await Promise.all([
        expedientes.cargarElementos(),
        pocs.cargarElementos(),
        reservistas.cargarElementos(),
        solicitudes.cargarElementos(),
      ]);
      await Promise.all([
        expedientes.cargarSolicitudesEnExpedienteAlIniciar(),
        pocs.crearListadoSolicitudes(),
        reservistas.crearListadoSolicitudes(),
        solicitudes.cargarReservistaYPocEnSolicitudAlIniciar(),
      ]);
      ("Datos cargados");
    },
  };
</script>
<template>
  <div class="app-container">
    <NavBar></NavBar>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<style>
  html,
  body {
    height: 100%;
    margin: 0;
    padding: 0;
  }

  .app-container {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    background: linear-gradient(135deg, #3d7275, #81c784);
    color: white;
    font-family: Arial, sans-serif;
  }

  .inicio-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100vh;
    background: linear-gradient(135deg, #3d7275, #81c784);
    color: white;
    text-align: center;
    font-family: Arial, sans-serif;
  }

  .header h1 {
    font-size: 3rem;
    margin: 0;
  }

  .subtitle {
    font-size: 1.5rem;
    margin: 10px 0 20px;
  }

  .actions button {
    background-color: #ffffff;
    color: #4caf50;
    border: none;
    padding: 10px 20px;
    margin: 10px;
    font-size: 1rem;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s ease, color 0.3s ease;
  }

  .actions button:hover {
    background-color: #4caf50;
    color: white;
  }

  .formulario-con-fondo {
    background: linear-gradient(135deg, #3d7275, #81c784);
    min-height: 100vh;
    padding: 2rem;
  }
</style>
