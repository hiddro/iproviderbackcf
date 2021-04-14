/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/dto/EntitydeltaDTOService.java.e.vm
 */
package com.incloud.hcp.service.delta.impl;

import com.incloud.hcp.domain.CcomparativoProveedor;
import com.incloud.hcp.service.delta.CcomparativoProveedorDeltaService;
import com.incloud.hcp.service.impl.CcomparativoProveedorServiceImpl;
import com.incloud.hcp.service.support.PageRequestByExample;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A simple DTO Facility for CcomparativoProveedor.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CcomparativoProveedorDeltaServiceImpl extends CcomparativoProveedorServiceImpl implements CcomparativoProveedorDeltaService {

    /**************************/
    /* Metodos Personalizados */
    /**************************/

    /**************************/
    /* Metodos Generados      */
    /**************************/

    protected Sort setFindAll(Sort sort) {
        return sort;
    }

    protected Sort setFind(CcomparativoProveedor req, ExampleMatcher matcher, Example<CcomparativoProveedor> example, Sort sort) {
        return sort;
    }

    protected void setFindPaginated(PageRequestByExample<CcomparativoProveedor> req, ExampleMatcher matcher, Example<CcomparativoProveedor> example) {
        return;
    }

    protected void setCreate(CcomparativoProveedor dto) {

    }

    protected void setSave(CcomparativoProveedor dto) {

    }

    protected void setDelete(Integer id) {

    }

    protected void setDeleteAll() {

    }

}