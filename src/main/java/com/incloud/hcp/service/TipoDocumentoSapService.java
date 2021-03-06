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

import com.incloud.hcp.domain.TipoDocumentoSap;
import com.incloud.hcp.service.support.PageRequestByExample;
import com.incloud.hcp.service.support.PageResponse;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;
import java.util.Optional;

/**
 * Simple Interface for TipoDocumentoSap.
 */
public interface TipoDocumentoSapService {

    Optional<TipoDocumentoSap> findOne(Integer id);

    List<TipoDocumentoSap> findAll();

    List<TipoDocumentoSap> find(TipoDocumentoSap req);

    PageResponse<TipoDocumentoSap> findPaginated(PageRequestByExample<TipoDocumentoSap> req);

    XSSFWorkbook downloadExcelXLSX(TipoDocumentoSap req);

    SXSSFWorkbook downloadExcelSXLSX(TipoDocumentoSap req);

    SXSSFWorkbook generateInsertExcelSXLSX(TipoDocumentoSap req);

    TipoDocumentoSap save(TipoDocumentoSap dto);

    TipoDocumentoSap create(TipoDocumentoSap dto);

    void delete(Integer id);

    void deleteAll();

}
