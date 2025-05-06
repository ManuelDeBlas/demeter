package es.mde.entidades;

import java.time.LocalDate;
import es.mde.secres.Expediente;
import es.mde.secres.FormacionContinuada;
import es.mde.secres.Reservista;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Formacion continuada")
public class FormacionContinuadaConId extends SolicitudConId implements FormacionContinuada {

  private int tiempoMaximo;
  private String escala;

  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

  public void setTiempoMaximo(int tiempoMaximo) {
    this.tiempoMaximo = tiempoMaximo;
  }

  @Override
  public String getEscala() {
    return escala;
  }

  public void setEscala(String escala) {
    this.escala = escala;
  }

  public FormacionContinuadaConId(String nombreUCO, String ciu, String situacion,
      Reservista reservista, LocalDate fechaInicio, LocalDate fechaFin, Expediente expediente,
      int tiempoMaximo, String escala) {
    super(nombreUCO, ciu, situacion, reservista, fechaInicio, fechaFin, expediente);
    setTiempoMaximo(tiempoMaximo);
    setEscala(escala);
  }

}
