package es.mde.entidades;

import java.util.Collection;
import java.util.List;
import es.mde.secres.ReservistaImpl;
import es.mde.secres.Solicitud;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

  public ReservistaConId(String nombre, String apellido1, String apellido2, String empleo) {
    super(nombre, apellido1, apellido2, empleo);
    // TODO Auto-generated constructor stub
  }

//  @Override
//  @OneToMany(targetEntity = SolicitudConId.class)
//  public List<Solicitud> getSolicitudes() {
//    return super.getSolicitudes();
//  }

}
