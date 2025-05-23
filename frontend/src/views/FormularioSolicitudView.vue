<script>
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { useReservistasStore } from "@/stores/reservistas";
  import { usePocsStore } from "@/stores/pocs";
  import { useUcosStore } from "@/stores/ucos";
  import { mapActions } from "pinia";
  import { get } from "@/stores/api-service";

  export default {
    name: "FormularioSolicitudView",
    data() {
      return {
        editando: true,
        mostrarModal: false,
        mostrarModalReservistas: false,
        mostrarModalPocs: false,
        mostrarModalUcos: false,
        mensajeModal: "",
        uco: null,
        reservista: null,
        poc: null,
      };
    },
    computed: {
      solicitudAbierta: {
        get() {
          if (!this.store.elementoAbierto) {
            this.editando = false;
            this.store.elementoAbierto = {
              nombreUco: "",
              ciu: "",
              reservista: null,
              poc: null,
              fechaInicio: "",
              fechaFin: "",
              tipoSolicitud: "",
              estado: "PENDIENTE_EVALUACION",
            };
          }
          return this.store.elementoAbierto;
        },
        set(value) {
          this.store.elementoAbierto = value;
        },
      },
      reservistas() {
        return useReservistasStore().elementos;
      },
      pocs() {
        return usePocsStore().elementos;
      },
      ucos() {
        return useUcosStore().ucos;
      },
      reservistaFormateado() {
        return this.reservista
          ? `${this.reservista.dni} ${this.reservista.empleo} ${this.reservista.apellido1} ${this.reservista.apellido2}, ${this.reservista.nombre}`
          : "";
      },
      pocFormateado() {
        return this.poc
          ? `${this.poc.empleo} ${this.poc.apellido1} ${this.poc.apellido2}, ${this.poc.nombre}`
          : "";
      },
    },
    methods: {
      ...mapActions(useSolicitudesStore, [
        "anadirElemento",
        "editarElemento",
        "eliminarElemento",
        "guardarSolicitudEnAPI",
      ]),
      seleccionarUco(uco) {
        this.solicitudAbierta.nombreUco = uco.nombreUco;
        this.solicitudAbierta.ciu = uco.ciu;
        this.mostrarModalUcos = false;
      },
      seleccionarReservista(reservista) {
        this.reservista = reservista;
        this.solicitudAbierta.reservista = `/reservistas/${reservista._links.self.href
          .split("/")
          .pop()}`;
        this.mostrarModalReservistas = false;
      },
      seleccionarPoc(poc) {
        this.poc = poc;
        this.solicitudAbierta.poc = `/pocs/${poc._links.self.href
          .split("/")
          .pop()}`;
        this.mostrarModalPocs = false;
      },
      async enviarFormulario() {
        try {
          if (this.editando) {
            await this.editarElemento(this.solicitudAbierta);
            await useSolicitudesStore().cargarElementos();
            this.mensajeModal = `Solicitud editada correctamente`;
          } else {
            await this.guardarSolicitudEnAPI(
              this.solicitudAbierta,
              this.reservista,
              this.poc
            );
            this.solicitudAbierta.poc = this.poc;
            this.solicitudAbierta.reservista = this.reservista;
            console.log(
              "Solicitud guardada en el store desde el formulario:",
              this.solicitudAbierta
            );
            this.mensajeModal = `Solicitud añadida correctamente`;
          }
        } catch (error) {
          this.mensajeModal = `Error al procesar la solicitud: ${error.message}`;
        } finally {
          this.mostrarModal = true;
        }
      },
      async eliminarSolicitud() {
        try {
          await this.eliminarElemento(this.solicitudAbierta._links.self.href);
          this.mensajeModal = `Solicitud eliminada correctamente.`;
        } catch (error) {
          this.mensajeModal = `Error al eliminar la solicitud: ${error.message}`;
        } finally {
          this.mostrarModal = true;
        }
      },
      cerrarModal() {
        this.mostrarModal = false;
        this.$router.push({ path: "/listado/solicitudes" });
      },
    },
    async created() {
      this.store = useSolicitudesStore();
      if (this.editando == true) {
        const reservistaObj = await get(
          this.solicitudAbierta._links.reservista.href
        );
        this.reservista = reservistaObj.data;
        const pocObj = await get(this.solicitudAbierta._links.poc.href);
        this.poc = pocObj.data;
      }
    },
  };
</script>

<template>
  <div
    class="formulario-con-fondo d-flex justify-content-center align-items-center"
  >
    <div class="card text-center">
      <div class="card-header fw-bold fs-5">
        <h2 v-if="editando">Editar solicitud</h2>
        <h2 v-else>Crear nueva solicitud</h2>
      </div>
      <div class="card-body">
        <form @submit.prevent="enviarFormulario">
          <div class="mb-3">
            <label class="form-label">Nombre UCO:</label>
            <div class="d-flex align-items-center gap-2 mx-auto">
              <input
                disabled
                v-model="solicitudAbierta.nombreUco"
                type="text"
                class="form-control"
              />
              <button
                type="button"
                class="btn btn-secondary mt-2"
                @click="mostrarModalUcos = true"
              >
                Seleccionar UCO
              </button>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">CIU:</label>
            <input
              disabled
              v-model="solicitudAbierta.ciu"
              type="text"
              class="form-control mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Reservista:</label>
            <div class="d-flex align-items-center gap-2 mx-auto">
              <input
                disabled
                v-model="reservistaFormateado"
                type="text"
                class="form-control"
              />
              <button
                type="button"
                class="btn btn-secondary mt-2"
                @click="mostrarModalReservistas = true"
              >
                Seleccionar Reservista
              </button>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">POC:</label>
            <div class="d-flex align-items-center gap-2 mx-auto">
              <input
                disabled
                v-model="pocFormateado"
                type="text"
                class="form-control"
              />
              <button
                type="button"
                class="btn btn-secondary mt-2"
                @click="mostrarModalPocs = true"
              >
                Seleccionar POC
              </button>
            </div>
          </div>
          <div class="mb-3">
            <label class="form-label">Fecha Inicio:</label>
            <input
              v-model="solicitudAbierta.fechaInicio"
              type="date"
              class="form-control mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Fecha Fin:</label>
            <input
              v-model="solicitudAbierta.fechaFin"
              type="date"
              class="form-control mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Tipo de Solicitud:</label>
            <select
              v-model="solicitudAbierta.tipoSolicitud"
              class="form-select mx-auto"
            >
              <option value="FC">Formación continuada</option>
              <option value="EX">Activación ampliada</option>
              <option value="PS">Prestación servicios unidad</option>
            </select>
          </div>

          <div v-if="solicitudAbierta.tipoSolicitud === 'FC'" class="mb-3">
            <label class="form-label">Tiempo máximo:</label>
            <input
              v-model.number="solicitudAbierta.tiempoMaximo"
              type="number"
              class="form-control mx-auto"
            />
          </div>
          <div v-if="solicitudAbierta.tipoSolicitud === 'FC'" class="mb-3">
            <label class="form-label">Escala:</label>
            <input
              v-model.number="solicitudAbierta.escala"
              type="text"
              class="form-control mx-auto"
            />
          </div>
          <div v-if="solicitudAbierta.tipoSolicitud === 'EX'" class="mb-3">
            <label class="form-label">Motivo:</label>
            <textarea
              v-model="solicitudAbierta.motivo"
              class="form-control mx-auto"
            ></textarea>
          </div>
          <div v-if="solicitudAbierta.tipoSolicitud === 'PS'" class="mb-3">
            <label class="form-label">Tiempo máximo:</label>
            <input
              v-model="solicitudAbierta.tiempoMaximo"
              type="number"
              class="form-control mx-auto"
            />
          </div>

          <div class="d-flex justify-content-between">
            <button v-if="editando" type="submit" class="btn btn-primary">
              Guardar cambios
            </button>
            <button v-else type="submit" class="btn btn-success">
              Crear solicitud
            </button>
            <button
              v-if="editando"
              type="button"
              @click="eliminarSolicitud"
              class="btn btn-danger"
            >
              Eliminar
            </button>
          </div>
        </form>
      </div>
    </div>

    <div
      v-if="mostrarModal"
      class="modal fade show"
      tabindex="-1"
      style="display: block; background-color: rgba(0, 0, 0, 0.5)"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Información</h5>
            <button
              type="button"
              class="btn-close"
              @click="cerrarModal"
            ></button>
          </div>
          <div class="modal-body">
            <p>{{ mensajeModal }}</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              @click="cerrarModal"
            >
              Cerrar
            </button>
          </div>
        </div>
      </div>
    </div>

    <div
      v-if="mostrarModalReservistas"
      class="modal fade show"
      tabindex="-1"
      style="display: block; background-color: rgba(0, 0, 0, 0.5)"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Seleccionar Reservista</h5>
            <button
              type="button"
              class="btn-close"
              @click="mostrarModalReservistas = false"
            ></button>
          </div>
          <div class="modal-body">
            <ul class="list-group">
              <li
                v-for="reservista in reservistas"
                :key="reservista.id"
                class="list-group-item"
                @click="seleccionarReservista(reservista)"
              >
                {{ reservista.dni }}&nbsp; {{ reservista.empleo }}&nbsp;
                {{ reservista.apellido1 }}&nbsp;
                {{ reservista.apellido2 }},&nbsp;
                {{ reservista.nombre }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <div
      v-if="mostrarModalPocs"
      class="modal fade show"
      tabindex="-1"
      style="display: block; background-color: rgba(0, 0, 0, 0.5)"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Seleccionar POC</h5>
            <button
              type="button"
              class="btn-close"
              @click="mostrarModalPocs = false"
            ></button>
          </div>
          <div class="modal-body">
            <ul class="list-group">
              <li
                v-for="poc in pocs"
                :key="poc.id"
                class="list-group-item"
                @click="seleccionarPoc(poc)"
              >
                {{ poc.empleo }}&nbsp; {{ poc.apellido1 }}&nbsp;
                {{ poc.apellido2 }},&nbsp;
                {{ poc.nombre }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div
    v-if="mostrarModalUcos"
    class="modal fade show"
    tabindex="-1"
    style="display: block; background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Seleccionar UCO</h5>
          <button
            type="button"
            class="btn-close"
            @click="mostrarModalUcos = false"
          ></button>
        </div>
        <div class="modal-body">
          <ul class="list-group">
            <li
              v-for="uco in ucos"
              :key="uco.id"
              class="list-group-item"
              @click="seleccionarUco(uco)"
            >
              {{ uco.nombreUco }}&nbsp; (CIU: {{ uco.ciu }})
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
  .card {
    margin: 20px auto;
    max-width: 600px;
  }
</style>
