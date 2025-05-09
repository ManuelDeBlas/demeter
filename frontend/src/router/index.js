import { createRouter, createWebHashHistory } from "vue-router";
import { getListadoConfig } from "@/router/listadoConfig";

const routes = [
  { path: "/", redirect: "/inicio" },
  {
    path: "/inicio",
    component: () => import("@/views/InicioView.vue"),
    name: "inicio",
  },
  {
    path: "/listado/:tipoListado",
    component: () => import("@/views/ListadoView.vue"),
    props: (route) => ({
      tipoListado: route.params.tipoListado,
      config: getListadoConfig(route.params.tipoListado),
    }),
  },
    {
    path: "/formulario/reservistas",
    name: "FormularioReservistaView",
    component: () => import("@/views/FormularioReservistaView.vue"),
  },
      {
    path: "/formulario/pocs",
    name: "FormularioPocView",
    component: () => import("@/views/FormularioPocView.vue"),
  },
  {
    path: "/formulario/solicitudes",
    name: "FormularioSolicitudView",
    component: () => import("@/views/FormularioSolicitudView.vue"),
  },
  {
    path: "/formulario/expedientes",
    name: "FormularioExpedienteView",
    component: () => import("@/views/FormularioExpedienteView.vue"),
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import("@/views/NotFoundView.vue"),
    name: "notfound",
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
