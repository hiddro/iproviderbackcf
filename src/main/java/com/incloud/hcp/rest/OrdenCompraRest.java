package com.incloud.hcp.rest;

import com.incloud.hcp.dto.OrdenCompraRespuestaDto;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.enums.OrdenCompraAprobacionEnum;
import com.incloud.hcp.exception.InvalidOptionException;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.service.OrdenCompraService;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.incloud.hcp.domain.OrdenCompra;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/OrdenCompra")
public class OrdenCompraRest {

    private static final String OPCION_INVALIDA = "'%s' no es una opción valida. Las opciones aceptadas son '%s' y '%s'.";
    private static final String RECHAZO_INVALIDO= "La opción '%s' es valida. Pero, el texto rechazo no puede ser vacio o nulo.";
    private OrdenCompraService ordenCompraService;
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    public OrdenCompraRest(OrdenCompraService ordenCompraService,
                           OrdenCompraRepository ordenCompraRepository) {
        this.ordenCompraService = ordenCompraService;
        this.ordenCompraRepository = ordenCompraRepository;
    }

    @GetMapping(value = "/getOrdenCompraList/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<OrdenCompra>> getOrdenCompraList(
            @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc){
        try{
            List<OrdenCompra> ordenCompraList = ordenCompraService.getOrdenCompraListPorFechasAndRuc(fechaInicio, fechaFin, ruc);

            if(ordenCompraList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ordenCompraList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PutMapping(value = "/ActualizarFechaVisualizacionById/{idOrdenCompra}")
    public ResponseEntity<OrdenCompraRespuestaDto> actualizarFechaVisualizacionById(@PathVariable("idOrdenCompra") Integer idOrdenCompra){
        try{
            OrdenCompraRespuestaDto ordenCompra = ordenCompraService.updateOrdenCompraFechaVisualizacion(idOrdenCompra);
            if(ordenCompra != null){
                return  new ResponseEntity<>(ordenCompra, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
       } catch (Exception e) {
               String error = Utils.obtieneMensajeErrorException(e);
               throw new RuntimeException(error);
        }
    }

    @PutMapping(value = "/AprobarRechazarOrdenCompra/{idOrdenCompra}/{aprobarRechazar}")
    public ResponseEntity<OrdenCompraRespuestaDto> aprobarRechazarOrdenCompra(@PathVariable("idOrdenCompra") Integer idOrdenCompra,
                                                                              @PathVariable("aprobarRechazar") OrdenCompraAprobacionEnum ordenCompraAprobacionEnum,
                                                                              @RequestBody(required = false) String textoRechazo){
        String opcion = ordenCompraAprobacionEnum.toString().trim().toUpperCase();

        if (!opcion.equals(OrdenCompraAprobacionEnum.APROBAR.toString()) && !opcion.equals(OrdenCompraAprobacionEnum.RECHAZAR.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OrdenCompraAprobacionEnum.APROBAR.toString(), OrdenCompraAprobacionEnum.RECHAZAR.toString()));

        if(opcion.equals(OrdenCompraAprobacionEnum.RECHAZAR.toString()) && (textoRechazo == null || textoRechazo.isEmpty())){
            throw new InvalidOptionException(String.format(RECHAZO_INVALIDO, opcion));
        }

        int idAprobacionEnum = ordenCompraAprobacionEnum.getId();

       try {
           OrdenCompraRespuestaDto ordenCompraAprobacionRechazo = ordenCompraService.aprobarRechazarOrdenCompra(idOrdenCompra, idAprobacionEnum, textoRechazo);
            if(ordenCompraAprobacionRechazo != null){
                return new ResponseEntity<>(ordenCompraAprobacionRechazo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/getOrdenCompraActivaByNumero/{numOrdenCompra}")
    public ResponseEntity<OrdenCompra> getOrdenCompraActivaByNumero(@PathVariable("numOrdenCompra") String numOrdenCompra){
        try{
            Optional<OrdenCompra> opOrdenCompra = ordenCompraRepository.getOrdenCompraActivaByNumero(numOrdenCompra);
            if(opOrdenCompra.isPresent()){
                return  new ResponseEntity<>(opOrdenCompra.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "/getOrdenCompraActivaById/{idOrdenCompra}")
    public ResponseEntity<OrdenCompra> getOrdenCompraActivaById(@PathVariable("idOrdenCompra") Integer idOrdenCompra){
        try{
            Optional<OrdenCompra> opOrdenCompra = ordenCompraRepository.findByIdAndIsActive(idOrdenCompra);
            if(opOrdenCompra.isPresent()){
                return  new ResponseEntity<>(opOrdenCompra.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "extraerOrdenCompraMasivo/{fechaInicio}/{fechaFin}/{enviarCorreoPublicacionOpcion}")
    public ResponseEntity<Void> extraerOrdenCompraMasivo(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                         @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                         @PathVariable(value = "enviarCorreoPublicacionOpcion") OpcionGenericaEnum enviarCorreoPublicacionOpcion){
        String opcion = enviarCorreoPublicacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoPublicacion = OpcionGenericaEnum.NO.getValor();

        if (!opcion.equals(OpcionGenericaEnum.SI.toString()) && !opcion.equals(OpcionGenericaEnum.NO.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));

        if(opcion.equals(OpcionGenericaEnum.SI.toString()))
            enviarCorreoPublicacion = OpcionGenericaEnum.SI.getValor();

        try {
            ordenCompraService.extraerOrdenCompraMasivoByRangoFechas(fechaInicio, fechaFin, enviarCorreoPublicacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/getOrdenCompraPdfByNumero/{numeroOrdenCompra}")
    public ResponseEntity<String> getOrdenCompraPdfByNumero(@PathVariable("numeroOrdenCompra") String numeroOrdenCompra){
        if (numeroOrdenCompra == null || numeroOrdenCompra.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        try{
            Optional<OrdenCompra> opOrdenCompra = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroOrdenCompra);

            if(opOrdenCompra.isPresent()){
                return new ResponseEntity<>(ordenCompraService.getOrdenCompraPdfContent(numeroOrdenCompra),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "/getContratoMarcoPdfByNumero/{numeroContratoMarco}")
    public ResponseEntity<String> getContratoMarcoPdfByNumero(@PathVariable("numeroContratoMarco") String numeroContratoMarco){
        if (numeroContratoMarco == null || numeroContratoMarco.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        try{
            Optional<OrdenCompra> opOrdenCompra = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroContratoMarco);

            if(opOrdenCompra.isPresent()){
                return new ResponseEntity<>(ordenCompraService.getContratoMarcoPdfContent(numeroContratoMarco),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerContratoMarcoMasivo/{fechaInicio}/{fechaFin}/{enviarCorreoPublicacionOpcion}")
    public ResponseEntity<Void> extraerContratoMarcoMasivo(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                           @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                           @PathVariable(value = "enviarCorreoPublicacionOpcion") OpcionGenericaEnum enviarCorreoPublicacionOpcion){
        String opcion = enviarCorreoPublicacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoPublicacion = OpcionGenericaEnum.NO.getValor();

        if (!opcion.equals(OpcionGenericaEnum.SI.toString()) && !opcion.equals(OpcionGenericaEnum.NO.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));

        if(opcion.equals(OpcionGenericaEnum.SI.toString()))
            enviarCorreoPublicacion = OpcionGenericaEnum.SI.getValor();

        try {
            ordenCompraService.extraerContratoMarcoMasivoByRangoFechas(fechaInicio, fechaFin, enviarCorreoPublicacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}
