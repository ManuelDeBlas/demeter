package es.mde.entidades;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.mde.secres.Reservista;
import es.mde.secres.Solicitud;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa un reservista.
 * Extiende la funcionalidad de {@link ReservistaImpl}.
 * 
 * Esta entidad está mapeada a la tabla "RESERVISTAS" en la base de datos.
 * Contiene información sobre las solicitudes asociadas al reservista.
 * 
 * @author
 * Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@Table(name = "RESERVISTAS")
public class ReservistaConId extends Reservista {

  /**
   * Identificador único del reservista.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

//  /**
//   * Lista de solicitudes asociadas al reservista.
//   */
//  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "reservista")
//  private List<SolicitudConId> solicitudesConId;

  /**
   * Obtiene el identificador único del reservista.
   * 
   * @return el identificador del reservista.
   */
  public Long getId() {
    return id;
  }

  /**
   * Establece el identificador único del reservista.
   * 
   * @param id el identificador a establecer.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Obtiene la lista de solicitudes asociadas al reservista.
   * 
   * @return la lista de solicitudes.
   */
  @Override
  @OneToMany(targetEntity = SolicitudConId.class)
  @JsonIgnore
  public Collection<Solicitud> getSolicitudes() {
    return super.getSolicitudes();
  }
//
//  /**
//   * Establece la lista de solicitudes asociadas al reservista.
//   * 
//   * @param solicitudesConId la lista de solicitudes a establecer.
//   */
//  public void setSolicitudesConId(List<SolicitudConId> solicitudesConId) {
//    this.solicitudesConId = solicitudesConId;
//  }
  
  public void addSolicitudConId(SolicitudConId solicitud) {
    super.getSolicitudes().add(solicitud);
    solicitud.setReservista(this);
}

  /**
   * Constructor por defecto.
   */
  public ReservistaConId() {}
  
}
