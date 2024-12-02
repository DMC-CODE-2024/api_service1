package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.RuleEngineMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RuleEngineMappingRepo extends JpaRepository<RuleEngineMapping, Long> {
    @Query("SELECT DISTINCT r.feature FROM RuleEngineMapping r")
    public List<String> findDistinctFeature();

    @Query("SELECT DISTINCT r.userType FROM RuleEngineMapping r")
    public List<String> findDistinctUserType();
}
