/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/dto/EntityDTO.java.e.vm
 */
package com.incloud.hcp.service;

import com.incloud.hcp.domain.CotizacionCampoRespuestaRechazada;
import com.incloud.hcp.service.support.PageRequestByExample;
import com.incloud.hcp.service.support.PageResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Simple Interface for CotizacionCampoRespuestaRechazada.
 */
public interface CotizacionCampoRespuestaRechazadaService {

    Optional<CotizacionCampoRespuestaRechazada> findOne(Integer id);

    List<CotizacionCampoRespuestaRechazada> findAll();

    List<CotizacionCampoRespuestaRechazada> find(CotizacionCampoRespuestaRechazada req);

    PageResponse<CotizacionCampoRespuestaRechazada> findPaginated(PageRequestByExample<CotizacionCampoRespuestaRechazada> req);

    XSSFWorkbook downloadExcelXLSX(CotizacionCampoRespuestaRechazada req);

    SXSSFWorkbook downloadExcelSXLSX(CotizacionCampoRespuestaRechazada req);

    SXSSFWorkbook generateInsertExcelSXLSX(CotizacionCampoRespuestaRechazada req);

    CotizacionCampoRespuestaRechazada save(CotizacionCampoRespuestaRechazada dto);

    CotizacionCampoRespuestaRechazada create(CotizacionCampoRespuestaRechazada dto);

    void delete(Integer id);

    void deleteAll();

}