package es.mde.entidades;

import java.util.Collection;
import java.util.Objects;
import es.mde.secres.Expediente;
import es.mde.secres.Solicitud;
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

//  /**
//   * Colección de solicitudes asociadas al expediente.
//   */
//  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "expediente")
//  private Collection<SolicitudConId> solicitudes;
//
//  /**
//   * Estado actual del expediente.
//   */
//  private String estado;

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
//
//  public String getEstado() {
//    return estado;
//  }
//
//  public void setEstado(String estado) {
//    this.estado = estado;
//  }

  public ExpedienteConId() {
  }
  
  @Override
  @OneToMany(targetEntity = SolicitudConId.class)
  public Collection<Solicitud> getSolicitudes() {
    return super.getSolicitudes();
}

  
  public void addSolicitudConId(SolicitudConId solicitud) {
    getSolicitudes().add(solicitud);
    solicitud.setExpediente(this);
  }

  public void removeSolicitud(SolicitudConId solicitud) {
    getSolicitudes().remove(solicitud);
    solicitud.setExpediente(null);
  }

}
