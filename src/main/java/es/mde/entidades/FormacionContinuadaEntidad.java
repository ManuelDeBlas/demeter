package es.mde.entidades;

import es.mde.secres.FormacionContinuada;

public class FormacionContinuadaEntidad extends SolicitudConId implements FormacionContinuada {

  private int tiempoMaximo;
  private String escala;

  @Override
  public int getTiempoMaximo() {
    return tiempoMaximo;
  }

  public void setTiempoMaximo(int tiempoMaximo) {
    this.tiempoMaximo = tiempoMaximo;
  }

  @Override
  public String getEscala() {
    return escala;
  }

  public void setEscala(String escala) {
    this.escala = escala;
  }

}
