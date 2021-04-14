package com.incloud.hcp.job;

import com.incloud.hcp.jco.centro.service.JCOCentroServiceNew;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenService;
import com.incloud.hcp.jco.grupoArticulo.service.JCOGrupoArticuloService;
import com.incloud.hcp.jco.materiales.service.JCOMaterialesService;
import com.incloud.hcp.jco.servicios.service.JCOServiciosService;
import com.incloud.hcp.jco.tipoCambio.service.JCOTipoCambioService;
import com.incloud.hcp.jco.unidadMedida.service.JCOUnidadMedidaServiceNew;
import com.incloud.hcp.repository.AppProcesoLogRepository;
import com.incloud.hcp.service.BienServicioService;
import com.incloud.hcp.service.LicitacionService;
import com.incloud.hcp.service.delta.LicitacionSubetapaDeltaService;
import com.incloud.hcp.service.extractor.ExtractorAlmacenService;
import com.incloud.hcp.service.extractor.ExtractorBienServicioService;
import com.incloud.hcp.util.Utils;
import com.incloud.hcp.ws.enums.CodEmpresaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ScheduledTasks.class);

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private LicitacionService licitacionService;

    @Autowired
    private BienServicioService bienServicioService;

    @Autowired
    private AppProcesoLogRepository appProcesoLogRepository;

    @Autowired
    private LicitacionSubetapaDeltaService licitacionSubetapaDeltaService;

    @Autowired
    private JCOGrupoArticuloService jcoGrupoArticuloService;

    @Autowired
    private JCOTipoCambioService jcoTipoCambioService;

    @Autowired
    private JCOMaterialesService jcoMaterialesService;

    @Autowired
    private JCOServiciosService jcoServiciosService;

    @Autowired
    private JCOCentroAlmacenService jcoCentroAlmacenService;

    @Autowired
    private JCOCentroServiceNew jcoCentroServiceNew;

    @Autowired
    private JCOUnidadMedidaServiceNew jcoUnidadMedidaServiceNew;

    @Autowired
    private ExtractorAlmacenService extractorAlmacenService;

    @Autowired
    private ExtractorBienServicioService extractorBienServicioService;

    @Scheduled(cron = "* * * * * ?")
    public void scheduleAlmacen() {
        logger.error("Cron Task scheduleAlmacen:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            // traer primero la lista de proveedores de la BD.
            this.extractorAlmacenService.obtenerAlmacen(CodEmpresaEnum.SILVESTRE.getValor(), "25/07/2020", "25/03/2021");
        } catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleAlmacen ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
        logger.error("Cron Task Fin JOB scheduleAlmacen");
    }

    //@Scheduled(cron = "* * * * * ?")
    public void scheduleProducto() {
        logger.error("Cron Task scheduleProducto:: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            // traer primero la lista de proveedores de la BD.
            this.extractorBienServicioService.extraerBienServicio("01/01/2020", "01/01/2021");
        } catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleProducto ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
        logger.error("Cron Task Fin JOB scheduleProducto");
    }



    /*@Scheduled(cron = "0 1,30 * * * ?")
    public void scheduleLicitacionEstadoPorEvaluar() {
        logger.error("Cron Task scheduleLicitacionEstadoPorEvaluar :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            this.licitacionService.updateLicitacionEstadoPorEvaluar();
        } catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleLicitacionEstadoPorEvaluar ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
        logger.error("Cron Task Fin JOB scheduleLicitacionEstadoPorEvaluar");
    }

    @Scheduled(cron = "0 5,35 * * * ?")
    public void scheduleEnviarCorreoRecordatorio() {
        logger.error("Cron Task scheduleEnviarCorreoRecordatorio :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            this.licitacionSubetapaDeltaService.enviarCorreoRecordatorio();
        } catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleEnviarCorreoRecordatorio ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }*/

//    @Scheduled(cron = "0 45 * * * ?")
//    public void scheduleSincronizarBienServicio() {
//        logger.error("Cron Task scheduleSincronizarBienServicio :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
//        try {
//           this.bienServicioService.sincronizarBienServicioByLastDate();
//        }
//        catch (Exception e) {
//            logger.error("Cron Task Fin JOB scheduleSincronizarBienServicio ERROR: " + Utils.obtieneMensajeErrorException(e));
//        }
//        logger.error("Cron Task Fin JOB scheduleSincronizarBienServicio");
//    }

    // Ejecutar todos los dias 1 al 3 de cada mes desde las 6:00 a 9:00 am cada hora
    /*@Scheduled(cron = "0 0 6-9 1-3 * ?")
    public void scheduleEliminarAppProcesoLog() {
        logger.error("Cron Task scheduleEliminarAppProcesoLog :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            Date fechaMenosMes = DateUtils.obtenerFechaActualMinusMonth(1);
            this.appProcesoLogRepository.deleteAllByFechaInicioEjecucionIsBefore(fechaMenosMes);
        }
        catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleEliminarAppProcesoLog ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }*/

    /***********************************/
    /* Procesos de RFC                 */
    /***********************************/

    /*@Scheduled(cron = "0 0 5-7 * * ?")
    public void scheduleActualizarGrupoArticulos() {
        logger.error("Cron Task scheduleActualizarGrupoArticulos :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            this.jcoGrupoArticuloService.actualizarGrupoArticulo();
        }
        catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleActualizarGrupoArticulos ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }

    @Scheduled(cron = "0 30 5-12 * * ?")
    public void scheduleActualizarTasaCambio() {
        logger.error("Cron Task scheduleActualizarTasaCambio :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            Date fecha = DateUtils.obtenerFechaActual();
            String sFecha = DateUtils.convertDateToString("yyyyMMdd", fecha);
            this.jcoTipoCambioService.actualizarTipoCambio(sFecha);
        }
        catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleActualizarTasaCambio ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }

    @Scheduled(cron = "0 45 5-12 * * ?")
    public void scheduleActualizarBienesServicio() {
        logger.error("Cron Task scheduleActualizarBienesServicio :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            Date fecha = DateUtils.obtenerFechaActual();
            String sFecha = DateUtils.convertDateToString("yyyyMMdd", fecha);
            this.jcoUnidadMedidaServiceNew.actualizarUnidadMedida();
            this.jcoGrupoArticuloService.actualizarGrupoArticulo();
            this.jcoMaterialesService.actualizarMaterialesRFC(sFecha, sFecha);
            this.jcoServiciosService.actualizarMaterialesRFC(sFecha, sFecha);
        }
        catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleActualizarBienesServicio ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }

    @Scheduled(cron = "0 10 6-8 1-3 * ?")
    public void scheduleActualizarCentroAlmacen() {
        logger.error("Cron Task scheduleActualizarCentroAlmacen :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            this.jcoCentroServiceNew.actualizarCentro("");
            this.jcoCentroAlmacenService.actualizaCentroAlmacen("");
        }
        catch (Exception e) {
            logger.error("Cron Task Fin JOB scheduleActualizarBienesServicio ERROR: " + Utils.obtieneMensajeErrorException(e));
        }
    }*/



}