package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.EirsResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface EirsResponseParamRepository extends JpaRepository<EirsResponse, Long>, JpaSpecificationExecutor<EirsResponse> {

    EirsResponse findByTagAndType(String tag, Integer type);

    @Modifying
    @Query("UPDATE EirsResponse SET description =:#{#e.description}, modifiedOn = CURRENT_TIMESTAMP, type =:#{#e.type}, value =:#{#e.value}, active =:#{#e.active}, featureName =:#{#e.featureName}, remarks =:#{#e.remarks}, language =:#{#e.language} WHERE id =:#{#e.id}")
    int updateColumns(@Param("e") EirsResponse eirsResponse);


}