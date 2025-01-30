package com.gl.ceir.config.feature.addressmanagement.rest_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementDistinct;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementExportService;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementPagingService;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementUDService;
import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.feature.service.AddressServiceImpl;
import com.gl.ceir.config.model.app.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/address-mgmt/address")
public class AddressListManagementRestController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    AddressServiceImpl addressServiceImpl;
    private final AddressListManagementPagingService addressListManagementPagingService;
    private final AddressListManagementExportService addressListManagementExportService;
    private final AddressListManagementUDService addressListManagementUDService;
    private final AddressListManagementDistinct addressListManagementDistinct;

    public AddressListManagementRestController(AddressListManagementUDService addressListManagementUDService, AddressListManagementPagingService addressListManagementPagingService, AddressListManagementExportService addressListManagementExportService, AddressListManagementDistinct addressListManagementDistinct) {
        this.addressListManagementUDService = addressListManagementUDService;
        this.addressListManagementPagingService = addressListManagementPagingService;
        this.addressListManagementExportService = addressListManagementExportService;
        this.addressListManagementDistinct = addressListManagementDistinct;
    }

    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody AddressEntity addressEntity, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("AddressEntity payload for paging [" + addressEntity + "]");
        return addressListManagementPagingService.paging(addressEntity, pageNo, pageSize);
    }

    @PostMapping
    public MappingJacksonValue findByID(@RequestBody AddressEntity addressEntity) {
        return new MappingJacksonValue(addressListManagementPagingService.find(addressEntity));
    }

    @PostMapping("/export")
    public MappingJacksonValue export(@RequestBody AddressEntity addressEntity) {
        logger.info("AddressEntity payload for export [" + addressEntity + "]");
        return addressListManagementExportService.export(addressEntity);
    }

    @PostMapping("/distinctDistrict")
    public ResponseEntity<?> distinctDistrict(@RequestBody List<String> param) {
        Class<DistrictDb> entity = DistrictDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @PostMapping("/distinctCommune")
    public ResponseEntity<?> distinctCommune(@RequestBody List<String> param) {
        Class<CommuneDb> entity = CommuneDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @PostMapping("/distinctProvince")
    public ResponseEntity<?> distinctProvince(@RequestBody List<String> param) {
        Class<ProvinceDb> entity = ProvinceDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @PostMapping("/distinctPoliceStation")
    public ResponseEntity<?> distinctPoliceStation(@RequestBody List<String> param) {
        Class<PoliceStationDb> entity = PoliceStationDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @DeleteMapping
    public GenricResponse delete(@RequestBody AddressEntity addressEntity) {
        logger.info("addressEntity payload for delete operation [" + addressEntity + "]");
        return addressListManagementUDService.delete(addressEntity);
    }

    @PutMapping
    public GenricResponse update(@RequestBody AddressEntity addressEntity) throws JsonProcessingException {
        logger.info("updateProvince request :  " + addressEntity);
        return addressListManagementUDService.update(addressEntity);
    }

    @PostMapping("/getDistrict")
    public ResponseEntity<?> getDistrictBasedOnProvince(@RequestBody DistrictDb districtEntity) {
        logger.info("Received request payload: {}", districtEntity);
        return new ResponseEntity<>(addressListManagementDistinct.findByProvinceId(districtEntity.getProvinceId()), HttpStatus.OK);
    }

    @PostMapping("/getCommune")
    public ResponseEntity<?> getCommuneBasedOnDistrict(@RequestBody AddressEntity addressEntity) {
        return new ResponseEntity<>(addressListManagementDistinct.findByDistrict(addressEntity.getDistrict()), HttpStatus.OK);
    }


    @PostMapping("/getProvinces")
    public ResponseEntity<?> getProvince(@RequestParam String lang) {
        return new ResponseEntity<>(addressServiceImpl.getAllProvince(lang), HttpStatus.OK);
    }

    @PostMapping("/getProvince")
    public ResponseEntity<?> getProvince(@RequestParam String lang, long id) {
        return new ResponseEntity<>(addressServiceImpl.getProvince(lang, id), HttpStatus.OK);
    }

    @PostMapping("/getDistricts")
    public ResponseEntity<?> getDistricts(@RequestParam String lang, long id) {
        return new ResponseEntity<>(addressServiceImpl.getDistricts(lang, id), HttpStatus.OK);
    }

    @PostMapping("/getCommunes")
    public ResponseEntity<?> getCommune(@RequestParam String lang, int id) {
        return new ResponseEntity<>(addressServiceImpl.getCommune(lang, id), HttpStatus.OK);
    }

    @PostMapping("/getPolices")
    public ResponseEntity<?> getPoliceStation(@RequestParam String lang, int id) {
        return new ResponseEntity<>(addressServiceImpl.getPoliceStation(lang, id), HttpStatus.OK);
    }


}
