package com.gl.ceir.config.feature.alert;

import com.gl.ceir.config.model.app.AlertDb;
import com.gl.ceir.config.model.app.FileDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/alertDb")
public class AlertDbController {

    @Autowired
    AlertDbService alertDbService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Tag(name = "Alert Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch and export all record",
            description = "fetch all entities from a data source and export the records into a CSV file.")
    @PostMapping("/viewAll")
    public MappingJacksonValue viewRecord(@RequestBody AlertDbFilter filterRequest, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "file", defaultValue = "0") Integer file, @RequestParam(name = "source", defaultValue = "menu", required = false) String source) {
        MappingJacksonValue mapping = null;
        log.info(" filter or export: " + file);
        if (file == 0) {
            log.info(" filter block: ");
            Page<AlertDb> alertDbReponse = alertDbService.viewAllAlertData(filterRequest, pageNo, pageSize, source);
            mapping = new MappingJacksonValue(alertDbReponse);

        } else {
            log.info(" export block: ");
            FileDetails fileDetails = alertDbService.getAlertDbInFile(filterRequest, source);
            mapping = new MappingJacksonValue(fileDetails);
        }
        return mapping;
    }


    @Tag(name = "Alert Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct value from the data source",
            description = "Fetch distinct values of alerts based on the received request")
    @PostMapping("/view")
    public ResponseEntity<?> getUsertypes() {
        return alertDbService.getAlertData();
    }


    @Tag(name = "Alert Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch single record based on Id",
            description = "Fetches record based on Id from data source")
    @PostMapping("/viewById")
    public ResponseEntity<?> viewById(@RequestBody AllRequest request) {
        return alertDbService.findById(request);
    }

    @Tag(name = "Alert Management", description = "System Configuration Module API")
    @Operation(
            summary = "Update record to the data source",
            description = "Update the record based on the received request")
    @PostMapping("/update")
    public ResponseEntity<?> updateAlertDb(@RequestBody AlertDb alertDb) {
        return alertDbService.updateAlertDb(alertDb);
    }
}
