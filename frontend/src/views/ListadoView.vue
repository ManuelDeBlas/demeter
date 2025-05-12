<script>
  import ElementoEnLista from "@/components/ElementoEnLista.vue";

  export default {
    props: {
      tipoListado: String,
      config: Object,
    },
    components: {
      ElementoEnLista,
    },
    data() {
      return {
        seleccionFiltro: "",
      };
    },
    watch: {
      tipoListado() {
        this.seleccionFiltro = "";
      },
    },
    // created() {
    //   this.config.store().cargarElementos();
    // },
    computed: {
      elementos() {
        return this.config.store().elementos;
      },
      elementosFiltrados() {
        let elementosFiltrados = this.elementos;
        console.log("Elementos filtrados:", elementosFiltrados);
        if (this.seleccionFiltro) {
          elementosFiltrados = this.elementos.filter(
            (elemento) => elemento.estado === this.seleccionFiltro
          );
        }
        return elementosFiltrados;
      },
    },
    methods: {
      anadirElemento() {
        this.config.store().elementoAbierto = null; // Vacía el store para añadir un nuevo elemento
        this.$router.push({ path: `/formulario/${this.tipoListado}` });
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">Lista de {{ tipoListado }}</h1>
    <button type="button" class="btn btn-success mb-3" @click="anadirElemento">
      Nuevo
    </button>
    <label class="block mb-2 font-bold">Filtrar:</label>
    <select v-model="seleccionFiltro" class="border p-2 rounded mb-4">
      <option value="">Todas</option>
      <option v-for="estado in config.estados" :key="estado" :value="estado">
        {{ estado }}
      </option>
    </select>
    <ul>
      <div
        v-for="elemento in elementosFiltrados"
        :key="elemento._links.self.href"
        class="mb-3"
      >
        <elemento-en-lista
          :tipoListado="tipoListado"
          :config="config"
          :elemento="elemento"
        ></elemento-en-lista>
      </div>
    </ul>
  </div>
</template>
