package es.mde.entidades;

import java.time.LocalDate;
import es.mde.secres.ActivacionAmpliada;
import es.mde.secres.Expediente;
import es.mde.secres.Reservista;
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
