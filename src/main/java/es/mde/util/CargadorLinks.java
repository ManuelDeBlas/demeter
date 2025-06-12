package es.mde.util;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import es.mde.dtos.SolicitudConLinks;
import es.mde.entidades.SolicitudConId;

public class CargadorLinks {

  public static <T extends SolicitudConId> SolicitudConLinks cargarLinksGenerico(T solicitud, String rutaApi) {
    SolicitudConLinks dto = new SolicitudConLinks();
    BeanUtils.copyProperties(solicitud, dto);

    Map<String, Map<String, String>> links = new LinkedHashMap<>();

    URI selfUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/" + rutaApi + "/{id}")
        .buildAndExpand(solicitud.getId()).toUri();

    links.put("self", Map.of("href", selfUri.toString()));
    links.put("expediente", Map.of("href", selfUri.toString() + "/expediente"));
    links.put("reservista", Map.of("href", selfUri.toString() + "/reservista"));

    dto.set_links(links);

    return dto;
  }

}
