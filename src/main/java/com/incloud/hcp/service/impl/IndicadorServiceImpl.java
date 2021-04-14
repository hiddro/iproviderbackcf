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
import com.incloud.hcp.domain.Indicador;
import com.incloud.hcp.domain.Indicador_;
import com.incloud.hcp.repository.IndicadorRepository;
import com.incloud.hcp.service.IndicadorService;
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
 * A simple DTO Facility for Indicador.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class IndicadorServiceImpl implements IndicadorService {

    protected final Logger log = LoggerFactory.getLogger(IndicadorServiceImpl.class);

    protected final String NAME_SHEET = "Indicador";
    protected final String CONFIG_TITLE = "com/incloud/hcp/excel/IndicadorExcel.xml";

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected IndicadorRepository indicadorRepository;

    @Transactional(readOnly = true)
    public Optional<Indicador> findOne(Integer id) {
        log.debug("Ingresando findOne: ", id);
        return this.indicadorRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Indicador> findAll() {
        log.debug("Ingresando findAll");
        Sort sort = Sort.by("id");
        sort = this.setFindAll(sort);
        List<Indicador> lista = this.indicadorRepository.findAll(sort);
        return lista;
    }

    protected abstract Sort setFindAll(Sort sort);

    @Transactional(readOnly = true)
    public List<Indicador> find(Indicador req) {
        log.debug("Ingresando find: ", req);
        Example<Indicador> example = null;
        Indicador indicador = req;
        ExampleMatcher matcher = null;
        if (indicador != null) {
            matcher = ExampleMatcher.matching() //
                    .withMatcher(Indicador_.codigoTipoValorIndicador.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(Indicador_.descripcionIndicador.getName(), match -> match.ignoreCase().startsWith());
            example = Example.of(indicador, matcher);
        }
        Sort sort = Sort.by("id");
        sort = this.setFind(req, matcher, example, sort);
        List<Indicador> lista = this.indicadorRepository.findAll(example, sort);
        return lista;
    }

    protected abstract Sort setFind(Indicador req, ExampleMatcher matcher, Example<Indicador> example, Sort sort);

    @Transactional(readOnly = true)
    public PageResponse<Indicador> findPaginated(PageRequestByExample<Indicador> req) {
        log.debug("Ingresando findPaginated: ", req);
        Example<Indicador> example = null;
        Indicador indicador = toEntity(req.example);
        ExampleMatcher matcher = null;
        if (indicador != null) {
            matcher = ExampleMatcher.matching() //
                    .withMatcher(Indicador_.codigoTipoValorIndicador.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(Indicador_.descripcionIndicador.getName(), match -> match.ignoreCase().startsWith());
            example = Example.of(indicador, matcher);
        }

        Page<Indicador> page;
        Sort sort = Sort.by("id");
        sort = this.setFind(indicador, matcher, example, sort);
        req.generarLazyDefecto();
        this.setFindPaginated(req, matcher, example);
        if (example != null) {
            page = this.indicadorRepository.findAll(example, req.toPageable(sort));
        } else {
            page = this.indicadorRepository.findAll(req.toPageable(sort));
        }

        List<Indicador> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    protected abstract void setFindPaginated(PageRequestByExample<Indicador> req, ExampleMatcher matcher, Example<Indicador> example);

    @Transactional(readOnly = true)
    public XSSFWorkbook downloadExcelXLSX(Indicador req) {
        log.debug("Ingresando downloadExcelXLSX: ", req);
        List<Indicador> lista = this.find(req);
        Optional<List<Indicador>> oList = Optional.ofNullable(lista);
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

        lista.forEach(indicador -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            XSSFRow dataRow = sheet.createRow(i + 1);
            int contador = 0;
            ExcelDefault.setValueCell(indicador.getId(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(indicador.getCodigoTipoValorIndicador(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(indicador.getValorMinimo(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(indicador.getValorMaximo(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(indicador.getValorIntermedio(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(indicador.getDescripcionIndicador(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(indicador, dataRow);

        });
        this.setDownloadExcel(sheet);
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return book;
    }

    protected void setDownloadExcelItem(Indicador bean, XSSFRow dataRow) {

    }

    protected void setDownloadExcel(XSSFSheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook downloadExcelSXLSX(Indicador req) {
        log.debug("Ingresando downloadExcelSXLSX: ", req);
        List<Indicador> lista = this.find(req);
        Optional<List<Indicador>> oList = Optional.ofNullable(lista);
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

        for (Indicador indicador : lista) {
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

            ExcelDefault.setValueCell(indicador.getId(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(indicador.getCodigoTipoValorIndicador(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(indicador.getValorMinimo(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(indicador.getValorMaximo(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(indicador.getValorIntermedio(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(indicador.getDescripcionIndicador(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(indicador, dataRow);

        }
        this.setDownloadExcel(sheet);
        return book;
    }

    protected void setDownloadExcelItem(Indicador bean, Row dataRow) {

    }

    protected void setDownloadExcel(Sheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook generateInsertExcelSXLSX(Indicador req) {
        log.debug("Ingresando generateInsertExcelSXLSX: ", req);
        List<Indicador> lista = this.find(req);
        Optional<List<Indicador>> oList = Optional.ofNullable(lista);
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

        for (Indicador indicador : lista) {
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
            String sqlInsert = "INSERT INTO indicador(";
            sqlInsert = sqlInsert + "id_indicador" + ", ";
            sqlInsert = sqlInsert + "codigo_tipo_valor_indicador" + ", ";
            sqlInsert = sqlInsert + "valor_minimo" + ", ";
            sqlInsert = sqlInsert + "valor_maximo" + ", ";
            sqlInsert = sqlInsert + "valor_intermedio" + ", ";
            sqlInsert = sqlInsert + "descripcion_indicador" + ")";
            sqlInsert = sqlInsert + " VALUES (";
            sqlInsert = sqlInsert + indicador.getId() + ", ";
            if (StringUtils.isBlank(indicador.getCodigoTipoValorIndicador())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + indicador.getCodigoTipoValorIndicador() + "'" + ", ";
            }
            sqlInsert = sqlInsert + indicador.getValorMinimo() + ", ";
            sqlInsert = sqlInsert + indicador.getValorMaximo() + ", ";
            sqlInsert = sqlInsert + indicador.getValorIntermedio() + ", ";
            if (StringUtils.isBlank(indicador.getDescripcionIndicador())) {
                sqlInsert = sqlInsert + "null";
            } else {
                sqlInsert = sqlInsert + "'" + indicador.getDescripcionIndicador() + "'";
            }
            sqlInsert = sqlInsert + " );";
            ExcelDefault.setValueCell(sqlInsert, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
        }
        return book;
    }

    protected Indicador completarDatosBean(Indicador bean) {
        BigDecimal data = new BigDecimal(0.00);
        if (Optional.ofNullable(bean.getValorMinimo()).isPresent()) {
            bean.setValorMinimo(bean.getValorMinimo().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getValorMaximo()).isPresent()) {
            bean.setValorMaximo(bean.getValorMaximo().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getValorIntermedio()).isPresent()) {
            bean.setValorIntermedio(bean.getValorIntermedio().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        bean = this.setCompletarDatosBean(bean);
        return bean;
    }

    protected Indicador setCompletarDatosBean(Indicador bean) {
        return bean;
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    public Indicador save(Indicador dto) {
        log.debug("Ingresando save: ", dto);
        if (dto == null) {
            return null;
        }
        dto = this.completarDatosBean(dto);
        dto = this.setBeforeSave(dto);
        this.setSave(dto);
        dto = this.indicadorRepository.save(dto);
        dto = this.setAfterSave(dto);
        return dto;
    }

    protected abstract void setSave(Indicador dto);

    protected Indicador setBeforeSave(Indicador dto) {
        return dto;
    }

    protected Indicador setAfterSave(Indicador dto) {
        return dto;
    }

    /**
    * Save new entity or update the corresponding entity if any.
    */
    public Indicador create(Indicador dto) {
        log.debug("Ingresando create: ", dto);
        if (dto == null) {
            return null;
        }

        Indicador indicador;
        indicador = new Indicador();

        indicador.setCodigoTipoValorIndicador(dto.getCodigoTipoValorIndicador());
        indicador.setValorMinimo(dto.getValorMinimo());
        indicador.setValorMaximo(dto.getValorMaximo());
        indicador.setValorIntermedio(dto.getValorIntermedio());
        indicador.setDescripcionIndicador(dto.getDescripcionIndicador());
        indicador = this.completarDatosBean(indicador);
        this.setCreate(indicador);
        return this.indicadorRepository.save(indicador);
    }

    protected abstract void setCreate(Indicador dto);

    protected String validacionesPrevias(Indicador dto) {
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
        this.indicadorRepository.deleteById(id);
    }

    protected abstract void setDelete(Integer id);

    public void deleteAll() {
        log.debug("Ingresando deleteAll");
        this.setDeleteAll();
        this.indicadorRepository.deleteAll();
    }

    protected abstract void setDeleteAll();

    /**
     * Converts the passed indicador to a DTO.
     */
    protected Indicador toDTO(Indicador indicador) {
        return indicador;
    }

    /**
     * Converts the passed dto to a Indicador.
     * Convenient for query by example.
     */
    protected Indicador toEntity(Indicador dto) {
        return dto;
    }

}