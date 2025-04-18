<script>
  export default {
    props: ["tipoListado" ,"elemento"],
    methods: {
      abrirElemento() {
        const tiposListado = {
          solicitudes: "FormularioSolicitud",
          expedientes: "FormularioExpediente",
        }
        const nombreFormulario = tiposListado[this.tipoListado];
        console.log("Se abre el elemento", this.elemento);
        console.log("Tipo de listado", this.tipoListado);
        this.$router.push({
          name: tiposListado[this.tipoListado],
          params: {
            elemento: this.elemento
          },
        });
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">Elemento ??? {{ tipoListado }}</div>
    <div class="card-body">
      <div class="container text-center">
        <div class="row justify-content-between align-items-center">
          <div class="col-md-6 text-start">
            <h5 class="mb-0">CIU: {{ elemento.ciu }}</h5>
            <p class="mb-0">Estado: <strong>{{ elemento.estado }}</strong></p>
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
