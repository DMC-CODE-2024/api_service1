package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.InvalidIMEI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InvalidIMEIRepo extends JpaRepository<InvalidIMEI, Long>, JpaSpecificationExecutor<InvalidIMEI> {

    Boolean existsByImei(String IMEI);
}
