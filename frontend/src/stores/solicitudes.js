import { API_BASE_URL } from "@/config/app";
import { crearStore } from "@/stores/fabricaStore";
import { get, post, put } from "@/utils/api-service";
import { useReservistasStore } from "@/stores/reservistas";
import { useExpedientesStore } from "@/stores/expedientes";
import { getNombreDAO } from "@/utils/utils";
import { getId } from "@/utils/utils";

export const useSolicitudesStore = crearStore("solicitudes", {
  async cargarReservistaEnSolicitudes() {
    for (let solicitud of this.elementos) {
      const reservistaAPI = await get(solicitud._links.reservista.href);
      const reservistaEnStore = useReservistasStore().recuperarObjetoDelStore(
        reservistaAPI.data._links.self.href
      );
      solicitud.reservista = reservistaEnStore;
      // TODO Esto genera una referencia circular.
      // reservistaEnStore.solicitudes.push(solicitud);
    }
  },
  async postSolicitud(solicitud) {
    console.log("postSolicitud", solicitud);
    const solicitudParaLaAPI = { ...solicitud };
    solicitudParaLaAPI.reservista = {
      id: getId(solicitud.reservista._links.self.href),
    };
    console.log("Solicitud para la API:", solicitudParaLaAPI);
    try {
      const respuesta = await post(
        solicitudParaLaAPI,
        `${API_BASE_URL}/${getNombreDAO(solicitud.tipoSolicitud)}`
      );
      solicitud._links = respuesta.data._links;
      this.elementos.unshift(solicitud);
      await Promise.all([useSolicitudesStore().cargarElementos()]);
      await Promise.all([
        useExpedientesStore().cargarSolicitudesEnExpedientes(),
        useReservistasStore().crearListadoSolicitudes(),
        useSolicitudesStore().cargarReservistaEnSolicitudes(),
      ]);
      return respuesta;
    } catch (error) {
      console.error("Error: ", error);
    }
  },
  async putSolicitud(solicitud) {
    "putSolicitud", solicitud;
    const solicitudParaLaAPI = { ...solicitud };
    solicitudParaLaAPI.reservista = {
      id: getId(solicitud.reservista._links.self.href),
    };
    try {
      const respuesta = await put(
        solicitudParaLaAPI,
        solicitud._links.self.href
      );
      await Promise.all([useSolicitudesStore().cargarElementos()]);
      await Promise.all([
        useExpedientesStore().cargarSolicitudesEnExpedientes(),
        useReservistasStore().crearListadoSolicitudes(),
        useSolicitudesStore().cargarReservistaEnSolicitudes(),
      ]);
      return respuesta;
    } catch (error) {
      console.error("Error: ", error);
    }
  },
});
