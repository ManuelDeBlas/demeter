<script>
  import { ESTADOS_EXPEDIENTE } from "@/constants/app";
  import ExpedienteEnListadoExpedientes from "@/components/componentes-en-lista/ExpedienteEnListadoExpedientes.vue";
  import { useExpedientesStore } from "@/stores/expedientes";

  export default {
    components: {
      ExpedienteEnListadoExpedientes,
    },
    data() {
      return {
        seleccionFiltro: "",
        ESTADOS_EXPEDIENTE: ESTADOS_EXPEDIENTE,
      };
    },
    computed: {
      expedientes() {
        return useExpedientesStore().elementos;
      },
      expedientesFiltrados() {
        let expedientesFiltrados = this.expedientes;
        if (this.seleccionFiltro) {
          expedientesFiltrados = this.expedientes.filter(
            (elemento) => elemento.estado === this.seleccionFiltro
          );
        }
        return expedientesFiltrados;
      },
    },
    methods: {
      anadirElemento() {
        useExpedientesStore().elementoAbierto = null; // Vacía el store para añadir un nuevo elemento
        this.$router.push({ path: "/formulario/expediente" });
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">Lista de expedientes</h1>
    <div class="d-flex align-items-center gap-2 mb-4">
      <button type="button" class="btn btn-warning" @click="anadirElemento">
        Nuevo Expediente
      </button>
      <label class="mb-0 fw-bold">Filtrar:</label>
      <select v-model="seleccionFiltro" class="form-select w-auto">
        <option value="">Todas</option>
        <option
          v-for="estado in ESTADOS_EXPEDIENTE"
          :key="estado"
          :value="estado"
        >
          {{ estado }}
        </option>
      </select>
    </div>
    <ul>
      <div
        v-for="expediente in expedientesFiltrados"
        :key="expediente._links.self.href"
        class="mb-3"
      >
        <expediente-en-listado-expedientes
          :expediente="expediente"
        ></expediente-en-listado-expedientes>
      </div>
    </ul>
  </div>
</template>
