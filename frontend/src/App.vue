<script>
import { mapState, mapActions } from "pinia";
  import { useReservistasStore } from "@/stores/reservistas";

  export default {
    computed: {
      ...mapState(useReservistasStore, ["reservistas"]),
    },
    methods: {
      ...mapActions(useReservistasStore, ["anadirReservista", "eliminarReservista"]),
    }, 
  };
</script>
<template>
  <div class="app-container">
    <div v-for="reservista in reservistas">
      <div>{{ reservista }}</div>
    </div>
  </div>
</template>
