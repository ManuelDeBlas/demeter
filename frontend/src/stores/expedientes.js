import { crearStore } from "@/stores/fabricaStore";
import { useSolicitudesStore } from "@/stores/solicitudes";
import { get, patchEntidad, API_HOST } from "@/stores/api-service";

export const useExpedientesStore = crearStore("expedientes", {
  agregarSolicitudAExpediente(solicitud) {
    ("Agregando solicitud al expediente:", solicitud);
    let urlExpediente = this.elementoAbierto._links.self.href.split("/");
    let expedienteId = urlExpediente[urlExpediente.length - 1];
    let urlSolicitud = solicitud._links.self.href.split("/");
    let solicitudId = urlSolicitud[urlSolicitud.length - 1];
    patchEntidad(
      `${API_HOST}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
    );
    (this.elementoAbierto.solicitudes);
    ("Solicitud a agregar:", solicitud);
    this.elementoAbierto.solicitudes.push(solicitud);
    solicitud.expediente = this.elementoAbierto;
    solicitud.estado = "ACEPTADA_PENDIENTE_PUBLICACION";
  },
  eliminarSolicitudDeExpediente(solicitud) {
    let urlExpediente = this.elementoAbierto._links.self.href.split("/");
    let expedienteId = urlExpediente[urlExpediente.length - 1];
    let urlSolicitud = solicitud._links.self.href.split("/");
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
    solicitud.estado = "PENDIENTE_EVALUACION";
  },
  async cargarSolicitudesEnExpedienteAlIniciar() {
    for (let expediente of this.elementos) {
      expediente.solicitudes = [];
      const solicitudesEnExpedienteAPI = await get(
        expediente._links.solicitudes.href
      );
      const embedded = solicitudesEnExpedienteAPI.data._embedded;
      const keys = Object.keys(embedded);
      const firstKey = keys[0];
      const solicitudesAPI = embedded[firstKey];
      for (let solicitudAPI of solicitudesAPI) {
        const soliditudEnStore = useSolicitudesStore().recuperarObjetoDelStore(
          solicitudAPI._links.self.href
        );
        expediente.solicitudes.push(soliditudEnStore);
        // TODO Esto genera una referencia circular.
        // soliditudEnStore.expediente = expediente;
      }
    }
  },
});
