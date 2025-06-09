import { API_BASE_URL } from "@/config/app";
import { crearStore } from "@/stores/fabricaStore";
import { get, post, put } from "@/utils/api-service";
import { useReservistasStore } from "@/stores/reservistas";
import { useExpedientesStore } from "@/stores/expedientes";
import { getNombreDAO } from "@/utils/utils";
import { getId } from "@/utils/utils";

export const useSolicitudesStore = crearStore("solicitudes", {
  async cargarReservistaEnSolicitud(solicitud) {
    const reservistaAPI = await get(solicitud._links.reservista.href);
    const reservistaEnStore = useReservistasStore().recuperarObjetoDelStore(
      reservistaAPI.data._links.self.href
    );
    solicitud.reservista = reservistaEnStore;
    // TODO Esto genera una referencia circular.
    // reservistaEnStore.solicitudes.push(solicitud);
  },
  async postSolicitud(solicitud) {
    // TODO Comprobar si funciona bien
    try {
      solicitud.reservista = {
        id: getId(solicitud.reservista._links.self.href),
      };
      const respuesta = await post(
        solicitud,
        `${API_BASE_URL}/${getNombreDAO(solicitud.tipoSolicitud)}`
      );
      solicitud = respuesta.data;
      await useSolicitudesStore().cargarReservistaEnSolicitud(solicitud);
      this.elementos.unshift(solicitud);

      return solicitud;
    } catch (error) {
      return error;
    }
  },
  async putSolicitud(solicitud) {
    // TODO como postSolicitud
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
