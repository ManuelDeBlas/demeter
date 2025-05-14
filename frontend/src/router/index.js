import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  { path: "/", redirect: "/inicio" },
  {
    path: "/inicio",
    name: "inicio",
    component: () => import("@/views/InicioView.vue"),
  },
  {
    path: "/listado/solicitudes",
    name: "ListadoSolicitudesView",
    component: () => import("@/views/listados/ListadoSolicitudesView.vue"),
  },
  {
    path: "/listado/reservistas",
    name: "ListadoReservistasView",
    component: () => import("@/views/listados/ListadoReservistasView.vue"),
  },
  {
    path: "/listado/pocs",
    name: "ListadoPocsView",
    component: () => import("@/views/listados/ListadoPocsView.vue"),
  },
  {
    path: "/listado/expedientes",
    name: "ListadoExpedientesView",
    component: () => import("@/views/listados/ListadoExpedientesView.vue"),
  },
    {
    path: "/formulario/reservista",
    name: "FormularioReservistaView",
    component: () => import("@/views/formularios/FormularioReservistaView.vue"),
  },
      {
    path: "/formulario/poc",
    name: "FormularioPocView",
    component: () => import("@/views/formularios/FormularioPocView.vue"),
  },
  {
    path: "/formulario/solicitud",
    name: "FormularioSolicitudView",
    component: () => import("@/views/formularios/FormularioSolicitudView.vue"),
  },
  {
    path: "/formulario/expediente",
    name: "FormularioExpedienteView",
    component: () => import("@/views/formularios/FormularioExpedienteView.vue"),
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
