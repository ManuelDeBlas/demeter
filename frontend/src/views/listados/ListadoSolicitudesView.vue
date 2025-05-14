<script>
  import { ESTADOS_SOLICITUD } from "@/constants/app";
  import { formatearAtributoEnElFrontend } from "@/utils/utils";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import SolicitudEnListadoSolicitudes from "@/components/componentes-en-lista/SolicitudEnListadoSolicitudes.vue";

  export default {
    components: {
      SolicitudEnListadoSolicitudes,
    },
    data() {
      return {
        seleccionFiltro: "",
        ESTADOS_SOLICITUD: ESTADOS_SOLICITUD,
      };
    },
    computed: {
      solicitudes() {
        return useSolicitudesStore().elementos;
      },
      elementosFiltrados() {
        let elementosFiltrados = this.solicitudes;
        if (this.seleccionFiltro) {
          elementosFiltrados = this.solicitudes.filter(
            (elemento) => elemento.estado === this.seleccionFiltro
          );
        }
        return elementosFiltrados;
      },
    },
    methods: {
      formatearAtributoEnElFrontend,
      anadirElemento() {
        useSolicitudesStore().elementoAbierto = null; // Vacía el store para añadir un nuevo elemento
        this.$router.push({ path: "/formulario/solicitud" });
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">Lista de Solicitudes</h1>
    <div class="d-flex align-items-center gap-2 mb-4">
      <button type="button" class="btn btn-warning" @click="anadirElemento">
        Nueva Solicitud
      </button>
      <label class="mb-0 fw-bold">Filtrar:</label>
      <select v-model="seleccionFiltro" class="form-select w-auto">
        <option value="">Todas</option>
        <option
          v-for="estado in ESTADOS_SOLICITUD"
          :key="estado"
          :value="estado"
        >
          {{ formatearAtributoEnElFrontend(estado) }}
        </option>
      </select>
    </div>
    <ul>
      <div
        v-for="solicitud in elementosFiltrados"
        :key="solicitud._links.self.href"
        class="mb-3"
      >
        <solicitud-en-listado-solicitudes
          :solicitud="solicitud"
        ></solicitud-en-listado-solicitudes>
      </div>
    </ul>
  </div>
</template>
