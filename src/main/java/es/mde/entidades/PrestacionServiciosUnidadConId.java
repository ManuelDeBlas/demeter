package es.mde.entidades;

import es.mde.secres.PrestacionServiciosUnidad;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Prestacion servicios unidad")
public class PrestacionServiciosUnidadConId extends SolicitudConId implements PrestacionServiciosUnidad {

  private int tiempoMaximo;

  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

  public PrestacionServiciosUnidadConId() {
  }

}
