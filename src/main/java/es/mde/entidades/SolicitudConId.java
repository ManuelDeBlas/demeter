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

@Entity
@Table(name = "SOLICITUDES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
//@EntityListeners(SolicitudListener.class)
public abstract class SolicitudConId extends SolicitudImpl {

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

  public void setId(Long id) {
    this.id = id;
  }

  public PocConId getPoc() {
    return poc;
  }

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

  public void setExpediente(ExpedienteConId expediente) {
    this.expediente = expediente;
  }

  public SolicitudConId() {}

}
