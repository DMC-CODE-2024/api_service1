package com.gl.ceir.config.controller;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.aud.ModulesAuditModel;
import com.gl.ceir.config.repository.aud.ModulesAuditRepo;
import com.gl.ceir.config.service.impl.ModulesAuditImpl;
//import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ModulesAuditController {

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    ModulesAuditImpl modulesAuditImpl;

    @Autowired
    ModulesAuditRepo modulesAuditRepo;

    private static final Logger logger = LogManager.getLogger(ModulesAuditController.class);

   // //@ApiOperation(value = "Paginated view of Modules Audit  .", response = ModulesAuditModel.class)
    @PostMapping("/filter/moduleAuditViewAll")
    public MappingJacksonValue paginatedViewOfmoduleAudit(@RequestBody FilterRequest filterRequest,
                                                          @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                          @RequestParam(value = "file", defaultValue = "0") Integer file) {
        MappingJacksonValue mapping = null;
        logger.info(" request  of modules audit view" + filterRequest);
        if (file == 0) {
            Page<ModulesAuditModel> page = modulesAuditImpl.filterModulesAudit(filterRequest, pageNo, pageSize);
            mapping = new MappingJacksonValue(page);
            logger.info("Response to send = " + mapping.toString());
        } else {
            FileDetails fileDetails = modulesAuditImpl.exportFile_System(filterRequest);
            mapping = new MappingJacksonValue(fileDetails);
        }

        return mapping;
    }

   // //@ApiOperation(value = "getDistinctModuleFeatureName")
    @GetMapping("/getDistinctModuleFeatureName")
    public ResponseEntity<?> getDistinctIMEIFeatureName() {
        Optional<List<String>> list = Optional.ofNullable(modulesAuditRepo.findDistinctFeatureName());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

   // //@ApiOperation(value = "getDistinctModulesName")
    @GetMapping("/getDistinctModulesName")
    public ResponseEntity<?> getDistinctModulesName() {
        Optional<List<String>> list = Optional.ofNullable(modulesAuditRepo.findDistinctModuleName());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

   // //@ApiOperation(value = "getDistinctModuleStatusName")
    @GetMapping("/getDistinctModuleStatus")
    public ResponseEntity<?> getDistinctModuleStatusName() {
        Optional<List<String>> list = Optional.ofNullable(modulesAuditRepo.findDistinctStatus());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/modules-audit-trails/{id}")
    public ResponseEntity<?> viewById(@PathVariable(name = "id", required = true) long id) {
        return new ResponseEntity<>(modulesAuditRepo.findById(id), HttpStatus.OK);
    }

   // //@ApiOperation(value = "getDistinctAction")
    @GetMapping("/getDistinctAction")
    public ResponseEntity<?> getDistinctAction() {
        Optional<List<String>> list = Optional.ofNullable(modulesAuditRepo.findDistinctAction());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
