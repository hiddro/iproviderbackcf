package com.incloud.hcp.rest;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Sociedad;
import com.incloud.hcp.enums.OpcionGenericaEnum;
import com.incloud.hcp.exception.InvalidOptionException;
import com.incloud.hcp.pdf.bean.FieldConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.FieldEntradaMercaderiaPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;
import com.incloud.hcp.pdf.service.PdfGeneratorService;
import com.incloud.hcp.service.DocumentoAceptacionService;
import com.incloud.hcp.service.OrdenCompraService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.SociedadService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.incloud.hcp.domain.DocumentoAceptacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/DocumentoAceptacion")
public class DocumentoAceptacionRest {

    private static final String OPCION_INVALIDA = "'%s' no es una opción valida. Las opciones aceptadas son '%s' y '%s'.";

    private DocumentoAceptacionService documentoAceptacionService;
    private OrdenCompraService ordenCompraService;
    private SociedadService sociedadService;
    private ProveedorService proveedorService;

    @Autowired
    public DocumentoAceptacionRest(DocumentoAceptacionService documentoAceptacionService, OrdenCompraService ordenCompraService, SociedadService sociedadService, ProveedorService proveedorService) {
        this.documentoAceptacionService = documentoAceptacionService;
        this.ordenCompraService = ordenCompraService;
        this.sociedadService = sociedadService;
        this.proveedorService = proveedorService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<DocumentoAceptacion>> getAllDocumentoAceptacion() {
        List<DocumentoAceptacion> documentoAceptacionList = documentoAceptacionService.getAllDocumentoAceptacion();

        if (documentoAceptacionList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(documentoAceptacionList, HttpStatus.OK);
    }

    @GetMapping(value = "/getDocumentoAceptacionList/{FechaInicio}/{FechaFin}")
    public ResponseEntity<List<DocumentoAceptacion>> getDocumentoAceptacionList(
            @PathVariable("FechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
            @PathVariable("FechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
            @RequestParam(value = "ruc", required = false) String ruc) {
        try {
            List<DocumentoAceptacion> documentoAceptacionList = documentoAceptacionService.getDocumentoAceptacionPorFechasAndRuc(fechaInicio, fechaFin, ruc);

            if (documentoAceptacionList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documentoAceptacionList, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerDocumentoAceptacionMasivo/{fechaInicio}/{fechaFin}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<Void> extraerDocumentoAceptacionMasivo(@PathVariable(value = "fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio,
                                                                 @PathVariable(value = "fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin,
                                                                 @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                                 @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion) {
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            LocalDate localDateFechaInicio = DateUtils.utilDateToLocalDate(fechaInicio);
            LocalDate localDateFechaFin = DateUtils.utilDateToLocalDate(fechaFin);

            documentoAceptacionService.extraerDocumentoAceptacionMasivoByRangoFechas(localDateFechaInicio, localDateFechaFin, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "extraerDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion/{numeroOrdenCompra}/{numeroDocumentoAceptacion}/{aprobarOrdenCompraOpcion}/{enviarCorreoAprobacionOpcion}")
    public ResponseEntity<String> extraerDocumentoAceptacionPorNumOrdenCompraYNumDocAceptacion(@PathVariable(value = "numeroOrdenCompra") String numeroOrdenCompra,
                                                                                     @PathVariable(value = "numeroDocumentoAceptacion") String numeroDocumentoAceptacion,
                                                                                     @PathVariable(value = "aprobarOrdenCompraOpcion") OpcionGenericaEnum aprobarOrdenCompraOpcion,
                                                                                     @PathVariable(value = "enviarCorreoAprobacionOpcion") OpcionGenericaEnum enviarCorreoAprobacionOpcion) {
        String opcionAprobarOC = aprobarOrdenCompraOpcion.toString().trim().toUpperCase();
        boolean aprobarOrdenCompra = OpcionGenericaEnum.NO.getValor();

        if (!opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()) && !opcionAprobarOC.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionAprobarOC, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionAprobarOC.equals(OpcionGenericaEnum.SI.toString()))
                aprobarOrdenCompra = OpcionGenericaEnum.SI.getValor();
        }

        String opcionEnviarCorreo = enviarCorreoAprobacionOpcion.toString().trim().toUpperCase();
        boolean enviarCorreoAprobacion = OpcionGenericaEnum.NO.getValor();

        if (!opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()) && !opcionEnviarCorreo.equals(OpcionGenericaEnum.NO.toString())) {
            throw new InvalidOptionException(String.format(OPCION_INVALIDA, opcionEnviarCorreo, OpcionGenericaEnum.SI.toString(), OpcionGenericaEnum.NO.toString()));
        }else {
            if (opcionEnviarCorreo.equals(OpcionGenericaEnum.SI.toString()))
                enviarCorreoAprobacion = OpcionGenericaEnum.SI.getValor();
        }

        try {
            String respuesta = documentoAceptacionService.extraerDocumentoAceptacionByNumOrdenCompraAndNumDocAceptacion(numeroOrdenCompra, numeroDocumentoAceptacion, aprobarOrdenCompra, enviarCorreoAprobacion);
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }


    @PostMapping(value = "entregaMercaderiaPdf/{idDocumentoAceptacion}")
    public String getEntregaMercaderiaPdf(@PathVariable(value = "idDocumentoAceptacion") Integer idEntregaMercaderia) {
        try {
            DocumentoAceptacion documentoAceptacion = documentoAceptacionService.getDocumentoAceptacionbyId(1, idEntregaMercaderia);
            OrdenCompra ordenCompra;
            if (documentoAceptacion.getIdOrdenCompra() != null) {
                ordenCompra = ordenCompraService.getOrdenCompraById(documentoAceptacion.getIdOrdenCompra());
            } else {
                throw new NullPointerException("El id del documento de aceptación no es válido.");
            }

            ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO = new ParameterEntradaMercaderiaPdfDTO();
            Proveedor proveedor = Optional
                    .ofNullable(proveedorService.getProveedorByRuc(documentoAceptacion.getProveedorRuc()))
                    .orElse(new Proveedor());

            parameterEntradaMercaderiaPdfDTO.setNroRuc(documentoAceptacion.getProveedorRuc());
            parameterEntradaMercaderiaPdfDTO.setNroGuia(documentoAceptacion.getNumeroGuiaProveedor());
            parameterEntradaMercaderiaPdfDTO.setRucCliente(ordenCompra.getInfoSociedad().getRuc());
            parameterEntradaMercaderiaPdfDTO.setFechaEmision(DateUtils.utilDateToString(documentoAceptacion.getFechaEmision()));
            parameterEntradaMercaderiaPdfDTO.setRazonSocialCliente(ordenCompra.getInfoSociedad().getRazonSocial());
            parameterEntradaMercaderiaPdfDTO.setDocumentoMaterial(documentoAceptacion.getNumeroDocumentoAceptacion());
            parameterEntradaMercaderiaPdfDTO.setDescripcionProveedor(documentoAceptacion.getProveedorRazonSocial());
            parameterEntradaMercaderiaPdfDTO.setUbicacionProveedor(proveedor.getDireccionFiscal() != null ? proveedor.getDireccionFiscal() : "");
            parameterEntradaMercaderiaPdfDTO.setTelefonoProveedor(proveedor.getTelefono() != null ? proveedor.getTelefono() : "");

            List<FieldEntradaMercaderiaPdfDTO> fieldEntradaMercaderiaPdfList = new ArrayList<>();

            if (documentoAceptacion.getDocumentoAceptacionDetalleList() != null && documentoAceptacion.getDocumentoAceptacionDetalleList().size() > 0) {
                documentoAceptacion.getDocumentoAceptacionDetalleList().forEach(documentoAceptacionDetalle -> {
                    FieldEntradaMercaderiaPdfDTO fieldEntradaMercaderiaPdf = new FieldEntradaMercaderiaPdfDTO();

                    fieldEntradaMercaderiaPdf.setNroItem(documentoAceptacionDetalle.getNumeroItem().toString());
                    fieldEntradaMercaderiaPdf.setNroOC(documentoAceptacionDetalle.getNumeroOrdenCompra());
                    fieldEntradaMercaderiaPdf.setNroItemOC(documentoAceptacionDetalle.getPosicionOrdenCompra());
                    fieldEntradaMercaderiaPdf.setCodigoProducto(documentoAceptacionDetalle.getCodigoSapBienServicio().replaceFirst("^0+(?!$)", ""));
                    fieldEntradaMercaderiaPdf.setDescripcionProducto(documentoAceptacionDetalle.getDescripcionBienServicio());
                    fieldEntradaMercaderiaPdf.setCantAceptableCliente(documentoAceptacionDetalle.getCantidadAceptadaCliente().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    fieldEntradaMercaderiaPdf.setUndMedida(documentoAceptacionDetalle.getUnidadMedida());
                    fieldEntradaMercaderiaPdf.setCantPedientePedido(documentoAceptacionDetalle.getCantidadPendiente().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    fieldEntradaMercaderiaPdf.setUndMedidaPedido(documentoAceptacionDetalle.getUnidadMedida());

                    fieldEntradaMercaderiaPdfList.add(fieldEntradaMercaderiaPdf);
                });
            }
            parameterEntradaMercaderiaPdfDTO.setFieldEntradaMercaderiaPdfDTOList(fieldEntradaMercaderiaPdfList);
            return documentoAceptacionService.getEntregaMercaderiaGenerateContent(parameterEntradaMercaderiaPdfDTO);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }

    @PostMapping(value = "conformidadServicioPdf/{idDocumentoAceptacion}")
    public String getConformidadServicioPdf(@PathVariable(value = "idDocumentoAceptacion") Integer idConformidadServicio) {
        try {
            DocumentoAceptacion documentoAceptacion = documentoAceptacionService.getDocumentoAceptacionbyId(2, idConformidadServicio);
            OrdenCompra ordenCompra;
            if (documentoAceptacion.getIdOrdenCompra() != null) {
                ordenCompra = ordenCompraService.getOrdenCompraById(documentoAceptacion.getIdOrdenCompra());
            } else {
                throw new NullPointerException("El id del documento de aceptación no es válido.");
            }

            ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO = new ParameterConformidadServicioPdfDTO();

            parameterConformidadServicioPdfDTO.setRucCliente(ordenCompra.getInfoSociedad().getRuc());
            parameterConformidadServicioPdfDTO.setUbicacionCliente(ordenCompra.getInfoSociedad().getDireccionFiscal());
            parameterConformidadServicioPdfDTO.setDescripcionCliente(ordenCompra.getInfoSociedad().getRazonSocial());
            parameterConformidadServicioPdfDTO.setTelefonoCliente(ordenCompra.getInfoSociedad().getTelefono());
            parameterConformidadServicioPdfDTO.setNroConformidadServicio(documentoAceptacion.getNumeroDocumentoAceptacion());
            parameterConformidadServicioPdfDTO.setRucProveedor(documentoAceptacion.getProveedorRuc());
            parameterConformidadServicioPdfDTO.setFechaEmision(DateUtils.utilDateToString(documentoAceptacion.getFechaEmision()));
            parameterConformidadServicioPdfDTO.setRazonSocialProveedor(documentoAceptacion.getProveedorRazonSocial());
            parameterConformidadServicioPdfDTO.setTipoMoneda(documentoAceptacion.getCodigoMoneda());
            parameterConformidadServicioPdfDTO.setRecepcionPersona(documentoAceptacion.getUsuarioSapRecepcion());
            parameterConformidadServicioPdfDTO.setAutorPersona(documentoAceptacion.getUsuarioSapAutoriza());
            parameterConformidadServicioPdfDTO.setFechaAcept(DateUtils.utilDateToString(documentoAceptacion.getFechaAceptacion()));

            List<FieldConformidadServicioPdfDTO> fieldConformidadServicioPdfList = new ArrayList<>();

            if (documentoAceptacion.getDocumentoAceptacionDetalleList() != null && documentoAceptacion.getDocumentoAceptacionDetalleList().size() > 0) {
                Integer[] nroItem = {0};
                documentoAceptacion.getDocumentoAceptacionDetalleList().forEach(documentoAceptacionDetalle -> {
                    nroItem[0]++;
                    FieldConformidadServicioPdfDTO fieldConformidadServicioPdf = new FieldConformidadServicioPdfDTO();

                    fieldConformidadServicioPdf.setNroItem(nroItem[0].toString());
                    fieldConformidadServicioPdf.setNroOrdenServicio(documentoAceptacionDetalle.getNumeroOrdenCompra());
                    fieldConformidadServicioPdf.setNroItemOrdenServicio(documentoAceptacion.getPosicionOrdenCompra());
                    fieldConformidadServicioPdf.setDescripcionServicio(documentoAceptacionDetalle.getDescripcionBienServicio());
                    fieldConformidadServicioPdf.setCantidad(documentoAceptacionDetalle.getCantidadAceptadaCliente().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    fieldConformidadServicioPdf.setUnidad(documentoAceptacionDetalle.getUnidadMedida());
                    fieldConformidadServicioPdf.setValorRecibido(documentoAceptacionDetalle.getValorRecibido().setScale(2, BigDecimal.ROUND_HALF_UP).toString());

                    fieldConformidadServicioPdfList.add(fieldConformidadServicioPdf);
                });
            }
            parameterConformidadServicioPdfDTO.setFieldConformidadServicioPdfDTOList(fieldConformidadServicioPdfList);
            return documentoAceptacionService.getConformidadServicioGenerateContent(parameterConformidadServicioPdfDTO);
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);
            throw new RuntimeException(error);
        }
    }
}

