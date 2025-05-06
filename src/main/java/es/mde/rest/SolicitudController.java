package es.mde.rest;

import es.mde.entidades.SolicitudConId;
import es.mde.repositorios.SolicitudDAO;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RepositoryRestController
public class SolicitudController {

    private SolicitudDAO solicitudDAO;

    @GetMapping("/api/solicitudes")
    public List<SolicitudConId> getSolicitudes() {
        return solicitudDAO.findAll();
    }
}
