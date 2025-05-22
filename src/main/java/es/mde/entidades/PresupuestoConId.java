package es.mde.entidades;

import es.mde.secres.Presupuesto;

import java.util.Collection;
import java.util.Objects;
import es.mde.secres.Expediente;
import es.mde.secres.Solicitud;
import es.mde.secres.Solicitud.Estados;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Representa un expediente con un identificador único.
 * Extiende la funcionalidad de la clase {@link Expediente}.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@Table(name = "PRESUPUESTOS")
public class PresupuestoConId extends Presupuesto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

  /**
   * Obtiene el identificador único del presupuesto.
   * 
   * @return el identificador del presupuesto.
   */
  public Long getId() {
    return id;
  }

  public PresupuestoConId() {
  }
  
}
