import solicitudesJSON from "@/assets/solicitudes.json";
import { crearStore } from "@/stores/fabricaStore";

export const useSolicitudesStore = crearStore(
  "solicitudes",
  solicitudesJSON._embedded.solicitudes,
  "http://api-demeter/api/solicitudes"  // TODO extraer la URL de la API
);
