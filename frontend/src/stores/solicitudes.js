import { defineStore } from "pinia";
import solicitudesJSON from "@/assets/solicitudes.json";

export const useSolicitudesStore = defineStore("solicitudes", {
  state: () => ({
    solicitudes: solicitudesJSON._embedded.solicitudes.map((solicitud) => ({
      ...solicitud,
    })),
  }),
  actions: {
    anadirSolicitud(nuevaSolicitud) {
      console.log("En el store recibo este poc para almacenar: ", nuevaSolicitud);
      const nuevoHref = `http://api-demeter/api/solicitudes/${this.solicitudes.length + 1}`;
      const solicitudConLink = {
        ...nuevaSolicitud,
        _links: {
          self: {
            href: nuevoHref,
          },
        },
      };
      this.solicitudes.unshift(solicitudConLink);
    },
    eliminarSolicitud(solicitudHref) {
      console.log("En el store recibo este solicitudHref a eliminar: ", solicitudHref);
      const index = this.solicitudes.findIndex(
        (solicitud) => solicitud._links.self.href === solicitudHref
      );
      if (index !== -1) {
        this.solicitudes.splice(index, 1);
      }
    },
    anadirSolicitud(solicitudHref, solicitudHref) {
      console.log("En el store recibo este solicitudHref a añadir solicitud: ", solicitudHref);
      console.log("En el store recibo este solicitudHref a añadir: ", solicitudHref);
      const index = this.solicitudes.findIndex(
        (Solicitud) => Solicitud._links.self.href === solicitudHref
      );
      if (index !== -1) {
        this.solicitudes[index].solicitudes.push(solicitudHref);
      }
    },
    eliminarSolicitud(solicitudHref, solicitudHref) {
      console.log("En el store recibo este solicitudHref a eliminar solicitud: ", solicitudHref);
      console.log("En el store recibo este solicitudHref a eliminar: ", solicitudHref);
      const index = this.solicitudes.findIndex(
        (Solicitud) => Solicitud._links.self.href === solicitudHref
      );
      if (index !== -1) {
        const solicitudIndex = this.solicitudes[index].solicitudes.findIndex(
          (solicitud) => solicitud === solicitudHref
        );
        if (solicitudIndex !== -1) {
          this.solicitudes[index].solicitudes.splice(solicitudIndex, 1);
        }
      }
    },
    modificarEstadoSolicitud(solicitudHref, nuevoEstado) {
      console.log("En el store recibo este solicitudHref a modificar: ", solicitudHref);
      console.log("En el store recibo este nuevoEstado a modificar: ", nuevoEstado);
      const index = this.solicitudes.findIndex(
        (solicitud) => solicitud._links.self.href === solicitudHref
      );
      if (index !== -1) {
        this.solicitudes[index].estado = nuevoEstado;
      }
    }
  },
});
