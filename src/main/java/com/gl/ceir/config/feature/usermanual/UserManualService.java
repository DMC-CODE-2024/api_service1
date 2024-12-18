package com.gl.ceir.config.feature.usermanual;

import com.gl.ceir.config.model.app.FeatureDocument;
import com.gl.ceir.config.repository.app.FeatureDocumentRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserManualService {

    @Autowired
    private FeatureDocumentRepository featureDocumentRepository;

    public List<FeatureDocumentDTO> get(List<Long> featureList) {
        Optional<List<FeatureDocumentDTO>> byFeatureIdInOrderByModifiedOn = featureDocumentRepository.getFeatureDetail(featureList);
        if (byFeatureIdInOrderByModifiedOn.isPresent()) {
            return byFeatureIdInOrderByModifiedOn.get();
        }
        return Collections.emptyList();
    }
}
