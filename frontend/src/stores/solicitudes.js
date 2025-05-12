import { crearStore } from "@/stores/fabricaStore";
import { get } from "@/stores/api-service";

export const useSolicitudesStore = crearStore("solicitudes", {
  async obtenerReservista() {
    for (let solicitud of this.elementos) {
      await get(solicitud._links.reservista.href)
          .then((response) => {
            console.log("Respuesta: ", response);
            if (response.data) {
              solicitud.reservista = response.data
            }
          })
          .catch((error) => {
            console.log("Error al obtener el reservista: ", error);
            console.log(error);
          });
    }
  },
  async guardarSolicitudEnAPI(solicitud, reservista, poc) {
    await this.anadirElemento(solicitud, reservista, poc);
    console.log("Solicitud guardada en la API: ", solicitud);
    solicitud.reservista = reservista;
    solicitud.poc = poc;
    console.log("Solicitud en el store: ", solicitud);
  }


});
