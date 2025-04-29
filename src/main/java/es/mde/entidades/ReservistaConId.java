package es.mde.entidades;

import java.util.Collection;
import es.mde.secres.ReservistaImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import net.bytebuddy.implementation.bytecode.assign.reference.GenericTypeAwareAssigner;

@Entity
@Table(name = "RESERVISTAS")
public class ReservistaConId extends ReservistaImpl {

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

  public ReservistaConId() {}

  @Override
  @OneToMany(targetEntity = SolicitudConId.class)
  public Collection<Solicitud> getSolicitudes() {
    return super.getSolicitudes();
  }

}
