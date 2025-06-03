import { API_BASE_URL } from "@/config/app";
import { defineStore } from "pinia";
import { getNombreDAO } from "@/utils/utils";
import { get, post, deleteEntidad, put } from "@/utils/api-service";

export function crearStore(nombreColeccion, accionesAdicionales = {}) {
  return defineStore(nombreColeccion, {
    state: () => ({
      elementos: [],
      elementoAbierto: null,
    }),
    actions: {
      async cargarElementos() {
        await get(API_BASE_URL + "/" + nombreColeccion)
          .then((response) => {
            if (response.data._embedded) {
              const embedded = response.data._embedded;
              this.elementos = Object.values(embedded).flat();
            }
          })
          .catch((error) => {
            error;
          });
      },
      recuperarObjetoDelStore(href) {
        const objetoRecuperado = this.elementos.filter(
          (elemento) => elemento._links.self.href === href
        )[0];
        return objetoRecuperado;
      },
      async anadirElemento(nuevoElemento) {
        // Esto evita hacer un post a '/soliticitudes'
        if (nombreColeccion === "solicitudes") {
          nombreColeccion = getNombreDAO(nuevoElemento.tipoSolicitud);
        }
        try {
          const respuesta = await post(
            nuevoElemento,
            API_BASE_URL + "/" + nombreColeccion
          );
          if (respuesta.status === 201) {
            const elementoAgregado = {
              ...nuevoElemento,
              _links: respuesta.data._links,
            };
            this.elementos.unshift(elementoAgregado);
          }
          return respuesta;
        } catch (error) {
          console.error("Error: ", error);
        }
      },
      async eliminarElemento(objeto) {
        // const hrefAEliminar = objeto;
        const hrefAEliminar = objeto._links.self.href;
        const respuesta = await deleteEntidad(hrefAEliminar);
        const indice = this.elementos.findIndex(
          (elemento) => elemento._links.self.href === hrefAEliminar
        );
        if (indice !== -1) {
          this.elementos.splice(indice, 1);
        }
        return respuesta;
      },
      async editarElemento(elementoEditado) {
        const indice = this.elementos.findIndex(
          (elemento) =>
            elemento._links.self.href === elementoEditado._links.self.href
        );
        if (indice !== -1) {
          this.elementos[indice] = elementoEditado;
        }
        await put(elementoEditado, elementoEditado._links.self.href)
          .then((response) => {
            return response;
          })
          .catch((error) => {
            return error;
          });
      },
      ...accionesAdicionales,
    },
  });
}
