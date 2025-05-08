<script>
  import NavBar from "@/components/navegacion/NavBar.vue";
  import { mapActions } from "pinia";
  import { useExpedientesStore } from "@/stores/expedientes.js";
  import { usePocsStore } from "@/stores/pocs.js";
  import { useReservistasStore } from "@/stores/reservistas.js";
  import { useSolicitudesStore } from "@/stores/solicitudes.js";

  export default {
    components: { NavBar },
    mounted() {
      for (let store of [
        useExpedientesStore(),
        usePocsStore(),
        useReservistasStore(),
        useSolicitudesStore(),
      ]) {
        store.cargarElementos();
      }
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
