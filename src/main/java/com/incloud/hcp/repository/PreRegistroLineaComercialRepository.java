/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/repository/EntityRepository.java.e.vm
 */
package com.incloud.hcp.repository;

import com.incloud.hcp.domain.LineaComercial;
import com.incloud.hcp.domain.PreRegistroLineaComercial;
import com.incloud.hcp.domain.PreRegistroProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreRegistroLineaComercialRepository extends JpaRepository<PreRegistroLineaComercial, Integer> {

    Integer deleteByIdRegistro(PreRegistroProveedor idRegistro);

    List<PreRegistroLineaComercial> findByIdRegistro(PreRegistroProveedor idRegistro);

    @Query("SELECT distinct p.idRegistro FROM PreRegistroLineaComercial p WHERE p.idFamilia in ?1 and p.idRegistro.estado = ?2 order by p.idRegistro.ruc")
    List<PreRegistroProveedor> findProveedorByLineaAndPendiente(List<LineaComercial> lineaComercialList, String estado);

}