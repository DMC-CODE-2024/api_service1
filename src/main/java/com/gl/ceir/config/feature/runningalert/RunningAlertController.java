package com.gl.ceir.config.feature.runningalert;

import com.gl.ceir.config.externalproperties.FeatureNameMap;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.RunningAlertDb;
import com.gl.ceir.config.model.app.SystemConfigListDb;
import com.gl.ceir.config.repository.app.RunningAlertDbRepo;
import com.gl.ceir.config.repository.app.SystemConfigDbListRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/runningAlert")
public class RunningAlertController {

    @Autowired
    SystemConfigDbListRepository systemConfigRepo;

    @Autowired
    RunningAlertDbService runAlertService;

    @Autowired
    RunningAlertDbRepo runningAlertDbRepo;

    @Tag(name = "Running Alert Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch and export all record",
            description = "fetch all entities from a data source and export the records into a CSV file.")
    @PostMapping("/viewAll")
    public MappingJacksonValue viewRecord(@RequestBody RunningAlertFilter filterRequest, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "file", defaultValue = "0") Integer file, @RequestParam(value = "source", defaultValue = "menu") String source) {
        MappingJacksonValue mapping = null;
        if (file == 0) {
            Page<RunningAlertDb> alertResponse = runAlertService.viewRunningAlertData(filterRequest, pageNo, pageSize, "View", source);
            List<SystemConfigListDb> statusList = systemConfigRepo.getByTag("ALERT_STATE");
            if (alertResponse != null) {
                for (RunningAlertDb alert : alertResponse.getContent()) {
                    for (SystemConfigListDb data : statusList) {
                        Integer value = Integer.parseInt(data.getValue());
                        if (alert.getStatus() == value) {
                            alert.setStatusInterp(data.getInterpretation());
                        }
                    }
                }
            }
            mapping = new MappingJacksonValue(alertResponse);

        } else {
            FileDetails fileDetails = runAlertService.getRunningAlertInFile(filterRequest, source);
            mapping = new MappingJacksonValue(fileDetails);
        }
        return mapping;
    }


    @Tag(name = "Running Alert Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct value from the data source",
            description = "Fetch distinct values of alert feature based on the received request")
    @GetMapping("/alertFeatures")
    public ResponseEntity<?> alertFeatures() {
        List<String> list = runningAlertDbRepo.findDistinctFeatureName();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
