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

import com.google.common.base.MoreObjects;
import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.Identifiable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "cotizacion_detalle_rechazada")
public class CotizacionDetalleRechazada extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(CotizacionDetalleRechazada.class.getName());

    // Raw attributes
    private Integer id;
    private BigDecimal cantidadCotizada;
    private BigDecimal cantidadSolicitada;
    private String descripcion;
    private String especificacion;
    private String indGanador;
    private String numeroParte;
    private BigDecimal precioUnitario;
    private String solicitudPedido;

    // Many to one
    private UnidadMedida idUnidadMedida;
    private BienServicio idBienServicio;
    private LicitacionDetalle idLicitacionDetalle;
    private CotizacionRechazada idCotizacionRechazada;

    @Override
    public String entityClassName() {
        return CotizacionDetalleRechazada.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_cotizacion_detalle_rechazada", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_cotizacion_detalle_rechazada")
    @Id
    @SequenceGenerator(name = "seq_cotizacion_detalle_rechazada", sequenceName = "seq_cotizacion_detalle_rechazada", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CotizacionDetalleRechazada id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [cantidadCotizada] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "cantidad_cotizada", nullable = false, precision = 15, scale = 2)
    public BigDecimal getCantidadCotizada() {
        return cantidadCotizada;
    }

    public void setCantidadCotizada(BigDecimal cantidadCotizada) {
        this.cantidadCotizada = cantidadCotizada;
    }

    public CotizacionDetalleRechazada cantidadCotizada(BigDecimal cantidadCotizada) {
        setCantidadCotizada(cantidadCotizada);
        return this;
    }
    // -- [cantidadSolicitada] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "cantidad_solicitada", nullable = false, precision = 15, scale = 2)
    public BigDecimal getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(BigDecimal cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public CotizacionDetalleRechazada cantidadSolicitada(BigDecimal cantidadSolicitada) {
        setCantidadSolicitada(cantidadSolicitada);
        return this;
    }
    // -- [descripcion] ------------------------

    @NotEmpty
    @Size(max = 80)
    @Column(name = "descripcion", nullable = false, length = 80)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public CotizacionDetalleRechazada descripcion(String descripcion) {
        setDescripcion(descripcion);
        return this;
    }
    // -- [especificacion] ------------------------

    @Size(max = 200)
    @Column(name = "especificacion", length = 200)
    public String getEspecificacion() {
        return especificacion;
    }

    public void setEspecificacion(String especificacion) {
        this.especificacion = especificacion;
    }

    public CotizacionDetalleRechazada especificacion(String especificacion) {
        setEspecificacion(especificacion);
        return this;
    }
    // -- [indGanador] ------------------------

    @NotEmpty
    @Size(max = 1)
    @Column(name = "ind_ganador", nullable = false, length = 1)
    public String getIndGanador() {
        return indGanador;
    }

    public void setIndGanador(String indGanador) {
        this.indGanador = indGanador;
    }

    public CotizacionDetalleRechazada indGanador(String indGanador) {
        setIndGanador(indGanador);
        return this;
    }
    // -- [numeroParte] ------------------------

    @Size(max = 40)
    @Column(name = "numero_parte", length = 40)
    public String getNumeroParte() {
        return numeroParte;
    }

    public void setNumeroParte(String numeroParte) {
        this.numeroParte = numeroParte;
    }

    public CotizacionDetalleRechazada numeroParte(String numeroParte) {
        setNumeroParte(numeroParte);
        return this;
    }
    // -- [precioUnitario] ------------------------

    @Digits(integer = 12, fraction = 3)
    @NotNull
    @Column(name = "precio_unitario", nullable = false, precision = 15, scale = 3)
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public CotizacionDetalleRechazada precioUnitario(BigDecimal precioUnitario) {
        setPrecioUnitario(precioUnitario);
        return this;
    }
    // -- [solicitudPedido] ------------------------

    @Size(max = 10)
    @Column(name = "solicitud_pedido", length = 10)
    public String getSolicitudPedido() {
        return solicitudPedido;
    }

    public void setSolicitudPedido(String solicitudPedido) {
        this.solicitudPedido = solicitudPedido;
    }

    public CotizacionDetalleRechazada solicitudPedido(String solicitudPedido) {
        setSolicitudPedido(solicitudPedido);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionDetalleRechazada.idUnidadMedida ==> UnidadMedida.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_unidad_medida", nullable = false)
    @ManyToOne
    public UnidadMedida getIdUnidadMedida() {
        return idUnidadMedida;
    }

    /**
     * Set the {@link #idUnidadMedida} without adding this CotizacionDetalleRechazada instance on the passed {@link #idUnidadMedida}
     */
    public void setIdUnidadMedida(UnidadMedida idUnidadMedida) {
        this.idUnidadMedida = idUnidadMedida;
    }

    public CotizacionDetalleRechazada idUnidadMedida(UnidadMedida idUnidadMedida) {
        setIdUnidadMedida(idUnidadMedida);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionDetalleRechazada.idBienServicio ==> BienServicio.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @JoinColumn(name = "id_bien_servicio")
    @ManyToOne
    public BienServicio getIdBienServicio() {
        return idBienServicio;
    }

    /**
     * Set the {@link #idBienServicio} without adding this CotizacionDetalleRechazada instance on the passed {@link #idBienServicio}
     */
    public void setIdBienServicio(BienServicio idBienServicio) {
        this.idBienServicio = idBienServicio;
    }

    public CotizacionDetalleRechazada idBienServicio(BienServicio idBienServicio) {
        setIdBienServicio(idBienServicio);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionDetalleRechazada.idLicitacionDetalle ==> LicitacionDetalle.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_licitacion_detalle", nullable = false)
    @ManyToOne
    public LicitacionDetalle getIdLicitacionDetalle() {
        return idLicitacionDetalle;
    }

    /**
     * Set the {@link #idLicitacionDetalle} without adding this CotizacionDetalleRechazada instance on the passed {@link #idLicitacionDetalle}
     */
    public void setIdLicitacionDetalle(LicitacionDetalle idLicitacionDetalle) {
        this.idLicitacionDetalle = idLicitacionDetalle;
    }

    public CotizacionDetalleRechazada idLicitacionDetalle(LicitacionDetalle idLicitacionDetalle) {
        setIdLicitacionDetalle(idLicitacionDetalle);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionDetalleRechazada.idCotizacionRechazada ==> CotizacionRechazada.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_cotizacion_rechazada", nullable = false)
    @ManyToOne
    public CotizacionRechazada getIdCotizacionRechazada() {
        return idCotizacionRechazada;
    }

    /**
     * Set the {@link #idCotizacionRechazada} without adding this CotizacionDetalleRechazada instance on the passed {@link #idCotizacionRechazada}
     */
    public void setIdCotizacionRechazada(CotizacionRechazada idCotizacionRechazada) {
        this.idCotizacionRechazada = idCotizacionRechazada;
    }

    public CotizacionDetalleRechazada idCotizacionRechazada(CotizacionRechazada idCotizacionRechazada) {
        setIdCotizacionRechazada(idCotizacionRechazada);
        return this;
    }

    /**
     * Apply the default values.
     */
    public CotizacionDetalleRechazada withDefaults() {
        return this;
    }


    /**
     * Construct a readable string representation for this CotizacionDetalleRechazada instance.
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("cantidadCotizada", getCantidadCotizada()) //
                .add("cantidadSolicitada", getCantidadSolicitada()) //
                .add("descripcion", getDescripcion()) //
                .add("especificacion", getEspecificacion()) //
                .add("indGanador", getIndGanador()) //
                .add("numeroParte", getNumeroParte()) //
                .add("precioUnitario", getPrecioUnitario()) //
                .add("solicitudPedido", getSolicitudPedido()) //
                .toString();
    }
}