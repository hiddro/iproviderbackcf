/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/dto/EntitydeltaDTO.java.e.vm
 */
package com.incloud.hcp.service.delta;

import com.incloud.hcp.domain.LicitacionProveedorRenegociacion;
import com.incloud.hcp.domain.LicitacionSubetapa;
import com.incloud.hcp.dto.TrazabilidadRespuestaDto;
import com.incloud.hcp.service.LicitacionSubetapaService;

import java.util.List;

/**
 * Simple Interface for LicitacionSubetapa.
 */
public interface LicitacionSubetapaDeltaService extends LicitacionSubetapaService {

    void enviarCorreoRecordatorio();

    List<LicitacionSubetapa> grabarLista(List<LicitacionSubetapa> licitacionSubetapaList) throws Exception;

    void enviarCorreoActualizarEtapas(List<LicitacionSubetapa> result);

    TrazabilidadRespuestaDto findTrazabilidad(Integer id) throws Exception;
    TrazabilidadRespuestaDto findTrazabilidadRenegociacion(Integer id) throws Exception;

    List<LicitacionSubetapa> obtenerTrazabilidad(Integer idLicitacion) throws Exception;
    List<LicitacionProveedorRenegociacion> obtenerTrazabilidadRenegociacion(Integer idLicitacion) throws Exception;



}
