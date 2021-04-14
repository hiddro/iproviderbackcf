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
import java.util.Date;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "licitacion_subetapa", uniqueConstraints = {
        @UniqueConstraint(name = "licitacion_subetapa_ak01", columnNames = { "id_licitacion", "id_subetapa" }) })
public class LicitacionSubetapa extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(LicitacionSubetapa.class.getName());

    // Raw attributes
    @Column(name = "id_licitacion_subestapa", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_licitacion_subetapa")
    @Id
    @SequenceGenerator(name = "seq_licitacion_subetapa", sequenceName = "seq_licitacion_subetapa", allocationSize = 1)
    private Integer id;

    @NotNull
    @Column(name = "fecha_cierre_subetapa", nullable = false, length = 29)
    @Temporal(TIMESTAMP)
    private Date fechaCierreSubetapa;

    @Column(name = "fecha_envio_correo_recordatorio", length = 29)
    @Temporal(TIMESTAMP)
    private Date fechaEnvioCorreoRecordatorio;

    @Size(max = 1)
    @Column(name = "ind_enviado_correo_recordatorio", length = 1)
    private String indEnviadoCorreoRecordatorio;

    @Transient
    private String fechaCierreSubetapaString;

    @Digits(integer = 10, fraction = 0)
    @Column(name = "orden", nullable = true, precision = 10)
    private Integer orden;

    // Many to one
    @NotNull
    @JoinColumn(name = "id_subetapa", nullable = false)
    @ManyToOne
    private Subetapa idSubetapa;

    @NotNull
    @JoinColumn(name = "id_licitacion", nullable = false)
    @ManyToOne
    private Licitacion idLicitacion;

    @Override
    public String entityClassName() {
        return LicitacionSubetapa.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LicitacionSubetapa id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [fechaCierreSubetapa] ------------------------


    public Date getFechaCierreSubetapa() {
        return fechaCierreSubetapa;
    }

    public void setFechaCierreSubetapa(Date fechaCierreSubetapa) {
        this.fechaCierreSubetapa = fechaCierreSubetapa;
    }

    public LicitacionSubetapa fechaCierreSubetapa(Date fechaCierreSubetapa) {
        setFechaCierreSubetapa(fechaCierreSubetapa);
        return this;
    }


    // -----------------------------------------------------------------
    // Many to One support
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionSubetapa.idSubetapa ==> Subetapa.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public Subetapa getIdSubetapa() {
        return idSubetapa;
    }

    /**
     * Set the {@link #idSubetapa} without adding this LicitacionSubetapa instance on the passed {@link #idSubetapa}
     */
    public void setIdSubetapa(Subetapa idSubetapa) {
        this.idSubetapa = idSubetapa;
    }

    public LicitacionSubetapa idSubetapa(Subetapa idSubetapa) {
        setIdSubetapa(idSubetapa);
        return this;
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // many-to-one: LicitacionSubetapa.idLicitacion ==> Licitacion.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public Licitacion getIdLicitacion() {
        return idLicitacion;
    }

    /**
     * Set the {@link #idLicitacion} without adding this LicitacionSubetapa instance on the passed {@link #idLicitacion}
     */
    public void setIdLicitacion(Licitacion idLicitacion) {
        this.idLicitacion = idLicitacion;
    }

    public LicitacionSubetapa idLicitacion(Licitacion idLicitacion) {
        setIdLicitacion(idLicitacion);
        return this;
    }

    public String getFechaCierreSubetapaString() {
        return fechaCierreSubetapaString;
    }

    public void setFechaCierreSubetapaString(String fechaCierreSubetapaString) {
        this.fechaCierreSubetapaString = fechaCierreSubetapaString;
    }

    public Date getFechaEnvioCorreoRecordatorio() {
        return fechaEnvioCorreoRecordatorio;
    }

    public void setFechaEnvioCorreoRecordatorio(Date fechaEnvioCorreoRecordatorio) {
        this.fechaEnvioCorreoRecordatorio = fechaEnvioCorreoRecordatorio;
    }


    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public LicitacionSubetapa() {

    }

    public LicitacionSubetapa(Date fechaCierreSubetapa, Date fechaEnvioCorreoRecordatorio, Subetapa idSubetapa, Licitacion idLicitacion) {
        this.fechaCierreSubetapa = fechaCierreSubetapa;
        this.fechaEnvioCorreoRecordatorio = fechaEnvioCorreoRecordatorio;
        this.idSubetapa = idSubetapa;
        this.idLicitacion = idLicitacion;
    }

    public LicitacionSubetapa(Date fechaCierreSubetapa, Date fechaEnvioCorreoRecordatorio, Subetapa idSubetapa, Licitacion idLicitacion, Integer orden) {
        this.fechaCierreSubetapa = fechaCierreSubetapa;
        this.fechaEnvioCorreoRecordatorio = fechaEnvioCorreoRecordatorio;
        this.idSubetapa = idSubetapa;
        this.idLicitacion = idLicitacion;
        this.orden = orden;
    }

    public String getIndEnviadoCorreoRecordatorio() {
        return indEnviadoCorreoRecordatorio;
    }

    public void setIndEnviadoCorreoRecordatorio(String indEnviadoCorreoRecordatorio) {
        this.indEnviadoCorreoRecordatorio = indEnviadoCorreoRecordatorio;
    }


    @Override
    public String toString() {
        return "LicitacionSubetapa{" +
                "id=" + id +
                ", fechaCierreSubetapa=" + fechaCierreSubetapa +
                ", fechaEnvioCorreoRecordatorio=" + fechaEnvioCorreoRecordatorio +
                ", indEnviadoCorreoRecordatorio='" + indEnviadoCorreoRecordatorio + '\'' +
                ", fechaCierreSubetapaString='" + fechaCierreSubetapaString + '\'' +
                ", orden=" + orden +
                ", idSubetapa=" + idSubetapa +
                ", idLicitacion=" + idLicitacion +
                '}';
    }
}