import { API_BASE_URL } from "@/config/app";
import { getId } from "@/utils/utils";
import { crearStore } from "@/stores/fabricaStore";
import { useSolicitudesStore } from "@/stores/solicitudes";
import { usePresupuestosSecresStore } from "@/stores/presupuestos-secres";
import { get, patch } from "@/utils/api-service";

export const useExpedientesStore = crearStore("expedientes", {
  async editarExpediente(expedienteAEditar) {
    try {
      const solicitudesEnStore = expedienteAEditar.solicitudes;
      delete expedienteAEditar.solicitudes;
      const expedienteEnStore = await this.editarElemento(expedienteAEditar);
      expedienteEnStore.solicitudes = solicitudesEnStore;

      return expedienteEnStore;
    } catch (error) {
      return error;
    }
  },
  async agregarSolicitudAExpediente(solicitud, expediente) {
    try {
      const expedienteId = getId(expediente._links.self.href);
      const solicitudId = getId(solicitud._links.self.href);
      const respuesta = await patch(
        `${API_BASE_URL}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
      );
      console.log(
        `${API_BASE_URL}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
      );
      if (respuesta.status === 200) {
        expediente.solicitudes.push(solicitud);
        solicitud.expediente = expediente;
        solicitud.estado = "ACEPTADA_PENDIENTE_PUBLICACION";
        await usePresupuestosSecresStore().cargarElementos();
        const costeExpediente = await get(
          `${API_BASE_URL}/expedientes/coste-expediente/${expediente.numeroExpediente}`
        );
        expediente.coste = costeExpediente.data;

        return "Solicitud añadida al expediente correctamente";
      }
    } catch (error) {
      return error.response;
    }
  },
  async eliminarSolicitudDeExpediente(solicitud, expediente) {
    try {
      const expedienteId = getId(expediente._links.self.href);
      const solicitudId = getId(solicitud._links.self.href);
      const respuesta = await patch(
        `${API_BASE_URL}/expedientes/${expedienteId}/desasignar-solicitud/${solicitudId}`
      );
      console.log(
        `${API_BASE_URL}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
      );
      if (respuesta.status === 200) {
        const indice = expediente.solicitudes.findIndex(
          (e) => e._links.self.href === solicitud._links.self.href
        );
        if (indice !== -1) {
          expediente.solicitudes.splice(indice, 1);
        }
        solicitud.expediente = null;
        solicitud.estado = "PENDIENTE_EVALUACION";
        await usePresupuestosSecresStore().cargarElementos();
        const costeExpediente = await get(
          `${API_BASE_URL}/expedientes/coste-expediente/${expediente.numeroExpediente}`
        );
        expediente.coste = costeExpediente.data;

        return "Solicitud eliminada del expediente correctamente";
      }
    } catch (error) {
      return error.response;
    }
  },
  async cargarSolicitudesEnExpediente(expediente) {
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
  },
});
