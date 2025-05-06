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

  public ActivacionAmpliadaConId(String nombreUCO, String ciu, String situacion,
      Reservista reservista, LocalDate fechaInicio, LocalDate fechaFin, Expediente expediente) {
    super(nombreUCO, ciu, situacion, reservista, fechaInicio, fechaFin, expediente);
    // TODO Auto-generated constructor stub
  }

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
