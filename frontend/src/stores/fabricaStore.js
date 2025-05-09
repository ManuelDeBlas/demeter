import { defineStore } from "pinia";
import { getNombreDAO } from "@/stores/expedientes";
import { API_HOST, get, post, deleteEntidad, putEntidad } from "@/stores/api-service";

export function crearStore(nombreColeccion, accionesAdicionales = {}) {
  return defineStore(nombreColeccion, {
    state: () => ({
      elementos: [],
      elementoAbierto: null,
    }),
    actions: {
      async cargarElementos() {
        await get(API_HOST + "/" + nombreColeccion)
          .then((response) => {
            if (response.data._embedded) {
              const embedded = response.data._embedded;
              this.elementos = Object.values(embedded).flat();
            }
            console.log("La API me devuelve ", response);
            console.log("En el store ", this.elementos);
          })
          .catch((error) => {
            console.log(error);
          });
      },
      async anadirElemento(nuevoElemento) {
        console.log("En el store, lo que recibe: ", nuevoElemento);
        // Esto evita hacer un post a '/soliticitudes'
        if (nombreColeccion === "solicitudes") {
          nombreColeccion = getNombreDAO(nuevoElemento.tipoSolicitud);
        }
        try {
          const respuesta = await post(
            nuevoElemento,
            API_HOST + "/" + nombreColeccion
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
      async eliminarElemento(hrefAEliminar) {
        console.log(
          `En el store ${nombreColeccion} recibo este href a eliminar: `,
          hrefAEliminar
        );
        const respuesta = await deleteEntidad(hrefAEliminar);
        const indice = this.elementos.findIndex(
          (elemento) => elemento._links.self.href === hrefAEliminar
        );
        console.log(
          `En el store ${nombreColeccion} el índice del elemento a eliminar es: ${indice}`
        );
        if (indice !== -1) {
          this.elementos.splice(indice, 1);
        }
        return respuesta;
      },
      editarElemento(elementoEditado) {
        return "Esta función se implementará en el futuro";
        // console.log(
        //   `En el store ${nombreColeccion} recibo este elemento editado: `,
        //   elementoEditado
        // );
        // const indice = this.elementos.findIndex(
        //   (elemento) =>
        //     elemento._links.self.href === elementoEditado._links.self.href
        // );
        // if (indice !== -1) {
        //   this.elementos[indice] = elementoEditado;
        // }
      },
      ...accionesAdicionales,
    },
  });
}
