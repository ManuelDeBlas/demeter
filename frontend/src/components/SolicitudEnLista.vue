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
    <div class="card-header"> Solicitud</div>
    <div class="card-body">
      <div class="container text-center">
        <div class="row justify-content-end">
          <div class="col-4 text-nowrap">
            <h3>CIU: {{ solicitud.ciu }}. Estado: {{ solicitud.estado }}</h3>
          </div>
          <div class="col-6">
            Editar
            <font-awesome-icon
              :icon="['fas', 'pen-to-square']"
              size="xl"
              @click="editarSolicitud"
            />
            Modificar estado
            <font-awesome-icon
              :icon="['fas', 'pen-to-square']"
              size="xl"
              @click="modificarEstadoSolicitud"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>