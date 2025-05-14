<script>
  import { useSolicitudesStore } from "@/stores/solicitudes";
  import { formatearAtributoEnElFront } from "@/utils/utils";

  export default {
    props: ["solicitud"],
    methods: {
      abrirFormularioSolicitud() {
        useSolicitudesStore().elementoAbierto = this.solicitud; // Guarda el elemento en el store para editarlo
        this.$router.push({ name: "FormularioSolicitudView" });
      },
      formatearAtributoEnElFront,
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
              DNI:
              <strong>{{ solicitud.reservista.dni }}&nbsp; </strong> &nbsp;
              {{ solicitud.reservista.empleo }} &nbsp;
              {{ solicitud.reservista.apellido1 }} &nbsp;
              {{ solicitud.reservista.apellido2 }}
              &nbsp;
              {{ solicitud.reservista.nombre }}&nbsp; Estado:
              <strong>{{
                formatearAtributoEnElFront(solicitud.estado)
              }}</strong>
              Tipo:
              {{ formatearAtributoEnElFront(solicitud.tipoSolicitud) }}
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
