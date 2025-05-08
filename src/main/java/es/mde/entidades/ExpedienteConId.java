package es.mde.entidades;

import java.util.Collection;
import es.mde.secres.Expediente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPEDIENTES")
public class ExpedienteConId extends Expediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;
  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "expediente")
  private Collection<SolicitudConId> solicitudes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Collection<SolicitudConId> getSolicitudes() {
    return solicitudes;
  }

  public void setSolicitudes(Collection<SolicitudConId> solicitudes) {
    this.solicitudes = solicitudes;
  }

  public ExpedienteConId() {}

}
