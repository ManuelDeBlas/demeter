package es.mde.entidades;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import es.mde.secres.SolicitudImpl;
import es.mde.secres.Expediente;
import es.mde.secres.Reservista;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "SOLICITUDES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO")
public abstract class SolicitudConId extends SolicitudImpl {

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
  
  @Override
  @JsonBackReference  // Evita un bucle infinito al generar el JSON
  public Reservista getReservista() {
    return super.getReservista();
  }
  
  @Override
  @JsonBackReference // Evita un bucle infinito al generar el JSON
  public Expediente getExpediente() {
      return super.getExpediente();
  }


  public SolicitudConId() {}

  public SolicitudConId(String nombreUCO, String ciu, String situacion, Reservista reservista,
      LocalDate fechaInicio, LocalDate fechaFin, Expediente expediente) {
    super(nombreUCO, ciu, situacion, reservista, fechaInicio, fechaFin, expediente);
  }

}
