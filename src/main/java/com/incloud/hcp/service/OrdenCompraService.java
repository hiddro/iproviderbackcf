package com.incloud.hcp.service;

import java.util.Date;
import java.util.List;
import com.incloud.hcp.domain.OrdenCompra;
import com.incloud.hcp.dto.OrdenCompraRespuestaDto;

public interface OrdenCompraService {

    List<OrdenCompra> getAllOrdenCompra();

    OrdenCompra getOrdenCompraById(Integer idOrdenCompra);

    List<OrdenCompra> getOrdenCompraListPorFechasAndRuc(Date fechaInicio, Date fechaFin, String ruc);

    OrdenCompraRespuestaDto updateOrdenCompraFechaVisualizacion(Integer idOrdenCompra);

    OrdenCompraRespuestaDto aprobarRechazarOrdenCompra(Integer idOrdenCompra, int estado, String textoRechazo);

    void extraerOrdenCompraMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion);

    void extraerContratoMarcoMasivoByRangoFechas(Date fechaInicio, Date fechaFin, boolean enviarCorreoPublicacion);

    String getOrdenCompraPdfContent(String numeroOrdenCompra) throws Exception;

    String getContratoMarcoPdfContent(String numeroContratoMarco) throws Exception;
}
