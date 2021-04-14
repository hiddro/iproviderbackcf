/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/domain/EntityMeta_.java.e.vm
 */
package com.incloud.hcp.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.util.Date;

@StaticMetamodel(LicitacionProveedorDetalleEvaluacion.class)
public abstract class LicitacionProveedorDetalleEvaluacion_ {

    // Raw attributes
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, Integer> id;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, Integer> factorDenominadorTasa;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> tasaMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> pesoMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> pesoCondicion;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> pesoEvaluacionTecnica;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> precioSuma;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> tasaInteresMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> costoFinancieroMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> precioFinalMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> precioFinalMinimoMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> puntajeCondicion;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> puntajeMaximoCondicion;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> puntajeEvaluacionTecnica;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> porcentajePrecioObtenido;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> porcentajeCondicionObtenido;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> porcentajeEvaluacionTecnicaObtenido;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> porcentajeTotalObtenido;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, Date> fechaTasaCambio;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> tasaCambio;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> cantidadTotalLicitacion;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, BigDecimal> cantidadTotalCotizacion;

    // Many to one
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, Proveedor> idProveedor;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, Moneda> idMoneda;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, LicitacionDetalle> idLicitacionDetalle;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, CotizacionDetalle> idCotizacionDetalle;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, TipoLicitacion> idTipoLicitacion;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, CondicionPago> idCondicionPago;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, Licitacion> idLicitacion;
    public static volatile SingularAttribute<LicitacionProveedorDetalleEvaluacion, TipoLicitacion> idTipoCuestionario;
}