package es.mde.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "COSTES_POR_DIA")
public class CostePorDia {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

  private String empleo;
  private int centimos;

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

  public String getEmpleo() {
    return empleo;
  }

  public void setEmpleo(String empleo) {
    this.empleo = empleo;
  }

  public int getCentimos() {
    return centimos;
  }

  public void setCentimos(int centimos) {
    this.centimos = centimos;
  }

}
