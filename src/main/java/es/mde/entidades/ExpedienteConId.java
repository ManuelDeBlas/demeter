package es.mde.entidades;

import es.mde.secres.Expediente;
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

  // TODO eliminar. Constructor creado para pruebas
  public ExpedienteConId(String numeroExpediente) {
    super();
    setNumeroExpediente(numeroExpediente);
  }

}
