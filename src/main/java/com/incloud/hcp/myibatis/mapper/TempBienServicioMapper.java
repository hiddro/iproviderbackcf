/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/mapper/EntityMapper.java.e.vm
 */
package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.domain.TempBienServicio;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TempBienServicioMapper {

    List<TempBienServicio> getTempBienServicio(TempBienServicio bean);

    void insertTempBienServicio(TempBienServicio bean);

    void insertMapTempBienServicio(Map<String, Object> map);

    void deleteTempBienServicio(TempBienServicio bean);

    void updateTempBienServicio(TempBienServicio bean);

    void deleteAll();

}