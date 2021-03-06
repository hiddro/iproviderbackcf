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
import com.google.common.base.Objects;
import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.Identifiable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.logging.Logger;

@Entity
@Table(name = "subetapa")
public class Subetapa extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(Subetapa.class.getName());

    // Raw attributes
    private Integer id;
    private String descripcionSubetapa;
    private String indConfirmacionParticipacion;
    private String indObligatorio;
    private String indRenegociacion;
    private String indEnvioCorreoRecordatorio;
    private Integer diasEnvioCorreoRecordatorio;
    private String indRecepcionConsulta;
    private String indAbsolucionConsulta;
    private String indFechaReferencial;
    private String indSubetapaFinal;
    private Integer orden;

    @Override
    public String entityClassName() {
        return Subetapa.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_subetapa", precision = 10)
    @Id
    //@GeneratedValue(strategy = SEQUENCE, generator = "seq_subetapa")
    //@SequenceGenerator(name = "seq_subetapa", sequenceName = "seq_subetapa", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Subetapa id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [descripcionSubetapa] ------------------------

    @NotEmpty
    @Size(max = 60)
    @Column(name = "descripcion_subetapa", nullable = false, unique = true, length = 60)
    public String getDescripcionSubetapa() {
        return descripcionSubetapa;
    }

    public void setDescripcionSubetapa(String descripcionSubetapa) {
        this.descripcionSubetapa = descripcionSubetapa;
    }

    public Subetapa descripcionSubetapa(String descripcionSubetapa) {
        setDescripcionSubetapa(descripcionSubetapa);
        return this;
    }
    // -- [indConfirmacionParticipacion] ------------------------

    @Size(max = 1)
    @Column(name = "ind_confirmacion_participacion", length = 1)
    public String getIndConfirmacionParticipacion() {
        return indConfirmacionParticipacion;
    }

    public void setIndConfirmacionParticipacion(String indConfirmacionParticipacion) {
        this.indConfirmacionParticipacion = indConfirmacionParticipacion;
    }

    public Subetapa indConfirmacionParticipacion(String indConfirmacionParticipacion) {
        setIndConfirmacionParticipacion(indConfirmacionParticipacion);
        return this;
    }
    // -- [indObligatorio] ------------------------

    @Size(max = 1)
    @Column(name = "ind_obligatorio", length = 1)
    public String getIndObligatorio() {
        return indObligatorio;
    }

    public void setIndObligatorio(String indObligatorio) {
        this.indObligatorio = indObligatorio;
    }

    public Subetapa indObligatorio(String indObligatorio) {
        setIndObligatorio(indObligatorio);
        return this;
    }
    // -- [indRenegociacion] ------------------------

    @Size(max = 1)
    @Column(name = "ind_renegociacion", length = 1)
    public String getIndRenegociacion() {
        return indRenegociacion;
    }

    public void setIndRenegociacion(String indRenegociacion) {
        this.indRenegociacion = indRenegociacion;
    }

    public Subetapa indRenegociacion(String indRenegociacion) {
        setIndRenegociacion(indRenegociacion);
        return this;
    }
    // -- [indEnvioCorreoRecordatorio] ------------------------

    @Size(max = 1)
    @Column(name = "ind_envio_correo_recordatorio", length = 1)
    public String getIndEnvioCorreoRecordatorio() {
        return indEnvioCorreoRecordatorio;
    }

    public void setIndEnvioCorreoRecordatorio(String indEnvioCorreoRecordatorio) {
        this.indEnvioCorreoRecordatorio = indEnvioCorreoRecordatorio;
    }

    public Subetapa indEnvioCorreoRecordatorio(String indEnvioCorreoRecordatorio) {
        setIndEnvioCorreoRecordatorio(indEnvioCorreoRecordatorio);
        return this;
    }
    // -- [diasEnvioCorreoRecordatorio] ------------------------

    @Digits(integer = 10, fraction = 0)
    @Column(name = "dias_envio_correo_recordatorio", precision = 10)
    public Integer getDiasEnvioCorreoRecordatorio() {
        return diasEnvioCorreoRecordatorio;
    }

    public void setDiasEnvioCorreoRecordatorio(Integer diasEnvioCorreoRecordatorio) {
        this.diasEnvioCorreoRecordatorio = diasEnvioCorreoRecordatorio;
    }

    public Subetapa diasEnvioCorreoRecordatorio(Integer diasEnvioCorreoRecordatorio) {
        setDiasEnvioCorreoRecordatorio(diasEnvioCorreoRecordatorio);
        return this;
    }
    // -- [indRecepcionConsulta] ------------------------

    @Size(max = 1)
    @Column(name = "ind_recepcion_consulta", length = 1)
    public String getIndRecepcionConsulta() {
        return indRecepcionConsulta;
    }

    public void setIndRecepcionConsulta(String indRecepcionConsulta) {
        this.indRecepcionConsulta = indRecepcionConsulta;
    }

    public Subetapa indRecepcionConsulta(String indRecepcionConsulta) {
        setIndRecepcionConsulta(indRecepcionConsulta);
        return this;
    }
    // -- [indAbsolucionConsulta] ------------------------

    @Size(max = 1)
    @Column(name = "ind_absolucion_consulta", length = 1)
    public String getIndAbsolucionConsulta() {
        return indAbsolucionConsulta;
    }

    public void setIndAbsolucionConsulta(String indAbsolucionConsulta) {
        this.indAbsolucionConsulta = indAbsolucionConsulta;
    }

    public Subetapa indAbsolucionConsulta(String indAbsolucionConsulta) {
        setIndAbsolucionConsulta(indAbsolucionConsulta);
        return this;
    }
    // -- [indFechaReferencial] ------------------------

    @Size(max = 1)
    @Column(name = "ind_fecha_referencial", length = 1)
    public String getIndFechaReferencial() {
        return indFechaReferencial;
    }

    public void setIndFechaReferencial(String indFechaReferencial) {
        this.indFechaReferencial = indFechaReferencial;
    }

    public Subetapa indFechaReferencial(String indFechaReferencial) {
        setIndFechaReferencial(indFechaReferencial);
        return this;
    }
    // -- [indSubetapaFinal] ------------------------

    @Size(max = 1)
    @Column(name = "ind_subetapa_final", length = 1)
    public String getIndSubetapaFinal() {
        return indSubetapaFinal;
    }

    public void setIndSubetapaFinal(String indSubetapaFinal) {
        this.indSubetapaFinal = indSubetapaFinal;
    }

    public Subetapa indSubetapaFinal(String indSubetapaFinal) {
        setIndSubetapaFinal(indSubetapaFinal);
        return this;
    }
    // -- [orden] ------------------------

    @Digits(integer = 10, fraction = 0)
    @NotNull
    @Column(name = "orden", nullable = false, precision = 10)
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Subetapa orden(Integer orden) {
        setOrden(orden);
        return this;
    }

    /**
     * Apply the default values.
     */
    public Subetapa withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Subetapa && hashCode() == other.hashCode());
    }

    private volatile int previousHashCode = 0;

    @Override
    public int hashCode() {
        int hashCode = Objects.hashCode(getDescripcionSubetapa());

        if (previousHashCode != 0 && previousHashCode != hashCode) {
            log.warning("DEVELOPER: hashCode has changed!." //
                    + "If you encounter this message you should take the time to carefuly review equals/hashCode for: " //
                    + getClass().getCanonicalName());
        }

        previousHashCode = hashCode;
        return hashCode;
    }

    /**
     * Construct a readable string representation for this Subetapa instance.
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("descripcionSubetapa", getDescripcionSubetapa()) //
                .add("indConfirmacionParticipacion", getIndConfirmacionParticipacion()) //
                .add("indObligatorio", getIndObligatorio()) //
                .add("indRenegociacion", getIndRenegociacion()) //
                .add("indEnvioCorreoRecordatorio", getIndEnvioCorreoRecordatorio()) //
                .add("diasEnvioCorreoRecordatorio", getDiasEnvioCorreoRecordatorio()) //
                .add("indRecepcionConsulta", getIndRecepcionConsulta()) //
                .add("indAbsolucionConsulta", getIndAbsolucionConsulta()) //
                .add("indFechaReferencial", getIndFechaReferencial()) //
                .add("indSubetapaFinal", getIndSubetapaFinal()) //
                .add("orden", getOrden()) //
                .toString();
    }
}