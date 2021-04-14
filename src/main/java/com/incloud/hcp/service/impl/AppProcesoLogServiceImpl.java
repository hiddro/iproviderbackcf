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
import com.incloud.hcp.domain.AppProcesoLog;
import com.incloud.hcp.domain.AppProcesoLog_;
import com.incloud.hcp.repository.AppProcesoLogRepository;
import com.incloud.hcp.service.AppProcesoLogService;
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
 * A simple DTO Facility for AppProcesoLog.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class AppProcesoLogServiceImpl implements AppProcesoLogService {

    protected final Logger log = LoggerFactory.getLogger(AppProcesoLogServiceImpl.class);

    protected final String NAME_SHEET = "AppProcesoLog";
    protected final String CONFIG_TITLE = "com/incloud/hcp/excel/AppProcesoLogExcel.xml";

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected AppProcesoLogRepository appProcesoLogRepository;

    @Transactional(readOnly = true)
    public Optional<AppProcesoLog> findOne(Integer id) {
        log.debug("Ingresando findOne: ", id);
        return this.appProcesoLogRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<AppProcesoLog> findAll() {
        log.debug("Ingresando findAll");
        Sort sort = Sort.by("id");
        sort = this.setFindAll(sort);
        List<AppProcesoLog> lista = this.appProcesoLogRepository.findAll(sort);
        return lista;
    }

    protected abstract Sort setFindAll(Sort sort);

    @Transactional(readOnly = true)
    public List<AppProcesoLog> find(AppProcesoLog req) {
        log.debug("Ingresando find: ", req);
        Example<AppProcesoLog> example = null;
        AppProcesoLog appProcesoLog = req;
        ExampleMatcher matcher = null;
        if (appProcesoLog != null) {
            matcher = ExampleMatcher.matching() //
                    .withMatcher(AppProcesoLog_.estadoEjecucion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.descripcionEstadoEjecucion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.claseProgramacion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.metodoProgramacion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.parametroEntrada.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.resultadoSalida.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.usuario.getName(), match -> match.ignoreCase().startsWith());
            example = Example.of(appProcesoLog, matcher);
        }
        Sort sort = Sort.by("id");
        sort = this.setFind(req, matcher, example, sort);
        List<AppProcesoLog> lista = this.appProcesoLogRepository.findAll(example, sort);
        return lista;
    }

    protected abstract Sort setFind(AppProcesoLog req, ExampleMatcher matcher, Example<AppProcesoLog> example, Sort sort);

    @Transactional(readOnly = true)
    public PageResponse<AppProcesoLog> findPaginated(PageRequestByExample<AppProcesoLog> req) {
        log.debug("Ingresando findPaginated: ", req);
        Example<AppProcesoLog> example = null;
        AppProcesoLog appProcesoLog = toEntity(req.example);
        ExampleMatcher matcher = null;
        if (appProcesoLog != null) {
            matcher = ExampleMatcher.matching() //
                    .withMatcher(AppProcesoLog_.estadoEjecucion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.descripcionEstadoEjecucion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.claseProgramacion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.metodoProgramacion.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.parametroEntrada.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.resultadoSalida.getName(), match -> match.ignoreCase().startsWith())
                    .withMatcher(AppProcesoLog_.usuario.getName(), match -> match.ignoreCase().startsWith());
            example = Example.of(appProcesoLog, matcher);
        }

        Page<AppProcesoLog> page;
        Sort sort = Sort.by("id");
        sort = this.setFind(appProcesoLog, matcher, example, sort);
        req.generarLazyDefecto();
        this.setFindPaginated(req, matcher, example);
        if (example != null) {
            page = this.appProcesoLogRepository.findAll(example, req.toPageable(sort));
        } else {
            page = this.appProcesoLogRepository.findAll(req.toPageable(sort));
        }

        List<AppProcesoLog> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    protected abstract void setFindPaginated(PageRequestByExample<AppProcesoLog> req, ExampleMatcher matcher, Example<AppProcesoLog> example);

    @Transactional(readOnly = true)
    public XSSFWorkbook downloadExcelXLSX(AppProcesoLog req) {
        log.debug("Ingresando downloadExcelXLSX: ", req);
        List<AppProcesoLog> lista = this.find(req);
        Optional<List<AppProcesoLog>> oList = Optional.ofNullable(lista);
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

        lista.forEach(appProcesoLog -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            XSSFRow dataRow = sheet.createRow(i + 1);
            int contador = 0;
            ExcelDefault.setValueCell(appProcesoLog.getId(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getFechaInicioEjecucion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getFechaFinEjecucion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getEstadoEjecucion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getDescripcionEstadoEjecucion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getClaseProgramacion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getMetodoProgramacion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getParametroEntrada(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getResultadoSalida(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getUsuario(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getDuracionMs(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(appProcesoLog, dataRow);

        });
        this.setDownloadExcel(sheet);
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return book;
    }

    protected void setDownloadExcelItem(AppProcesoLog bean, XSSFRow dataRow) {

    }

    protected void setDownloadExcel(XSSFSheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook downloadExcelSXLSX(AppProcesoLog req) {
        log.debug("Ingresando downloadExcelSXLSX: ", req);
        List<AppProcesoLog> lista = this.find(req);
        Optional<List<AppProcesoLog>> oList = Optional.ofNullable(lista);
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

        for (AppProcesoLog appProcesoLog : lista) {
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

            ExcelDefault.setValueCell(appProcesoLog.getId(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getFechaInicioEjecucion(), dataRow.createCell(contador), "DT", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getFechaFinEjecucion(), dataRow.createCell(contador), "DT", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getEstadoEjecucion(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getDescripcionEstadoEjecucion(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getClaseProgramacion(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getMetodoProgramacion(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getParametroEntrada(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getResultadoSalida(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getUsuario(), dataRow.createCell(contador), "S", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(appProcesoLog.getDuracionMs(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(appProcesoLog, dataRow);

        }
        this.setDownloadExcel(sheet);
        return book;
    }

    protected void setDownloadExcelItem(AppProcesoLog bean, Row dataRow) {

    }

    protected void setDownloadExcel(Sheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook generateInsertExcelSXLSX(AppProcesoLog req) {
        log.debug("Ingresando generateInsertExcelSXLSX: ", req);
        List<AppProcesoLog> lista = this.find(req);
        Optional<List<AppProcesoLog>> oList = Optional.ofNullable(lista);
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

        for (AppProcesoLog appProcesoLog : lista) {
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
            String sqlInsert = "INSERT INTO app_proceso_log(";
            sqlInsert = sqlInsert + "id_app_proceso_log" + ", ";
            sqlInsert = sqlInsert + "fecha_inicio_ejecucion" + ", ";
            sqlInsert = sqlInsert + "fecha_fin_ejecucion" + ", ";
            sqlInsert = sqlInsert + "estado_ejecucion" + ", ";
            sqlInsert = sqlInsert + "descripcion_estado_ejecucion" + ", ";
            sqlInsert = sqlInsert + "clase_programacion" + ", ";
            sqlInsert = sqlInsert + "metodo_programacion" + ", ";
            sqlInsert = sqlInsert + "parametro_entrada" + ", ";
            sqlInsert = sqlInsert + "resultado_salida" + ", ";
            sqlInsert = sqlInsert + "usuario" + ", ";
            sqlInsert = sqlInsert + "duracion_ms" + ")";
            sqlInsert = sqlInsert + " VALUES (";
            sqlInsert = sqlInsert + appProcesoLog.getId() + ", ";
            if (Optional.ofNullable(appProcesoLog.getFechaInicioEjecucion()).isPresent()) {
                sqlInsert = sqlInsert + appProcesoLog.getFechaInicioEjecucion().getTime() + ", ";
            }

            else {
                sqlInsert = sqlInsert + "null" + ", ";
            }
            if (Optional.ofNullable(appProcesoLog.getFechaFinEjecucion()).isPresent()) {
                sqlInsert = sqlInsert + appProcesoLog.getFechaFinEjecucion().getTime() + ", ";
            }

            else {
                sqlInsert = sqlInsert + "null" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getEstadoEjecucion())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getEstadoEjecucion() + "'" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getDescripcionEstadoEjecucion())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getDescripcionEstadoEjecucion() + "'" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getClaseProgramacion())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getClaseProgramacion() + "'" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getMetodoProgramacion())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getMetodoProgramacion() + "'" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getParametroEntrada())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getParametroEntrada() + "'" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getResultadoSalida())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getResultadoSalida() + "'" + ", ";
            }
            if (StringUtils.isBlank(appProcesoLog.getUsuario())) {
                sqlInsert = sqlInsert + "null" + ", ";
            } else {
                sqlInsert = sqlInsert + "'" + appProcesoLog.getUsuario() + "'" + ", ";
            }
            sqlInsert = sqlInsert + appProcesoLog.getDuracionMs();
            sqlInsert = sqlInsert + " );";
            ExcelDefault.setValueCell(sqlInsert, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
        }
        return book;
    }

    protected AppProcesoLog completarDatosBean(AppProcesoLog bean) {
        BigDecimal data = new BigDecimal(0.00);
        bean = this.setCompletarDatosBean(bean);
        return bean;
    }

    protected AppProcesoLog setCompletarDatosBean(AppProcesoLog bean) {
        return bean;
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    public AppProcesoLog save(AppProcesoLog dto) {
        log.debug("Ingresando save: ", dto);
        if (dto == null) {
            return null;
        }
        dto = this.completarDatosBean(dto);
        dto = this.setBeforeSave(dto);
        this.setSave(dto);
        dto = this.appProcesoLogRepository.save(dto);
        dto = this.setAfterSave(dto);
        return dto;
    }

    protected abstract void setSave(AppProcesoLog dto);

    protected AppProcesoLog setBeforeSave(AppProcesoLog dto) {
        return dto;
    }

    protected AppProcesoLog setAfterSave(AppProcesoLog dto) {
        return dto;
    }

    /**
    * Save new entity or update the corresponding entity if any.
    */
    public AppProcesoLog create(AppProcesoLog dto) {
        log.debug("Ingresando create: ", dto);
        if (dto == null) {
            return null;
        }

        AppProcesoLog appProcesoLog;
        appProcesoLog = new AppProcesoLog();

        appProcesoLog.setFechaInicioEjecucion(dto.getFechaInicioEjecucion());
        appProcesoLog.setFechaFinEjecucion(dto.getFechaFinEjecucion());
        appProcesoLog.setEstadoEjecucion(dto.getEstadoEjecucion());
        appProcesoLog.setDescripcionEstadoEjecucion(dto.getDescripcionEstadoEjecucion());
        appProcesoLog.setClaseProgramacion(dto.getClaseProgramacion());
        appProcesoLog.setMetodoProgramacion(dto.getMetodoProgramacion());
        appProcesoLog.setParametroEntrada(dto.getParametroEntrada());
        appProcesoLog.setResultadoSalida(dto.getResultadoSalida());
        appProcesoLog.setUsuario(dto.getUsuario());
        appProcesoLog.setDuracionMs(dto.getDuracionMs());
        appProcesoLog = this.completarDatosBean(appProcesoLog);
        this.setCreate(appProcesoLog);
        return this.appProcesoLogRepository.save(appProcesoLog);
    }

    protected abstract void setCreate(AppProcesoLog dto);

    protected String validacionesPrevias(AppProcesoLog dto) {
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
        this.appProcesoLogRepository.deleteById(id);
    }

    protected abstract void setDelete(Integer id);

    public void deleteAll() {
        log.debug("Ingresando deleteAll");
        this.setDeleteAll();
        this.appProcesoLogRepository.deleteAll();
    }

    protected abstract void setDeleteAll();

    /**
     * Converts the passed appProcesoLog to a DTO.
     */
    protected AppProcesoLog toDTO(AppProcesoLog appProcesoLog) {
        return appProcesoLog;
    }

    /**
     * Converts the passed dto to a AppProcesoLog.
     * Convenient for query by example.
     */
    protected AppProcesoLog toEntity(AppProcesoLog dto) {
        return dto;
    }

}