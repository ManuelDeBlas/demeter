<script>
export default {
  name: "InicioView",
  methods: {
    irASolicitudes() {
      this.$router.push("listado/solicitudes");
    },
  },
};
</script>

<template>
  <div class="inicio-container">
    <div class="header">
      <h1>Demeter</h1>
      <p class="subtitle">Gestión de reservistas del ET</p>
    </div>
    <div class="actions">
      <button @click="irASolicitudes">Ver Solicitudes</button>
    </div>
  </div>
</template>
