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
        "Pendiente de evaluación",
        "Aceptada sin expediente",
        "Aceptada pendiente de publicación",
        "Aceptada publicada",
        "Rechazada",
      ],
    },
    expedientes: {
      store: () => useExpedientesStore(),
      estados: ["Pendiente de publicación", "Enviado al BOD", "Publicado"],
    },
  };
  return config[tipoListado] || null;
}
