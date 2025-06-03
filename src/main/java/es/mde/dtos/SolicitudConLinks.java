package es.mde.dtos;

import java.util.Map;
import es.mde.entidades.SolicitudConId;

public class SolicitudConLinks extends SolicitudConId {

  private Map<String, String> _links;

  public Map<String, String> get_links() {
    return _links;
  }

  public void set_links(Map<String, String> _links) {
    this._links = _links;
  }
}
