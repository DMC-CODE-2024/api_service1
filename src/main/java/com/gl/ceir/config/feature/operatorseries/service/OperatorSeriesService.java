package com.gl.ceir.config.feature.operatorseries.service;

import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.model.app.MSISDNSeriesModel;
import com.gl.ceir.config.repository.app.MSISDNSeriesModelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperatorSeriesService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private MSISDNSeriesModelRepository msisdnSeriesModelRepository;
    private final int LENGTH = 5;

    public OperatorSeriesService(MSISDNSeriesModelRepository msisdnSeriesModelRepository) {
        this.msisdnSeriesModelRepository = msisdnSeriesModelRepository;
    }


    public GenricResponse seriesValidation(String msisdn) {
        Optional<String> optional = getLength(msisdn);
        GenricResponse response = new GenricResponse();
        String len;
        if (optional.isPresent()) {
            List<MSISDNSeriesModel> bySeriesStart = msisdnSeriesModelRepository.findBySeriesStart(Integer.valueOf(optional.get()));
            logger.info("record fetched [" + bySeriesStart + "]");

            if (bySeriesStart.isEmpty()) {
                len = null;
                return response
                        .setMessage("no data found")
                        .setStatusCode(HttpStatus.OK.value())
                        .setData(bySeriesStart)
                        .setSeriesLength(len)
                        .setValid(false);
            } else {
                len = bySeriesStart.get(0).getLength();
                return response
                        .setMessage("data found")
                        .setStatusCode(HttpStatus.OK.value())
                        .setData(bySeriesStart)
                        .setSeriesLength(len)
                        .setValid(true);
            }

        }
        return response
                .setMessage("pass a valid msisdn length")
                .setStatusCode(HttpStatus.OK.value())
                .setData(null)
                .setSeriesLength(null)
                .setValid(false);
    }

    public Optional<String> getLength(String val) {
        if (val.length() >= LENGTH) {
            logger.info("extract 5 alpha from string " + val.substring(0, LENGTH));
            return Optional.of(val.substring(0, LENGTH));
        } else {
            logger.info("length is less than defined length" + LENGTH);
            return Optional.empty();
        }
    }
}
