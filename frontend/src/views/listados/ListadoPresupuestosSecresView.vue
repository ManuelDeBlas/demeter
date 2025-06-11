<script>
  import { usePresupuestosSecresStore } from "@/stores/presupuestos-secres";
  import { formatearCentimosAEuros } from "@/utils/utils";

  export default {
    computed: {
      presupuestos() {
        return usePresupuestosSecresStore().elementos;
      },
    },
    methods: {
      formatearCentimosAEuros,
    },
  };
</script>

<template>
  <div class="container">
        <!-- TODO Poner una tabla -->
    <h1 class="titulo p-4">Presupuestos SECRES</h1>
    <ul>
      <div
        v-for="presupuesto in presupuestos"
        :key="presupuesto._links.self.href"
        class="mb-3"
      >
        <li>
          {{ presupuesto.anho }}
          {{ formatearCentimosAEuros(presupuesto.cantidadCentimos) }}
        </li>
      </div>
    </ul>
  </div>
</template>
