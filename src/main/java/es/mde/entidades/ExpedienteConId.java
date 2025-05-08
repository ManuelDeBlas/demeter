package es.mde.entidades;

import java.util.Collection;
import java.util.Objects;
import java.util.regex.Matcher;
import es.mde.secres.Expediente;
import es.mde.secres.SolicitudImpl.Estados;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "EXPEDIENTES")
public class ExpedienteConId extends Expediente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true)
  private Long id;
  @OneToMany(targetEntity = SolicitudConId.class, mappedBy = "expediente")
  private Collection<SolicitudConId> solicitudes;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Collection<SolicitudConId> getSolicitudes() {
    return solicitudes;
  }

  public void setSolicitudes(Collection<SolicitudConId> solicitudes) {
    this.solicitudes = solicitudes;
  }

  public ExpedienteConId() {}

  // private String getTipoSolicitud() {
  // Matcher matcher = REGEX_TIPO_SOLICITUD.matcher(getNumeroExpediente());
  // String resultado = null;
  // resultado = matcher.group(1);
  //
  // return resultado;
  // }

  public void addSolicitud(SolicitudConId solicitud) {
    if (Objects.equals(solicitud.getTipoSolicitud(), this.getTipoSolicitud())) {
      getSolicitudes().add(solicitud);
      solicitud.setExpediente(this);
      solicitud.setEstado(Estados.ACEPTADA_PENDIENTE_PUBLICACION);
    } else {
      throw new IllegalArgumentException("El tipo de la solicitud (" + solicitud.getTipoSolicitud()
          + ") no coincide con el tipo del expediente (" + this.getTipoSolicitud() + ")");
    }
  }

  public void removeSolicitud(SolicitudConId solicitud) {
    if (getSolicitudes().contains(solicitud)) {
      getSolicitudes().remove(solicitud);
      solicitud.setExpediente(null);
      solicitud.setEstado(Estados.PENDIENTE_EVALUACION);
    } else {
      System.err.println("Este expediente no contiene esta solicitud");
    }
  }

}
