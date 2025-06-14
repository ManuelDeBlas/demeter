package es.mde.rest;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import es.mde.entidades.PrestacionServiciosUnidadConId;
import es.mde.servicios.PrestacionServiciosUnidadServicio;

/**
 * Controlador REST para gestionar las solicitudes de prestación de servicios de
 * unidad.
 * 
 * Define el endpoint específico para la entidad
 * {@link PrestacionServiciosUnidadConId}.
 * 
 * @author Manuel de Blas Pino
 * @version 1.0
 */
@RepositoryRestController
public class PrestacionServiciosController extends AbstractSolicitudController<PrestacionServiciosUnidadConId> {

  /**
   * Servicio que contiene la lógica de negocio para prestación de servicios.
   */
  private final PrestacionServiciosUnidadServicio prestacionServiciosUnidadServicio;

  /**
   * Constructor para la inyección de dependencias del servicio.
   * 
   * @param prestacionServiciosUnidadServicio servicio para gestionar prestación
   *                                          de servicios
   */
  public PrestacionServiciosController(PrestacionServiciosUnidadServicio prestacionServiciosUnidadServicio) {
    this.prestacionServiciosUnidadServicio = prestacionServiciosUnidadServicio;
  }

  /**
   * Endpoint para crear una nueva solicitud de prestación de servicios de unidad.
   * 
   * @param prestacionServiciosUnidadConId datos de la solicitud recibidos en el
   *                                       cuerpo de la petición HTTP
   * @return ResponseEntity con el estado HTTP y el recurso creado con enlaces
   *         HATEOAS
   */
  @PostMapping("/prestaciones-servicios-unidad")
  public ResponseEntity<?> crearPrestacionServiciosUnidad(
      @RequestBody PrestacionServiciosUnidadConId prestacionServiciosUnidadConId) {

    // Utiliza el método genérico crearSolicitud del controlador abstracto,
    // pasando la función de creación del servicio y la ruta base
    return crearSolicitud(prestacionServiciosUnidadConId, prestacionServiciosUnidadServicio::crearSolicitud,
        "prestaciones-servicios-unidad");
  }

}
