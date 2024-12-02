package com.gl.ceir.config.feature.alert;

import com.gl.ceir.config.model.app.AlertDb;
import com.gl.ceir.config.model.app.FileDetails;
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

    //@ApiOperation(value = "alert db  data.", response = AlertDb.class)
    @PostMapping("/viewAll")
    public MappingJacksonValue viewRecord(@RequestBody AlertDbFilter filterRequest, @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, @RequestParam(value = "file", defaultValue = "0") Integer file, @RequestParam(name = "source", defaultValue = "menu", required = false) String source) {
        MappingJacksonValue mapping = null;
        log.info(" filter or export: " + file);
        if (file == 0) {
            log.info(" filter block: ");
            Page<AlertDb> alertDbReponse = alertDbService.viewAllAlertData(filterRequest, pageNo, pageSize, source);
            mapping = new MappingJacksonValue(alertDbReponse);

        } else {
            //hh
            log.info(" export block: ");
            FileDetails fileDetails = alertDbService.getAlertDbInFile(filterRequest, source);
            mapping = new MappingJacksonValue(fileDetails);
        }
        return mapping;
    }

    //@ApiOperation(value = "alert ids data", response = HttpResponse.class)
    @CrossOrigin
    @PostMapping("/view")
    public ResponseEntity<?> getUsertypes(@RequestHeader HttpHeaders headers) {
        return alertDbService.getAlertData();
    }

    //@ApiOperation(value="view alert d by id")
    @PostMapping("/viewById")
    public ResponseEntity<?> viewById(@RequestBody AllRequest request) {
        return alertDbService.findById(request);
    }

    //@ApiOperation(value="update alert db")
    @PostMapping("/update")
    public ResponseEntity<?> updateAlertDb(@RequestBody AlertDb alertDb) {
        return alertDbService.updateAlertDb(alertDb);
    }
}
