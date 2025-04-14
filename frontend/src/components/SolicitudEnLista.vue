<script>
  export default {
    props: ["solicitud"],
    emits: [
      "editar-solicitud",
      "modificar-estado-solicitud",
    ],
    methods: {
      editarSolicitud() {
        this.$emit("editar-solicitud", this.solicitud);
      },
      modificarEstadoSolicitud() {
        this.$emit("modificar-estado-solicitud", this.solicitud);
      },
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
            <h5 class="mb-0">CIU: {{ solicitud.ciu }}</h5>
            <p class="mb-0">Estado: <strong>{{ solicitud.estado }}</strong></p>
          </div>
          <div class="col-md-6 text-end">
            <div class="d-flex justify-content-end align-items-center gap-3">
              <div class="d-flex align-items-center">
                <span class="me-2">Editar</span>
                <font-awesome-icon
                  :icon="['fas', 'pen-to-square']"
                  size="lg"
                  @click="editarSolicitud"
                  class="text-primary cursor-pointer"
                />
              </div>
              <div class="d-flex align-items-center">
                <span class="me-2">Modificar estado</span>
                <font-awesome-icon
                  :icon="['fas', 'pen-to-square']"
                  size="lg"
                  @click="modificarEstadoSolicitud"
                  class="text-success cursor-pointer"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
