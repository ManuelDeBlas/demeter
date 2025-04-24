import { crearStore } from "@/stores/fabricaStore";
import expedientesJSON from "@/assets/expedientes.json";

export const useExpedientesStore = crearStore(
  "expedientes",
  expedientesJSON._embedded.expedientes,
  "http://api-demeter/api/expedientes",
  {
    agregarSolicitudAExpediente(solicitud) {
      console.log('Agregando solicitud al expediente:', solicitud);
      // TODO
      console.log("Solicitud agregada correctamente.");
    },
    eliminarSolicitudDeExpediente(solicitud) {
      console.log('Eliminando solicitud del expediente:', solicitud);
      // TODO
      console.log("Solicitud eliminada correctamente.");
    },
  }
);
