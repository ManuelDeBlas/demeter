import expedientesJSON from "@/assets/expedientes.json";
import { crearStore } from "@/stores/fabricaStore";

export const useExpedientesStore = crearStore(
  "expedientes",
  expedientesJSON._embedded.expedientes,
  "http://api-demeter/api/expedientes"
);