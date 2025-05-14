<script>
  import { useReservistasStore } from "@/stores/reservistas";
  import ReservistaEnListadoReservistas from "@/components/componentes-en-lista/ReservistaEnListadoReservistas.vue";

  export default {
    components: {
      ReservistaEnListadoReservistas,
    },
    computed: {
      elementos() {
        return useReservistasStore().elementos;
      },
    },
    methods: {
      anadirElemento() {
        useReservistasStore().elementoAbierto = null; // Vacía el store para añadir un nuevo elemento
        this.$router.push({ path: "/formulario/reservista" });
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">Lista de Reservistas</h1>
    <div class="d-flex align-items-center gap-2 mb-4">
      <button type="button" class="btn btn-warning" @click="anadirElemento">
        Nuevo Reservista
      </button>
    </div>
    <ul>
      <div
        v-for="reservista in elementos"
        :key="reservista._links.self.href"
        class="mb-3"
      >
        <reservista-en-listado-reservistas
          :reservista="reservista"
        ></reservista-en-listado-reservistas>
      </div>
    </ul>
  </div>
</template>
