package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.jco.documentoAceptacion.service.JCODocumentoAceptacionService;
import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;
import com.incloud.hcp.service.DocumentoAceptacionService;
import com.incloud.hcp.service.extractor.ExtractorDocumentoAceptacionService;
import com.incloud.hcp.util.DateUtils;
import com.incloud.hcp.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.incloud.hcp.repository.DocumentoAceptacionRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DocumentoAceptacionServiceImpl implements DocumentoAceptacionService {

    private DocumentoAceptacionRepository documentoAceptacionRepository;
    private JCODocumentoAceptacionService jcoDocumentoAceptacionService;
    private ExtractorDocumentoAceptacionService extractorDocumentoAceptacionService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public DocumentoAceptacionServiceImpl(DocumentoAceptacionRepository documentoAceptacionRepository,
                                          JCODocumentoAceptacionService jcoDocumentoAceptacionService,
                                          ExtractorDocumentoAceptacionService extractorDocumentoAceptacionService) {
        this.documentoAceptacionRepository = documentoAceptacionRepository;
        this.jcoDocumentoAceptacionService = jcoDocumentoAceptacionService;
        this.extractorDocumentoAceptacionService = extractorDocumentoAceptacionService;
    }

    @Override
    public List<DocumentoAceptacion> getAllDocumentoAceptacion() {
        return documentoAceptacionRepository.findAll();
    }

    @Override
    public DocumentoAceptacion getDocumentoAceptacionbyId(Integer idTipoDocumentoAceptacion, Integer idEntregaMercaderia) {
        return documentoAceptacionRepository.getDocumentoAceptacionById(idTipoDocumentoAceptacion, idEntregaMercaderia);
    }

    @Override
    public List<DocumentoAceptacion> getDocumentoAceptacionPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc) {
        List<DocumentoAceptacion> documentoAceptacionList = new ArrayList<>();

        if (ruc == null || ruc.isEmpty()) {
            documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetween(fechaInicio, fechaFin);
        }
        else {
            documentoAceptacionList = documentoAceptacionRepository.getDocumentoAceptacionByFechaRegistroBetweenAndProveedorRuc(fechaInicio, fechaFin, ruc);
        }
        // Opción iProvider - Silvestre (los id OC no están a nivel de cabecera, sino a detalle)
        return documentoAceptacionList;

        // Opción Iprovider - SAP
        /*return documentoAceptacionList.stream()
                .filter(da-> da.getIdOrdenCompra() != null) // si el Id de OC es null significa que el numero de OC asociado no se encontro entre las OC liberadas y porlo tanto el doc de aceptacion no es facturable y no debe mostrarse
                .collect(Collectors.toList());*/
    }

    @Override
    public void extraerDocumentoAceptacionMasivoByRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion){
        logger.info("EXTRACCION DOC_ACEP MASIVA - INICIO: " + fechaInicio.toString());
        logger.info("EXTRACCION DOC_ACEP MASIVA - FIN: " + fechaFin.toString());

        while (fechaInicio.isBefore(fechaFin.plusDays(1))){
            try {
                String currentDateAsString = DateUtils.localDateToStringPattern(fechaInicio, DateUtils.STANDARD_DATE_FORMAT);
                extractorDocumentoAceptacionService.extraerDocumentoAceptacion(currentDateAsString, currentDateAsString, enviarCorreoAprobacion);
                //String currentDateAsSapString = DateUtils.localDateToSapString(fechaInicio);
                //jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(currentDateAsSapString, currentDateAsSapString, false, aprobarOrdenCompra, enviarCorreoAprobacion);
                fechaInicio = fechaInicio.plusDays(1);
            }
            catch(Exception e){
                String error = Utils.obtieneMensajeErrorException(e);
                logger.error("ERROR al extraer Documentos de Aceptacion de la fecha " + DateUtils.localDateToString(fechaInicio) + " : " + error);
                fechaInicio = fechaInicio.plusDays(1);
            }
        }
    }

    @Override
    public String extraerDocumentoAceptacionByNumOrdenCompraAndNumDocAceptacion(String numeroOrdenCompra, String numeroDocumentoAceptacion, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion){
        String header = "EXTRACCION DOC_ACEP POR NUMERO_OC Y NUMERO_DA: " + numeroOrdenCompra + " / " + numeroDocumentoAceptacion;
        String respuesta = "";
        logger.error(header + " // " + DateUtils.getCurrentTimestamp().toString());

        try {
            Optional<DocumentoAceptacion> opDocumentoAceptacion = documentoAceptacionRepository.findByNumeroDocumentoAceptacion(numeroDocumentoAceptacion);
            if (opDocumentoAceptacion.isPresent()){
                DocumentoAceptacion documentoAceptacion = opDocumentoAceptacion.get();
                respuesta = "El documento ya fue publicado previamente (asociado a Orden de Compra '" + documentoAceptacion.getNumeroOrdenCompra() + "') y actualmente esta en estado '" + documentoAceptacion.getEstadoDocumentoAceptacion().getDescripcion() + "'";
                logger.error(header + " // " + respuesta);
            }
            else{
                jcoDocumentoAceptacionService.extraerDocumentoAceptacionListRFC(numeroOrdenCompra, numeroDocumentoAceptacion, true, aprobarOrdenCompra, enviarCorreoAprobacion);
                respuesta = "El documento no fue publicado previamente y los datos de busqueda fueron enviados a SAP, verificar si hubo publicacion exitosa";
                logger.error(header + " // " + respuesta);
            }
        }
        catch(Exception e){
            String error = Utils.obtieneMensajeErrorException(e);
            logger.error(header + " // EXCEPCION: " + error);
            throw new RuntimeException("EXCEPCION al extraer Documento de Aceptacion '" + numeroDocumentoAceptacion + "' asociado a la Orden de Compra '" + numeroOrdenCompra + "' : " + error);
        }
        return respuesta;
    }

    @Override
    public String getEntregaMercaderiaGenerateContent(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO){
        byte[] generateEntradaMercaderia =PdfGeneratorFactory.getJasperGenerator().generateEntradaMercaderia(parameterEntradaMercaderiaPdfDTO);
        return Base64.getEncoder().encodeToString(generateEntradaMercaderia);
    }

    @Override
    public String getConformidadServicioGenerateContent(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO){
        byte[] generateConformidadServicio = PdfGeneratorFactory.getJasperGenerator().generateConformidadServicio(parameterConformidadServicioPdfDTO);
        return Base64.getEncoder().encodeToString(generateConformidadServicio);
    }
}
