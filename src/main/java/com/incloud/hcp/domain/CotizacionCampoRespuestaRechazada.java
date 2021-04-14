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

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.Identifiable;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "cotizacion_campo_respuesta_rechazada")
public class CotizacionCampoRespuestaRechazada extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(CotizacionCampoRespuestaRechazada.class.getName());

    // Raw attributes
    private Integer id;
    private String comentario;
    private Date fechaRegistro;
    private BigDecimal peso;
    private BigDecimal puntaje;
    private String textoRespuestaLibre;

    // Many to one
    private GrupoCondicionLicRespuesta idCondicionRespuesta;
    private LicitacionGrupoCondicionLic idLicitacionGrupoCondicion;
    private CotizacionRechazada idCotizacionRechazada;

    @Override
    public String entityClassName() {
        return CotizacionCampoRespuestaRechazada.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_cotizacion_campo_respuesta_rechazada", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_cotizacion_campo_respuesta_rechazada")
    @Id
    @SequenceGenerator(name = "seq_cotizacion_campo_respuesta_rechazada", sequenceName = "seq_cotizacion_campo_respuesta_rechazada", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CotizacionCampoRespuestaRechazada id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [comentario] ------------------------

    @Size(max = 64000)
    @Column(name = "comentario", length = 64000)
    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public CotizacionCampoRespuestaRechazada comentario(String comentario) {
        setComentario(comentario);
        return this;
    }
    // -- [fechaRegistro] ------------------------

    @Column(name = "fecha_registro", length = 29)
    @Temporal(TIMESTAMP)
    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public CotizacionCampoRespuestaRechazada fechaRegistro(Date fechaRegistro) {
        setFechaRegistro(fechaRegistro);
        return this;
    }
    // -- [peso] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "peso", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public CotizacionCampoRespuestaRechazada peso(BigDecimal peso) {
        setPeso(peso);
        return this;
    }
    // -- [puntaje] ------------------------

    @Digits(integer = 13, fraction = 2)
    @NotNull
    @Column(name = "puntaje", nullable = false, precision = 15, scale = 2)
    public BigDecimal getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(BigDecimal puntaje) {
        this.puntaje = puntaje;
    }

    public CotizacionCampoRespuestaRechazada puntaje(BigDecimal puntaje) {
        setPuntaje(puntaje);
        return this;
    }
    // -- [textoRespuestaLibre] ------------------------

    @Size(max = 250)
    @Column(name = "texto_respuesta_libre", length = 250)
    public String getTextoRespuestaLibre() {
        return textoRespuestaLibre;
    }

    public void setTextoRespuestaLibre(String textoRespuestaLibre) {
        this.textoRespuestaLibre = textoRespuestaLibre;
    }

    public CotizacionCampoRespuestaRechazada textoRespuestaLibre(String textoRespuestaLibre) {
        setTextoRespuestaLibre(textoRespuestaLibre);
        return this;
    }

    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionCampoRespuestaRechazada.idCondicionRespuesta ==> GrupoCondicionLicRespuesta.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @JoinColumn(name = "id_condicion_respuesta")
    @ManyToOne
    public GrupoCondicionLicRespuesta getIdCondicionRespuesta() {
        return idCondicionRespuesta;
    }

    /**
     * Set the {@link #idCondicionRespuesta} without adding this CotizacionCampoRespuestaRechazada instance on the passed {@link #idCondicionRespuesta}
     */
    public void setIdCondicionRespuesta(GrupoCondicionLicRespuesta idCondicionRespuesta) {
        this.idCondicionRespuesta = idCondicionRespuesta;
    }

    public CotizacionCampoRespuestaRechazada idCondicionRespuesta(GrupoCondicionLicRespuesta idCondicionRespuesta) {
        setIdCondicionRespuesta(idCondicionRespuesta);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionCampoRespuestaRechazada.idLicitacionGrupoCondicion ==> LicitacionGrupoCondicionLic.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_licitacion_grupo_condicion", nullable = false)
    @ManyToOne
    public LicitacionGrupoCondicionLic getIdLicitacionGrupoCondicion() {
        return idLicitacionGrupoCondicion;
    }

    /**
     * Set the {@link #idLicitacionGrupoCondicion} without adding this CotizacionCampoRespuestaRechazada instance on the passed {@link #idLicitacionGrupoCondicion}
     */
    public void setIdLicitacionGrupoCondicion(LicitacionGrupoCondicionLic idLicitacionGrupoCondicion) {
        this.idLicitacionGrupoCondicion = idLicitacionGrupoCondicion;
    }

    public CotizacionCampoRespuestaRechazada idLicitacionGrupoCondicion(LicitacionGrupoCondicionLic idLicitacionGrupoCondicion) {
        setIdLicitacionGrupoCondicion(idLicitacionGrupoCondicion);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: CotizacionCampoRespuestaRechazada.idCotizacionRechazada ==> CotizacionRechazada.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @NotNull
    @JoinColumn(name = "id_cotizacion_rechazada", nullable = false)
    @ManyToOne
    public CotizacionRechazada getIdCotizacionRechazada() {
        return idCotizacionRechazada;
    }

    /**
     * Set the {@link #idCotizacionRechazada} without adding this CotizacionCampoRespuestaRechazada instance on the passed {@link #idCotizacionRechazada}
     */
    public void setIdCotizacionRechazada(CotizacionRechazada idCotizacionRechazada) {
        this.idCotizacionRechazada = idCotizacionRechazada;
    }

    public CotizacionCampoRespuestaRechazada idCotizacionRechazada(CotizacionRechazada idCotizacionRechazada) {
        setIdCotizacionRechazada(idCotizacionRechazada);
        return this;
    }

    /**
     * Apply the default values.
     */
    public CotizacionCampoRespuestaRechazada withDefaults() {
        return this;
    }

    @Override
    public String toString() {
        return "CotizacionCampoRespuestaRechazada{" +
                "id=" + id +
                ", comentario='" + comentario + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", peso=" + peso +
                ", puntaje=" + puntaje +
                ", textoRespuestaLibre='" + textoRespuestaLibre + '\'' +
                ", idCondicionRespuesta=" + idCondicionRespuesta +
                ", idLicitacionGrupoCondicion=" + idLicitacionGrupoCondicion +
                ", idCotizacionRechazada=" + idCotizacionRechazada +
                '}';
    }
}