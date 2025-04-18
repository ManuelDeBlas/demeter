import { useSolicitudesStore } from "@/stores/solicitudes";

export function getListadoConfig(tipoListado) {
    const config = {
        solicitudes: {
            store: () => useSolicitudesStore(),
            estados: [
                "Pendiente de evaluación",
                "Aceptada sin expediente",
                "Aceptada pendiente de publicación",
                "Aceptada publicada",
                "Rechazada",
              ],
        },
    };
    return config[tipoListado] || null;
};
