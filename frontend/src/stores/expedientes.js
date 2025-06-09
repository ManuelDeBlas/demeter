import { API_BASE_URL } from "@/config/app";
import { getId } from "@/utils/utils";
import { crearStore } from "@/stores/fabricaStore";
import { useSolicitudesStore } from "@/stores/solicitudes";
import { get, patchEntidad } from "@/utils/api-service";

export const useExpedientesStore = crearStore("expedientes", {
  async editarExpediente(expediente) {
    const expedienteParaLaAPI = { ...expediente };
    delete expedienteParaLaAPI.solicitudes;
    const respuesta = await this.editarElemento(expedienteParaLaAPI);
    return respuesta;
  },
  async agregarSolicitudAExpediente(solicitud) {
    "Agregando solicitud al expediente:", solicitud;
    const expedienteId = getId(this.elementoAbierto._links.self.href);
    const solicitudId = getId(solicitud._links.self.href);
    await patchEntidad(
      `${API_BASE_URL}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
    );
    this.elementoAbierto.solicitudes;
    "Solicitud a agregar:", solicitud;
    this.elementoAbierto.solicitudes.push(solicitud);
    solicitud.expediente = this.elementoAbierto;
    solicitud.estado = "ACEPTADA_PENDIENTE_PUBLICACION";
  },
  async eliminarSolicitudDeExpediente(solicitud) {
    const expedienteId = getId(this.elementoAbierto._links.self.href);
    const solicitudId = getId(solicitud._links.self.href);
    await patchEntidad(
      `${API_BASE_URL}/expedientes/${expedienteId}/desasignar-solicitud/${solicitudId}`
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
  async cargarSolicitudesEnExpedientes() {
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
      const costeExpediente = await get(
        `${API_BASE_URL}/expedientes/coste-expediente/${expediente.numeroExpediente}`
      );
      expediente.coste = costeExpediente.data;
    }
  },
});
