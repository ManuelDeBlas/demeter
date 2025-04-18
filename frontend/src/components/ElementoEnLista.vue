<script>
  export default {
    props: ["elemento"],
    emits: [
      "editar-elemento"
    ],
    methods: {
      editarElemento() {
        this.$emit("editar-elemento", this.elemento);
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">Elemento ???</div>
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
                <span class="me-2">Editar</span>
                <font-awesome-icon
                  :icon="['fas', 'pen-to-square']"
                  size="lg"
                  @click="editarElemento"
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
