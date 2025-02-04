package com.gl.ceir.config.controller;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.MSISDNSeriesImpl;
//import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/v1/operator-configuration/msisdn-series")
public class MSISDNSeriesController {
    private MSISDNSeriesImpl msisdnSeriesImpl;

    @Autowired
    public MSISDNSeriesController(MSISDNSeriesImpl msisdnSeriesImpl) {
        this.msisdnSeriesImpl = msisdnSeriesImpl;
    }

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    private static final Logger logger = LogManager.getLogger(MSISDNSeriesController.class);

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Save record to the data source", description = "Save the record based on the received request")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("msisdn-series payload save operation [" + msisdnSeriesModel + "]");
        GenricResponse genricResponse = msisdnSeriesImpl.save(msisdnSeriesModel);
        HttpStatus httpStatus = genricResponse.getErrorCode() == 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(genricResponse, httpStatus);
    }

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Update record to the data source", description = "Update the record based on the received request")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("msisdn-series payload for update operation [" + msisdnSeriesModel + "]");
        GenricResponse genricResponse = msisdnSeriesImpl.update(msisdnSeriesModel);
        HttpStatus httpStatus = genricResponse.getErrorCode() == 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(genricResponse, httpStatus);
    }

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Fetch all record", description = "Fetches all operator series entities and their data from data source")
    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody MSISDNSeriesModel msisdnSeriesModel, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "file", defaultValue = "0") Integer file) {
        return msisdnSeriesImpl.pagingAndExport(msisdnSeriesModel, pageNo, pageSize, file);
    }

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Fetch single record based on Id", description = "Fetches record based on Id from data source")
    @GetMapping("/{id}")
    public MappingJacksonValue findByID(@PathVariable(name = "id") Long id) {
        return new MappingJacksonValue(msisdnSeriesImpl.find(id));
    }

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Export csv file", description = "Fetches operator series entities and their associated data from the data source, with the number of records limited to a configurable parameter, up to a maximum of 50,000. Subsequently, generate a .csv file containing the retrieved data.")
    @PostMapping("/export")
    public FileDetails export(@Valid @RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("export [" + msisdnSeriesModel + "]");
        return msisdnSeriesImpl.export(msisdnSeriesModel);
    }

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Fetch distinct value from the data source", description = "Fetch distinct values from the operator series based on the received request")
    @PostMapping("/distinct")
    public ResponseEntity<?> distinct(@RequestBody List<String> param) {
        Class<MSISDNSeriesModel> entity = MSISDNSeriesModel.class;
        return new ResponseEntity<>(msisdnSeriesImpl.distinct(param, entity), HttpStatus.OK);
    }

    @Tag(name = "Operator Series Configuration", description = "System Configuration Module API")
    @Operation(summary = "Delete record from the data source", description = "Delete the record based on the received request")
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("msisdn-series payload for delete operation [" + msisdnSeriesModel + "]");
        GenricResponse genricResponse = msisdnSeriesImpl.delete(msisdnSeriesModel);
        HttpStatus httpStatus = genricResponse.getErrorCode() == 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(genricResponse, httpStatus);
    }


}
