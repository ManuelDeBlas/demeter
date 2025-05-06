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

  public PrestacionServiciosUnidadConId(String nombreUCO, String ciu, String situacion,
      Reservista reservista, LocalDate fechaInicio, LocalDate fechaFin, Expediente expediente) {
    super(nombreUCO, ciu, situacion, reservista, fechaInicio, fechaFin, expediente);
    // TODO Auto-generated constructor stub
  }

  private int tiempoMaximo;
  
  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

}
