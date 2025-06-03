package es.mde.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.rest.PrestacionServiciosController;

@Component
public class PrestacionServiciosUnidadAssembler implements
    RepresentationModelAssembler<PrestacionServiciosUnidadConId, EntityModel<PrestacionServiciosUnidadConId>> {

  @Override
  public EntityModel<PrestacionServiciosUnidadConId> toModel(
      PrestacionServiciosUnidadConId prestacion) {
    return EntityModel.of(prestacion,
        linkTo(methodOn(PrestacionServiciosController.class).obtenerPrestacion(prestacion.getId()))
            .withSelfRel(),
        linkTo(methodOn(PrestacionServiciosController.class).listarPrestaciones())
            .withRel("prestaciones"));
  }
}

