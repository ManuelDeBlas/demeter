<script>
  import { ESTADOS_SOLICITUD } from "@/constants/app";
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
        console.log("Elementos filtrados:", elementosFiltrados);
        if (this.seleccionFiltro) {
          elementosFiltrados = this.solicitudes.filter(
            (elemento) => elemento.estado === this.seleccionFiltro
          );
        }
        return elementosFiltrados;
      },
    },
    methods: {
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
    <button type="button" class="btn btn-success mb-3" @click="anadirElemento">
      Nuevo
    </button>
    <label class="block mb-2 font-bold">Filtrar:</label>
    <select v-model="seleccionFiltro" class="border p-2 rounded mb-4">
      <option value="">Todas</option>
      <option v-for="estado in ESTADOS_SOLICITUD" :key="estado" :value="estado">
        {{ estado }}
      </option>
    </select>
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
