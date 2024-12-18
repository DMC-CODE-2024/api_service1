package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.feature.usermanual.FeatureDocumentDTO;
import com.gl.ceir.config.model.app.FeatureDocument;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface FeatureDocumentRepository extends JpaRepository<FeatureDocument, Long>, JpaSpecificationExecutor<FeatureDocument> {
    @Query(value = "SELECT new com.gl.ceir.config.feature.usermanual.FeatureDocumentDTO(f.featureName,fd.lang,fd.docName) FROM com.gl.ceir.config.model.app.Feature f JOIN com.gl.ceir.config.model.app.FeatureDocument fd ON fd.featureId=f.id where fd.featureId IN (:featureIds) order by fd.modifiedOn")
    Optional<List<FeatureDocumentDTO>> getFeatureDetail(List<Long> featureIds);

}
