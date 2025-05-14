import { useSolicitudesStore } from '@/stores/solicitudes'
import { useReservistasStore } from '@/stores/reservistas'
import { useExpedientesStore } from '@/stores/expedientes'
import { usePocsStore } from '@/stores/pocs'


export function getStorePorTipoListado(tipoListado) {
  const stores = {
    solicitudes: useSolicitudesStore(),
    reservistas: useReservistasStore(),
    expedientes: useExpedientesStore(),
    pocs: usePocsStore(),
  }
  return stores[tipoListado];
}

export const getNombreDAO = (tipoSolicitud) => {
  const tiposSolicitudes = {
    PS: "prestaciones-servicios-unidad",
    FC: "formaciones-continuadas",
    EX: "activaciones-ampliadas",
  };
  return tiposSolicitudes[tipoSolicitud];
};