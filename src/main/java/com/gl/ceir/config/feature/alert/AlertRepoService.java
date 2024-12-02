package com.gl.ceir.config.feature.alert;

import com.gl.ceir.config.model.app.AlertDb;
import com.gl.ceir.config.repository.app.AlertDbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertRepoService {

    @Autowired
    AlertDbRepository alertRepo;

    private final Logger log = LoggerFactory.getLogger(getClass());


    public AlertDb getById(long id) {
        try {
          return alertRepo.findById(id);
        } catch (Exception e) {
            log.info("error occurs when fetching data by id");
            log.info(e.toString());
            return null;
        }
    }

    public AlertDb save(AlertDb alertDb) {

        try {
            log.info("going to save   alertDb data");
            return alertRepo.save(alertDb);

        } catch (Exception e) {
            log.info("error occurs while adding  alertDb data");
            log.info(e.toString());
            return null;
        }
    }

}
