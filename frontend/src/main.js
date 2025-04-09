import { createApp } from "vue";
import App from "@/App.vue";
import Inicio from "@/components/Inicio.vue";
import { createRouter, createWebHashHistory } from "vue-router";
import { createPinia } from "pinia";

// Import our custom CSS
import "@/scss/styles.scss";

// Import all of Bootstrap's JS
import * as bootstrap from "bootstrap";

const NotFound = () => import("@/components/NotFound.vue");
const ListaSolicitudes = () => import("@/components/ListaSolicitudes.vue");

const routes = [
  { path: "/", redirect: "/inicio" },
  { path: "/inicio", component: Inicio, name: "inicio" },
  {
    path: "/listasolicitudes",
    component: ListaSolicitudes,
    name: "listasolicitudes",
  },
  { path: "/:pathMatch(.*)*", component: NotFound, name: "notfound" },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

const app = createApp(App);
app.use(router);
const pinia = createPinia();

app.use(pinia);
app.mount("#app");
