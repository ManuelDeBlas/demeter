package es.mde.entidades;

import es.mde.secres.CostePorDia;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "COSTES_POR_DIA")
public class CostePorDiaConId extends CostePorDia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

  /**
   * Obtiene el identificador único del coste.
   * 
   * @return el identificador del coste.
   */
  public Long getId() {
    return id;
  }

  /**
   * Establece el identificador único del coste.
   * 
   * @param id el identificador a establecer.
   */
  public void setId(Long id) {
    this.id = id;
  }

}
