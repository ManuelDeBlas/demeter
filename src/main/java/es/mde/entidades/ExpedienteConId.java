package es.mde.entidades;

import java.util.Collection;
import java.util.Objects;
import es.mde.secres.Expediente;
import es.mde.secres.Solicitud.Estados;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa un expediente con un identificador único.
 * Extiende la funcionalidad de la clase {@link Expediente}.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@Table(name = "EXPEDIENTES")
public class ExpedienteConId extends Expediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

  /**
   * Colección de solicitudes asociadas al expediente.
   */
  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "expediente")
  private Collection<SolicitudConId> solicitudes;

  /**
   * Estado actual del expediente.
   */
  private String estado;

  /**
   * Obtiene el identificador único del expediente.
   * 
   * @return el identificador del expediente.
   */
  public Long getId() {
    return id;
  }

  /**
   * Establece el identificador único del expediente.
   * 
   * @param id el identificador a establecer.
   */
  public void setId(Long id) {
    this.id = id;
  }

  public Collection<SolicitudConId> getSolicitudes() {
    return solicitudes;
  }

  public void setSolicitudes(Collection<SolicitudConId> solicitudes) {
    this.solicitudes = solicitudes;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public ExpedienteConId() {
  }

  public void addSolicitud(SolicitudConId solicitud) {
    if (solicitud.getExpediente() != null) {
      throw new IllegalArgumentException(
          "Esta solicitud ya está asignada al expediente " + solicitud.getExpediente().getNumeroExpediente()
              + ". Elimínela de su expediente antes de asignarla a otro.");
    }
    if (Objects.equals(solicitud.getTipoSolicitud(), this.getTipoSolicitud())) {
      getSolicitudes().add(solicitud);
      solicitud.setExpediente(this);
      solicitud.setEstado(Estados.ACEPTADA_PENDIENTE_PUBLICACION);
    } else {
      throw new IllegalArgumentException("El tipo de la solicitud (" + solicitud.getTipoSolicitud()
          + ") no coincide con el tipo del expediente (" + this.getTipoSolicitud() + ")");
    }
  }

  public void removeSolicitud(SolicitudConId solicitud) {
    if (getSolicitudes().contains(solicitud)) {
      getSolicitudes().remove(solicitud);
      solicitud.setExpediente(null);
      solicitud.setEstado(Estados.PENDIENTE_EVALUACION);
    } else {
      throw new IllegalArgumentException(
          "El expediente " + this.getNumeroExpediente() + " no contiene la solicitud que se quiere eliminar.");
    }
  }

}
