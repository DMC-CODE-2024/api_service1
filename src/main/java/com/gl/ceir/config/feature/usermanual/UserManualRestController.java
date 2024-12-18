package com.gl.ceir.config.feature.usermanual;

import com.gl.ceir.config.model.app.FeatureDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-manuals")
public class UserManualRestController {
    @Autowired
    UserManualService userManualService;
    private final Logger logger = LogManager.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<?> getUserManualFiles(@RequestParam List<Long> featureList) {
        logger.info("payload {}", featureList);
        List<FeatureDocumentDTO> featureDocuments = userManualService.get(featureList);
        logger.info("featureDocuments {}", featureDocuments);
        return new ResponseEntity<>(featureDocuments, HttpStatus.OK);
    }
}
