package es.mde.entidades;

import es.mde.secres.SolicitudImpl;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

/**
 * Representa una solicitud.
 * Contiene información sobre el estado, las fechas y el expediente al que pertenece.
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
//@EntityListeners(SolicitudListener.class)
public abstract class SolicitudConId extends SolicitudImpl {

  /**
   * Identificador único de la solicitud.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;
  @ManyToOne(targetEntity = PocConId.class)
  private PocConId poc;
  @ManyToOne(targetEntity = ReservistaConId.class)
  private ReservistaConId reservista;
  @ManyToOne(targetEntity = ExpedienteConId.class)
  private ExpedienteConId expediente;
  @Transient
  private Estados estadoAnterior;
  
  @PostLoad
  public void guardarEstadoPrevio() {
    this.estadoAnterior = this.getEstado();
  }

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

  /**
   * Obtiene el estado actual de la solicitud.
   * 
   * @return el estado de la solicitud.
   */
  public PocConId getPoc() {
    return poc;
  }

  /**
   * Establece el estado actual de la solicitud.
   * 
   * @param estado el estado a establecer.
   */
  public void setPoc(PocConId poc) {
    this.poc = poc;
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

  /**
   * Constructor por defecto.
   */
  public SolicitudConId() {
  }
}
