package es.mde.entidades;

import es.mde.secres.SolicitudImpl;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Representa una solicitud. Contiene información sobre el estado, las fechas y
 * el expediente al que pertenece.
 * 
 * Esta entidad está mapeada a la tabla "SOLICITUDES" en la base de datos.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@Table(name = "SOLICITUDES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class SolicitudConId extends SolicitudImpl {

  /**
   * Identificador único de la solicitud.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

  @ManyToOne(targetEntity = ReservistaConId.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "RESERVISTA")
  private ReservistaConId reservista;
  @ManyToOne(targetEntity = ExpedienteConId.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "EXPEDIENTE")
  private ExpedienteConId expediente;

  public Long getId() {
    return id;
  }

  /**
   * Establece el identificador único de la solicitud.
   * 
   * @param id el identificador a establecer.
   */
  public void setId(Long id) {
    this.id = id;
  }

  public ReservistaConId getReservista() {
    return reservista;
  }

  public void setReservista(ReservistaConId reservista) {
    this.reservista = reservista;
  }

  public ExpedienteConId getExpediente() {
    return expediente;
  }

  /**
   * Establece el expediente al que pertenece la solicitud.
   * 
   * @param expediente el expediente a asociar.
   */
  public void setExpediente(ExpedienteConId expediente) {
    this.expediente = expediente;
  }

}
