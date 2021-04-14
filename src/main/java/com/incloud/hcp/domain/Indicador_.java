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
import java.math.BigDecimal;

@StaticMetamodel(Indicador.class)
public abstract class Indicador_ {

    // Raw attributes
    public static volatile SingularAttribute<Indicador, Integer> id;
    public static volatile SingularAttribute<Indicador, String> codigoTipoValorIndicador;
    public static volatile SingularAttribute<Indicador, BigDecimal> valorMinimo;
    public static volatile SingularAttribute<Indicador, BigDecimal> valorMaximo;
    public static volatile SingularAttribute<Indicador, BigDecimal> valorIntermedio;
    public static volatile SingularAttribute<Indicador, String> descripcionIndicador;
}