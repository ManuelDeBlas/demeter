<script>
  export default {
    props: ["elemento"],
    methods: {
      abrirElemento() {
        this.store.elementoAbierto = this.elemento; // Guarda el elemento en el store para editarlo
        this.$router.push({
          name: "nombreFormulario", // Cambia esto por el nombre del formulario correspondiente
        });
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
              <div class="mb-0" v-if="tipoListado === 'solicitudes'">
                DNI:
                <strong>{{ elemento.reservista.dni }}&nbsp; </strong> &nbsp;
                {{ elemento.reservista.empleo }} &nbsp;
                {{ elemento.reservista.apellido1 }} &nbsp;
                {{ elemento.reservista.apellido2 }}
                &nbsp;
                {{ elemento.reservista.nombre }}&nbsp;
              </div>
              <div class="mb-0" v-if="tipoListado === 'expedientes'">
                {{ elemento.numeroExpediente }}
              </div>
              <div
                class="mb-0"
                v-if="
                  tipoListado === 'solicitudes' || tipoListado === 'expedientes'
                "
              >
                Estado: <strong>{{ elemento.estado }}</strong> Tipo:
                {{ elemento.tipoSolicitud }}
              </div>
              <div class="mb-0" v-if="tipoListado === 'pocs'">
                {{ elemento.empleo }}&nbsp; {{ elemento.apellido1 }}&nbsp;
                {{ elemento.apellido2 }},&nbsp;
                {{ elemento.nombre }}
              </div>
              <div class="mb-0" v-if="tipoListado === 'reservistas'">
                DNI: <strong>{{ elemento.dni }}&nbsp; </strong>
                {{ elemento.empleo }}&nbsp; {{ elemento.apellido1 }}&nbsp;
                {{ elemento.apellido2 }},&nbsp;
                {{ elemento.nombre }}
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