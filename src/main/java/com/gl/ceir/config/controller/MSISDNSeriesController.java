package com.gl.ceir.config.controller;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.MSISDNSeriesImpl;
//import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import jakarta.validation. Valid;
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


    @PostMapping
   // //@ApiOperation(value = "Save || msisdn series", response = GenricResponse.class)
    public ResponseEntity<?> save(@RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("msisdn-series payload save operation [" + msisdnSeriesModel + "]");
        GenricResponse genricResponse = msisdnSeriesImpl.save(msisdnSeriesModel);
        HttpStatus httpStatus = genricResponse.getErrorCode() == 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(genricResponse, httpStatus);
    }

    @PutMapping
   // //@ApiOperation(value = "Update || msisdn series", response = GenricResponse.class)
    public ResponseEntity<?> update(@RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("msisdn-series payload for update operation [" + msisdnSeriesModel + "]");
        GenricResponse genricResponse = msisdnSeriesImpl.update(msisdnSeriesModel);
        HttpStatus httpStatus = genricResponse.getErrorCode() == 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(genricResponse, httpStatus);
    }

   // //@ApiOperation(value = "Paging and Export || msisdn series", response = MSISDNSeriesModel.class)
    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody MSISDNSeriesModel msisdnSeriesModel, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "file", defaultValue = "0") Integer file) {
        return msisdnSeriesImpl.pagingAndExport(msisdnSeriesModel, pageNo, pageSize, file);
    }

   // //@ApiOperation(value = "By ID || msisdn series", response = MSISDNSeriesModel.class)
    @GetMapping("/{id}")
    public MappingJacksonValue findByID(@PathVariable(name = "id") Long id) {
        return new MappingJacksonValue(msisdnSeriesImpl.find(id));
    }

    @PostMapping("/export")
    public FileDetails export(@Valid @RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("export [" + msisdnSeriesModel + "]");
        return msisdnSeriesImpl.export(msisdnSeriesModel);
    }

    @PostMapping("/distinct")
   // //@ApiOperation(value = "Distinct Result || msisdn series", response = MSISDNSeriesModel.class)
    public ResponseEntity<?> distinct(@RequestBody List<String> param) {
        Class<MSISDNSeriesModel> entity = MSISDNSeriesModel.class;
        return new ResponseEntity<>(msisdnSeriesImpl.distinct(param, entity), HttpStatus.OK);
    }

    @DeleteMapping
   // //@ApiOperation(value = "Delete || msisdn series", response = GenricResponse.class)
    public ResponseEntity<?> delete(@RequestBody MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("msisdn-series payload for delete operation [" + msisdnSeriesModel + "]");
        GenricResponse genricResponse = msisdnSeriesImpl.delete(msisdnSeriesModel);
        HttpStatus httpStatus = genricResponse.getErrorCode() == 0 ? HttpStatus.OK : HttpStatus.EXPECTATION_FAILED;
        return new ResponseEntity<>(genricResponse, httpStatus);
    }


}
