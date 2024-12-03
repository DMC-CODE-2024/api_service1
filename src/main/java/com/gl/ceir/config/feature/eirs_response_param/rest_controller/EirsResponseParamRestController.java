package com.gl.ceir.config.feature.eirs_response_param.rest_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gl.ceir.config.feature.eirs_response_param.service.EirsResponseParamExportService;
import com.gl.ceir.config.feature.eirs_response_param.service.EirsResponseParamPagingService;
import com.gl.ceir.config.feature.eirs_response_param.service.EirsResponseParamUpdateService;
import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.model.app.EirsResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/eirs-response-param")
public class EirsResponseParamRestController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private final EirsResponseParamPagingService eirsResponseParamPagingService;
    private final EirsResponseParamExportService eirsResponseParamExportService;
    private final EirsResponseParamUpdateService eirsResponseParamUpdateService;

    public EirsResponseParamRestController(EirsResponseParamPagingService eirsResponseParamPagingService, EirsResponseParamExportService eirsResponseParamExportService, EirsResponseParamUpdateService eirsResponseParamUpdateService) {
        this.eirsResponseParamPagingService = eirsResponseParamPagingService;
        this.eirsResponseParamExportService = eirsResponseParamExportService;
        this.eirsResponseParamUpdateService = eirsResponseParamUpdateService;
    }

    @PostMapping("/paging")
    public MappingJacksonValue paging(@RequestBody EirsResponse eirsResponse, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("EirsResponseParamEntity payload for paging [" + eirsResponse + "]");
        return eirsResponseParamPagingService.paging(eirsResponse, pageNo, pageSize);
    }

    @PostMapping
    public MappingJacksonValue findByID(@RequestBody EirsResponse eirsResponse) {
        return eirsResponseParamPagingService.find(eirsResponse);
    }

    @PostMapping("/export")
    public MappingJacksonValue export(@RequestBody EirsResponse eirsResponse) {
        logger.info("EirsResponseParamEntity payload for export [" + eirsResponse + "]");
        return eirsResponseParamExportService.export(eirsResponse);
    }

    @PutMapping
    public GenricResponse update(@RequestBody EirsResponse eirsResponse) throws JsonProcessingException {
        logger.info("update request :  " + eirsResponse);
        return eirsResponseParamUpdateService.update(eirsResponse);
    }

}