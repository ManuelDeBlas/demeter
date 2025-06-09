import { API_BASE_URL } from "@/config/app";
import { defineStore } from "pinia";
import { getNombreDAO, cargarTodaLaApi } from "@/utils/utils";
import { get, post, deleteEntidad, put } from "@/utils/api-service";

export function crearStore(nombreColeccion, accionesAdicionales = {}) {
  return defineStore(nombreColeccion, {
    state: () => ({
      elementos: [],
      elementoAbierto: null,
      consultando: false,
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
            this.elementos.unshift(respuesta.data);
          }

          return respuesta.data;
        } catch (error) {
          return error;
        }
      },
      async eliminarElemento(objeto) {
        try {
          const hrefAEliminar = objeto._links.self.href;
          const respuesta = await deleteEntidad(hrefAEliminar);
          if (respuesta.status === 200) {
            const indice = this.elementos.findIndex(
              (elemento) =>
                elemento._links.self.href === respuesta.data._links.self.href
            );
            if (indice !== -1) {
              this.elementos.splice(indice, 1);
            }
          }

          return respuesta.data;
        } catch (error) {
          return error;
        }
      },
      async editarElemento(elementoEditado) {
        try {
          const respuesta = await put(
            elementoEditado,
            elementoEditado._links.self.href
          );
          if (respuesta.status === 200) {
            const indice = this.elementos.findIndex(
              (elemento) =>
                elemento._links.self.href === respuesta.data._links.self.href
            );
            if (indice !== -1) {
              this.elementos[indice] = respuesta.data;
            }
          }

          return respuesta.data;
        } catch (error) {
          return error;
        }
      },
      ...accionesAdicionales,
    },
  });
}
