<script>
  export default {
    props: ["tipoListado", "config", "elemento"],
    methods: {
      abrirElemento() {
        const tiposFormularios = {
          solicitudes: "FormularioSolicitudView",
          expedientes: "FormularioExpedienteView",
          reservistas: "FormularioReservistaView",
          pocs: "FormularioPocView",
        };
        this.config.store().elementoAbierto = this.elemento; // Guarda el elemento en el store para editarlo
        this.$router.push({
          name: tiposFormularios[this.tipoListado],
        });
      },
      getNombreElemento(tipoListado) {
        const nombres = {
          solicitudes: "Solicitud",
          expedientes: "Expediente",
          reservistas: "Reservista",
          pocs: "POC",
        };
        return nombres[tipoListado] || "Indefinido";
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">
      {{ getNombreElemento(tipoListado) }}
    </div>
    <div class="card-body">
      <div class="container text-center">
        <div class="row justify-content-between align-items-center">
          <div class="col-md-6 text-start">
            <div
              class="mb-0"
              v-if="
                tipoListado === 'solicitudes' || tipoListado === 'expedientes'
              "
            >
              Estado: <strong>{{ elemento.estado }}</strong> Tipo:
              <strong>{{ elemento.tipoSolicitud }}</strong>
            </div>
            <p
              class="mb-0"
              v-if="tipoListado === 'reservistas' || tipoListado === 'pocs'"
            >
              <strong>{{ elemento.empleo }}&nbsp; </strong>
              <strong>{{ elemento.apellido1 }}&nbsp; </strong>
              <strong>{{ elemento.apellido2 }},&nbsp; </strong>
              <strong>{{ elemento.nombre }}</strong>
            </p>
          </div>
          <div class="col-md-6 text-end">
            <div class="d-flex justify-content-end align-items-center gap-3">
              <div class="d-flex align-items-center">
                <span class="me-2">Abrir</span>
                <font-awesome-icon
                  :icon="['fas', 'magnifying-glass']"
                  size="lg"
                  @click="abrirElemento"
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
