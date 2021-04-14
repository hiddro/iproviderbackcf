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
import com.incloud.hcp.domain._framework.Identifiable;
import com.incloud.hcp.domain._framework.IdentifiableHashBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.logging.Logger;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "temp_centro_almacen")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name = "SP_MIGRACION_RFC_CENTRO_ALMACEN",
                procedureName = "SP_MIGRACION_RFC_CENTRO_ALMACEN"
        )
})
public class TempCentroAlmacen implements Identifiable<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(TempCentroAlmacen.class.getName());

    // Raw attributes
    private Integer id;
    private String centro;
    private String poblacion;
    private String distrito;
    private String direccion;
    private String codigoAlmacen;
    private String descripcionAlmacen;

    @Override
    public String entityClassName() {
        return TempCentroAlmacen.class.getSimpleName();
    }

    // -- [id] ------------------------

    @Override
    @Column(name = "id_centro_almacen", precision = 10)
    @GeneratedValue(strategy = SEQUENCE, generator = "seq_temp_centro_almacen")
    @Id
    @SequenceGenerator(name = "seq_temp_centro_almacen", sequenceName = "seq_temp_centro_almacen", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public TempCentroAlmacen id(Integer id) {
        setId(id);
        return this;
    }

    @Override
    @Transient
    public boolean isIdSet() {
        return id != null;
    }
    // -- [centro] ------------------------

    @Size(max = 60)
    @Column(name = "centro", length = 60)
    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public TempCentroAlmacen centro(String centro) {
        setCentro(centro);
        return this;
    }
    // -- [poblacion] ------------------------

    @Size(max = 60)
    @Column(name = "poblacion", length = 60)
    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public TempCentroAlmacen poblacion(String poblacion) {
        setPoblacion(poblacion);
        return this;
    }
    // -- [distrito] ------------------------

    @Size(max = 200)
    @Column(name = "distrito", length = 200)
    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public TempCentroAlmacen distrito(String distrito) {
        setDistrito(distrito);
        return this;
    }
    // -- [direccion] ------------------------

    @Size(max = 200)
    @Column(name = "direccion", length = 200)
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TempCentroAlmacen direccion(String direccion) {
        setDireccion(direccion);
        return this;
    }
    // -- [codigoAlmacen] ------------------------

    @Size(max = 60)
    @Column(name = "codigo_almacen", length = 60)
    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public TempCentroAlmacen codigoAlmacen(String codigoAlmacen) {
        setCodigoAlmacen(codigoAlmacen);
        return this;
    }
    // -- [descripcionAlmacen] ------------------------

    @Size(max = 255)
    @Column(name = "descripcion_almacen")
    public String getDescripcionAlmacen() {
        return descripcionAlmacen;
    }

    public void setDescripcionAlmacen(String descripcionAlmacen) {
        this.descripcionAlmacen = descripcionAlmacen;
    }

    public TempCentroAlmacen descripcionAlmacen(String descripcionAlmacen) {
        setDescripcionAlmacen(descripcionAlmacen);
        return this;
    }

    /**
     * Apply the default values.
     */
    public TempCentroAlmacen withDefaults() {
        return this;
    }

    /**
     * Equals implementation using a business key.
     */
    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof TempCentroAlmacen && hashCode() == other.hashCode());
    }

    private IdentifiableHashBuilder identifiableHashBuilder = new IdentifiableHashBuilder();

    @Override
    public int hashCode() {
        return identifiableHashBuilder.hash(log, this);
    }

    /**
     * Construct a readable string representation for this TempCentroAlmacen instance.
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this) //
                .add("id", getId()) //
                .add("centro", getCentro()) //
                .add("poblacion", getPoblacion()) //
                .add("distrito", getDistrito()) //
                .add("direccion", getDireccion()) //
                .add("codigoAlmacen", getCodigoAlmacen()) //
                .add("descripcionAlmacen", getDescripcionAlmacen()) //
                .toString();
    }
}