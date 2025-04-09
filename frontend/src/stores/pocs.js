import { defineStore } from "pinia";
import pocsJSON from "@/assets/pocs.json";

export const usePocsStore = defineStore("reservistas", {
  state: () => ({
    pocs: pocsJSON._embedded.pocs.map((poc) => ({
      ...poc,
    })),
  }),
  actions: {
    anadirPoc(nuevoPoc) {
      console.log("En el store recibo este poc para almacenar: ", nuevoPoc);
      const nuevoHref = `http://api-demeter/api/pocs/${this.pocs.length + 1}`;
      const pocConLink = {
        ...nuevoPoc,
        _links: {
          self: {
            href: nuevoHref,
          },
        },
      };
      this.pocs.unshift(pocConLink);
    },
    eliminarPoc(pocHref) {
      console.log("En el store recibo este pocHref a eliminar: ", pocHref);
      const index = this.pocs.findIndex(
        (poc) => poc._links.self.href === pocHref
      );
      if (index !== -1) {
        this.pocs.splice(index, 1);
      }
    },
    editarPoc(pocHref, pocEditado) {
      console.log("En el store recibo este pocHref a editar: ", pocHref);
      console.log("En el store recibo este pocEditado: ", pocEditado);
      const index = this.pocs.findIndex(
        (poc) => poc._links.self.href === pocHref
      );
      if (index !== -1) {
        (this.pocs[index].nombre = pocEditado.nombre),
          (this.pocs[index].apellido1 = pocEditado.apellido1),
          (this.pocs[index].apellido2 = pocEditado.apellido2),
          (this.pocs[index].empleo = pocEditado.empleo),
          (this.pocs[index].ucoDestino = pocEditado.ucoDestino),
          (this.pocs[index].telefonoCorporativo =
            pocEditado.telefonoCorporativo),
          (this.pocs[index].emailCorporativo = pocEditado.emailCorporativo);
      }
    },
  },
});
