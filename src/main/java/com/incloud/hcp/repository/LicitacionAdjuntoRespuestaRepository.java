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

import com.incloud.hcp.domain.LicitacionAdjuntoRespuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicitacionAdjuntoRespuestaRepository extends JpaRepository<LicitacionAdjuntoRespuesta, Integer> {

    public List<LicitacionAdjuntoRespuesta> findByIdLicitacionOrderByArchivoId(Integer idLicitacion);

}