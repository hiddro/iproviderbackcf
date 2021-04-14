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
import java.util.Date;

@StaticMetamodel(LicitacionSubetapa.class)
public abstract class LicitacionSubetapa_ {

    // Raw attributes
    public static volatile SingularAttribute<LicitacionSubetapa, Integer> id;
    public static volatile SingularAttribute<LicitacionSubetapa, Date> fechaCierreSubetapa;
    public static volatile SingularAttribute<LicitacionSubetapa, Date> fechaEnvioCorreoRecordatorio;

    // Many to one
    public static volatile SingularAttribute<LicitacionSubetapa, Subetapa> idSubetapa;
    public static volatile SingularAttribute<LicitacionSubetapa, Licitacion> idLicitacion;
}