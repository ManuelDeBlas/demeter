import { defineStore } from "pinia";

export function crearStore(nombreStore, datosJSON, urlBase, accionesAdicionales = {}) {
  return defineStore(nombreStore, {
    state: () => ({
      elementos: datosJSON.map((elemento) => ({
        ...elemento,
      })),
      elementoAbierto: null,
    }),
    actions: {
      anadirElemento(nuevoElemento) {
        console.log(
          `En el store ${nombreStore} recibo este elemento para almacenar: `,
          nuevoElemento
        );
        const nuevoHref = `${urlBase}/${this.elementos.length + 1}`;
        const elementoConLink = {
          ...nuevoElemento,
          _links: {
            self: {
              href: nuevoHref,
            },
          },
        };
        this.elementos.unshift(elementoConLink);
      },
      eliminarElemento(hrefAEliminar) {
        console.log(
          `En el store ${nombreStore} recibo este href a eliminar: `,
          hrefAEliminar
        );
        const indice = this.elementos.findIndex(
          (elemento) => elemento._links.self.href === hrefAEliminar
        );
        console.log(
          `En el store ${nombreStore} el índice del elemento a eliminar es: ${indice}`
        );
        if (indice !== -1) {
          this.elementos.splice(indice, 1);
        }
      },
      editarElemento(elementoEditado) {
        console.log(
          `En el store ${nombreStore} recibo este elemento editado: `,
          elementoEditado
        );
        const indice = this.elementos.findIndex(
          (elemento) =>
            elemento._links.self.href === elementoEditado._links.self.href
        );
        if (indice !== -1) {
          this.elementos[indice] = elementoEditado;
        }
      },
      ...accionesAdicionales,
    },
  });
}
