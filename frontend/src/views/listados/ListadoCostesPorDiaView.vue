<script>
  import { useCostesPorDiaStore } from "@/stores/costes-por-dia";
  import { formatearCentimosAEuros } from "@/utils/utils";

  export default {
    computed: {
      costes() {
        return useCostesPorDiaStore().elementos;
      },
    },
    methods: {
      formatearCentimosAEuros,
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo py-4">Costes por día</h1>
    <!-- TODO Poner una tabla -->
    <ul>
      <div v-for="coste in costes" :key="coste._links.self.href" class="mb-3">
        <li>
          {{ coste.empleo }} {{ formatearCentimosAEuros(coste.centimos) }}
        </li>
      </div>
    </ul>
  </div>
</template>
