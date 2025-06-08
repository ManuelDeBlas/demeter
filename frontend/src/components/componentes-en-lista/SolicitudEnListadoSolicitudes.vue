<script>
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { formatearAtributoEnElFrontend, formatearFecha, formatearCentimosAEuros } from "@/utils/utils";

  export default {
    props: ["solicitud"],
    methods: {
      formatearAtributoEnElFrontend,
      formatearFecha,
      formatearCentimosAEuros,
      abrirFormularioSolicitud() {
        useSolicitudesStore().elementoAbierto = this.solicitud; // Guarda el elemento en el store para editarlo
        this.$router.push({ name: "FormularioSolicitudView" });
      },
      abrirFormularioSolicitudConsulta() {
        useSolicitudesStore().consultando = true;
        useSolicitudesStore().elementoAbierto = this.solicitud; // Guarda el elemento en el store para consultarlo
        this.$router.push({ name: "FormularioSolicitudView" });
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-body">
      <div class="row justify-content-between align-items-center">
        <div class="col-md-6 text-start">
          <div class="mb-0">
            {{ solicitud.nombreUco }}
            {{ solicitud.reservista.dni }}
            <strong>
              {{ solicitud.reservista.empleo }}
              {{ solicitud.reservista.nombre }}
              {{ solicitud.reservista.apellido1 }}
              {{ solicitud.reservista.apellido2 }}</strong
            >
            Duración: {{ formatearFecha(solicitud.fechaInicio) }} -
            {{ formatearFecha(solicitud.fechaFin) }} Coste:
            {{ formatearCentimosAEuros(solicitud.costeCentimos) }}
          </div>
        </div>
        <div class="col-md-6 text-end">
          <div class="d-flex justify-content-end align-items-center gap-3">
            <div class="d-flex align-items-center">
              <button type="button" class="btn btn-info" @click="abrirFormularioSolicitudConsulta">Consultar</button>
              <button type="button" class="btn btn-primary ms-2" @click="abrirFormularioSolicitud">Modificar</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
