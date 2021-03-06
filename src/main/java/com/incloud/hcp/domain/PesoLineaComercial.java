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

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "peso_linea_comercial")
public class PesoLineaComercial extends BaseDomain implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(PesoLineaComercial.class.getName());

    // Raw attributes
    private Integer id;
    private Double porcentajePesoHomologacion;
    private Double porcentajePesoCondicionLic;
    private Double porcentajePesoMoneda;

    // One to one
    private LineaComercial idLineaComercial;

    @Override
    public String entityClassName() {
        return PesoLineaComercial.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_peso_linea_comercial", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_peso_linea_comercial")
    @Id
    @SequenceGenerator(name = "seq_peso_linea_comercial", sequenceName = "seq_peso_linea_comercial", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public PesoLineaComercial id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [porcentajePesoHomologacion] ------------------------

    @Digits(integer = 3, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_peso_homologacion", nullable = false, precision = 5, scale = 2)
    public Double getPorcentajePesoHomologacion() {
        return porcentajePesoHomologacion;
    }

    public void setPorcentajePesoHomologacion(Double porcentajePesoHomologacion) {
        this.porcentajePesoHomologacion = porcentajePesoHomologacion;
    }

    public PesoLineaComercial porcentajePesoHomologacion(Double porcentajePesoHomologacion) {
        setPorcentajePesoHomologacion(porcentajePesoHomologacion);
        return this;
    }
    // -- [porcentajePesoCondicionLic] ------------------------

    @Digits(integer = 3, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_peso_condicion_lic", nullable = false, precision = 5, scale = 2)
    public Double getPorcentajePesoCondicionLic() {
        return porcentajePesoCondicionLic;
    }

    public void setPorcentajePesoCondicionLic(Double porcentajePesoCondicionLic) {
        this.porcentajePesoCondicionLic = porcentajePesoCondicionLic;
    }

    public PesoLineaComercial porcentajePesoCondicionLic(Double porcentajePesoCondicionLic) {
        setPorcentajePesoCondicionLic(porcentajePesoCondicionLic);
        return this;
    }
    // -- [porcentajePesoMoneda] ------------------------

    @Digits(integer = 3, fraction = 2)
    @NotNull
    @Column(name = "porcentaje_peso_moneda", nullable = false, precision = 5, scale = 2)
    public Double getPorcentajePesoMoneda() {
        return porcentajePesoMoneda;
    }

    public void setPorcentajePesoMoneda(Double porcentajePesoMoneda) {
        this.porcentajePesoMoneda = porcentajePesoMoneda;
    }

    public PesoLineaComercial porcentajePesoMoneda(Double porcentajePesoMoneda) {
        setPorcentajePesoMoneda(porcentajePesoMoneda);
        return this;
    }

    // -----------------------------------------------------------------
    // One to one
    // -----------------------------------------------------------------

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    // Owner side of one-to-one relation: PesoLineaComercial.idLineaComercial ==> LineaComercial.id
    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @NotNull
    @JoinColumn(name = "id_linea_comercial", nullable = false, unique = true)
    @OneToOne
    public LineaComercial getIdLineaComercial() {
        return idLineaComercial;
    }

    public void setIdLineaComercial(LineaComercial idLineaComercial) {
        this.idLineaComercial = idLineaComercial;
    }

    public PesoLineaComercial idLineaComercial(LineaComercial idLineaComercial) {
        setIdLineaComercial(idLineaComercial);
        return this;
    }

    /**
     * Apply the default values.
     */
    public PesoLineaComercial withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof PesoLineaComercial && hashCode() == other.hashCode());
    }


    /**
     * Construct a readable string representation for this PesoLineaComercial instance.
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("porcentajePesoHomologacion", getPorcentajePesoHomologacion()) //
                .add("porcentajePesoCondicionLic", getPorcentajePesoCondicionLic()) //
                .add("porcentajePesoMoneda", getPorcentajePesoMoneda()) //
                .toString();
    }
}