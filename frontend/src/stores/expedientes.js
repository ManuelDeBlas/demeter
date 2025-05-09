import { crearStore } from "@/stores/fabricaStore";
import { patchEntidad, API_HOST } from "@/stores/api-service";

export const getTipoSolicitud = (tipoSolicitud) => {
  const tiposSolicitudes = {
    PS: "prestaciones-servicios-unidad",
    FC: "formaciones-continuadas",
    EX: "activaciones-ampliadas",
  };
  return tiposSolicitudes[tipoSolicitud];
};

export const useExpedientesStore = crearStore("expedientes", {
  agregarSolicitudAExpediente(solicitud) {
    console.log("Agregando solicitud al expediente:", solicitud);
    let urlExpediente = this.elementoAbierto._links.self.href.split("/");
    let expedienteId = urlExpediente[urlExpediente.length - 1];
    let urlSolicitud = this.elementoAbierto._links.self.href.split("/");
    let solicitudId = urlSolicitud[urlSolicitud.length - 1];
    patchEntidad(
      `${API_HOST}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
    );
    console.log(this.elementoAbierto.solicitudes);
    console.log("Solicitud a agregar:", solicitud);
    this.elementoAbierto.solicitudes.push(solicitud);
    solicitud.expediente = this.elementoAbierto;
    console.log("Solicitud agregada correctamente.");
  },
  eliminarSolicitudDeExpediente(solicitud) {
    console.log("Eliminando solicitud del expediente:", solicitud);
    let urlExpediente = this.elementoAbierto._links.self.href.split("/");
    let expedienteId = urlExpediente[urlExpediente.length - 1];
    let urlSolicitud = this.elementoAbierto._links.self.href.split("/");
    let solicitudId = urlSolicitud[urlSolicitud.length - 1];
    patchEntidad(
      `${API_HOST}/expedientes/${expedienteId}/desasignar-solicitud/${solicitudId}`
    );
    const indice = this.elementoAbierto.solicitudes.findIndex(
      (e) => e._links.self.href === solicitud._links.self.href
    );
    if (indice !== -1) {
      this.elementoAbierto.solicitudes.splice(indice, 1);
    }
    solicitud.expediente = null;
  },
});
