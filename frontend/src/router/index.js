import Inicio from "@/components/Inicio.vue";
import { createRouter, createWebHashHistory } from "vue-router";
import { getListadoConfig } from "@/router/listadoConfig";

const NotFound = () => import("@/components/NotFound.vue");
const Listado = () => import("@/components/Listado.vue");

const routes = [
  { path: "/", redirect: "/inicio" },
  { path: "/inicio", component: Inicio, name: "inicio" },
  {
    path: "/listado/:tipoListado",
    component: Listado,
    props: (route) => ({
      tipoListado: route.params.tipoListado,
      config: getListadoConfig(route.params.tipoListado),
    }),
  },
  { path: "/:pathMatch(.*)*", component: NotFound, name: "notfound" },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
