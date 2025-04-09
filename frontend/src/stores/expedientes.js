import { defineStore } from "pinia";
import expedientesJSON from "@/assets/expedientes.json";

export const useExpedientesStore = defineStore("expedientes", {
  state: () => ({
    expedientes: expedientesJSON._embedded.expedientes.map((expediente) => ({
      ...expediente,
    })),
  }),
  actions: {
    anadirExpediente(nuevoExpediente) {
      console.log("En el store recibo este poc para almacenar: ", nuevoExpediente);
      const nuevoHref = `http://api-demeter/api/expedientes/${this.expedientes.length + 1}`;
      const expedienteConLink = {
        ...nuevoExpediente,
        _links: {
          self: {
            href: nuevoHref,
          },
        },
      };
      this.expedientes.unshift(expedienteConLink);
    },
    eliminarExpediente(expedienteHref) {
      console.log("En el store recibo este expedienteHref a eliminar: ", expedienteHref);
      const index = this.expedientes.findIndex(
        (expediente) => expediente._links.self.href === expedienteHref
      );
      if (index !== -1) {
        this.expedientes.splice(index, 1);
      }
    },
    anadirSolicitud(expedienteHref, solicitudHref) {
      console.log("En el store recibo este expedienteHref a añadir solicitud: ", expedienteHref);
      console.log("En el store recibo este solicitudHref a añadir: ", solicitudHref);
      const index = this.expedientes.findIndex(
        (expediente) => expediente._links.self.href === expedienteHref
      );
      if (index !== -1) {
        this.expedientes[index].solicitudes.push(solicitudHref);
      }
    },
    eliminarSolicitud(expedienteHref, solicitudHref) {
      console.log("En el store recibo este expedienteHref a eliminar solicitud: ", expedienteHref);
      console.log("En el store recibo este solicitudHref a eliminar: ", solicitudHref);
      const index = this.expedientes.findIndex(
        (expediente) => expediente._links.self.href === expedienteHref
      );
      if (index !== -1) {
        const solicitudIndex = this.expedientes[index].solicitudes.findIndex(
          (solicitud) => solicitud === solicitudHref
        );
        if (solicitudIndex !== -1) {
          this.expedientes[index].solicitudes.splice(solicitudIndex, 1);
        }
      }
    }
  },
});
