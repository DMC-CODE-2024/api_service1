package com.gl.ceir.config.feature.alert;

import com.gl.ceir.config.model.app.RunningAlertDb;
import com.gl.ceir.config.repository.app.RunningAlertDbRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RunningAlertRepoService {


    @Autowired
    RunningAlertDbRepo alertRepo;

    public RunningAlertDb saveAlertDb(RunningAlertDb runningAlertDb) {

        try {
            return alertRepo.save(runningAlertDb);
        } catch (Exception e) {
            return null;
        }
    }

}
