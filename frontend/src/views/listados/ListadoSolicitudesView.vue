<script>
  import { ESTADOS_SOLICITUD, TIPOS_SOLICITUD } from "@/constants/app";
  import { formatearAtributoEnElFrontend } from "@/utils/utils";
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import SolicitudEnListadoSolicitudes from "@/components/componentes-en-lista/SolicitudEnListadoSolicitudes.vue";

  export default {
    components: {
      SolicitudEnListadoSolicitudes,
    },
    data() {
      return {
        filtroEstadoSeleccionado: "",
        filtroTipoSeleccionado: "",
        paginaActual: 1,
        registrosPorPagina: 10,
        ESTADOS_SOLICITUD: ESTADOS_SOLICITUD,
        TIPOS_SOLICITUD: TIPOS_SOLICITUD,
      };
    },
    computed: {
      solicitudes() {
        return useSolicitudesStore().elementos;
      },
      solicitudesFiltradas() {
        let filtradas = this.solicitudes;
        if (this.filtroEstadoSeleccionado) {
          filtradas = filtradas.filter(
            (solicitud) => solicitud.estado === this.filtroEstadoSeleccionado
          );
        }
        if (this.filtroTipoSeleccionado) {
          filtradas = filtradas.filter(
            (solicitud) => solicitud.tipoSolicitud === this.filtroTipoSeleccionado
          );
        }
        return filtradas;
      },
      totalPaginas() {
        return (
          Math.ceil(
            this.solicitudesFiltradas.length / this.registrosPorPagina
          ) || 1
        );
      },
      inicio() {
        return (this.paginaActual - 1) * this.registrosPorPagina;
      },
      fin() {
        return Math.min(
          this.paginaActual * this.registrosPorPagina,
          this.solicitudesFiltradas.length
        );
      },
      solicitudesPaginadas() {
        return this.solicitudesFiltradas.slice(this.inicio, this.fin);
      },
    },
    methods: {
      formatearAtributoEnElFrontend,
      agregarSolicitud() {
        useSolicitudesStore().elementoAbierto = null;
        this.$router.push({ path: "/formulario/solicitud" });
      },
      irAPagina(pagina) {
        if (pagina >= 1 && pagina <= this.totalPaginas) {
          this.paginaActual = pagina;
        }
      },
      cambiarRegistrosPorPagina() {
        this.paginaActual = 1;
      },
    },
    watch: {
      filtroEstadoSeleccionado() {
        this.paginaActual = 1;
      },
      filtroTipoSeleccionado() {
        this.paginaActual = 1;
      },
    },
  };
</script>

<template>
  <div class="container">
    <h1 class="titulo p-4">Lista de Solicitudes</h1>
    <div class="d-flex align-items-center gap-2 mb-4">
      <button type="button" class="btn btn-warning" @click="agregarSolicitud">
        Nueva Solicitud
      </button>
      <label class="mb-0 fw-bold">Filtrar por estado:</label>
      <select v-model="filtroEstadoSeleccionado" class="form-select w-auto">
        <option value="">Todas</option>
        <option
          v-for="estado in ESTADOS_SOLICITUD"
          :key="estado"
          :value="estado"
        >
          {{ formatearAtributoEnElFrontend(estado) }}
        </option>
      </select>
      <label class="mb-0 fw-bold">Filtrar por tipo de solicitud:</label>
      <select v-model="filtroTipoSeleccionado" class="form-select w-auto">
        <option value="">Todas</option>
        <option v-for="tipo in TIPOS_SOLICITUD" :key="tipo" :value="tipo">
          {{ formatearAtributoEnElFrontend(tipo) }}
        </option>
      </select>
    </div>
    <ul id="solicitudes-lista" class="list-unstyled">
      <li
        v-for="solicitud in solicitudesPaginadas"
        :key="solicitud._links.self.href"
        class="mb-3"
      >
        <solicitud-en-listado-solicitudes :solicitud="solicitud" />
      </li>
    </ul>
    <div class="row">
      <div class="col-lg-12">
        <div class="card">
          <div class="card-body">
            <nav>
              <div class="d-flex justify-content-between align-items-center">
                Registros por página:
                <select
                  class="table_selector"
                  aria-label="Selector de registros por página"
                  v-model="registrosPorPagina"
                  @change="cambiarRegistrosPorPagina"
                >
                  <option disabled>Registro por página</option>
                  <option :value="5">5</option>
                  <option :value="10">10</option>
                  <option :value="20">20</option>
                  <option :value="30">30</option>
                </select>

                <div class="d-flex justify-content-end align-items-center">
                  <p class="mx-3">
                    Mostrando el intervalo {{ inicio + 1 }} - {{ fin }} de
                    {{ solicitudesFiltradas.length }} registros.
                  </p>
                  <ul class="pagination justify-content-end">
                    <li
                      class="page-item dnone-d"
                      :class="{ disabled: paginaActual === 1 }"
                    >
                      <a
                        class="page-link"
                        href="#"
                        @click.prevent="irAPagina(paginaActual - 1)"
                      >
                        <span aria-hidden="true"
                          ><i class="bi bi-arrow-left me-2"></i>Anterior</span
                        >
                      </a>
                    </li>
                    <li
                      class="page-item dblock-m"
                      :class="{ disabled: paginaActual === 1 }"
                    ></li>

                    <li
                      v-for="pagina in totalPaginas"
                      :key="pagina"
                      class="page-item"
                      :class="{ active: paginaActual === pagina }"
                    >
                      <a
                        class="page-link"
                        href="#"
                        @click.prevent="irAPagina(pagina)"
                        >{{ pagina }}</a
                      >
                    </li>

                    <li
                      class="page-item dnone-d"
                      :class="{ disabled: paginaActual === totalPaginas }"
                    >
                      <a
                        class="page-link"
                        href="#"
                        @click.prevent="irAPagina(paginaActual + 1)"
                      >
                        <span aria-hidden="true"
                          >Siguiente<i class="bi bi-arrow-right gap-2"></i
                        ></span>
                      </a>
                    </li>
                  </ul>
                </div>
              </div>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
