package com.incloud.hcp.service;
import  com.incloud.hcp.domain.DocumentoAceptacion;
import com.incloud.hcp.pdf.bean.ParameterConformidadServicioPdfDTO;
import com.incloud.hcp.pdf.bean.ParameterEntradaMercaderiaPdfDTO;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface DocumentoAceptacionService {

    List<DocumentoAceptacion> getAllDocumentoAceptacion();

    DocumentoAceptacion getDocumentoAceptacionbyId(Integer idTipoDocumentoAceptacion, Integer idEntregaMercaderia);

    List<DocumentoAceptacion> getDocumentoAceptacionPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc);

    void extraerDocumentoAceptacionMasivoByRangoFechas(LocalDate fechaInicio, LocalDate fechaFin, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion);

    String extraerDocumentoAceptacionByNumOrdenCompraAndNumDocAceptacion(String numeroOrdenCompra, String numeroDocumentoAceptacion, boolean aprobarOrdenCompra, boolean enviarCorreoAprobacion);

    String getEntregaMercaderiaGenerateContent(ParameterEntradaMercaderiaPdfDTO parameterEntradaMercaderiaPdfDTO);

    String getConformidadServicioGenerateContent(ParameterConformidadServicioPdfDTO parameterConformidadServicioPdfDTO);
}
