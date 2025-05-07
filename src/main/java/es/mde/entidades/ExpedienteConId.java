package es.mde.entidades;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import es.mde.secres.Expediente;
import es.mde.secres.Solicitud;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPEDIENTES")
public class ExpedienteConId extends Expediente {

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
  
//  @Override
//  @JsonManagedReference  // Evita un bucle infinito al generar el JSON
//  public List<Solicitud> getSolicitudes() {
//    return super.getSolicitudes();
//  }
  
  public ExpedienteConId() {
  }

}
