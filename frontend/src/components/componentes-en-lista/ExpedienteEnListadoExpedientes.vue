<script>
  import { useExpedienteStore } from "@/stores/expedientes";

  export default {
    props: ["expediente"],
    methods: {
      abrirElemento() {
        this.store.elementoAbierto = this.expediente; // Guarda el elemento en el store para editarlo
        this.$router.push({
          name: "FormularioExpedienteView",
        });
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">Expediente</div>
    <div class="card-body">
      <div class="container text-center">
        <div class="row justify-content-between align-items-center">
          <div class="col-md-6 text-start">
            <div class="mb-0">
              {{ elemento.numeroExpediente }}
            </div>
            <div class="mb-0">
              Estado: <strong>{{ elemento.estado }}</strong> Tipo:
              {{ elemento.tipoSolicitud }}
            </div>
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
