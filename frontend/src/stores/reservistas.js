import { defineStore } from "pinia";
import reservistasJSON from "@/assets/reservistas.json";

export const useReservistasStore = defineStore("reservistas", {
  state: () => ({
    reservistas: reservistasJSON._embedded.reservistas.map((reservista) => ({
      ...reservista,
    })),
  }),
  actions: {
    anadirReservista(nuevoReservista) {
      console.log(
        "En el store recibo este reservista para almacenar: ",
        nuevoReservista
      );
      const nuevoHref = `http://api-demeter/api/reservistas/${
        this.reservistas.length + 1
      }`;
      const reservistaConLink = {
        ...nuevoReservista,
        _links: {
          self: {
            href: nuevoHref,
          },
        },
      };
      this.reservistas.unshift(reservistaConLink);
    },
    eliminarReservista(reservistaHref) {
      console.log(
        "En el store recibo este reservistaHref a eliminar: ",
        reservistaHref
      );
      const index = this.reservistas.findIndex(
        (reservista) => reservista._links.self.href === reservistaHref
      );
      if (index !== -1) {
        this.reservistas.splice(index, 1);
      }
    },
    editarReservista(reservistaHref, reservistaEditado) {
      console.log(
        "En el store recibo este reservistaHref a editar: ",
        reservistaHref
      );
      console.log(
        "En el store recibo este reservistaEditado: ",
        reservistaEditado
      );
      const index = this.reservistas.findIndex(
        (reservista) => reservista._links.self.href === reservistaHref
      );
      if (index !== -1) {
        (this.reservistas[index].nombre = reservistaEditado.nombre),
          (this.reservistas[index].apellido1 = reservistaEditado.apellido1),
          (this.reservistas[index].apellido2 = reservistaEditado.apellido2),
          (this.reservistas[index].empleo = reservistaEditado.empleo),
          (this.reservistas[index].ucoDestino = reservistaEditado.ucoDestino),
          (this.reservistas[index].telefonoCorporativo =
            reservistaEditado.telefonoCorporativo),
          (this.reservistas[index].emailCorporativo =
            reservistaEditado.emailCorporativo);
      }
    },
  },
});
