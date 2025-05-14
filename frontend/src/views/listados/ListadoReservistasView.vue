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
    <button type="button" class="btn btn-success mb-3" @click="anadirElemento">
      Nuevo
    </button>
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
