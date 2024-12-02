package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.ReportColumnDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportColumnDbRepository extends JpaRepository<ReportColumnDb, Long> {

    public List<ReportColumnDb> findByReportnameIdOrderByColumnOrderAsc(Long reportnameId);

    public List<ReportColumnDb> findByReportnameIdAndTypeFlagOrderByColumnOrderAsc(Long reportnameId, Integer typeFlag);

    public ReportColumnDb findByReportnameIdAndColumnName(Long reportnameId, String columnName);

}
