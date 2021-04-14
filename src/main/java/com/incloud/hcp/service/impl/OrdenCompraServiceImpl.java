package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.domain.OrdenCompraDetalle;
import com.incloud.hcp.domain.Proveedor;
import com.incloud.hcp.domain.Usuario;
import com.incloud.hcp.dto.OrdenCompraRespuestaDto;
import com.incloud.hcp.enums.OrdenCompraEstadoEnum;
import com.incloud.hcp.jco.contratoMarco.dto.ContratoMarcoPdfSapDto;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPdfService;
import com.incloud.hcp.jco.contratoMarco.service.JCOContratoMarcoPublicacionService;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPdfDto;
import com.incloud.hcp.jco.ordenCompra.dto.OrdenCompraPosicionPdfDto;
import com.incloud.hcp.jco.ordenCompra.service.JCOOrdenCompraPdfService;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.repository.OrdenCompraRepository;
import com.incloud.hcp.repository.ProveedorRepository;
import com.incloud.hcp.repository.UsuarioRepository;
import com.incloud.hcp.service.OrdenCompraDetalleService;
import com.incloud.hcp.service.OrdenCompraService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.extractor.ExtractorOCService;
import com.incloud.hcp.service.notificacion.ContactoAprobadaRechazadaOCNotificacion;
import com.incloud.hcp.service.notificacion.ContactoVisualizadoOCNotificacion;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrdenCompraServiceImpl implements OrdenCompraService {

    private OrdenCompraRepository ordenCompraRepository;
    private ContactoVisualizadoOCNotificacion contactoVisualizadoOCNotificacion;
    private ContactoAprobadaRechazadaOCNotificacion contactoAprobadaRechazadaOCNotificacion;
    private ProveedorRepository proveedorRepository;
    private UsuarioRepository usuarioRepository;
    private JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService;
    private JCOOrdenCompraPdfService jcoOrdenCompraPdfService;
    private OrdenCompraDetalleService ordenCompraDetalleService;
    private JCOContratoMarcoPdfService jcoContratoMarcoPdfService;
    private ProveedorService proveedorService;

    private ExtractorOCService extractorOCService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrdenCompraServiceImpl(OrdenCompraRepository ordenCompraRepository,
                                  ContactoVisualizadoOCNotificacion contactoVisualizadoOCNotificacion,
                                  ContactoAprobadaRechazadaOCNotificacion contactoAprobadaRechazadaOCNotificacion,
                                  ProveedorRepository proveedorRepository,
                                  UsuarioRepository usuarioRepository,
                                  JCOContratoMarcoPublicacionService jcoContratoMarcoPublicacionService,
                                  JCOOrdenCompraPdfService jcoOrdenCompraPdfService,
                                  OrdenCompraDetalleService ordenCompraDetalleService,
                                  JCOContratoMarcoPdfService jcoContratoMarcoPdfService,
                                  ProveedorService proveedorService,
                                  ExtractorOCService extractorOCService) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.contactoVisualizadoOCNotificacion = contactoVisualizadoOCNotificacion;
        this.contactoAprobadaRechazadaOCNotificacion = contactoAprobadaRechazadaOCNotificacion;
        this.proveedorRepository = proveedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.jcoContratoMarcoPublicacionService = jcoContratoMarcoPublicacionService;
        this.jcoOrdenCompraPdfService = jcoOrdenCompraPdfService;
        this.ordenCompraDetalleService = ordenCompraDetalleService;
        this.jcoContratoMarcoPdfService = jcoContratoMarcoPdfService;
        this.proveedorService = proveedorService;
        this.extractorOCService= extractorOCService;
    }

    @Override
    public List<OrdenCompra> getAllOrdenCompra() {
        return ordenCompraRepository.getAllActive();
    }

    @Override
    public OrdenCompra getOrdenCompraById(Integer idOrdenCompra) {
        return ordenCompraRepository.getOrdenCompraById(idOrdenCompra);
    }


    @Override
    public List<OrdenCompra> getOrdenCompraListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc) {

        if (ruc == null || ruc.isEmpty()) {
            return ordenCompraRepository.getOrdenCompraByFechaRegistroBetween(fechaInicio, fechaFin);
        } else {
            return ordenCompraRepository.getOrdenCompraByFechaRegistroBetweenAndProveedorRuc(fechaInicio, fechaFin, ruc);
        }
    }

    @Override
    @Transactional
    /* En este metodo se guarda la fecha de actualización cuando se abre por primera vez la orden de compra
     * Además, se cambia de estado de orden de compra a '2: Visualiza'*/
    public OrdenCompraRespuestaDto updateOrdenCompraFechaVisualizacion(Integer idOrdenCompra) {
        OrdenCompraRespuestaDto ordenCompraRespuestaDto = new OrdenCompraRespuestaDto();
        List<String> mensajes = new ArrayList<>();
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findByIdAndIsActive(idOrdenCompra);
        OrdenCompra ordenCompra;

        if (ordenCompraOptional.isPresent()) {
            ordenCompra = ordenCompraOptional.get();

            if (ordenCompra.getFechaVisualizacion() == null) {
                ordenCompra.setFechaVisualizacion(DateUtils.getCurrentTimestamp());

                if (ordenCompra.getIdEstadoOrdenCompra().compareTo(OrdenCompraEstadoEnum.ACTIVA.getId()) == 0) {
                    ordenCompra.setIdEstadoOrdenCompra(OrdenCompraEstadoEnum.VISUALIZADA.getId());
                }
                ordenCompra = ordenCompraRepository.save(ordenCompra);
                ordenCompraRespuestaDto.setOrdenCompra(ordenCompra);

                /*Enviando Correo*/
                Usuario comprador = usuarioRepository.findByCodigoSap(ordenCompra.getCompradorUsuarioSap());
                if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                    contactoVisualizadoOCNotificacion.enviar(ordenCompra, null, comprador);
                else
                    mensajes.add("Error al obtener los datos del comprador para envio de correo.");

                Proveedor proveedor = proveedorService.getProveedorByRuc(ordenCompra.getProveedorRuc());
                Usuario proveedorUsuario = null;
                if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                    proveedorUsuario = new Usuario();
                    proveedorUsuario.setEmail(proveedor.getEmail());
                    proveedorUsuario.setApellido(proveedor.getRazonSocial());
                }
                else{
                    List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenCompra.getProveedorRuc());
                    if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                        proveedorUsuario = posibleProveedorList.get(0);
                }

                if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                    contactoVisualizadoOCNotificacion.enviar(ordenCompra, proveedorUsuario, null);
                else
                    mensajes.add("Error al obtener los datos del proveedor para envio de correo.");

                ordenCompraRespuestaDto.setMensajes(mensajes);
            }
        }
        return ordenCompraRespuestaDto;
    }

    @Override
    public OrdenCompraRespuestaDto aprobarRechazarOrdenCompra(Integer idOrdenCompra, int estado, String textoRechazo) {
        OrdenCompraRespuestaDto ordenCompraRespuestaDto = new OrdenCompraRespuestaDto();
        Optional<OrdenCompra> ordenCompraOptional = ordenCompraRepository.findByIdAndIsActive(idOrdenCompra);
        OrdenCompra ordenCompra;
        List<String> mensajes = new ArrayList<>();

        if (ordenCompraOptional.isPresent()) {
            ordenCompra = ordenCompraOptional.get();

            ordenCompra.setIdEstadoOrdenCompra(estado);
            ordenCompra.setFechaAprobacion(DateUtils.getCurrentTimestamp());

            if (estado == OrdenCompraEstadoEnum.RECHAZADA.getId()) {
                ordenCompra.setMotivoRechazo(textoRechazo);
            }
            logger.error("OC " +  ordenCompra.getNumeroOrdenCompra() +  " APROB/RECHAZO estado (antes) :" + ordenCompra.getIdEstadoOrdenCompra());
            ordenCompra = ordenCompraRepository.save(ordenCompra);
            logger.error("OC " +  ordenCompra.getNumeroOrdenCompra() +  " APROB/RECHAZO estado (despues) :" + ordenCompra.getIdEstadoOrdenCompra());
            ordenCompraRespuestaDto.setOrdenCompra(ordenCompra);


            /*Enviando Correo*/
            Usuario comprador = usuarioRepository.findByCodigoSap(ordenCompra.getCompradorUsuarioSap());
            if (comprador != null && comprador.getEmail() != null && !comprador.getEmail().isEmpty())
                contactoAprobadaRechazadaOCNotificacion.enviar(ordenCompra, null, comprador);
            else
                mensajes.add("Error al obtener los datos del comprador para envio de correo de aprobacion de orden de compra.");

            Proveedor proveedor = proveedorService.getProveedorByRuc(ordenCompra.getProveedorRuc());
            Usuario proveedorUsuario = null;
            if(proveedor != null && proveedor.getEmail() != null && !proveedor.getEmail().isEmpty()){
                proveedorUsuario = new Usuario();
                proveedorUsuario.setEmail(proveedor.getEmail());
                proveedorUsuario.setApellido(proveedor.getRazonSocial());
            }
            else{
                List<Usuario> posibleProveedorList = usuarioRepository.findByCodigoUsuarioIdp(ordenCompra.getProveedorRuc());
                if (posibleProveedorList != null && !posibleProveedorList.isEmpty() && posibleProveedorList.size() == 1)
                    proveedorUsuario = posibleProveedorList.get(0);
            }

            if(proveedorUsuario != null && proveedorUsuario.getEmail() != null && !proveedorUsuario.getEmail().isEmpty())
                contactoAprobadaRechazadaOCNotificacion.enviar(ordenCompra, proveedorUsuario, null);
            else
                mensajes.add("Error al obtener los datos del proveedor para envio de correo de aprobacion de orden de compra.");

            ordenCompraRespuestaDto.setMensajes(mensajes);
        }
        return ordenCompraRespuestaDto;
    }


    @Override
    public void extraerOrdenCompraMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion){
        LocalDate currentlyExtractLocalDate = DateUtils.utilDateToLocalDate(fechaInicio);
        LocalDate finalLocalDate = DateUtils.utilDateToLocalDate(fechaFin);
        logger.info("EXTRACCION ORDEN_COMPRA MASIVA - FECHA INICIO: " + currentlyExtractLocalDate.toString());
        logger.info("EXTRACCION ORDEN_COMPRA MASIVA - FECHA FIN: " + finalLocalDate.toString());

        while (currentlyExtractLocalDate.isBefore(finalLocalDate.plusDays(1))){
            try {
                String currentDateAsString = DateUtils.localDateToStringPattern(currentlyExtractLocalDate, DateUtils.STANDARD_DATE_FORMAT);
                extractorOCService.extraerOC(currentDateAsString,currentDateAsString, false);
                //String currentDateAsSapString = DateUtils.localDateToSapString(currentlyExtractLocalDate);
                //jcoOrdenCompraPublicacionService.extraerOrdenCompraListRFC(currentDateAsSapString, currentDateAsSapString, enviarCorreoPublicacion);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer ordenes de compra de la fecha " + DateUtils.localDateToString(currentlyExtractLocalDate) + " : " + error);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
        }
    }


    @Override
    public void extraerContratoMarcoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion){
        LocalDate currentlyExtractLocalDate = DateUtils.utilDateToLocalDate(fechaInicio);
        LocalDate finalLocalDate = DateUtils.utilDateToLocalDate(fechaFin);
        logger.error("EXTRACCION CONTRATO_MARCO MASIVA - FECHA INICIO: " + currentlyExtractLocalDate.toString());
        logger.error("EXTRACCION CONTRATO_MARCO MASIVA - FECHA FIN: " + finalLocalDate.toString());

        while (currentlyExtractLocalDate.isBefore(finalLocalDate.plusDays(1))){
            try {
                String currentDateAsSapString = DateUtils.localDateToSapString(currentlyExtractLocalDate);
                jcoContratoMarcoPublicacionService.extraerContratoMarcoListRFC(currentDateAsSapString, currentDateAsSapString, enviarCorreoPublicacion);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer contratos marco de la fecha " + DateUtils.localDateToString(currentlyExtractLocalDate) + " : " + error);
                currentlyExtractLocalDate = currentlyExtractLocalDate.plusDays(1);
            }
        }
    }


    @Override
    public String getOrdenCompraPdfContent(String numeroOrdenCompra) throws Exception{
        OrdenCompraPdfDto ordenCompraPdfDto = jcoOrdenCompraPdfService.extraerOrdenCompraPdfDtoRFC(numeroOrdenCompra);
        byte[] generateOrdenCompraBytes = PdfGeneratorFactory.getJasperGenerator().generateOrdenCompraPdfBytes(ordenCompraPdfDto);
        return Base64.getEncoder().encodeToString(generateOrdenCompraBytes);
    }


    @Override
    public String getContratoMarcoPdfContent(String numeroContratoMarco) throws Exception{
        OrdenCompraPdfDto contratoMarcoPdfDto = new OrdenCompraPdfDto();
        Optional<OrdenCompra> optionalContratoMarco = ordenCompraRepository.getOrdenCompraActivaByNumero(numeroContratoMarco);

        if(optionalContratoMarco.isPresent()){
            OrdenCompra contratoMarco = optionalContratoMarco.get();
            ContratoMarcoPdfSapDto contratoMarcoPdfSapDto = jcoContratoMarcoPdfService.extraerContratoMarcoPdfDtoRFC(numeroContratoMarco);


            contratoMarcoPdfDto.setOrdenCompraNumero(contratoMarco.getNumeroOrdenCompra());
            contratoMarcoPdfDto.setOrdenCompraTipo(contratoMarco.getTipoOrdenCompra().getDescripcion());
            contratoMarcoPdfDto.setOrdenCompraVersion(String.valueOf(contratoMarco.getVersion()));
            contratoMarcoPdfDto.setOrdenCompraFechaCreacion(contratoMarco.getFechaRegistro());
            contratoMarcoPdfDto.setOrdenCompraFormaPago(contratoMarco.getCondicionPagoDescripcion());
            contratoMarcoPdfDto.setOrdenCompraMoneda(contratoMarco.getCodigoMondeda());
//            contratoMarcoPdfDto.setOrdenCompraAutorizador(contratoMarco.getAutorizadorFechaLiberacion());
            contratoMarcoPdfDto.setOrdenCompraAutorizador(contratoMarcoPdfSapDto.getClienteAutorizador());
            contratoMarcoPdfDto.setOrdenCompraPersonaContacto(contratoMarcoPdfSapDto.getClientePersonaContacto());

            contratoMarcoPdfDto.setClienteRuc(contratoMarco.getInfoSociedad().getRuc());
            contratoMarcoPdfDto.setClienteTelefono(contratoMarco.getInfoSociedad().getTelefono());
            contratoMarcoPdfDto.setClienteDireccion(contratoMarco.getInfoSociedad().getDireccionFiscal());
            contratoMarcoPdfDto.setClienteRazonSocial(contratoMarco.getInfoSociedad().getRazonSocial());

            contratoMarcoPdfDto.setProveedorRazonSocial(contratoMarco.getProveedorRazonSocial());
            contratoMarcoPdfDto.setProveedorRuc(contratoMarco.getProveedorRuc());
            if(contratoMarco.getProveedorCodigoSap() != null && !contratoMarco.getProveedorCodigoSap().isEmpty())
                contratoMarcoPdfDto.setProveedorNumero(String.valueOf(Integer.parseInt(contratoMarco.getProveedorCodigoSap())));
            contratoMarcoPdfDto.setProveedorDireccion(contratoMarcoPdfSapDto.getProveedorDireccion());
            contratoMarcoPdfDto.setProveedorContactoNombre(contratoMarcoPdfSapDto.getProveedorContactoNombre());
            contratoMarcoPdfDto.setProveedorContactoTelefono(contratoMarcoPdfSapDto.getProveedorContactoTelefono());

//            contratoMarcoPdfDto.setMontoSubtotal();
//            contratoMarcoPdfDto.setMontoDescuento();
//            contratoMarcoPdfDto.setMontoIgv();
            contratoMarcoPdfDto.setMontoImporteTotal(contratoMarco.getTotal());

            List<OrdenCompraDetalle> ordenCompraDetalleList = ordenCompraDetalleService.getOrdenCompraDetalleListByIdOc(contratoMarco.getId());
            List<OrdenCompraPosicionPdfDto> posicionPdfDtoList = new ArrayList<>();

            ordenCompraDetalleList.forEach(ocd -> {
                OrdenCompraPosicionPdfDto posicionPdfDto = new OrdenCompraPosicionPdfDto();

                if(ocd.getPosicion() != null && !ocd.getPosicion().isEmpty())
                    posicionPdfDto.setPosicion(String.valueOf(Integer.parseInt(ocd.getPosicion())));
                posicionPdfDto.setCentro(ocd.getDenominacionCentro());
                if(ocd.getCodigoSapBienServicio() != null && !ocd.getCodigoSapBienServicio().isEmpty())
                    posicionPdfDto.setMaterial(String.valueOf(Integer.parseInt(ocd.getCodigoSapBienServicio())));
                posicionPdfDto.setDescripcion(ocd.getDescripcionBienServicio());
                posicionPdfDto.setCantidad(ocd.getCantidad());
                posicionPdfDto.setUnidad(ocd.getUnidadMedidaBienServicio());
                posicionPdfDto.setFechaEntrega(ocd.getFechaEntrega());
                posicionPdfDto.setPrecioUnitario(ocd.getPrecioUnitario());
                posicionPdfDto.setImporte(ocd.getPrecioTotal());

                posicionPdfDtoList.add(posicionPdfDto);
            });

            contratoMarcoPdfDto.setOrdenCompraPosicionPdfDtoList(posicionPdfDtoList);
        }
        else{
            return null;
        }

        byte[] generateContratoMarcoBytes = PdfGeneratorFactory.getJasperGenerator().generateContratoMarcoPdfBytes(contratoMarcoPdfDto);
        return Base64.getEncoder().encodeToString(generateContratoMarcoBytes);
    }
}