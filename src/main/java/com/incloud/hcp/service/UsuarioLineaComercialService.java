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

import com.incloud.hcp.domain.UsuarioLineaComercial;
import com.incloud.hcp.service.support.PageRequestByExample;
import com.incloud.hcp.service.support.PageResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Simple Interface for UsuarioLineaComercial.
 */
public interface UsuarioLineaComercialService {

    Optional<UsuarioLineaComercial> findOne(Integer id);

    List<UsuarioLineaComercial> findAll();

    List<UsuarioLineaComercial> find(UsuarioLineaComercial req);

    PageResponse<UsuarioLineaComercial> findPaginated(PageRequestByExample<UsuarioLineaComercial> req);

    XSSFWorkbook downloadExcelXLSX(UsuarioLineaComercial req);

    SXSSFWorkbook downloadExcelSXLSX(UsuarioLineaComercial req);

    SXSSFWorkbook generateInsertExcelSXLSX(UsuarioLineaComercial req);

    UsuarioLineaComercial save(UsuarioLineaComercial dto);

    UsuarioLineaComercial create(UsuarioLineaComercial dto);

    void delete(Integer id);

    void deleteAll();

}