<script>
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { useReservistasStore } from "@/stores/reservistas";
  import { useUcosStore } from "@/stores/ucos";
  import { mapActions } from "pinia";

  export default {
    name: "FormularioSolicitudView",
    data() {
      return {
        editando: true,
        mostrarModal: false,
        mostrarModalReservistas: false,
        mostrarModalUcos: false,
        mostrarModalConfirmacion: false,
        mensajeModal: "",
        uco: null,
        reservista: null,
        inicio: null,
        fin: null,
      };
    },
    computed: {
      solicitudAbierta: {
        get() {
          if (!useSolicitudesStore().elementoAbierto) {
            this.editando = false;
            useSolicitudesStore().elementoAbierto = {
              nombreUco: "",
              ciu: "",
              reservista: null,
              fechaInicio: "",
              fechaFin: "",
              tipoSolicitud: "",
              estado: "PENDIENTE_EVALUACION",
            };
          }
          return useSolicitudesStore().elementoAbierto;
        },
        set(value) {
          useSolicitudesStore().elementoAbierto = value;
        },
      },
      reservistas() {
        return useReservistasStore().elementos;
      },
      ucos() {
        return useUcosStore().ucos;
      },
      reservistaFormateado() {
        return this.solicitudAbierta.reservista
          ? `${this.solicitudAbierta.reservista.dni} ${this.solicitudAbierta.reservista.empleo} ${this.solicitudAbierta.reservista.nombre} ${this.solicitudAbierta.reservista.apellido1} ${this.solicitudAbierta.reservista.apellido2}`
          : "";
      },
    },
    methods: {
      ...mapActions(useSolicitudesStore, [
        "putSolicitud",
        "eliminarElemento",
        "postSolicitud",
      ]),
      seleccionarUco(uco) {
        this.solicitudAbierta.nombreUco = uco.nombreUco;
        this.solicitudAbierta.ciu = uco.ciu;
        this.mostrarModalUcos = false;
      },
      seleccionarReservista(reservista) {
        this.reservista = reservista;
        this.solicitudAbierta.reservista = reservista;
        this.mostrarModalReservistas = false;
      },
      async enviarFormulario() {
        try {
          this.inicio = new Date(this.solicitudAbierta.fechaInicio);
          this.fin = new Date(this.solicitudAbierta.fechaFin);
          if (this.inicio.getFullYear() !== this.fin.getFullYear()) {
            this.mostrarModalConfirmacion = true;
            return;
          }
          if (this.editando) {
            const respuesta = await this.putSolicitud(this.solicitudAbierta);
            this.mensajeModal = `Solicitud editada correctamente`;
          } else {
            const respuesta = await this.postSolicitud(this.solicitudAbierta);
            this.mensajeModal = `Solicitud añadida correctamente`;
          }
        } catch (error) {
          this.mensajeModal = `Error al procesar la solicitud: ${error.message}`;
        } finally {
          this.mostrarModal = true;
          this.envioPendiente = false;
        }
      },
      async enviarDosSolicitudes() {
        this.mostrarModalConfirmacion = false;

        try {
          const finPrimerTramo = new Date(this.inicio.getFullYear(), 11, 32);
          const inicioSegundoTramo = new Date(this.fin.getFullYear(), 0, 2);

          const solicitud1 = { ...this.solicitudAbierta };
          const solicitud2 = { ...this.solicitudAbierta };

          solicitud1.fechaInicio = this.inicio.toISOString().split("T")[0];
          solicitud1.fechaFin = finPrimerTramo.toISOString().split("T")[0];

          solicitud2.fechaInicio = inicioSegundoTramo
            .toISOString()
            .split("T")[0];
          solicitud2.fechaFin = this.fin.toISOString().split("T")[0];

          console.log("solicitiudes", solicitud1, solicitud2);
          if (this.editando) {
            await this.putSolicitud(solicitud1);
            await this.putSolicitud(solicitud2);
            this.mensajeModal = "Solicitudes editadas correctamente.";
          } else {
            await this.postSolicitud(solicitud1);
            await this.postSolicitud(solicitud2);
            this.mensajeModal = "Solicitudes añadidas correctamente.";
          }
        } catch (error) {
          this.mensajeModal = `Error al procesar las solicitudes: ${error.message}`;
        } finally {
          this.mostrarModal = true;
          this.envioPendiente = false;
        }
      },
      cancelarEnvio() {
        this.mostrarModalConfirmacion = false;
        this.envioPendiente = false;
      },
      async eliminarSolicitud() {
        try {
          await this.eliminarElemento(this.solicitudAbierta);
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
  };
</script>

<template>
  <div>{{ solicitudAbierta }}</div>
  <div>{{ editando }}</div>
  <div
    class="formulario-con-fondo d-flex justify-content-center align-items-center"
  >
    <div class="card text-center card-ancha">
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

  <div
    v-if="mostrarModalConfirmacion"
    class="modal fade show"
    tabindex="-1"
    style="display: block; background-color: rgba(0, 0, 0, 0.5)"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">Confirmar envío</h5>
          <button
            type="button"
            class="btn-close"
            @click="cancelarEnvio"
          ></button>
        </div>
        <div class="modal-body">
          <p>
            Las fechas de inicio y fin no están en el mismo año natural. Si
            desea continuar, se crearán dos solicitudes independientes
            transcurriendo cada una durante su propio año natural. ¿Desea
            continuar?
          </p>
        </div>
        <div class="modal-footer">
          <button
            type="button"
            class="btn btn-secondary"
            @click="cancelarEnvio"
          >
            Cancelar
          </button>
          <button
            type="button"
            class="btn btn-primary"
            @click="enviarDosSolicitudes"
          >
            Continuar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
