package es.mde.entidades;

import java.time.LocalDate;
import es.mde.secres.Expediente;
import es.mde.secres.PrestacionServiciosUnidad;
import es.mde.secres.Reservista;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Prestacion servicios unidad")
public class PrestacionServiciosUnidadConId extends SolicitudConId
    implements PrestacionServiciosUnidad {

  private int tiempoMaximo;
  
  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }
  
  // TODO eliminar
  public PrestacionServiciosUnidadConId(String nombreUCO, String ciu, String situacion,
      Reservista reservista, LocalDate fechaInicio, LocalDate fechaFin, Expediente expediente, int tiempoMaximo) {
    super(nombreUCO, ciu, situacion, reservista, fechaInicio, fechaFin, expediente);
    this.tiempoMaximo = tiempoMaximo;
  }
  
  public PrestacionServiciosUnidadConId() {}

}
