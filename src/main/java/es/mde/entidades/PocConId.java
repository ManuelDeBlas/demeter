package es.mde.entidades;

import java.util.List;
import es.mde.secres.Poc;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "POCS")
public class PocConId extends Poc {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;
  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "poc")
  private List<SolicitudConId> solicitudesConId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
  public List<SolicitudConId> getSolicitudesConId() {
    return solicitudesConId;
  }
  
  public void setSolicitudesConId(List<SolicitudConId> solicitudesConId) {
    this.solicitudesConId = solicitudesConId;
  }

  public PocConId() {
  }

}
