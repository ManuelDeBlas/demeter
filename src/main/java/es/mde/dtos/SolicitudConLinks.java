package es.mde.dtos;

import java.util.Map;
import es.mde.entidades.SolicitudConId;

public class SolicitudConLinks extends SolicitudConId {

  private Map<String, Map<String, String>> _links;

  public Map<String, Map<String, String>> get_links() {
    return _links;
  }

  public void set_links(Map<String, Map<String, String>> links) {
    this._links = links;
  }
}
