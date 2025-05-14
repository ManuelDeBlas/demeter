<script>
  import { useReservistasStore } from "@/stores/reservistas";

  export default {
    props: ["reservista"],
    methods: {
      abrirElemento() {
        useReservistasStore().elementoAbierto = this.reservista; // Guarda el elemento en el store para editarlo
        this.$router.push({ path: "/formulario/reservista" });
      },
    },
  };
</script>

<template>
  <div class="card text-center">
    <div class="card-header fw-bold fs-5">Reservista</div>
    <div class="card-body">
      <div class="container text-center">
        <div class="row justify-content-between align-items-center">
          <div class="col-md-6 text-start">
            <div class="mb-0">
              <strong>
                {{ reservista.empleo }}
                {{ reservista.nombre }}
                {{ reservista.apellido1 }}
                {{ reservista.apellido2 }}</strong
              ><br />
              DNI: {{ reservista.dni }}<br />
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
