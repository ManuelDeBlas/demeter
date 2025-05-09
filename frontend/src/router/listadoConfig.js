import { useReservistasStore } from "@/stores/reservistas";
import { usePocsStore } from "@/stores/pocs";
import { useSolicitudesStore } from "@/stores/solicitudes";
import { useExpedientesStore } from "@/stores/expedientes";

export function getListadoConfig(tipoListado) {
  const config = {
    reservistas: {
      store: () => useReservistasStore(),
      estados: [],
    },
    pocs: {
      store: () => usePocsStore(),
      estados: [],
    },
    solicitudes: {
      store: () => useSolicitudesStore(),
      estados: [
        "PENDIENTE_EVALUACION",
        "ACEPTADA_PENDIENTE_PUBLICACION",
        "ACEPTADA_PUBLICADA",
        "RECHAZADA",
      ],
    },
    expedientes: {
      store: () => useExpedientesStore(),
      estados: ["ABIERTO", "ENVIADO_AL_BOD", "PUBLICADO"],
    },
  };
  return config[tipoListado] || null;
}
