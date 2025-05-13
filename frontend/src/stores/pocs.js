import { crearStore } from "@/stores/fabricaStore";

export const usePocsStore = crearStore(
  "pocs", {
    crearListadoSolicitudes() {
      this.elementos.forEach((poc) => {
        poc.solicitudes = [];
      });
    }
  }
);