package com.gl.ceir.config.feature.operatorseries.rest_controller;

import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.feature.operatorseries.service.OperatorSeriesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/operator-series/series")
public class OperatorSeriesRestController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private OperatorSeriesService operatorSeriesService;

    public OperatorSeriesRestController(OperatorSeriesService operatorSeriesService) {
        this.operatorSeriesService = operatorSeriesService;
    }

    @GetMapping("/{msisdn}")
    public GenricResponse operatorSeriesLengthValidation(@PathVariable String msisdn) {
        if (msisdn.charAt(0) == '0') {
            // Replace the first character with "855"
            msisdn = "855" + msisdn.substring(1);
        }

        logger.info("msisdn received " + msisdn);
        return operatorSeriesService.seriesValidation(msisdn);
    }
}
