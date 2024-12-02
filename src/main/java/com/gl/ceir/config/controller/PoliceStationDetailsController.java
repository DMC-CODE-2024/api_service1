package com.gl.ceir.config.controller;

import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.service.impl.PoliceStationService;
//import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import com.gl.ceir.config.model.app.UserProfile;

import java.util.List;

@RestController
@RequestMapping("/policeStation")
public class PoliceStationDetailsController {
    private static final Logger logger = LogManager.getLogger(PoliceStationDetailsController.class);

    public PoliceStationDetailsController(PoliceStationService policeStationService) {
        this.policeStationService = policeStationService;
    }

    @Autowired
    PoliceStationService policeStationService;

    @PostMapping("/filterPoliceDetails")
    public MappingJacksonValue filterPoliceDetails(@RequestBody FilterRequest filterRequest,
                                                   @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                   @RequestParam(value = "file", defaultValue = "0") Integer file) {
        MappingJacksonValue mapping = null;
        logger.info("request in view police details " + filterRequest);
        if (file == 0) {
            Page<UserProfile> page = policeStationService.filterPoliceStationDetail(filterRequest, pageNo, pageSize);
            mapping = new MappingJacksonValue(page);
            logger.info("Response to send = " + page);
        } else {
            FileDetails fileDetails = policeStationService.exportFile_System(filterRequest);
            mapping = new MappingJacksonValue(fileDetails);
        }

        return mapping;
    }

    @PostMapping("/distinctDistrict")
    public ResponseEntity<?> distinctDistrict(@RequestBody List<String> param) {
        Class<DistrictDb> entity = DistrictDb.class;
        return new ResponseEntity<>(policeStationService.distinct(param, entity), HttpStatus.OK);
    }

    @PostMapping("/distinctCommune")
    public ResponseEntity<?> distinctCommune(@RequestBody List<String> param) {
        Class<CommuneDb> entity = CommuneDb.class;
        return new ResponseEntity<>(policeStationService.distinct(param, entity), HttpStatus.OK);
    }

    @PostMapping("/distinctProvince")
    public ResponseEntity<?> distinctProvince(@RequestBody List<String> param) {
        Class<ProvinceDb> entity = ProvinceDb.class;
        return new ResponseEntity<>(policeStationService.distinct(param, entity), HttpStatus.OK);
    }

    @PostMapping("/distinctPoliceStation")
    public ResponseEntity<?> distinctPoliceStation(@RequestBody List<String> param) {
        Class<PoliceStationDb> entity = PoliceStationDb.class;
        return new ResponseEntity<>(policeStationService.distinct(param, entity), HttpStatus.OK);
    }
}

