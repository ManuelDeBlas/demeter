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
      console.log("Cargando datos iniciales...");

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

      solicitudes.obtenerReservista();
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
