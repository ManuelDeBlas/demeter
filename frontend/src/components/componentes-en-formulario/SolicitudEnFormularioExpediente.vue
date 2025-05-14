<script>
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { formatearAtributoEnElFrontend } from "@/utils/utils";

  export default {
    props: ["solicitud"],
    methods: {
      abrirFormularioSolicitud() {
        useSolicitudesStore().elementoAbierto = this.solicitud; // Guarda el elemento en el store para editarlo
        this.$router.push({ name: "FormularioSolicitudView" });
      },
      formatearAtributoEnElFrontend,
    },
  };
</script>

<template>
  <div class="card my-2 py-1">
    <div class="card-body">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-auto d-flex align-items-center">
            <div class="fw-bold fs-6">Solicitud</div>
          </div>
          <div class="col">
            <div class="text-start fs-6">
              DNI: {{ solicitud.reservista.dni }}
              <strong>
                {{ solicitud.reservista.empleo }}
                {{ solicitud.reservista.nombre }}
                {{ solicitud.reservista.apellido1 }}
                {{ solicitud.reservista.apellido2 }}
              </strong>
              <br />
              Desde {{ solicitud.fechaInicio }} hasta {{ solicitud.fechaFin }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
