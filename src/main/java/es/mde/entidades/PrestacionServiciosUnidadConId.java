package es.mde.entidades;

import es.mde.secres.PrestacionServiciosUnidad;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa una prestación de servicios en una unidad.
 * Extiende la funcionalidad de {@link SolicitudConId} e implementa la interfaz {@link PrestacionServiciosUnidad}.
 * 
 * Esta entidad está mapeada a la base de datos con un valor de discriminador "Prestacion servicios unidad".
 * Contiene información específica como el tiempo máximo permitido para la prestación de servicios.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@DiscriminatorValue("Prestacion servicios unidad")
public class PrestacionServiciosUnidadConId extends SolicitudConId implements PrestacionServiciosUnidad {

  /**
   * Tiempo máximo permitido para la prestación de servicios en la unidad.
   */
  private int tiempoMaximo;

  /**
   * Obtiene el tiempo máximo permitido para la prestación de servicios en la unidad.
   * 
   * @return el tiempo máximo permitido.
   */
  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

  /**
   * Establece el tiempo máximo permitido para la prestación de servicios en la unidad.
   * 
   * @param tiempoMaximo el tiempo máximo a establecer.
   */
  public void setTiempoMaximo(int tiempoMaximo) {
    this.tiempoMaximo = tiempoMaximo;
  }

  /**
   * Constructor por defecto.
   */
  public PrestacionServiciosUnidadConId() {
  }

}
