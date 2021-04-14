package com.incloud.hcp.rest;

import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.dto.InfoMessage;
import com.incloud.hcp.dto.OrdenCompraSapDataDto;
import com.incloud.hcp.dto.PrefacturaAnuladaRespuestaDto;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.exception.InvalidOptionException;
import com.incloud.hcp.jco.documentoAceptacion.dto.SapTableItemDto;
import com.incloud.hcp.jco.documentoAceptacion.service.JCODocumentoAceptacionService;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPublicarOneService;
import com.incloud.hcp.jco.peticionOferta.dto.PeticionOfertaRFCResponseDto;
import com.incloud.hcp.jco.peticionOferta.service.JCOPeticionOfertaService;
import com.incloud.hcp.jco.proveedor.dto.ProveedorRFCResponseDto;
import com.incloud.hcp.jco.proveedor.service.JCOProveedorService;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.service.PrefacturaService;
import com.incloud.hcp.service.extractor.ExtractorDocumentoAceptacionService;
import com.incloud.hcp.service.extractor.ExtractorOCService;
import com.incloud.hcp.util.StrUtils;
import com.incloud.hcp.util.Utils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/test")
public class TestServiceRest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String OPCION_INVALIDA = "'%s' no es una opción valida. Las opciones aceptadas son '%s' y '%s'.";

    private JCOOrdenCompraPublicarOneService jcoOrdenCompraPublicarOneService;
    private JCODocumentoAceptacionService jcoDocumentoAceptacionService;
    private PrefacturaService prefacturaService;
    private JCOProveedorService jcoProveedorService;
    private ProveedorRepository proveedorRepository;
    private JCOPeticionOfertaService jcoPeticionOfertaService;

    private ExtractorOCService extractorOCService;
    private ExtractorDocumentoAceptacionService extractorDocumentoAceptacionService;

    @Autowired
    public TestServiceRest(JCOOrdenCompraPublicarOneService jcoOrdenCompraPublicarOneService,
                           JCODocumentoAceptacionService jcoDocumentoAceptacionService,
                           PrefacturaService prefacturaService,
                           JCOProveedorService jcoProveedorService,
                           ProveedorRepository proveedorRepository,
                           JCOPeticionOfertaService jcoPeticionOfertaService,
                           ExtractorOCService extractorOCService,
                           ExtractorDocumentoAceptacionService extractorDocumentoAceptacionService) {
        this.jcoOrdenCompraPublicarOneService = jcoOrdenCompraPublicarOneService;
        this.jcoDocumentoAceptacionService = jcoDocumentoAceptacionService;
        this.prefacturaService = prefacturaService;
        this.jcoProveedorService = jcoProveedorService;
        this.proveedorRepository = proveedorRepository;
        this.jcoPeticionOfertaService = jcoPeticionOfertaService;
        this.extractorOCService = extractorOCService;
        this.extractorDocumentoAceptacionService = extractorDocumentoAceptacionService;
    }

    @PostMapping(value = "extraerOrdenCompraListManual/{fechaInicio}/{fechaFin}/{enviarCorreoPublicacionOpcion}")
    public ResponseEntity<Void> extraerOrdenCompraList(@PathVariable(value = "fechaInicio") String fechaInicio,
                                                       @PathVariable(value = "fechaFin") String fechaFin,
                                                       @PathVariable(value = "enviarCorreoPublicacionOpcion") OpcionGenericaEnum enviarCorreoPublicacionOpcion){
        String opcion = enviarCorreoPublicacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoPublicacion = OpcionGenericaEnum.NO.getValor();

        if (!opcion.equals(OpcionGenericaEnum.SI.toString()) && !opcion.equals(OpcionGenericaEnum.NO.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));

        if(opcion.equals(OpcionGenericaEnum.SI.toString()))
            enviarCorreoPublicacion = OpcionGenericaEnum.SI.getValor();

        try {
            extractorOCService.extraerOC(fechaInicio,fechaFin, false);
            //jcoOrdenCompraPublicacionService.extraerOrdenCompraListRFC(fechaInicio, fechaFin, enviarCorreoPublicacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerOneOrdenCompraManual/{numeroOrdenCompra}/{enviarCorreoPublicacionOpcion}")
    public ResponseEntity<InfoMessage> extraerOneOrdenCompra(@PathVariable(value = "numeroOrdenCompra") String numeroOrdenCompra,
                                                             @PathVariable(value = "enviarCorreoPublicacionOpcion") OpcionGenericaEnum enviarCorreoPublicacionOpcion){
        String opcion = enviarCorreoPublicacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoPublicacion = OpcionGenericaEnum.NO.getValor();

        if (!opcion.equals(OpcionGenericaEnum.SI.toString()) && !opcion.equals(OpcionGenericaEnum.NO.toString()))
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcion, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));

        if(opcion.equals(OpcionGenericaEnum.SI.toString()))
            enviarCorreoPublicacion = OpcionGenericaEnum.SI.getValor();

        try {
            InfoMessage infoMessage = jcoOrdenCompraPublicarOneService.extraerOneOrdenCompraRFC(numeroOrdenCompra, enviarCorreoPublicacion);
            return new ResponseEntity<>(infoMessage,HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "extraerDataOneOrdenCompra/{numeroOrdenCompra}")
    public ResponseEntity<OrdenCompraSapDataDto> extraerDataOneOrdenCompra(@PathVariable(value = "numeroOrdenCompra") String numeroOrdenCompra){
        try {
            OrdenCompraSapDataDto ordenCompraSapDataDto = jcoOrdenCompraPublicarOneService.extraerDataOneOrdenCompraRFC(numeroOrdenCompra);
            return new ResponseEntity<>(ordenCompraSapDataDto,HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerDocumentoAceptacionListManual/{fechaInicio}/{fechaFin}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<Void> extraerDocumentoAceptacionList(@PathVariable(value = "fechaInicio") String fechaInicio,
                                                               @PathVariable(value = "fechaFin") String fechaFin,
                                                               @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                               @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion){
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else{
            if(opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else{
            if(opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            extractorDocumentoAceptacionService.extraerDocumentoAceptacion(fechaInicio, fechaFin, enviarCorreoAprobacion);
            //jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(fechaInicio, fechaFin, false, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }


    @GetMapping(value = "extraerDataDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion")
    public ResponseEntity<List<SapTableItemDto>> extraerDataDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion(@RequestParam(value = "numeroOrdenCompra") String numeroOrdenCompra,
                                                                                                                  @RequestParam(value = "numeroDocumentoAceptacion", required = false) String numeroDocumentoAceptacion,
                                                                                                                  @RequestParam(value = "unicoDocumentoAceptacionOpcion") OpcionGenericaEnum unicoDocumentoAceptacionOpcion) {
        String opcionUnicoDocAcep = unicoDocumentoAceptacionOpcion.toString().trim().toUpperCase();
        boolean unicoDocumentoAceptacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionUnicoDocAcep.equals(OpcionGenericaEnum.SI.toString()) && !opcionUnicoDocAcep.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionUnicoDocAcep, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else{
            if(opcionUnicoDocAcep.equals(OpcionGenericaEnum.SI.toString()))
                unicoDocumentoAceptacion = OpcionGenericaEnum.SI.getValor();
        }
        try {
            List<SapTableItemDto> sapTableItemDtoList = jcoDocumentoAceptacionService.extraerDataDocumentoAceptacionRFC(numeroOrdenCompra, numeroDocumentoAceptacion, unicoDocumentoAceptacion);
            return new ResponseEntity<>(sapTableItemDtoList, HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "actualizarPrefacturasAnuladasEnSapByRangoFechasManual/{fechaInicio}/{fechaFin}")
    public ResponseEntity<PrefacturaAnuladaRespuestaDto> actualizarPrefacturasAnuladasManual(@PathVariable(value = "fechaInicio") String fechaInicio,
                                                                                             @PathVariable(value = "fechaFin") String fechaFin) {
        try {
            PrefacturaAnuladaRespuestaDto respuestaDto = prefacturaService.actualizarPrefacturasAnuladasPorRangoFechas(fechaInicio, fechaFin, true);
            return new ResponseEntity<>(respuestaDto, HttpStatus.OK);
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }

    @GetMapping(value = "getNetworkData")
    public ResponseEntity<List<String>> getNetworkData() {
        List<String> responseList = new ArrayList<>();

        InetAddress ip;
        String hostname;
        String port;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();

            responseList.add("IP : " + ip);
            responseList.add("HOSTNAME : " + hostname);

            return new ResponseEntity<>(responseList, HttpStatus.OK);

        } catch (UnknownHostException e) {
            logger.error("UnknownHostException : " + e.getMessage() + " -- " + e.getCause());
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException (error);
        }
    }

    @PostMapping(value = "crearProveedorEnSap/{ruc}")
    public ResponseEntity<ProveedorRFCResponseDto> crearProveedorEnSap(@PathVariable(value = "ruc") String ruc) {
        try {
            ProveedorRFCResponseDto proveedorResponse = new ProveedorRFCResponseDto();
            Proveedor proveedor = this.proveedorRepository.getProveedorByRuc(ruc);

            if(proveedor != null) {
            /* Agregando Proveedor Potencial para envio a crear en SAP */
                String codigoEmpleadoSap = "";
                proveedorResponse = this.jcoProveedorService.grabarUnicoProveedorSAP(proveedor, codigoEmpleadoSap);
            }

            return new ResponseEntity<>(proveedorResponse, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    /* Peticion de Oferta */
    @ApiOperation(value = "Busca Peticion de Oferta en base al Numero de Solicitud o Numero de Licitacion SAP", produces = "application/json")
    @GetMapping(value = "/getPeticionOfertaListByCodigo/{codigo}/{fullListOption}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PeticionOfertaRFCResponseDto> getSolpedByCodigo(@PathVariable(value = "codigo") String codigo,
                                                                          @PathVariable(value = "fullListOption") OpcionGenericaEnum fullListOption) throws URISyntaxException {

        String opcion = fullListOption.toString().trim().toUpperCase();
        boolean obtainFullPositionList = opcion.equals(OpcionGenericaEnum.SI.toString()) ? OpcionGenericaEnum.SI.getValor() : OpcionGenericaEnum.NO.getValor();

        logger.debug("Find by id getPeticionOfertaByCodigo : {}", codigo);
        try {
            return Optional.ofNullable(jcoPeticionOfertaService.getPeticionOfertaResponseByCodigo(codigo, obtainFullPositionList))
                    .map(l -> new ResponseEntity<>(l, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            String error = StrUtils.obtieneMensajeErrorExceptionCustom(e);
            throw new RuntimeException(error);
        }
    }
}