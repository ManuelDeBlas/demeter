<script>
  import { useReservistasStore } from "@/stores/reservistas";
  import { mapActions } from "pinia";

  export default {
    name: "FormularioReservistaView",
    data() {
      return {
        editando: true,
        mostrarModal: false,
        mensajeModal: "",
      };
    },
    computed: {
      reservistaAbierto: {
        get() {
          if (!useReservistasStore().elementoAbierto) {
            this.editando = false;
            useReservistasStore().elementoAbierto = {
              nombre: "",
              apellido1: "",
              apellido2: "",
              empleo: "",
              dni: "",
              telefonoParticular: "",
              fechaFinCompromiso: "",
              diasConsumidos: 0,
              localidadResidencia: "",
              subdelegacionDefensa: "",
            };
          }
          return useReservistasStore().elementoAbierto;
        },
        set(value) {
          useReservistasStore().elementoAbierto = value;
        },
      },
    },
    methods: {
      ...mapActions(useReservistasStore, [
        "anadirElemento",
        "editarElemento",
        "eliminarElemento",
      ]),
      async enviarFormulario() {
        try {
          if (this.editando) {
            let respuesta = await this.editarElemento(this.reservistaAbierto);
            this.mensajeModal = `Reservista editado correctamente`;
          } else {
            this.reservistaAbierto;
            let respuesta = await this.anadirElemento(this.reservistaAbierto);
            this.mensajeModal = `Reservista añadido correctamente`;
          }
        } catch (error) {
          this.mensajeModal = `Error al procesar la solicitud: ${error.message}`;
        } finally {
          this.mostrarModal = true;
        }
      },
      cerrarModal() {
        this.mostrarModal = false;
        this.$router.push({ path: "/listado/reservistas" });
      },
    },
  };
</script>

<template>
  <div
    class="formulario-con-fondo d-flex justify-content-center align-items-center"
  >
    <div class="card text-center card-ancha">
      <div class="card-header fw-bold fs-5">
        <h2 v-if="editando">Editar reservista</h2>
        <h2 v-else>Crear nuevo reservista</h2>
      </div>
      <div class="card-body">
        <form @submit.prevent="enviarFormulario">
          <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input
              v-model="reservistaAbierto.nombre"
              type="text"
              class="form-control w-50 mx-auto"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Primer Apellido:</label>
            <input
              v-model="reservistaAbierto.apellido1"
              type="text"
              class="form-control w-50 mx-auto"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Segundo Apellido:</label>
            <input
              v-model="reservistaAbierto.apellido2"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Empleo:</label>
            <input
              v-model="reservistaAbierto.empleo"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">DNI:</label>
            <input
              v-model="reservistaAbierto.dni"
              type="text"
              class="form-control w-50 mx-auto"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Teléfono Particular:</label>
            <input
              v-model="reservistaAbierto.telefonoParticular"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Fecha Fin Compromiso:</label>
            <input
              v-model="reservistaAbierto.fechaFinCompromiso"
              type="date"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Días Consumidos:</label>
            <input
              v-model.number="reservistaAbierto.diasConsumidos"
              type="number"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Localidad de Residencia:</label>
            <input
              v-model="reservistaAbierto.localidadResidencia"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Subdelegación de Defensa:</label>
            <input
              v-model="reservistaAbierto.subdelegacionDefensa"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="d-flex justify-content-between">
            <button v-if="editando" type="submit" class="btn btn-primary">
              Guardar cambios
            </button>
            <button v-else type="submit" class="btn btn-success">
              Crear reservista
            </button>
            <!-- <button
              v-if="editando"
              type="button"
              class="btn btn-danger mt-2"
              @click="eliminarElemento(this.reservistaAbierto)"
            >
              Eliminar Reservista
            </button> -->
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- Modal -->
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

<style scoped>
  .card {
    margin: 20px auto;
    max-width: 600px;
  }
</style>
