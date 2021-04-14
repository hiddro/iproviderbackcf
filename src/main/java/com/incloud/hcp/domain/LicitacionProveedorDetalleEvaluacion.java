/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/domain/Entity.java.e.vm
 */
package com.incloud.hcp.domain;

import com.incloud.hcp.domain._framework.Identifiable;
import com.incloud.hcp.domain._framework.IdentifiableHashBuilder;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "licitacion_proveedor_detalle_evaluacion", uniqueConstraints = {
        @UniqueConstraint(name = "licitacion_proveedor_detalle_evaluacion_ak01", columnNames = { "id_licitacion", "id_proveedor", "id_tipo_licitacion",
                "id_tipo_cuestionario", "id_licitacion_detalle" }) })
public class LicitacionProveedorDetalleEvaluacion implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(LicitacionProveedorDetalleEvaluacion.class.getName());

    // Raw attributes
    private Integer id;
    private Integer factorDenominadorTasa;
    private BigDecimal tasaMoneda;
    private BigDecimal pesoMoneda;
    private BigDecimal pesoCondicion;
    private BigDecimal pesoEvaluacionTecnica;
    private BigDecimal precioUnitario;
    private BigDecimal precioSuma;
    private BigDecimal tasaInteresMoneda;
    private BigDecimal costoFinancieroMoneda;
    private BigDecimal precioFinalMoneda;
    private BigDecimal precioFinalMinimoMoneda;
    private BigDecimal puntajeCondicion;
    private BigDecimal puntajeMaximoCondicion;
    private BigDecimal puntajeEvaluacionTecnica;
    private BigDecimal porcentajePrecioObtenido;
    private BigDecimal porcentajeCondicionObtenido;
    private BigDecimal porcentajeEvaluacionTecnicaObtenido;
    private BigDecimal porcentajeTotalObtenido;
    private Date fechaTasaCambio;
    private BigDecimal tasaCambio;
    private BigDecimal cantidadTotalLicitacion;
    private BigDecimal cantidadTotalCotizacion;

    // Many to one
    private Proveedor idProveedor;
    private Moneda idMoneda;
    private LicitacionDetalle idLicitacionDetalle;
    private CotizacionDetalle idCotizacionDetalle;
    private TipoLicitacion idTipoLicitacion;
    private CondicionPago idCondicionPago;
    private Licitacion idLicitacion;
    private TipoLicitacion idTipoCuestionario;

    @Override
    public String entityClassName() {
        return LicitacionProveedorDetalleEvaluacion.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_licitacion_proveedor_detalle_evaluacion", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_licitacion_proveedor_detalle_evaluacion")
    @Id
    @SequenceGenerator(name = "seq_licitacion_proveedor_detalle_evaluacion", sequenceName = "seq_licitacion_proveedor_detalle_evaluacion")
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LicitacionProveedorDetalleEvaluacion id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [factorDenominadorTasa] ------------------------

    @Digits(integer = 10, fraction = 0)
    @NotNull
    @Column(name = "factor_denominador_tasa", nullable = false, precision = 10)
    public Integer getFactorDenominadorTasa() {
        return factorDenominadorTasa;
    }

    public void setFactorDenominadorTasa(Integer factorDenominadorTasa) {
        this.factorDenominadorTasa = factorDenominadorTasa;
    }

    public LicitacionProveedorDetalleEvaluacion factorDenominadorTasa(Integer factorDenominadorTasa) {
        setFactorDenominadorTasa(factorDenominadorTasa);
        return this;
    }
    // -- [tasaMoneda] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "tasa_moneda", nullable = false, precision = 15, scale = 2)
    public BigDecimal getTasaMoneda() {
        return tasaMoneda;
    }

    public void setTasaMoneda(BigDecimal tasaMoneda) {
        this.tasaMoneda = tasaMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion tasaMoneda(BigDecimal tasaMoneda) {
        setTasaMoneda(tasaMoneda);
        return this;
    }
    // -- [pesoMoneda] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "peso_moneda", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPesoMoneda() {
        return pesoMoneda;
    }

    public void setPesoMoneda(BigDecimal pesoMoneda) {
        this.pesoMoneda = pesoMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion pesoMoneda(BigDecimal pesoMoneda) {
        setPesoMoneda(pesoMoneda);
        return this;
    }
    // -- [pesoCondicion] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "peso_condicion", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPesoCondicion() {
        return pesoCondicion;
    }

    public void setPesoCondicion(BigDecimal pesoCondicion) {
        this.pesoCondicion = pesoCondicion;
    }

    public LicitacionProveedorDetalleEvaluacion pesoCondicion(BigDecimal pesoCondicion) {
        setPesoCondicion(pesoCondicion);
        return this;
    }
    // -- [pesoEvaluacionTecnica] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "peso_evaluacion_tecnica", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPesoEvaluacionTecnica() {
        return pesoEvaluacionTecnica;
    }

    public void setPesoEvaluacionTecnica(BigDecimal pesoEvaluacionTecnica) {
        this.pesoEvaluacionTecnica = pesoEvaluacionTecnica;
    }

    public LicitacionProveedorDetalleEvaluacion pesoEvaluacionTecnica(BigDecimal pesoEvaluacionTecnica) {
        setPesoEvaluacionTecnica(pesoEvaluacionTecnica);
        return this;
    }
    // -- [precioUnitario] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "precio_unitario", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public LicitacionProveedorDetalleEvaluacion precioUnitario(BigDecimal precioUnitario) {
        setPrecioUnitario(precioUnitario);
        return this;
    }

    // -- [precioSuma] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "precio_suma", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPrecioSuma() {
        return precioSuma;
    }

    public void setPrecioSuma(BigDecimal precioSuma) {
        this.precioSuma = precioSuma;
    }

    public LicitacionProveedorDetalleEvaluacion precioSuma(BigDecimal precioSuma) {
        setPrecioSuma(precioSuma);
        return this;
    }
    // -- [tasaInteresMoneda] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "tasa_interes_moneda", nullable = false, precision = 15, scale = 2)
    public BigDecimal getTasaInteresMoneda() {
        return tasaInteresMoneda;
    }

    public void setTasaInteresMoneda(BigDecimal tasaInteresMoneda) {
        this.tasaInteresMoneda = tasaInteresMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion tasaInteresMoneda(BigDecimal tasaInteresMoneda) {
        setTasaInteresMoneda(tasaInteresMoneda);
        return this;
    }
    // -- [costoFinancieroMoneda] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "costo_financiero_moneda", nullable = false, precision = 15, scale = 2)
    public BigDecimal getCostoFinancieroMoneda() {
        return costoFinancieroMoneda;
    }

    public void setCostoFinancieroMoneda(BigDecimal costoFinancieroMoneda) {
        this.costoFinancieroMoneda = costoFinancieroMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion costoFinancieroMoneda(BigDecimal costoFinancieroMoneda) {
        setCostoFinancieroMoneda(costoFinancieroMoneda);
        return this;
    }
    // -- [precioFinalMoneda] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "precio_final_moneda", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPrecioFinalMoneda() {
        return precioFinalMoneda;
    }

    public void setPrecioFinalMoneda(BigDecimal precioFinalMoneda) {
        this.precioFinalMoneda = precioFinalMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion precioFinalMoneda(BigDecimal precioFinalMoneda) {
        setPrecioFinalMoneda(precioFinalMoneda);
        return this;
    }
    // -- [precioFinalMinimoMoneda] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "precio_final_minimo_moneda", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPrecioFinalMinimoMoneda() {
        return precioFinalMinimoMoneda;
    }

    public void setPrecioFinalMinimoMoneda(BigDecimal precioFinalMinimoMoneda) {
        this.precioFinalMinimoMoneda = precioFinalMinimoMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion precioFinalMinimoMoneda(BigDecimal precioFinalMinimoMoneda) {
        setPrecioFinalMinimoMoneda(precioFinalMinimoMoneda);
        return this;
    }
    // -- [puntajeCondicion] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "puntaje_condicion", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPuntajeCondicion() {
        return puntajeCondicion;
    }

    public void setPuntajeCondicion(BigDecimal puntajeCondicion) {
        this.puntajeCondicion = puntajeCondicion;
    }

    public LicitacionProveedorDetalleEvaluacion puntajeCondicion(BigDecimal puntajeCondicion) {
        setPuntajeCondicion(puntajeCondicion);
        return this;
    }
    // -- [puntajeMaximoCondicion] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "puntaje_maximo_condicion", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPuntajeMaximoCondicion() {
        return puntajeMaximoCondicion;
    }

    public void setPuntajeMaximoCondicion(BigDecimal puntajeMaximoCondicion) {
        this.puntajeMaximoCondicion = puntajeMaximoCondicion;
    }

    public LicitacionProveedorDetalleEvaluacion puntajeMaximoCondicion(BigDecimal puntajeMaximoCondicion) {
        setPuntajeMaximoCondicion(puntajeMaximoCondicion);
        return this;
    }
    // -- [puntajeEvaluacionTecnica] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "puntaje_evaluacion_tecnica", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPuntajeEvaluacionTecnica() {
        return puntajeEvaluacionTecnica;
    }

    public void setPuntajeEvaluacionTecnica(BigDecimal puntajeEvaluacionTecnica) {
        this.puntajeEvaluacionTecnica = puntajeEvaluacionTecnica;
    }

    public LicitacionProveedorDetalleEvaluacion puntajeEvaluacionTecnica(BigDecimal puntajeEvaluacionTecnica) {
        setPuntajeEvaluacionTecnica(puntajeEvaluacionTecnica);
        return this;
    }
    // -- [porcentajePrecioObtenido] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_precio_obtenido", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPorcentajePrecioObtenido() {
        return porcentajePrecioObtenido;
    }

    public void setPorcentajePrecioObtenido(BigDecimal porcentajePrecioObtenido) {
        this.porcentajePrecioObtenido = porcentajePrecioObtenido;
    }

    public LicitacionProveedorDetalleEvaluacion porcentajePrecioObtenido(BigDecimal porcentajePrecioObtenido) {
        setPorcentajePrecioObtenido(porcentajePrecioObtenido);
        return this;
    }
    // -- [porcentajeCondicionObtenido] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_condicion_obtenido", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPorcentajeCondicionObtenido() {
        return porcentajeCondicionObtenido;
    }

    public void setPorcentajeCondicionObtenido(BigDecimal porcentajeCondicionObtenido) {
        this.porcentajeCondicionObtenido = porcentajeCondicionObtenido;
    }

    public LicitacionProveedorDetalleEvaluacion porcentajeCondicionObtenido(BigDecimal porcentajeCondicionObtenido) {
        setPorcentajeCondicionObtenido(porcentajeCondicionObtenido);
        return this;
    }
    // -- [porcentajeEvaluacionTecnicaObtenido] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_evaluacion_tecnica_obtenido", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPorcentajeEvaluacionTecnicaObtenido() {
        return porcentajeEvaluacionTecnicaObtenido;
    }

    public void setPorcentajeEvaluacionTecnicaObtenido(BigDecimal porcentajeEvaluacionTecnicaObtenido) {
        this.porcentajeEvaluacionTecnicaObtenido = porcentajeEvaluacionTecnicaObtenido;
    }

    public LicitacionProveedorDetalleEvaluacion porcentajeEvaluacionTecnicaObtenido(BigDecimal porcentajeEvaluacionTecnicaObtenido) {
        setPorcentajeEvaluacionTecnicaObtenido(porcentajeEvaluacionTecnicaObtenido);
        return this;
    }
    // -- [porcentajeTotalObtenido] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_total_obtenido", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPorcentajeTotalObtenido() {
        return porcentajeTotalObtenido;
    }

    public void setPorcentajeTotalObtenido(BigDecimal porcentajeTotalObtenido) {
        this.porcentajeTotalObtenido = porcentajeTotalObtenido;
    }

    public LicitacionProveedorDetalleEvaluacion porcentajeTotalObtenido(BigDecimal porcentajeTotalObtenido) {
        setPorcentajeTotalObtenido(porcentajeTotalObtenido);
        return this;
    }
    // -- [fechaTasaCambio] ------------------------

    @Column(name = "fecha_tasa_cambio", length = 29)
    @Temporal(TIMESTAMP)
    public Date getFechaTasaCambio() {
        return fechaTasaCambio;
    }

    public void setFechaTasaCambio(Date fechaTasaCambio) {
        this.fechaTasaCambio = fechaTasaCambio;
    }

    public LicitacionProveedorDetalleEvaluacion fechaTasaCambio(Date fechaTasaCambio) {
        setFechaTasaCambio(fechaTasaCambio);
        return this;
    }
    // -- [tasaCambio] ------------------------

    @Digits(integer = 14, fraction = 4)
    @NotNull
    @Column(name = "tasa_cambio", nullable = false, precision = 18, scale = 4)
    public BigDecimal getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(BigDecimal tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    public LicitacionProveedorDetalleEvaluacion tasaCambio(BigDecimal tasaCambio) {
        setTasaCambio(tasaCambio);
        return this;
    }
    // -- [cantidadTotalLicitacion] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "cantidad_total_licitacion", nullable = false, precision = 15, scale = 2)
    public BigDecimal getCantidadTotalLicitacion() {
        return cantidadTotalLicitacion;
    }

    public void setCantidadTotalLicitacion(BigDecimal cantidadTotalLicitacion) {
        this.cantidadTotalLicitacion = cantidadTotalLicitacion;
    }

    public LicitacionProveedorDetalleEvaluacion cantidadTotalLicitacion(BigDecimal cantidadTotalLicitacion) {
        setCantidadTotalLicitacion(cantidadTotalLicitacion);
        return this;
    }
    // -- [cantidadTotalCotizacion] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "cantidad_total_cotizacion", nullable = false, precision = 15, scale = 2)
    public BigDecimal getCantidadTotalCotizacion() {
        return cantidadTotalCotizacion;
    }

    public void setCantidadTotalCotizacion(BigDecimal cantidadTotalCotizacion) {
        this.cantidadTotalCotizacion = cantidadTotalCotizacion;
    }

    public LicitacionProveedorDetalleEvaluacion cantidadTotalCotizacion(BigDecimal cantidadTotalCotizacion) {
        setCantidadTotalCotizacion(cantidadTotalCotizacion);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idProveedor ==> Proveedor.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_proveedor", nullable = false)
    @ManyToOne
    public Proveedor getIdProveedor() {
        return idProveedor;
    }

    /**
     * Set the {@link #idProveedor} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idProveedor}
     */
    public void setIdProveedor(Proveedor idProveedor) {
        this.idProveedor = idProveedor;
    }

    public LicitacionProveedorDetalleEvaluacion idProveedor(Proveedor idProveedor) {
        setIdProveedor(idProveedor);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idMoneda ==> Moneda.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_moneda", nullable = false)
    @ManyToOne
    public Moneda getIdMoneda() {
        return idMoneda;
    }

    /**
     * Set the {@link #idMoneda} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idMoneda}
     */
    public void setIdMoneda(Moneda idMoneda) {
        this.idMoneda = idMoneda;
    }

    public LicitacionProveedorDetalleEvaluacion idMoneda(Moneda idMoneda) {
        setIdMoneda(idMoneda);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idLicitacionDetalle ==> LicitacionDetalle.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_licitacion_detalle", nullable = false)
    @ManyToOne(cascade = CascadeType.REMOVE)
    public LicitacionDetalle getIdLicitacionDetalle() {
        return idLicitacionDetalle;
    }

    /**
     * Set the {@link #idLicitacionDetalle} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idLicitacionDetalle}
     */
    public void setIdLicitacionDetalle(LicitacionDetalle idLicitacionDetalle) {
        this.idLicitacionDetalle = idLicitacionDetalle;
    }

    public LicitacionProveedorDetalleEvaluacion idLicitacionDetalle(LicitacionDetalle idLicitacionDetalle) {
        setIdLicitacionDetalle(idLicitacionDetalle);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idCotizacionDetalle ==> CotizacionDetalle.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_cotizacion_detalle", nullable = false)
    @ManyToOne(cascade = CascadeType.REMOVE)
    public CotizacionDetalle getIdCotizacionDetalle() {
        return idCotizacionDetalle;
    }

    /**
     * Set the {@link #idCotizacionDetalle} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idCotizacionDetalle}
     */
    public void setIdCotizacionDetalle(CotizacionDetalle idCotizacionDetalle) {
        this.idCotizacionDetalle = idCotizacionDetalle;
    }

    public LicitacionProveedorDetalleEvaluacion idCotizacionDetalle(CotizacionDetalle idCotizacionDetalle) {
        setIdCotizacionDetalle(idCotizacionDetalle);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idTipoLicitacion ==> TipoLicitacion.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_tipo_licitacion", nullable = false)
    @ManyToOne
    public TipoLicitacion getIdTipoLicitacion() {
        return idTipoLicitacion;
    }

    /**
     * Set the {@link #idTipoLicitacion} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idTipoLicitacion}
     */
    public void setIdTipoLicitacion(TipoLicitacion idTipoLicitacion) {
        this.idTipoLicitacion = idTipoLicitacion;
    }

    public LicitacionProveedorDetalleEvaluacion idTipoLicitacion(TipoLicitacion idTipoLicitacion) {
        setIdTipoLicitacion(idTipoLicitacion);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idCondicionPago ==> CondicionPago.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_condicion_pago", nullable = false)
    @ManyToOne
    public CondicionPago getIdCondicionPago() {
        return idCondicionPago;
    }

    /**
     * Set the {@link #idCondicionPago} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idCondicionPago}
     */
    public void setIdCondicionPago(CondicionPago idCondicionPago) {
        this.idCondicionPago = idCondicionPago;
    }

    public LicitacionProveedorDetalleEvaluacion idCondicionPago(CondicionPago idCondicionPago) {
        setIdCondicionPago(idCondicionPago);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idLicitacion ==> Licitacion.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_licitacion", nullable = false)
    @ManyToOne
    public Licitacion getIdLicitacion() {
        return idLicitacion;
    }

    /**
     * Set the {@link #idLicitacion} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idLicitacion}
     */
    public void setIdLicitacion(Licitacion idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public LicitacionProveedorDetalleEvaluacion idLicitacion(Licitacion idLicitacion) {
        setIdLicitacion(idLicitacion);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionProveedorDetalleEvaluacion.idTipoCuestionario ==> TipoLicitacion.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_tipo_cuestionario", nullable = false)
    @ManyToOne
    public TipoLicitacion getIdTipoCuestionario() {
        return idTipoCuestionario;
    }

    /**
     * Set the {@link #idTipoCuestionario} without adding this LicitacionProveedorDetalleEvaluacion instance on the passed {@link #idTipoCuestionario}
     */
    public void setIdTipoCuestionario(TipoLicitacion idTipoCuestionario) {
        this.idTipoCuestionario = idTipoCuestionario;
    }

    public LicitacionProveedorDetalleEvaluacion idTipoCuestionario(TipoLicitacion idTipoCuestionario) {
        setIdTipoCuestionario(idTipoCuestionario);
        return this;
    }

    /**
     * Apply the default values.
     */
    public LicitacionProveedorDetalleEvaluacion withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof LicitacionProveedorDetalleEvaluacion && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    @Override
    public String toString() {
        return "LicitacionProveedorDetalleEvaluacion{" +
                "id=" + id +
                ", factorDenominadorTasa=" + factorDenominadorTasa +
                ", tasaMoneda=" + tasaMoneda +
                ", pesoMoneda=" + pesoMoneda +
                ", pesoCondicion=" + pesoCondicion +
                ", pesoEvaluacionTecnica=" + pesoEvaluacionTecnica +
                ", precioUnitario=" + precioUnitario +
                ", precioSuma=" + precioSuma +
                ", tasaInteresMoneda=" + tasaInteresMoneda +
                ", costoFinancieroMoneda=" + costoFinancieroMoneda +
                ", precioFinalMoneda=" + precioFinalMoneda +
                ", precioFinalMinimoMoneda=" + precioFinalMinimoMoneda +
                ", puntajeCondicion=" + puntajeCondicion +
                ", puntajeMaximoCondicion=" + puntajeMaximoCondicion +
                ", puntajeEvaluacionTecnica=" + puntajeEvaluacionTecnica +
                ", porcentajePrecioObtenido=" + porcentajePrecioObtenido +
                ", porcentajeCondicionObtenido=" + porcentajeCondicionObtenido +
                ", porcentajeEvaluacionTecnicaObtenido=" + porcentajeEvaluacionTecnicaObtenido +
                ", porcentajeTotalObtenido=" + porcentajeTotalObtenido +
                ", fechaTasaCambio=" + fechaTasaCambio +
                ", tasaCambio=" + tasaCambio +
                ", cantidadTotalLicitacion=" + cantidadTotalLicitacion +
                ", cantidadTotalCotizacion=" + cantidadTotalCotizacion +
                ", idProveedor=" + idProveedor +
                ", idMoneda=" + idMoneda +
                ", idLicitacionDetalle=" + idLicitacionDetalle +
                ", idCotizacionDetalle=" + idCotizacionDetalle +
                ", idTipoLicitacion=" + idTipoLicitacion +
                ", idCondicionPago=" + idCondicionPago +
                ", idLicitacion=" + idLicitacion +
                ", idTipoCuestionario=" + idTipoCuestionario +
                ", identifiableHashBuilder=" + identifiableHashBuilder +
                '}';
    }


}