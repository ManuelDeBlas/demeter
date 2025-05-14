<!-- filepath: c:\Users\Manuelo\git\demeter\frontend\src\views\FormularioPocView.vue -->
<script>
  import { mapActions } from "pinia";
  import { usePocsStore } from "@/stores/pocs.js";

  export default {
    name: "FormularioPocView",
    data() {
      return {
        editando: true,
        mostrarModal: false,
        mensajeModal: "",
      };
    },
    computed: {
      pocAbierto: {
        get() {
          if (!this.store.elementoAbierto) {
            this.editando = false;
            this.store.elementoAbierto = {
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
          return this.store.elementoAbierto;
        },
        set(value) {
          this.store.elementoAbierto = value;
        },
      },
    },
    methods: {
      ...mapActions(usePocsStore, [
        "anadirElemento",
        "editarElemento",
        "eliminarElemento",
      ]),
      async enviarFormulario() {
        try {
          let respuesta;
          if (this.editando) {
            respuesta = await this.editarElemento(this.pocAbierto);
            this.mensajeModal = `POC editado correctamente`;
          } else {
            respuesta = await this.anadirElemento(this.pocAbierto);
            this.mensajeModal = `POC añadido correctamente`;
          }
        } catch (error) {
          this.mensajeModal = `Error al procesar la solicitud: ${error.message}`;
        } finally {
          this.mostrarModal = true;
        }
      },
      cerrarModal() {
        this.mostrarModal = false;
        this.$router.push({ path: "/listado/pocs" });
      },
    },
    created() {
      this.store = usePocsStore();
    },
  };
</script>

<template>
  <div
    class="formulario-con-fondo d-flex justify-content-center align-items-center"
  >
    <div class="card text-center card-ancha">
      <div class="card-header fw-bold fs-5">
        <h2 v-if="editando">Editar POC</h2>
        <h2 v-else>Crear nuevo POC</h2>
      </div>
      <div class="card-body">
        <form @submit.prevent="enviarFormulario">
          <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input
              v-model="pocAbierto.nombre"
              type="text"
              class="form-control w-50 mx-auto"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Primer Apellido:</label>
            <input
              v-model="pocAbierto.apellido1"
              type="text"
              class="form-control w-50 mx-auto"
              required
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Segundo Apellido:</label>
            <input
              v-model="pocAbierto.apellido2"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Empleo:</label>
            <input
              v-model="pocAbierto.empleo"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">UCO Destino:</label>
            <input
              v-model="pocAbierto.ucoDestino"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Teléfono Corporativo:</label>
            <input
              v-model="pocAbierto.telefonoCorporativo"
              type="text"
              class="form-control w-50 mx-auto"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Email Corporativo:</label>
            <input
              v-model="pocAbierto.emailCorporativo"
              type="email"
              class="form-control w-50 mx-auto"
              required
            />
          </div>
          <div class="d-flex justify-content-between">
            <button v-if="editando" type="submit" class="btn btn-primary">
              Guardar cambios
            </button>
            <button v-else type="submit" class="btn btn-success">
              Crear POC
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
