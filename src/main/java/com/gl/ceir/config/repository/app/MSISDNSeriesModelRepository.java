package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.MSISDNSeriesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MSISDNSeriesModelRepository extends JpaRepository<MSISDNSeriesModel, Long>, JpaSpecificationExecutor<MSISDNSeriesModel> {
    public List<MSISDNSeriesModel> findBySeriesStart(Integer seriesStart);
}
