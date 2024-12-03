package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.AlertDb;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlertDbRepository extends JpaRepository<AlertDb, Long>, JpaSpecificationExecutor<AlertDb> {

    public AlertDb getById(long id);

    public AlertDb getByAlertId(String alertId);

    @Query("SELECT DISTINCT s.feature FROM AlertDb s")
    public List<String> findDistinctFeature();

    @Query("SELECT DISTINCT s.alertId FROM AlertDb s")
    public List<String> findDistinctAlertId();

    public AlertDb findById(long id);

}
