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
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">Solicitud</div>
    <div class="card-body">
      <div class="container text-center">
        <div class="row justify-content-between align-items-center">
          <div class="col-md-6 text-start">
            <div class="mb-0">
              DNI: {{ solicitud.reservista.dni }}<br />
              <strong>
                {{ solicitud.reservista.empleo }}
                {{ solicitud.reservista.nombre }}
                {{ solicitud.reservista.apellido1 }}
                {{ solicitud.reservista.apellido2 }}</strong
              ><br />
              Estado:
              <strong>{{
                formatearAtributoEnElFrontend(solicitud.estado)
              }}</strong
              ><br />
              Tipo: {{ formatearAtributoEnElFrontend(solicitud.tipoSolicitud) }}
            </div>
          </div>
          <div class="col-md-6 text-end">
            <div class="d-flex justify-content-end align-items-center gap-3">
              <div class="d-flex align-items-center">
                <span class="me-2">Abrir</span>
                <font-awesome-icon
                  :icon="['fas', 'magnifying-glass']"
                  size="lg"
                  @click="abrirFormularioSolicitud"
                  class="text-primary cursor-pointer"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
