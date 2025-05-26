package es.mde.entidades;

import es.mde.secres.FormacionContinuada;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Representa una formación continuada. Extiende la funcionalidad de
 * {@link SolicitudConId} e implementa la interfaz {@link FormacionContinuada}.
 * 
 * Esta entidad está mapeada a la base de datos con un valor de discriminador
 * "Formacion continuada". Contiene información específica como el tiempo máximo
 * permitido y la escala asociada.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@DiscriminatorValue("Formacion continuada")
public class FormacionContinuadaConId extends SolicitudConId implements FormacionContinuada {

  /**
   * Tiempo máximo permitido para la formación continuada.
   */
  private int tiempoMaximo;

  /**
   * Escala asociada a la formación continuada.
   */
  private String escala;

  /**
   * Obtiene el tiempo máximo permitido para la formación continuada.
   * 
   * @return el tiempo máximo permitido.
   */
  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

  /**
   * Establece el tiempo máximo permitido para la formación continuada.
   * 
   * @param tiempoMaximo el tiempo máximo a establecer.
   */
  public void setTiempoMaximo(int tiempoMaximo) {
    this.tiempoMaximo = tiempoMaximo;
  }

  /**
   * Obtiene la escala asociada a la formación continuada.
   * 
   * @return la escala asociada.
   */
  @Override
  public String getEscala() {
    return escala;
  }

  /**
   * Establece la escala asociada a la formación continuada.
   * 
   * @param escala la escala a establecer.
   */
  public void setEscala(String escala) {
    this.escala = escala;
  }

  /**
   * Constructor por defecto.
   */
  public FormacionContinuadaConId() {
  }

}
