package es.mde.entidades;

import java.util.Collection;
import es.mde.secres.SolicitudImpl;
import es.mde.secres.Reservista;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "SOLICITUDES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class SolicitudConId extends SolicitudImpl {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;
  
  @ManyToOne(targetEntity = ExpedienteConId.class)
  private ExpedienteConId expediente;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public ExpedienteConId getExpediente() {
    return expediente;
  }
  
  public void setExpediente(ExpedienteConId expediente) {
    this.expediente = expediente;
  }

  public SolicitudConId() {}

  @Override
  @ManyToOne(targetEntity = ReservistaConId.class)
  @JoinColumn(name = "reservista_id", nullable = false)
  public Reservista getReservista() {
    return super.getReservista();
  }
}
