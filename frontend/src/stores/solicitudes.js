import { crearStore } from "@/stores/fabricaStore";
import { API_HOST, get, post, put } from "@/stores/api-service";
import { useReservistasStore } from "@/stores/reservistas";
import { usePocsStore } from "@/stores/pocs";
import { getNombreDAO } from "@/utils/utils";

export const useSolicitudesStore = crearStore("solicitudes", {
  async cargarReservistaYPocEnSolicitudAlIniciar() {
    for (let solicitud of this.elementos) {
      const reservistaAPI = await get(solicitud._links.reservista.href);
      const pocAPI = await get(solicitud._links.poc.href);
      const reservistaEnStore = useReservistasStore().recuperarObjetoDelStore(
        reservistaAPI.data._links.self.href
      );
      const pocEnStore = usePocsStore().recuperarObjetoDelStore(
        pocAPI.data._links.self.href
      );
      solicitud.reservista = reservistaEnStore;
      solicitud.poc = pocEnStore;
      // TODO Esto genera una referencia circular.
      // reservistaEnStore.solicitudes.push(solicitud);
      // pocEnStore.solicitudes.push(solicitud);
    }
  },
  async postSolicitud(solicitud) {
    const solicitudParaLaAPI = { ...solicitud };
    solicitudParaLaAPI.reservista = solicitud.reservista._links.self.href;
    solicitudParaLaAPI.poc = solicitud.poc._links.self.href;
    const respuesta = await post(
      solicitudParaLaAPI,
      `${API_HOST}/${getNombreDAO(solicitud.tipoSolicitud)}`
    );
    solicitud._links = respuesta.data._links;
    this.elementos.unshift(solicitud);
    return respuesta;
  },
  async putSolicitud(solicitud) {
    ("putSolicitud", solicitud);
    const solicitudParaLaAPI = { ...solicitud };
    solicitudParaLaAPI.reservista = solicitud.reservista._links.self.href;
    solicitudParaLaAPI.poc = solicitud.poc._links.self.href;
    const respuesta = await put(
      solicitudParaLaAPI,
      solicitud._links.self.href
    );
    (respuesta);
    return respuesta;
  }
});
