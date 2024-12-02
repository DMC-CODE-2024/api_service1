package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.RunningAlertDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RunningAlertDbRepo extends JpaRepository<RunningAlertDb, Long>, JpaSpecificationExecutor<RunningAlertDb> {

    public RunningAlertDb save(RunningAlertDb alertDb);

    @Query("SELECT DISTINCT s.featureName FROM RunningAlertDb s")
    public List<String> findDistinctFeatureName();
}
