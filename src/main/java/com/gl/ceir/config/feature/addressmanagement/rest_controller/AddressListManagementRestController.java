package com.gl.ceir.config.feature.addressmanagement.rest_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementDistinct;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementExportService;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementPagingService;
import com.gl.ceir.config.feature.addressmanagement.service.AddressListManagementUDService;
import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.feature.service.AddressServiceImpl;
import com.gl.ceir.config.model.app.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all record",
            description = "Fetches all address entities and their data from data source")
    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody AddressEntity addressEntity, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("AddressEntity payload for paging [" + addressEntity + "]");
        return addressListManagementPagingService.paging(addressEntity, pageNo, pageSize);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch single record based on Id",
            description = "Fetches record based on Id from data source")
    @PostMapping
    public MappingJacksonValue findByID(@RequestBody AddressEntity addressEntity) {
        return new MappingJacksonValue(addressListManagementPagingService.find(addressEntity));
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Export csv file",
            description = "Fetches address entities and their associated data from the data source, with the number of records limited to a configurable parameter, up to a maximum of 50,000. Subsequently, generate a .csv file containing the retrieved data.")
    @PostMapping("/export")
    public MappingJacksonValue export(@RequestBody AddressEntity addressEntity) {
        logger.info("AddressEntity payload for export [" + addressEntity + "]");
        return addressListManagementExportService.export(addressEntity);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct value from the data source",
            description = "Fetch distinct values from the districts based on the received request")
    @PostMapping("/distinctDistrict")
    public ResponseEntity<?> distinctDistrict(@RequestBody List<String> param) {
        Class<DistrictDb> entity = DistrictDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct value from the data source",
            description = "Fetch distinct values from the commune based on the received request")
    @PostMapping("/distinctCommune")
    public ResponseEntity<?> distinctCommune(@RequestBody List<String> param) {
        Class<CommuneDb> entity = CommuneDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct value from the data source",
            description = "Fetch distinct values from the province based on the received request")
    @PostMapping("/distinctProvince")
    public ResponseEntity<?> distinctProvince(@RequestBody List<String> param) {
        Class<ProvinceDb> entity = ProvinceDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct value from the data source",
            description = "Fetch distinct values for the police_station based on the received request")
    @PostMapping("/distinctPoliceStation")
    public ResponseEntity<?> distinctPoliceStation(@RequestBody List<String> param) {
        Class<PoliceStationDb> entity = PoliceStationDb.class;
        return new ResponseEntity<>(addressListManagementDistinct.distinct(param, entity), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Delete record from the data source",
            description = "Delete the record based on the received request")
    @DeleteMapping
    public GenricResponse delete(@RequestBody AddressEntity addressEntity) {
        logger.info("addressEntity payload for delete operation [" + addressEntity + "]");
        return addressListManagementUDService.delete(addressEntity);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Update record to the data source",
            description = "Update the record based on the received request")
    @PutMapping
    public GenricResponse update(@RequestBody AddressEntity addressEntity) throws JsonProcessingException {
        logger.info("updateProvince request :  " + addressEntity);
        return addressListManagementUDService.update(addressEntity);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct record from the data source",
            description = "Fetch distinct record based on the province Id")
    @PostMapping("/getDistrict")
    public ResponseEntity<?> getDistrictBasedOnProvince(@RequestBody DistrictDb districtEntity) {
        logger.info("Received request payload: {}", districtEntity);
        return new ResponseEntity<>(addressListManagementDistinct.findByProvinceId(districtEntity.getProvinceId()), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch distinct record from the data source",
            description = "Fetch distinct record based on the district Id")
    @PostMapping("/getCommune")
    public ResponseEntity<?> getCommuneBasedOnDistrict(@RequestBody AddressEntity addressEntity) {
        return new ResponseEntity<>(addressListManagementDistinct.findByDistrict(addressEntity.getDistrict()), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all record from the data source",
            description = "Fetch all province based on the language selection")
    @PostMapping("/getProvinces")
    public ResponseEntity<?> getProvince(@RequestParam String lang) {
        return new ResponseEntity<>(addressServiceImpl.getAllProvince(lang), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all record from the data source",
            description = "Fetch all province based on the language selection and Id")
    @PostMapping("/getProvince")
    public ResponseEntity<?> getProvince(@RequestParam String lang, long id) {
        return new ResponseEntity<>(addressServiceImpl.getProvince(lang, id), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all record from the data source",
            description = "Fetch all district based on the language selection and Id")
    @PostMapping("/getDistricts")
    public ResponseEntity<?> getDistricts(@RequestParam String lang, long id) {
        return new ResponseEntity<>(addressServiceImpl.getDistricts(lang, id), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all record from the data source",
            description = "Fetch all commune based on the language selection and Id")
    @PostMapping("/getCommunes")
    public ResponseEntity<?> getCommune(@RequestParam String lang, int id) {
        return new ResponseEntity<>(addressServiceImpl.getCommune(lang, id), HttpStatus.OK);
    }

    @Tag(name = "Address List Management", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all record from the data source",
            description = "Fetch all police_station based on the language selection and Id")
    @PostMapping("/getPolices")
    public ResponseEntity<?> getPoliceStation(@RequestParam String lang, int id) {
        return new ResponseEntity<>(addressServiceImpl.getPoliceStation(lang, id), HttpStatus.OK);
    }


}
