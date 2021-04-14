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
import com.incloud.hcp.domain.LicitacionProveedorDetalleEvaluacion;
import com.incloud.hcp.repository.*;
import com.incloud.hcp.service.*;
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
 * A simple DTO Facility for LicitacionProveedorDetalleEvaluacion.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class LicitacionProveedorDetalleEvaluacionServiceImpl implements LicitacionProveedorDetalleEvaluacionService {

    protected final Logger log = LoggerFactory.getLogger(LicitacionProveedorDetalleEvaluacionServiceImpl.class);

    protected final String NAME_SHEET = "LicitacionProveedorDetalleEvaluacion";
    protected final String CONFIG_TITLE = "com/incloud/hcp/excel/LicitacionProveedorDetalleEvaluacionExcel.xml";

    @Autowired
    protected MessageSource messageSource;

    @Autowired
    protected LicitacionProveedorDetalleEvaluacionRepository licitacionProveedorDetalleEvaluacionRepository;

    @Autowired
    protected ProveedorService proveedorService;

    @Autowired
    protected ProveedorRepository proveedorRepository;

    @Autowired
    protected MonedaService monedaService;

    @Autowired
    protected MonedaRepository monedaRepository;



    @Autowired
    protected LicitacionDetalleRepository licitacionDetalleRepository;


    @Autowired
    protected CotizacionDetalleRepository cotizacionDetalleRepository;

    @Autowired
    protected TipoLicitacionService tipoLicitacionService;

    @Autowired
    protected TipoLicitacionRepository tipoLicitacionRepository;

    @Autowired
    protected CondicionPagoService condicionPagoService;


    @Autowired
    protected LicitacionService licitacionService;

    @Autowired
    protected LicitacionRepository licitacionRepository;

    @Transactional(readOnly = true)
    public Optional<LicitacionProveedorDetalleEvaluacion> findOne(Integer id) {
        log.debug("Ingresando findOne: ", id);
        return this.licitacionProveedorDetalleEvaluacionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LicitacionProveedorDetalleEvaluacion> findAll() {
        log.debug("Ingresando findAll");
        Sort sort = Sort.by("id");
        sort = this.setFindAll(sort);
        List<LicitacionProveedorDetalleEvaluacion> lista = this.licitacionProveedorDetalleEvaluacionRepository.findAll(sort);
        return lista;
    }

    protected abstract Sort setFindAll(Sort sort);

    @Transactional(readOnly = true)
    public List<LicitacionProveedorDetalleEvaluacion> find(LicitacionProveedorDetalleEvaluacion req) {
        log.debug("Ingresando find: ", req);
        Example<LicitacionProveedorDetalleEvaluacion> example = null;
        LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion = req;
        ExampleMatcher matcher = null;
        if (licitacionProveedorDetalleEvaluacion != null) {
            matcher = ExampleMatcher.matching() //
            ;
            example = Example.of(licitacionProveedorDetalleEvaluacion, matcher);
        }
        Sort sort = Sort.by("id");
        sort = this.setFind(req, matcher, example, sort);
        List<LicitacionProveedorDetalleEvaluacion> lista = this.licitacionProveedorDetalleEvaluacionRepository.findAll(example, sort);
        return lista;
    }

    protected abstract Sort setFind(LicitacionProveedorDetalleEvaluacion req, ExampleMatcher matcher, Example<LicitacionProveedorDetalleEvaluacion> example,
            Sort sort);

    @Transactional(readOnly = true)
    public PageResponse<LicitacionProveedorDetalleEvaluacion> findPaginated(PageRequestByExample<LicitacionProveedorDetalleEvaluacion> req) {
        log.debug("Ingresando findPaginated: ", req);
        Example<LicitacionProveedorDetalleEvaluacion> example = null;
        LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion = toEntity(req.example);
        ExampleMatcher matcher = null;
        if (licitacionProveedorDetalleEvaluacion != null) {
            matcher = ExampleMatcher.matching() //
            ;
            example = Example.of(licitacionProveedorDetalleEvaluacion, matcher);
        }

        Page<LicitacionProveedorDetalleEvaluacion> page;
        Sort sort = Sort.by("id");
        sort = this.setFind(licitacionProveedorDetalleEvaluacion, matcher, example, sort);
        req.generarLazyDefecto();
        this.setFindPaginated(req, matcher, example);
        if (example != null) {
            page = this.licitacionProveedorDetalleEvaluacionRepository.findAll(example, req.toPageable(sort));
        } else {
            page = this.licitacionProveedorDetalleEvaluacionRepository.findAll(req.toPageable(sort));
        }

        List<LicitacionProveedorDetalleEvaluacion> content = page.getContent().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResponse<>(page.getTotalPages(), page.getTotalElements(), content);
    }

    protected abstract void setFindPaginated(PageRequestByExample<LicitacionProveedorDetalleEvaluacion> req, ExampleMatcher matcher,
            Example<LicitacionProveedorDetalleEvaluacion> example);

    @Transactional(readOnly = true)
    public XSSFWorkbook downloadExcelXLSX(LicitacionProveedorDetalleEvaluacion req) {
        log.debug("Ingresando downloadExcelXLSX: ", req);
        List<LicitacionProveedorDetalleEvaluacion> lista = this.find(req);
        Optional<List<LicitacionProveedorDetalleEvaluacion>> oList = Optional.ofNullable(lista);
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

        lista.forEach(licitacionProveedorDetalleEvaluacion -> {
            int lastRow = sheet.getLastRowNum();
            int i = lastRow < 0 ? 0 : lastRow;
            XSSFRow dataRow = sheet.createRow(i + 1);
            int contador = 0;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getId(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getFactorDenominadorTasa(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getTasaMoneda(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPesoMoneda(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPesoCondicion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPesoEvaluacionTecnica(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPrecioSuma(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getTasaInteresMoneda(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getCostoFinancieroMoneda(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPrecioFinalMinimoMoneda(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPuntajeCondicion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPuntajeMaximoCondicion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPuntajeEvaluacionTecnica(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido(), dataRow.createCell(contador), cellStyle,
                    dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido(), dataRow.createCell(contador), cellStyle,
                    dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getFechaTasaCambio(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getTasaCambio(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getCantidadTotalLicitacion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion(), dataRow.createCell(contador), cellStyle, dataFormat);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(licitacionProveedorDetalleEvaluacion, dataRow);

        });
        this.setDownloadExcel(sheet);
        int totalColumn = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < totalColumn; i++) {
            sheet.autoSizeColumn(i, true);
        }
        return book;
    }

    protected void setDownloadExcelItem(LicitacionProveedorDetalleEvaluacion bean, XSSFRow dataRow) {

    }

    protected void setDownloadExcel(XSSFSheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook downloadExcelSXLSX(LicitacionProveedorDetalleEvaluacion req) {
        log.debug("Ingresando downloadExcelSXLSX: ", req);
        List<LicitacionProveedorDetalleEvaluacion> lista = this.find(req);
        Optional<List<LicitacionProveedorDetalleEvaluacion>> oList = Optional.ofNullable(lista);
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

        for (LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion : lista) {
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

            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getId(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getFactorDenominadorTasa(), dataRow.createCell(contador), "I", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getTasaMoneda(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPesoMoneda(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPesoCondicion(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPesoEvaluacionTecnica(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPrecioSuma(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getTasaInteresMoneda(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getCostoFinancieroMoneda(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPrecioFinalMinimoMoneda(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPuntajeCondicion(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPuntajeMaximoCondicion(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPuntajeEvaluacionTecnica(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido(), dataRow.createCell(contador), "N",
                    cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getFechaTasaCambio(), dataRow.createCell(contador), "DT", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getTasaCambio(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getCantidadTotalLicitacion(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            ExcelDefault.setValueCell(licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion(), dataRow.createCell(contador), "N", cellStyleList);
            contador++;
            /* Agregar aqui si desean colocar mas campos del BEAN */
            this.setDownloadExcelItem(licitacionProveedorDetalleEvaluacion, dataRow);

        }
        this.setDownloadExcel(sheet);
        return book;
    }

    protected void setDownloadExcelItem(LicitacionProveedorDetalleEvaluacion bean, Row dataRow) {

    }

    protected void setDownloadExcel(Sheet sheet) {

    }

    @Transactional(readOnly = true)
    public SXSSFWorkbook generateInsertExcelSXLSX(LicitacionProveedorDetalleEvaluacion req) {
        log.debug("Ingresando generateInsertExcelSXLSX: ", req);
        List<LicitacionProveedorDetalleEvaluacion> lista = this.find(req);
        Optional<List<LicitacionProveedorDetalleEvaluacion>> oList = Optional.ofNullable(lista);
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

        for (LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion : lista) {
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
            String sqlInsert = "INSERT INTO licitacion_proveedor_detalle_evaluacion(";
            sqlInsert = sqlInsert + "id_licitacion_proveedor_detalle_evaluacion" + ", ";
            sqlInsert = sqlInsert + "factor_denominador_tasa" + ", ";
            sqlInsert = sqlInsert + "tasa_moneda" + ", ";
            sqlInsert = sqlInsert + "peso_moneda" + ", ";
            sqlInsert = sqlInsert + "peso_condicion" + ", ";
            sqlInsert = sqlInsert + "peso_evaluacion_tecnica" + ", ";
            sqlInsert = sqlInsert + "precio_suma" + ", ";
            sqlInsert = sqlInsert + "tasa_interes_moneda" + ", ";
            sqlInsert = sqlInsert + "costo_financiero_moneda" + ", ";
            sqlInsert = sqlInsert + "precio_final_moneda" + ", ";
            sqlInsert = sqlInsert + "precio_final_minimo_moneda" + ", ";
            sqlInsert = sqlInsert + "puntaje_condicion" + ", ";
            sqlInsert = sqlInsert + "puntaje_maximo_condicion" + ", ";
            sqlInsert = sqlInsert + "puntaje_evaluacion_tecnica" + ", ";
            sqlInsert = sqlInsert + "porcentaje_precio_obtenido" + ", ";
            sqlInsert = sqlInsert + "porcentaje_condicion_obtenido" + ", ";
            sqlInsert = sqlInsert + "porcentaje_evaluacion_tecnica_obtenido" + ", ";
            sqlInsert = sqlInsert + "porcentaje_total_obtenido" + ", ";
            sqlInsert = sqlInsert + "fecha_tasa_cambio" + ", ";
            sqlInsert = sqlInsert + "tasa_cambio" + ", ";
            sqlInsert = sqlInsert + "cantidad_total_licitacion" + ", ";
            sqlInsert = sqlInsert + "cantidad_total_cotizacion" + ")";
            sqlInsert = sqlInsert + " VALUES (";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getId() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getFactorDenominadorTasa() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getTasaMoneda() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPesoMoneda() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPesoCondicion() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPesoEvaluacionTecnica() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPrecioSuma() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getTasaInteresMoneda() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getCostoFinancieroMoneda() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPrecioFinalMoneda() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPrecioFinalMinimoMoneda() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPuntajeCondicion() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPuntajeMaximoCondicion() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPuntajeEvaluacionTecnica() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPorcentajePrecioObtenido() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPorcentajeCondicionObtenido() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPorcentajeEvaluacionTecnicaObtenido() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getPorcentajeTotalObtenido() + ", ";
            if (Optional.ofNullable(licitacionProveedorDetalleEvaluacion.getFechaTasaCambio()).isPresent()) {
                sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getFechaTasaCambio().getTime() + ", ";
            }

            else {
                sqlInsert = sqlInsert + "null" + ", ";
            }
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getTasaCambio() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getCantidadTotalLicitacion() + ", ";
            sqlInsert = sqlInsert + licitacionProveedorDetalleEvaluacion.getCantidadTotalCotizacion();
            sqlInsert = sqlInsert + " );";
            ExcelDefault.setValueCell(sqlInsert, dataRow.createCell(contador), "S", cellStyleList);
            contador++;
        }
        return book;
    }

    protected LicitacionProveedorDetalleEvaluacion completarDatosBean(LicitacionProveedorDetalleEvaluacion bean) {
        BigDecimal data = new BigDecimal(0.00);
        if (Optional.ofNullable(bean.getTasaMoneda()).isPresent()) {
            bean.setTasaMoneda(bean.getTasaMoneda().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPesoMoneda()).isPresent()) {
            bean.setPesoMoneda(bean.getPesoMoneda().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPesoCondicion()).isPresent()) {
            bean.setPesoCondicion(bean.getPesoCondicion().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPesoEvaluacionTecnica()).isPresent()) {
            bean.setPesoEvaluacionTecnica(bean.getPesoEvaluacionTecnica().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPrecioSuma()).isPresent()) {
            bean.setPrecioSuma(bean.getPrecioSuma().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getTasaInteresMoneda()).isPresent()) {
            bean.setTasaInteresMoneda(bean.getTasaInteresMoneda().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getCostoFinancieroMoneda()).isPresent()) {
            bean.setCostoFinancieroMoneda(bean.getCostoFinancieroMoneda().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPrecioFinalMoneda()).isPresent()) {
            bean.setPrecioFinalMoneda(bean.getPrecioFinalMoneda().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPrecioFinalMinimoMoneda()).isPresent()) {
            bean.setPrecioFinalMinimoMoneda(bean.getPrecioFinalMinimoMoneda().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPuntajeCondicion()).isPresent()) {
            bean.setPuntajeCondicion(bean.getPuntajeCondicion().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPuntajeMaximoCondicion()).isPresent()) {
            bean.setPuntajeMaximoCondicion(bean.getPuntajeMaximoCondicion().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPuntajeEvaluacionTecnica()).isPresent()) {
            bean.setPuntajeEvaluacionTecnica(bean.getPuntajeEvaluacionTecnica().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPorcentajePrecioObtenido()).isPresent()) {
            bean.setPorcentajePrecioObtenido(bean.getPorcentajePrecioObtenido().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPorcentajeCondicionObtenido()).isPresent()) {
            bean.setPorcentajeCondicionObtenido(bean.getPorcentajeCondicionObtenido().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPorcentajeEvaluacionTecnicaObtenido()).isPresent()) {
            bean.setPorcentajeEvaluacionTecnicaObtenido(bean.getPorcentajeEvaluacionTecnicaObtenido().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getPorcentajeTotalObtenido()).isPresent()) {
            bean.setPorcentajeTotalObtenido(bean.getPorcentajeTotalObtenido().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getTasaCambio()).isPresent()) {
            bean.setTasaCambio(bean.getTasaCambio().setScale(4, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getCantidadTotalLicitacion()).isPresent()) {
            bean.setCantidadTotalLicitacion(bean.getCantidadTotalLicitacion().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if (Optional.ofNullable(bean.getCantidadTotalCotizacion()).isPresent()) {
            bean.setCantidadTotalCotizacion(bean.getCantidadTotalCotizacion().setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        bean = this.setCompletarDatosBean(bean);
        return bean;
    }

    protected LicitacionProveedorDetalleEvaluacion setCompletarDatosBean(LicitacionProveedorDetalleEvaluacion bean) {
        return bean;
    }

    /**
     * Save the passed dto as a new entity or update the corresponding entity if any.
     */
    public LicitacionProveedorDetalleEvaluacion save(LicitacionProveedorDetalleEvaluacion dto) {
        log.debug("Ingresando save: ", dto);
        if (dto == null) {
            return null;
        }
        dto = this.completarDatosBean(dto);
        dto = this.setBeforeSave(dto);
        this.setSave(dto);
        dto = this.licitacionProveedorDetalleEvaluacionRepository.save(dto);
        dto = this.setAfterSave(dto);
        return dto;
    }

    protected abstract void setSave(LicitacionProveedorDetalleEvaluacion dto);

    protected LicitacionProveedorDetalleEvaluacion setBeforeSave(LicitacionProveedorDetalleEvaluacion dto) {
        return dto;
    }

    protected LicitacionProveedorDetalleEvaluacion setAfterSave(LicitacionProveedorDetalleEvaluacion dto) {
        return dto;
    }

    /**
    * Save new entity or update the corresponding entity if any.
    */
    public LicitacionProveedorDetalleEvaluacion create(LicitacionProveedorDetalleEvaluacion dto) {
        log.debug("Ingresando create: ", dto);
        if (dto == null) {
            return null;
        }

        LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion;
        licitacionProveedorDetalleEvaluacion = new LicitacionProveedorDetalleEvaluacion();

        licitacionProveedorDetalleEvaluacion.setFactorDenominadorTasa(dto.getFactorDenominadorTasa());
        licitacionProveedorDetalleEvaluacion.setTasaMoneda(dto.getTasaMoneda());
        licitacionProveedorDetalleEvaluacion.setPesoMoneda(dto.getPesoMoneda());
        licitacionProveedorDetalleEvaluacion.setPesoCondicion(dto.getPesoCondicion());
        licitacionProveedorDetalleEvaluacion.setPesoEvaluacionTecnica(dto.getPesoEvaluacionTecnica());
        licitacionProveedorDetalleEvaluacion.setPrecioSuma(dto.getPrecioSuma());
        licitacionProveedorDetalleEvaluacion.setTasaInteresMoneda(dto.getTasaInteresMoneda());
        licitacionProveedorDetalleEvaluacion.setCostoFinancieroMoneda(dto.getCostoFinancieroMoneda());
        licitacionProveedorDetalleEvaluacion.setPrecioFinalMoneda(dto.getPrecioFinalMoneda());
        licitacionProveedorDetalleEvaluacion.setPrecioFinalMinimoMoneda(dto.getPrecioFinalMinimoMoneda());
        licitacionProveedorDetalleEvaluacion.setPuntajeCondicion(dto.getPuntajeCondicion());
        licitacionProveedorDetalleEvaluacion.setPuntajeMaximoCondicion(dto.getPuntajeMaximoCondicion());
        licitacionProveedorDetalleEvaluacion.setPuntajeEvaluacionTecnica(dto.getPuntajeEvaluacionTecnica());
        licitacionProveedorDetalleEvaluacion.setPorcentajePrecioObtenido(dto.getPorcentajePrecioObtenido());
        licitacionProveedorDetalleEvaluacion.setPorcentajeCondicionObtenido(dto.getPorcentajeCondicionObtenido());
        licitacionProveedorDetalleEvaluacion.setPorcentajeEvaluacionTecnicaObtenido(dto.getPorcentajeEvaluacionTecnicaObtenido());
        licitacionProveedorDetalleEvaluacion.setPorcentajeTotalObtenido(dto.getPorcentajeTotalObtenido());
        licitacionProveedorDetalleEvaluacion.setFechaTasaCambio(dto.getFechaTasaCambio());
        licitacionProveedorDetalleEvaluacion.setTasaCambio(dto.getTasaCambio());
        licitacionProveedorDetalleEvaluacion.setCantidadTotalLicitacion(dto.getCantidadTotalLicitacion());
        licitacionProveedorDetalleEvaluacion.setCantidadTotalCotizacion(dto.getCantidadTotalCotizacion());
        licitacionProveedorDetalleEvaluacion = this.completarDatosBean(licitacionProveedorDetalleEvaluacion);
        this.setCreate(licitacionProveedorDetalleEvaluacion);
        return this.licitacionProveedorDetalleEvaluacionRepository.save(licitacionProveedorDetalleEvaluacion);
    }

    protected abstract void setCreate(LicitacionProveedorDetalleEvaluacion dto);

    protected String validacionesPrevias(LicitacionProveedorDetalleEvaluacion dto) {
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
        this.licitacionProveedorDetalleEvaluacionRepository.deleteById(id);
    }

    protected abstract void setDelete(Integer id);

    public void deleteAll() {
        log.debug("Ingresando deleteAll");
        this.setDeleteAll();
        this.licitacionProveedorDetalleEvaluacionRepository.deleteAll();
    }

    protected abstract void setDeleteAll();

    /**
     * Converts the passed licitacionProveedorDetalleEvaluacion to a DTO.
     */
    protected LicitacionProveedorDetalleEvaluacion toDTO(LicitacionProveedorDetalleEvaluacion licitacionProveedorDetalleEvaluacion) {
        return licitacionProveedorDetalleEvaluacion;
    }

    /**
     * Converts the passed dto to a LicitacionProveedorDetalleEvaluacion.
     * Convenient for query by example.
     */
    protected LicitacionProveedorDetalleEvaluacion toEntity(LicitacionProveedorDetalleEvaluacion dto) {
        return dto;
    }

}