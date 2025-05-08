package es.mde.entidades;

import es.mde.secres.ActivacionAmpliada;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Activacion ampliada")
public class ActivacionAmpliadaConId extends SolicitudConId implements ActivacionAmpliada {

  private String motivo;

  @Override
  public String getMotivo() {
    return motivo;
  }

  @Override
  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

}
