export function getNombreDAO(tipoSolicitud) {
  const tiposSolicitudes = {
    PS: "prestaciones-servicios-unidad",
    FC: "formaciones-continuadas",
    EX: "activaciones-ampliadas",
  };
  return tiposSolicitudes[tipoSolicitud];
}

export function formatearAtributoEnElFront(atributo) {
  const nombresFront = {
    PS: "Prestación servicio unidad",
    FC: "Formación continuada",
    EX: "Activación ampliada",
    PENDIENTE_EVALUACION: "Pendiente de evaluación",
    ACEPTADA_PENDIENTE_PUBLICACION: "Aceptada, pendiente de publicación",
    ACEPTADA_PUBLICADA: "Aceptada y publicada",
    RECHAZADA: "Rechazada",
    ABIERTO: "Abierto",
    ENVIADO_AL_BOD: "Enviado al BOD",
    PUBLICADO: "Publicado",
  };
  return nombresFront[atributo];
}
