package es.mde.entidades;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import es.mde.secres.SolicitudImpl;
import es.mde.secres.Expediente;
import es.mde.secres.Reservista;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  @ManyToOne(targetEntity = ReservistaConId.class)
  public Reservista getReservista() {
    return super.getReservista();
  }

  @Override
  @ManyToOne(targetEntity = ExpedienteConId.class)
  public Expediente getExpediente() {
    return super.getExpediente();
  }

  public SolicitudConId() {
  }

}
