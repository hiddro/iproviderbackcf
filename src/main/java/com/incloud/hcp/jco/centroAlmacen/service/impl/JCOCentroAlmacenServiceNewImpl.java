package com.incloud.hcp.jco.centroAlmacen.service.impl;

import com.incloud.hcp.domain.TempCentroAlmacen;
import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCParameterBuilder;
import com.incloud.hcp.jco.centroAlmacen.dto.CentroAlmacenRFCResponseDto;
import com.incloud.hcp.jco.centroAlmacen.service.JCOCentroAlmacenServiceNew;
import com.incloud.hcp.repository.CentroAlmacenRepository;
import com.incloud.hcp.repository.TempCentroAlmacenRepository;
import com.incloud.hcp.sap.SapLog;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
public class JCOCentroAlmacenServiceNewImpl implements JCOCentroAlmacenServiceNew {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final int NRO_EJECUCIONES_RFC = 10;
    private final int NIVEL = 1;
//    private final String FUNCION_RFC = "ZMMRFC_LISTA_CNTRO_ALMCN";
    private final String FUNCION_RFC = "ZPE_MM_LISTA_CENTRO_ALMACEN";
    private final String NOMBRE_TABLA_RFC = "TO_CENTRO_ALMACEN";

    @Value("${destination.rfc.profit}")
    private String destinationProfit;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;

    @Autowired
    private TempCentroAlmacenRepository tempCentroAlmacenRepository;



    @Override
    public CentroAlmacenRFCResponseDto getListaCentroAlmacen(String centro) throws Exception {

        CentroAlmacenRFCResponseDto centroAlmacenRFCResponseDto = new CentroAlmacenRFCResponseDto();

        /* Ejecucion invocacion a RFC */
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        JCoRepository repo = destination.getRepository();
        logger.error("01A - getCentroAlmacen");
        JCoFunction jCoFunction = repo.getFunction(FUNCION_RFC);
        logger.error("01B - getCentroAlmacen");

        logger.error("parametro ingresado" + centro);
        CentroAlmacenRFCParameterBuilder.build(
                jCoFunction,
                centro
        );
        logger.error("01C - getCentroAlmacen");
        for(int contador=0; contador < NRO_EJECUCIONES_RFC; contador++) {
            try {
                jCoFunction.execute(destination);
                break;
            } catch (Exception e) {
                if (contador == NRO_EJECUCIONES_RFC - 1 ) {
                    logger.error("01Ca - getCentroAlmacen - INI RFC ERROR: "+ e.toString());
                    throw new Exception(e);
                }
            }
        }

        /* Obteniendo los valores obtenidos del RFC */
        logger.error("02 - getCentroAlmacen - FIN RFC");
        JCoParameterList tableParameterList = jCoFunction.getTableParameterList();
        JCoParameterList result = jCoFunction.getExportParameterList();
        SapLog sapLog = new SapLog();
        String codigoSap = result.getString("PO_CODE");
        String message = result.getString("PO_MSJE");
        sapLog.setCode(codigoSap);
        sapLog.setMesaj(message);
        centroAlmacenRFCResponseDto.setSapLog(sapLog);
        logger.error("02b - getCentroAlmacen - sapLog: " + sapLog.toString());

        /* Recorriendo valores obtenidos del RFC */

        List<TempCentroAlmacen> centroAlmacenRFCDtoList = new ArrayList<TempCentroAlmacen>();
        JCoTable table = tableParameterList.getTable(NOMBRE_TABLA_RFC);
        logger.error("02b 01 - getCentroAlmacen - sapLog: " + sapLog.toString());
        if (table != null && !table.isEmpty()) {

            do {
                //logger.error("02 bA - getDevuelveValores TABLE JCO: " + table.toString());
                TempCentroAlmacen centroAlmacenRFCDto = new TempCentroAlmacen();
                centroAlmacenRFCDto.setCentro(table.getString("WERKS"));
                centroAlmacenRFCDto.setPoblacion(table.getString("ORT01"));
                centroAlmacenRFCDto.setDistrito(table.getString("CITY2"));
                centroAlmacenRFCDto.setDireccion(table.getString("STRAS"));
                centroAlmacenRFCDto.setCodigoAlmacen(table.getString("LGORT"));
                centroAlmacenRFCDto.setDescripcionAlmacen(table.getString("LGOBE"));

                centroAlmacenRFCDtoList.add(centroAlmacenRFCDto);
            } while (table.nextRow());
        }
        logger.error("02b 01 - getCentroAlmacen - fin recorrido list: " + sapLog.toString());
        centroAlmacenRFCResponseDto.setListaCentroAlmacen(centroAlmacenRFCDtoList);
        if (centroAlmacenRFCDtoList != null && centroAlmacenRFCDtoList.size() > 0) {
            centroAlmacenRFCResponseDto.setContador(centroAlmacenRFCDtoList.size());
            logger.error("02 getDevuelveValores centroAlmacenRFCDtoList: " + centroAlmacenRFCDtoList.size());
        }
        this.tempCentroAlmacenRepository.deleteAlll();
        this.tempCentroAlmacenRepository.saveAll(centroAlmacenRFCDtoList);
        return centroAlmacenRFCResponseDto;
    }
}
