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

import com.incloud.hcp.domain.LicitacionProveedorDetalleEvaluacion;
import com.incloud.hcp.service.support.PageRequestByExample;
import com.incloud.hcp.service.support.PageResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Simple Interface for LicitacionProveedorDetalleEvaluacion.
 */
public interface LicitacionProveedorDetalleEvaluacionService {

    Optional<LicitacionProveedorDetalleEvaluacion> findOne(Integer id);

    List<LicitacionProveedorDetalleEvaluacion> findAll();

    List<LicitacionProveedorDetalleEvaluacion> find(LicitacionProveedorDetalleEvaluacion req);

    PageResponse<LicitacionProveedorDetalleEvaluacion> findPaginated(PageRequestByExample<LicitacionProveedorDetalleEvaluacion> req);

    XSSFWorkbook downloadExcelXLSX(LicitacionProveedorDetalleEvaluacion req);

    SXSSFWorkbook downloadExcelSXLSX(LicitacionProveedorDetalleEvaluacion req);

    SXSSFWorkbook generateInsertExcelSXLSX(LicitacionProveedorDetalleEvaluacion req);

    LicitacionProveedorDetalleEvaluacion save(LicitacionProveedorDetalleEvaluacion dto);

    LicitacionProveedorDetalleEvaluacion create(LicitacionProveedorDetalleEvaluacion dto);

    void delete(Integer id);

    void deleteAll();

}
