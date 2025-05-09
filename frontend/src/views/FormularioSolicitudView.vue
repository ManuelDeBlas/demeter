<script>
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { mapActions } from "pinia";

  export default {
    name: "FormularioSolicitudView",
    data() {
      return {
        editando: true,
        mostrarModal: false,
        mensajeModal: "",
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
              estado: "",
              reservista: "",
              poc: "",
              fechaInicio: "",
              fechaFin: "",
              tipoSolicitud: "",
            };
          }
          return this.store.elementoAbierto;
        },
        set(value) {
          this.store.elementoAbierto = value;
        },
      },
    },
    methods: {
      ...mapActions(useSolicitudesStore, [
        "anadirElemento",
        "editarElemento",
        "eliminarElemento",
      ]),
      async enviarFormulario() {
        try {
          if (this.editando) {
            await this.editarElemento(this.solicitudAbierta);
            this.mensajeModal = `Solicitud editada correctamente`;
          } else {
            await this.anadirElemento(this.solicitudAbierta);
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
    created() {
      this.store = useSolicitudesStore();
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">
      <h2 v-if="editando">Editar solicitud</h2>
      <h2 v-else>Crear nueva solicitud</h2>
    </div>
    <div class="card-body">
      <form @submit.prevent="enviarFormulario">
        <div class="mb-3">
          <label class="form-label">Nombre UCO:</label>
          <input
            v-model="solicitudAbierta.nombreUco"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">CIU:</label>
          <input
            v-model="solicitudAbierta.ciu"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Estado:</label>
          <input
            v-model="solicitudAbierta.estado"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Reservista:</label>
          <input
            v-model="solicitudAbierta.reservista"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">POC:</label>
          <input
            v-model="solicitudAbierta.poc"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Fecha Inicio:</label>
          <input
            v-model="solicitudAbierta.fechaInicio"
            type="date"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Fecha Fin:</label>
          <input
            v-model="solicitudAbierta.fechaFin"
            type="date"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div class="mb-3">
          <label class="form-label">Tipo de Solicitud:</label>
          <select
            v-model="solicitudAbierta.tipoSolicitud"
            class="form-select w-50 mx-auto"
          >
            <option value="formaciones-continuadas">
              Formación continuada
            </option>
            <option value="activaciones-ampliadas">Activación ampliada</option>
            <option value="prestaciones-servicios-unidad">
              Prestación servicios unidad
            </option>
          </select>
        </div>

        <div
          v-if="solicitudAbierta.tipoSolicitud === 'formaciones-continuadas'"
          class="mb-3"
        >
          <label class="form-label">Tiempo máximo:</label>
          <input
            v-model.number="solicitudAbierta.tiempoMaximo"
            type="number"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div
          v-if="solicitudAbierta.tipoSolicitud === 'formaciones-continuadas'"
          class="mb-3"
        >
          <label class="form-label">Escala:</label>
          <input
            v-model.number="solicitudAbierta.escala"
            type="text"
            class="form-control w-50 mx-auto"
          />
        </div>
        <div
          v-if="solicitudAbierta.tipoSolicitud === 'activaciones-ampliadas'"
          class="mb-3"
        >
          <label class="form-label">Motivo:</label>
          <textarea
            v-model="solicitudAbierta.motivo"
            class="form-control w-50 mx-auto"
          ></textarea>
        </div>
        <div
          v-if="
            solicitudAbierta.tipoSolicitud === 'prestaciones-servicios-unidad'
          "
          class="mb-3"
        >
          <label class="form-label">Tiempo máximo:</label>
          <input
            v-model="solicitudAbierta.tiempoMaximo"
            type="number"
            class="form-control w-50 mx-auto"
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
          <button type="button" class="btn-close" @click="cerrarModal"></button>
        </div>
        <div class="modal-body">
          <p>{{ mensajeModal }}</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" @click="cerrarModal">
            Cerrar
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
