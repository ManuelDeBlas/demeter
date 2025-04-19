<script>
import { useSolicitudesStore } from "@/stores/solicitudes";
import { mapActions } from "pinia";

export default {
  name: "FormularioSolicitud",
  computed: {
    solicitudAbierta() {
      return useSolicitudesStore().elementoAbierto;
    },
  },
  methods: {
    ...mapActions(useSolicitudesStore, [
      "recuperarElemento",
      "anadirElemento",
      "eliminarElemento",
      "editarElemento",
    ]),
    enviarFormulario() {
      if (this.solicitudAbierta._links.self.href) {
        this.editarElemento(this.solicitudAbierta);
      } else {
        this.anadirElemento(this.solicitudAbierta);
      }
      this.solicitudAbierta = null;
      this.$router.push({ path: "/listado/solicitudes" });
    },
  },
};
</script>

<template>
  <div>
    <h2>Editar Solicitud</h2>
    <form @submit.prevent="enviarFormulario">
      <div>
        <label>Nombre UCOfdsfs: {{ solicitudAbierta }}</label>
        <input v-model="solicitudAbierta.nombreUco" type="text" />{{
          solicitudAbierta.nombreUco
        }}
      </div>
      <div>
        <label>CIU:</label>
        <input v-model="solicitudAbierta.ciu" type="text" />
      </div>

      <div>
        <label>Estado:</label>
        <input v-model="solicitudAbierta.estado" type="text" />
      </div>

      <div>
        <label>Reservista:</label>
        <input v-model="solicitudAbierta.reservista" type="text" />
      </div>

      <div>
        <label>POC:</label>
        <input v-model="solicitudAbierta.poc" type="text" />
      </div>

      <div>
        <label>Fecha Inicio:</label>
        <input v-model="solicitudAbierta.fechaInicio" type="date" />
      </div>

      <div>
        <label>Fecha Fin:</label>
        <input v-model="solicitudAbierta.fechaFin" type="date" />
      </div>

      <div>
        <label>Tipo de Solicitud:</label>
        <input v-model="solicitudAbierta.tipoSolicitud" type="text" />
      </div>

      <div>
        <label>Tiempo Máximo:</label>
        <input v-model.number="solicitudAbierta.tiempoMaximo" type="number" />
      </div>

      <div>
        <label>Escala:</label>
        <input v-model="solicitudAbierta.escala" type="text" />
      </div>

      <button type="submit">Guardar Cambios</button>
    </form>
  </div>
</template>
