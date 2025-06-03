package es.mde.util;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import es.mde.dtos.SolicitudConLinks;
import es.mde.entidades.SolicitudConId;

public class CargadorLinks {

  public static SolicitudConLinks cargarLinks(SolicitudConId solicitud) {
    SolicitudConLinks dto = new SolicitudConLinks();
    BeanUtils.copyProperties(solicitud, dto);
    Map<String, String> links = new LinkedHashMap<>();
    URI selfUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/solicitudes/{id}")
        .buildAndExpand(solicitud.getId()).toUri();
    links.put("self", selfUri.toString());
    links.put("expediente", selfUri.toString() + "/expediente");
    links.put("reservista", selfUri.toString() + "/reservista");
    dto.set_links(links);

    return dto;
  }

}

