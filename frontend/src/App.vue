<script>
  import NavBar from "@/components/navegacion/NavBar.vue";

  export default {
    components: { NavBar },
  };
</script>
<template>
  <div class="app-container">
    <NavBar></NavBar>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>
