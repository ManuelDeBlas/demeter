import { createApp } from "vue";
import App from "@/App.vue";
import { createPinia } from "pinia";

// Import our custom CSS
import "@/scss/styles.scss";

// Import all of Bootstrap's JS
import * as bootstrap from "bootstrap";

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.mount("#app");
