import { crearStore } from "@/stores/fabricaStore";

export const useReservistasStore = crearStore(
  "reservistas", {
    crearListadoSolicitudes() {
      this.elementos.forEach((reservista) => {
        reservista.solicitudes = [];
      });
    }
  }
);