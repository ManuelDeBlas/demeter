<script>
import { useSolicitudesStore } from "@/stores/solicitudes";
import { mapActions } from "pinia";

export default {
  name: "FormularioSolicitudView",
  data() {
    return {
      editando: true,
    };
  },
  computed: {
    solicitudAbierta() {
      let solicitud = useSolicitudesStore().elementoAbierto;
      if (solicitud === null) {
        solicitud = {
          nombreUco: "",
          ciu: "",
          estado: "",
          reservista: "",
          poc: "",
          fechaInicio: "",
          fechaFin: "",
          tipoSolicitud: "",
        };
        this.editando = false;
      }
      return solicitud;
    },
  },
  methods: {
    ...mapActions(useSolicitudesStore, [
      "anadirElemento",
      "editarElemento",
      "eliminarElemento",
    ]),
    enviarFormulario() {
      if (this.editando) {
        this.editarElemento(this.solicitudAbierta);
      } else {
        this.anadirElemento(this.solicitudAbierta);
      }
      this.$router.push({ path: "/listado/solicitudes" });
    },
    eliminarSolicitud() {
      this.eliminarElemento(this.solicitudAbierta._links.self.href);
      this.$router.push({ path: "/listado/solicitudes" });
    },
  },
};
</script>

<template>
  <div>{{ solicitudAbierta }}</div>
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
            <option value="Formación continuada">Formación continuada</option>
            <option value="Activación ampliada">Activación ampliada</option>
            <option value="Prestación servicios unidad">
              Prestación servicios unidad
            </option>
          </select>
        </div>

        <div
          v-if="solicitudAbierta.tipoSolicitud === 'Formación continuada'"
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
          v-if="solicitudAbierta.tipoSolicitud === 'Formación continuada'"
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
          v-if="solicitudAbierta.tipoSolicitud === 'Activación ampliada'"
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
            solicitudAbierta.tipoSolicitud === 'Prestación servicios unidad'
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
</template>
