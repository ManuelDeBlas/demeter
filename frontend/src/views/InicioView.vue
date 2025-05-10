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

<style scoped>
.inicio-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(135deg, #3d7275, #81c784);
  color: white;
  text-align: center;
  font-family: Arial, sans-serif;
}

.header h1 {
  font-size: 3rem;
  margin: 0;
}

.subtitle {
  font-size: 1.5rem;
  margin: 10px 0 20px;
}

.actions button {
  background-color: #ffffff;
  color: #4caf50;
  border: none;
  padding: 10px 20px;
  margin: 10px;
  font-size: 1rem;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.actions button:hover {
  background-color: #4caf50;
  color: white;
}
</style>
