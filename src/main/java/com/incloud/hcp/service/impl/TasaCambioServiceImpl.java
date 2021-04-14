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
import com.incloud.hcp.domain.TasaCambio;
import com.incloud.hcp.repository.MonedaRepository;
import com.incloud.hcp.repository.TasaCambioRepository;
import com.incloud.hcp.service.MonedaService;
import com.incloud.hcp.service.TasaCambioService;
import com.incloud.hcp.service.support.PageRequestByExample;
import com.incloud.hcp.service.support.PageResponse;
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
 * A simple DTO Facility for TasaCambio.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class TasaCambioServiceImpl implements TasaCambioService {

    protected final Logger log = LoggerFactory.getLogger(TasaCambioServiceImpl.class);

    protected final String NAME_SHEET = "TasaCambio";
    protected final String CONFIG_TITLE = "com/incloud/hcp/excel/TasaCambioExcel.xml";

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected TasaCambioRepository tasaCambioRepository;

    @Autowired
    protected MonedaService monedaService;

    @Autowired
    protected MonedaRepository monedaRepository;

    @Transactional(readOnly = true)
    public Optional<TasaCambio> findOne(Integer id) {
        log.debug("Ingresando findOne: ", id);
        return this.tasaCambioRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<TasaCambio> findAll() {
        log.debug("Ingresando findAll");
        Sort sort = Sort.by("id");
        sort = this.setFindAll(sort);
        List<TasaCambio> lista = this.tasaCambioRepository.findAll(sort);
        return lista;
    }

    protected abstract Sort setFindAll(Sort sort);

    @Transactional(readOnly = true)
    public List<TasaCambio> find(TasaCambio req) {
        log.debug("Ingresando find: ", req);
        Example<TasaCambio> example = null;
        TasaCambio tasaCambio = req;
        ExampleMatcher matcher = null;
        if (tasaCambio != null) {
            matcher = ExampleMatcher.matching() //
            ;
            example = Example.of(tasaCambio, matcher);
        }
        Sort sort = Sort.by("id");
        sort = this.setFind(req, matcher, example, sort);
        List<TasaCambio> lista = this.tasaCambioRepository.findAll(example, sort);
        return lista;
    }

    protected abstract Sort setFind(TasaCambio req, ExampleMatcher matcher, Example<TasaCambio> example, Sort sort);

    @Transactional(readOnly = true)
    public PageResponse<TasaCambio> findPaginated(PageRequestByExample<TasaCambio> req) {
        log.debug("Ingresando findPaginated: ", req);
        Example<TasaCambio> example = null;
        TasaCambio tasaCambio = toEntity(req.example);
        ExampleMatcher matcher = null;
        if (tasaCambio != null) {
            matcher = ExampleMatcher.matching() //
            ;
            example = Example.of(tasaCambio, matcher);
        }

        Page<TasaCambio> page;
        Sort sort = Sort.by("id");
        sort = this.setFind(tasaCambio, matcher, example, sort);
        req.generarLazyDefecto();
        this.setFindPaginated(req, matcher, example);
        if (example != null) {
            page = this.tasaCambioRepository.findAll(example, req.toPageable(sort));
        } else {
            page = this.tasaCambioRepository.findAll(req.toPageable(sort));
        }

        List<TasaCambio> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    protected abstract void setFindPaginated(PageRequestByExample<TasaCambio> req, ExampleMatcher matcher, Example<TasaCambio> example);

    @Transactional(readOnly = true)
    public XSSFWorkbook downloadExcelXLSX(TasaCambio req) {
        log.debug("Ingresando downloadExcelXLSX: ", req);
        List<TasaCambio> lista = this.find(req);
        Optional<List<TasaCambio>> oList = Optional.ofNullable(lista);
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

        lista.forEach(tasaCambio -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            XSSFRow dataRow = sheet.createRow(i + 1);
            int contador = 0;
            ExcelDefault.setValueCell(tasaCambio.getId(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(tasaCambio.getFechaTasa(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(tasaCambio.getValor(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(tasaCambio, dataRow);

        });
        this.setDownloadExcel(sheet);
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return book;
    }

    protected void setDownloadExcelItem(TasaCambio bean, XSSFRow dataRow) {

    }

    protected void setDownloadExcel(XSSFSheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook downloadExcelSXLSX(TasaCambio req) {
        log.debug("Ingresando downloadExcelSXLSX: ", req);
        List<TasaCambio> lista = this.find(req);
        Optional<List<TasaCambio>> oList = Optional.ofNullable(lista);
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

        for (TasaCambio tasaCambio : lista) {
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

            ExcelDefault.setValueCell(tasaCambio.getId(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(tasaCambio.getFechaTasa(), dataRow.createCell(contador), "DT", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(tasaCambio.getValor(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(tasaCambio, dataRow);

        }
        this.setDownloadExcel(sheet);
        return book;
    }

    protected void setDownloadExcelItem(TasaCambio bean, Row dataRow) {

    }

    protected void setDownloadExcel(Sheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook generateInsertExcelSXLSX(TasaCambio req) {
        log.debug("Ingresando generateInsertExcelSXLSX: ", req);
        List<TasaCambio> lista = this.find(req);
        Optional<List<TasaCambio>> oList = Optional.ofNullable(lista);
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

        for (TasaCambio tasaCambio : lista) {
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
            String sqlInsert = "INSERT INTO tasa_cambio(";
            sqlInsert = sqlInsert + "id_tasa_cambio" + ", ";
            sqlInsert = sqlInsert + "fecha_tasa" + ", ";
            sqlInsert = sqlInsert + "valor" + ")";
            sqlInsert = sqlInsert + " VALUES (";
            sqlInsert = sqlInsert + tasaCambio.getId() + ", ";
            if (Optional.ofNullable(tasaCambio.getFechaTasa()).isPresent()) {
                sqlInsert = sqlInsert + tasaCambio.getFechaTasa().getTime() + ", ";
            }

            else {
                sqlInsert = sqlInsert + "null" + ", ";
            }
            sqlInsert = sqlInsert + tasaCambio.getValor();
            sqlInsert = sqlInsert + " );";
            ExcelDefault.setValueCell(sqlInsert, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
        }
        return book;
    }

    protected TasaCambio completarDatosBean(TasaCambio bean) {
        BigDecimal data = new BigDecimal(0.00);
        if (Optional.ofNullable(bean.getValor()).isPresent()) {
            bean.setValor(bean.getValor().setScale(4, BigDecimal.ROUND_HALF_UP));
        }
        bean = this.setCompletarDatosBean(bean);
        return bean;
    }

    protected TasaCambio setCompletarDatosBean(TasaCambio bean) {
        return bean;
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    public TasaCambio save(TasaCambio dto) {
        log.debug("Ingresando save: ", dto);
        if (dto == null) {
            return null;
        }
        dto = this.completarDatosBean(dto);
        dto = this.setBeforeSave(dto);
        this.setSave(dto);
        dto = this.tasaCambioRepository.save(dto);
        dto = this.setAfterSave(dto);
        return dto;
    }

    protected abstract void setSave(TasaCambio dto);

    protected TasaCambio setBeforeSave(TasaCambio dto) {
        return dto;
    }

    protected TasaCambio setAfterSave(TasaCambio dto) {
        return dto;
    }

    /**
    * Save new entity or update the corresponding entity if any.
    */
    public TasaCambio create(TasaCambio dto) {
        log.debug("Ingresando create: ", dto);
        if (dto == null) {
            return null;
        }

        TasaCambio tasaCambio;
        tasaCambio = new TasaCambio();

        tasaCambio.setFechaTasa(dto.getFechaTasa());
        tasaCambio.setValor(dto.getValor());
        tasaCambio = this.completarDatosBean(tasaCambio);
        this.setCreate(tasaCambio);
        return this.tasaCambioRepository.save(tasaCambio);
    }

    protected abstract void setCreate(TasaCambio dto);

    protected String validacionesPrevias(TasaCambio dto) {
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
        this.tasaCambioRepository.deleteById(id);
    }

    protected abstract void setDelete(Integer id);

    public void deleteAll() {
        log.debug("Ingresando deleteAll");
        this.setDeleteAll();
        this.tasaCambioRepository.deleteAll();
    }

    protected abstract void setDeleteAll();

    /**
     * Converts the passed tasaCambio to a DTO.
     */
    protected TasaCambio toDTO(TasaCambio tasaCambio) {
        return tasaCambio;
    }

    /**
     * Converts the passed dto to a TasaCambio.
     * Convenient for query by example.
     */
    protected TasaCambio toEntity(TasaCambio dto) {
        return dto;
    }

}