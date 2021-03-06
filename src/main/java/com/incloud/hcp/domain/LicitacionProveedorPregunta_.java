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

@StaticMetamodel(LicitacionProveedorPregunta.class)
public abstract class LicitacionProveedorPregunta_ {

    // Raw attributes
    public static volatile SingularAttribute<LicitacionProveedorPregunta, Integer> id;
    public static volatile SingularAttribute<LicitacionProveedorPregunta, Integer> idLicitacion;
    public static volatile SingularAttribute<LicitacionProveedorPregunta, Integer> idProveedor;
    public static volatile SingularAttribute<LicitacionProveedorPregunta, String> pregunta;
}