import { crearStore } from "@/stores/fabricaStore";
import { useSolicitudesStore } from "@/stores/solicitudes";
import { get, patchEntidad, API_HOST } from "@/stores/api-service";

export const getNombreDAO = (tipoSolicitud) => {
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
    let urlSolicitud = solicitud._links.self.href.split("/");
    let solicitudId = urlSolicitud[urlSolicitud.length - 1];
    patchEntidad(
      `${API_HOST}/expedientes/${expedienteId}/asignar-solicitud/${solicitudId}`
    );
    console.log(this.elementoAbierto.solicitudes);
    console.log("Solicitud a agregar:", solicitud);
    this.elementoAbierto.solicitudes.push(solicitud);
    solicitud.expediente = this.elementoAbierto;
    solicitud.estado = "ACEPTADA_PENDIENTE_PUBLICACION";
  },
  eliminarSolicitudDeExpediente(solicitud) {
    console.log("Eliminando solicitud del expediente:", solicitud);
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
  async cargarSolicitudesEnExpediente() {
    // Este método asocia los objetos 'solicitud' del store 'useSolicitudesStore' con el expediente
    if (this.elementoAbierto !== null && !this.elementoAbierto.solicitudes) {
      this.elementoAbierto.solicitudes = [];
      try {
        // Descargar de la API las solicitudes del expediente abierto
        const resSolicitudesAPI = await get(
          useExpedientesStore().elementoAbierto._links.solicitudes.href
        );
        // Obtener cuáles son los href de las solicitudes pertenecientes a este expediente
        console.log("Solicitudes de la API:", resSolicitudesAPI);
        try {
          let solicitudesAPIhref =
            resSolicitudesAPI.data._embedded[
              getNombreDAO(useExpedientesStore().elementoAbierto.tipoSolicitud)
            ].map((s) => s._links.self.href) || [];
          // Almacenar en este expediente los objetos del store solicitudes que le corresponden
          for (let solicitudEnStore of useSolicitudesStore().elementos) {
            if (
              !solicitudEnStore.expediente &&
              solicitudesAPIhref.includes(solicitudEnStore._links.self.href)
            ) {
              console.log("ASIGNACION");
              // Si la solicitud no tiene expediente asignado y está en el listado de solicitudes del expediente
              const solicitud = useSolicitudesStore().elementos.find(
                (s) => s._links.self.href === solicitudEnStore._links.self.href
              );
              if (solicitud) {
                this.elementoAbierto.solicitudes.push(solicitud);
                solicitud.expediente = this.elementoAbierto;
              }
            }
          }        } catch (error) {
          console.log(
            "El listado de solicitudes de este expediente está vacío."
          );
        }
      } catch (error) {
        console.error("Error al cargar las solicitudes:", error);
      }
    }
  },
});
