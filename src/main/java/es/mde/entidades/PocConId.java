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

/**
 * Representa un Punto de Contacto (POC).
 * Extiende la funcionalidad de {@link Poc}.
 * 
 * Esta entidad está mapeada a la tabla "POCS" en la base de datos.
 * Contiene información sobre las solicitudes asociadas al POC.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@Entity
@Table(name = "POCS")
public class PocConId extends Poc {

  /**
   * Identificador único del POC.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;

  /**
   * Lista de solicitudes asociadas al POC.
   */
  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "poc")
  private List<SolicitudConId> solicitudesConId;

  /**
   * Obtiene el identificador único del POC.
   * 
   * @return el identificador del POC.
   */
  public Long getId() {
    return id;
  }

  /**
   * Establece el identificador único del POC.
   * 
   * @param id el identificador a establecer.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Obtiene la lista de solicitudes asociadas al POC.
   * 
   * @return la lista de solicitudes.
   */
  public List<SolicitudConId> getSolicitudesConId() {
    return solicitudesConId;
  }

  /**
   * Establece la lista de solicitudes asociadas al POC.
   * 
   * @param solicitudesConId la lista de solicitudes a establecer.
   */
  public void setSolicitudesConId(List<SolicitudConId> solicitudesConId) {
    this.solicitudesConId = solicitudesConId;
  }

  /**
   * Constructor por defecto.
   */
  public PocConId() {
  }

}
