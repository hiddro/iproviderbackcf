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

@StaticMetamodel(PesoLineaComercialAprobacion.class)
public abstract class PesoLineaComercialAprobacion_ {

    // Raw attributes
    public static volatile SingularAttribute<PesoLineaComercialAprobacion, Integer> id;
    public static volatile SingularAttribute<PesoLineaComercialAprobacion, Date> fechaVigencia;

    // Many to one
    public static volatile SingularAttribute<PesoLineaComercialAprobacion, Usuario> idUsuario;
    public static volatile SingularAttribute<PesoLineaComercialAprobacion, PesoLineaComercial> idPesoLineaComercial;
}