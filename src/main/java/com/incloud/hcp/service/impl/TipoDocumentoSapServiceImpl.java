/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/dto/EntityDTOService.java.e.vm
 */
package com.incloud.hcp.service.impl;

import com.incloud.hcp.config.excel.ExcelDefault;
import com.incloud.hcp.domain.TipoDocumentoSap;
import com.incloud.hcp.domain.TipoDocumentoSap_;
import com.incloud.hcp.repository.TipoDocumentoSapRepository;
import com.incloud.hcp.service.TipoDocumentoSapService;
import com.incloud.hcp.service.support.PageRequestByExample;
import com.incloud.hcp.service.support.PageResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A simple DTO Facility for TipoDocumentoSap.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class TipoDocumentoSapServiceImpl implements TipoDocumentoSapService {

    protected final Logger log = LoggerFactory.getLogger(TipoDocumentoSapServiceImpl.class);

    protected final String NAME_SHEET = "TipoDocumentoSap";
    protected final String CONFIG_TITLE = "com/incloud/hcp/excel/TipoDocumentoSapExcel.xml";

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected TipoDocumentoSapRepository tipoDocumentoSapRepository;

    @Transactional(readOnly = true)
    public Optional<TipoDocumentoSap> findOne(Integer id) {
        log.debug("Ingresando findOne: ", id);
        return this.tipoDocumentoSapRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<TipoDocumentoSap> findAll() {
        log.debug("Ingresando findAll");
        Sort sort = Sort.by("id");
        sort = this.setFindAll(sort);
        List<TipoDocumentoSap> lista = this.tipoDocumentoSapRepository.findAll(sort);
        return lista;
    }

    protected abstract Sort setFindAll(Sort sort);

    @Transactional(readOnly = true)
    public List<TipoDocumentoSap> find(TipoDocumentoSap req) {
        log.debug("Ingresando find: ", req);
        Example<TipoDocumentoSap> example = null;
        TipoDocumentoSap tipoDocumentoSap = req;
        ExampleMatcher matcher = null;
        if (tipoDocumentoSap != null) {
            matcher = ExampleMatcher.matching() //
                    .withMatcher(TipoDocumentoSap_.descripcionTipoDocumentoSap.getName(), match -> match.ignoreCase().startsWith());
            example = Example.of(tipoDocumentoSap, matcher);
        }
        Sort sort = Sort.by("id");
        sort = this.setFind(req, matcher, example, sort);
        List<TipoDocumentoSap> lista = this.tipoDocumentoSapRepository.findAll(example, sort);
        return lista;
    }

    protected abstract Sort setFind(TipoDocumentoSap req, ExampleMatcher matcher, Example<TipoDocumentoSap> example, Sort sort);

    @Transactional(readOnly = true)
    public PageResponse<TipoDocumentoSap> findPaginated(PageRequestByExample<TipoDocumentoSap> req) {
        log.debug("Ingresando findPaginated: ", req);
        Example<TipoDocumentoSap> example = null;
        TipoDocumentoSap tipoDocumentoSap = toEntity(req.example);
        ExampleMatcher matcher = null;
        if (tipoDocumentoSap != null) {
            matcher = ExampleMatcher.matching() //
                    .withMatcher(TipoDocumentoSap_.descripcionTipoDocumentoSap.getName(), match -> match.ignoreCase().startsWith());
            example = Example.of(tipoDocumentoSap, matcher);
        }

        Page<TipoDocumentoSap> page;
        Sort sort = Sort.by("id");
        sort = this.setFind(tipoDocumentoSap, matcher, example, sort);
        req.generarLazyDefecto();
        this.setFindPaginated(req, matcher, example);
        if (example != null) {
            page = this.tipoDocumentoSapRepository.findAll(example, req.toPageable(sort));
        } else {
            page = this.tipoDocumentoSapRepository.findAll(req.toPageable(sort));
        }

        List<TipoDocumentoSap> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    protected abstract void setFindPaginated(PageRequestByExample<TipoDocumentoSap> req, ExampleMatcher matcher, Example<TipoDocumentoSap> example);

    @Transactional(readOnly = true)
    public XSSFWorkbook downloadExcelXLSX(TipoDocumentoSap req) {
        log.debug("Ingresando downloadExcelXLSX: ", req);
        List<TipoDocumentoSap> lista = this.find(req);
        Optional<List<TipoDocumentoSap>> oList = Optional.ofNullable(lista);
        if (!oList.isPresent()) {
            return null;
        }

        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        ExcelDefault.createTitle(sheet, CONFIG_TITLE, book.createCellStyle(), book.createFont());
        CellStyle cellStyle = book.createCellStyle();
        DataFormat dataFormat = book.createDataFormat();

        lista.forEach(tipoDocumentoSap -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            XSSFRow dataRow = sheet.createRow(i + 1);
            int contador = 0;
            ExcelDefault.setValueCell(tipoDocumentoSap.getId(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(tipoDocumentoSap.getDescripcionTipoDocumentoSap(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(tipoDocumentoSap, dataRow);

        });
        this.setDownloadExcel(sheet);
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return book;
    }

    protected void setDownloadExcelItem(TipoDocumentoSap bean, XSSFRow dataRow) {

    }

    protected void setDownloadExcel(XSSFSheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook downloadExcelSXLSX(TipoDocumentoSap req) {
        log.debug("Ingresando downloadExcelSXLSX: ", req);
        List<TipoDocumentoSap> lista = this.find(req);
        Optional<List<TipoDocumentoSap>> oList = Optional.ofNullable(lista);
        if (!oList.isPresent()) {
            return null;
        }

        SXSSFWorkbook book = new SXSSFWorkbook(100);
        XSSFWorkbook xbook = book.getXSSFWorkbook();
        SXSSFSheet sheet = book.createSheet();
        sheet.trackAllColumnsForAutoSizing();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        int nroColumnas = ExcelDefault.createTitle(xbook, sheet, CONFIG_TITLE);
        for (int i = 0; i < nroColumnas; i++) {
            sheet.autoSizeColumn(i, true);
        }
        sheet.untrackAllColumnsForAutoSizing();

        XSSFCellStyle cellStyle01 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 1), new Color(226, 239, 218), false, (short) 10);
        XSSFCellStyle cellStyle02 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 192), new Color(255, 255, 255), false, (short) 10);
        List<CellStyle> cellStyleList = null;
        List<CellStyle> cellStyleList01 = ExcelDefault.generarCellStyle(xbook, cellStyle01);
        List<CellStyle> cellStyleList02 = ExcelDefault.generarCellStyle(xbook, cellStyle02);
        boolean filaImpar = true;

        for (TipoDocumentoSap tipoDocumentoSap : lista) {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            Row dataRow = sheet.createRow(i + 1);
            int contador = 0;
            if (filaImpar) {
                cellStyleList = cellStyleList01;
            } else {
                cellStyleList = cellStyleList02;
            }
            filaImpar = !filaImpar;

            ExcelDefault.setValueCell(tipoDocumentoSap.getId(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(tipoDocumentoSap.getDescripcionTipoDocumentoSap(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(tipoDocumentoSap, dataRow);

        }
        this.setDownloadExcel(sheet);
        return book;
    }

    protected void setDownloadExcelItem(TipoDocumentoSap bean, Row dataRow) {

    }

    protected void setDownloadExcel(Sheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook generateInsertExcelSXLSX(TipoDocumentoSap req) {
        log.debug("Ingresando generateInsertExcelSXLSX: ", req);
        List<TipoDocumentoSap> lista = this.find(req);
        Optional<List<TipoDocumentoSap>> oList = Optional.ofNullable(lista);
        if (!oList.isPresent()) {
            return null;
        }

        SXSSFWorkbook book = new SXSSFWorkbook(100);
        XSSFWorkbook xbook = book.getXSSFWorkbook();
        SXSSFSheet sheet = book.createSheet();
        sheet.trackAllColumnsForAutoSizing();
        int numberOfSheets = book.getNumberOfSheets();
        book.setSheetName(numberOfSheets - 1, NAME_SHEET);
        int nroColumnas = 1;
        for (int i = 0; i < nroColumnas; i++) {
            sheet.autoSizeColumn(i, true);
        }
        sheet.untrackAllColumnsForAutoSizing();
        XSSFCellStyle cellStyle01 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 1), new Color(226, 239, 218), false, (short) 10);
        XSSFCellStyle cellStyle02 = ExcelDefault.devuelveCellStyle(xbook, new Color(0, 0, 192), new Color(255, 255, 255), false, (short) 10);
        List<CellStyle> cellStyleList = null;
        List<CellStyle> cellStyleList01 = ExcelDefault.generarCellStyle(xbook, cellStyle01);
        List<CellStyle> cellStyleList02 = ExcelDefault.generarCellStyle(xbook, cellStyle02);
        boolean filaImpar = true;

        for (TipoDocumentoSap tipoDocumentoSap : lista) {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            Row dataRow = sheet.createRow(i + 1);
            int contador = 0;
            if (filaImpar) {
                cellStyleList = cellStyleList01;
            } else {
                cellStyleList = cellStyleList02;
            }
            filaImpar = !filaImpar;
            String sqlInsert = "INSERT INTO tipo_documento_sap(";
            sqlInsert = sqlInsert + "id_tipo_documento_sap" + ", ";
            sqlInsert = sqlInsert + "descripcion_tipo_documento_sap" + ")";
            sqlInsert = sqlInsert + " VALUES (";
            sqlInsert = sqlInsert + tipoDocumentoSap.getId() + ", ";
            if (StringUtils.isBlank(tipoDocumentoSap.getDescripcionTipoDocumentoSap())) {
                sqlInsert = sqlInsert + "null";
            } else {
                sqlInsert = sqlInsert + "'" + tipoDocumentoSap.getDescripcionTipoDocumentoSap() + "'";
            }
            sqlInsert = sqlInsert + " );";
            ExcelDefault.setValueCell(sqlInsert, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
        }
        return book;
    }

    protected TipoDocumentoSap completarDatosBean(TipoDocumentoSap bean) {
        BigDecimal data = new BigDecimal(0.00);
        bean = this.setCompletarDatosBean(bean);
        return bean;
    }

    protected TipoDocumentoSap setCompletarDatosBean(TipoDocumentoSap bean) {
        return bean;
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    public TipoDocumentoSap save(TipoDocumentoSap dto) {
        log.debug("Ingresando save: ", dto);
        if (dto == null) {
            return null;
        }
        dto = this.completarDatosBean(dto);
        dto = this.setBeforeSave(dto);
        this.setSave(dto);
        dto = this.tipoDocumentoSapRepository.save(dto);
        dto = this.setAfterSave(dto);
        return dto;
    }

    protected abstract void setSave(TipoDocumentoSap dto);

    protected TipoDocumentoSap setBeforeSave(TipoDocumentoSap dto) {
        return dto;
    }

    protected TipoDocumentoSap setAfterSave(TipoDocumentoSap dto) {
        return dto;
    }

    /**
    * Save new entity or update the corresponding entity if any.
    */
    public TipoDocumentoSap create(TipoDocumentoSap dto) {
        log.debug("Ingresando create: ", dto);
        if (dto == null) {
            return null;
        }

        TipoDocumentoSap tipoDocumentoSap;
        tipoDocumentoSap = new TipoDocumentoSap();

        tipoDocumentoSap.setDescripcionTipoDocumentoSap(dto.getDescripcionTipoDocumentoSap());
        tipoDocumentoSap = this.completarDatosBean(tipoDocumentoSap);
        this.setCreate(tipoDocumentoSap);
        return this.tipoDocumentoSapRepository.save(tipoDocumentoSap);
    }

    protected abstract void setCreate(TipoDocumentoSap dto);

    protected String validacionesPrevias(TipoDocumentoSap dto) {
        return "";
    }

    /**
     * Delete the passed dto as a new entity or update the corresponding entity if any.
     */
    public void delete(Integer id) {
        log.debug("Ingresando delete: ", id);
        if (id == null) {
            return;
        }
        this.setDelete(id);
        this.tipoDocumentoSapRepository.deleteById(id);
    }

    protected abstract void setDelete(Integer id);

    public void deleteAll() {
        log.debug("Ingresando deleteAll");
        this.setDeleteAll();
        this.tipoDocumentoSapRepository.deleteAll();
    }

    protected abstract void setDeleteAll();

    /**
     * Converts the passed tipoDocumentoSap to a DTO.
     */
    protected TipoDocumentoSap toDTO(TipoDocumentoSap tipoDocumentoSap) {
        return tipoDocumentoSap;
    }

    /**
     * Converts the passed dto to a TipoDocumentoSap.
     * Convenient for query by example.
     */
    protected TipoDocumentoSap toEntity(TipoDocumentoSap dto) {
        return dto;
    }

}