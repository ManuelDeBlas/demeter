<script>
  import { usePocsStore } from "@/stores/pocs";
  import PocEnListadoPocs from "@/components/componentes-en-lista/PocEnListadoPocs.vue";

  export default {
    components: {
      PocEnListadoPocs,
    },
    computed: {
      elementos() {
        return usePocsStore().elementos;
      },
    },
    methods: {
      anadirElemento() {
        usePocsStore().elementoAbierto = null; // Vacía el store para añadir un nuevo elemento
        this.$router.push({ path: "/formulario/poc" });
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">Lista de POCs</h1>
    <div class="d-flex align-items-center gap-2 mb-4">
      <button type="button" class="btn btn-warning" @click="anadirElemento">
        Nuevo POC
      </button>
    </div>
    <ul>
      <div
        v-for="poc in elementos"
        :key="poc._links.self.href"
        class="mb-3"
      >
        <poc-en-listado-pocs
          :poc="poc"
        ></poc-en-listado-pocs>
      </div>
    </ul>
  </div>
</template>
