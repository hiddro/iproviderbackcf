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
import com.incloud.hcp.domain._framework.Identifiable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "temp_rubro_bien")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "SP_MIGRACION_RFC_RUBRO_BIEN",
                procedureName = "SP_MIGRACION_RFC_RUBRO_BIEN"
        )
})
public class TempRubroBien implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(TempRubroBien.class.getName());

    // Raw attributes
    private Integer id;
    private String codigoSap;
    private String descripcion;
    private Integer nivel;

    @Override
    public String entityClassName() {
        return TempRubroBien.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_rubro", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_temp_rubro_bien")
    @Id
    @SequenceGenerator(name = "seq_temp_rubro_bien", sequenceName = "seq_temp_rubro_bien", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public TempRubroBien id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [codigoSap] ------------------------

    @NotEmpty
    @Size(max = 10)
    @Column(name = "codigo_sap", unique = true, length = 10)
    public String getCodigoSap() {
        return codigoSap;
    }

    public void setCodigoSap(String codigoSap) {
        this.codigoSap = codigoSap;
    }

    public TempRubroBien codigoSap(String codigoSap) {
        setCodigoSap(codigoSap);
        return this;
    }
    // -- [descripcion] ------------------------

    @NotEmpty
    @Size(max = 60)
    @Column(name = "descripcion", length = 60)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TempRubroBien descripcion(String descripcion) {
        setDescripcion(descripcion);
        return this;
    }
    // -- [nivel] ------------------------

    @Digits(integer = 10, fraction = 0)
    @NotNull
    @Column(name = "nivel", precision = 10)
    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public TempRubroBien nivel(Integer nivel) {
        setNivel(nivel);
        return this;
    }

    /**
     * Apply the default values.
     */
    public TempRubroBien withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof TempRubroBien && hashCode() == other.hashCode());
    }

    private volatile int previousHashCode = 0;

    @Override
    public int hashCode() {
        int hashCode = Objects.hashCode(getCodigoSap());

        if (previousHashCode != 0 && previousHashCode != hashCode) {
            log.warning("DEVELOPER: hashCode has changed!." //
                    + "If you encounter this message you should take the time to carefuly review equals/hashCode for: " //
                    + getClass().getCanonicalName());
        }

        previousHashCode = hashCode;
        return hashCode;
    }

    /**
     * Construct a readable string representation for this TempRubroBien instance.
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("codigoSap", getCodigoSap()) //
                .add("descripcion", getDescripcion()) //
                .add("nivel", getNivel()) //
                .toString();
    }
}