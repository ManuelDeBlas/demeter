package es.mde.entidades;

import es.mde.secres.Poc;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "POCS")
public class PocConId extends Poc {

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
  
  public PocConId(String nombre, String apellido1, String apellido2, String empleo,
      String ucoDestino, String telefonoCorporativo, String emailCorporativo) {
    super(nombre, apellido1, apellido2, empleo, ucoDestino, telefonoCorporativo, emailCorporativo);
    // TODO Auto-generated constructor stub
  }
  
}
