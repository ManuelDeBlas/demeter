import { crearStore } from "@/stores/fabricaStore";
import { putEntidad } from "@/stores/api-service";

export const useExpedientesStore = crearStore(
  "expedientes",
  {
    agregarSolicitudAExpediente(solicitud) {
      console.log('Agregando solicitud al expediente:', solicitud);
      putEntidad(
      console.log("Solicitud agregada correctamente.");
    },
    eliminarSolicitudDeExpediente(solicitud) {
      console.log('Eliminando solicitud del expediente:', solicitud);
      // TODO
      console.log("Solicitud eliminada correctamente.");
    },
  }
);
