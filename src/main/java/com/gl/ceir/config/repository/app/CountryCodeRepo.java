package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.CountryCodeModel;
import com.gl.ceir.config.model.app.StolenLostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CountryCodeRepo  extends JpaRepository<CountryCodeModel, Long>, JpaSpecificationExecutor<CountryCodeModel> {

    @Query(value="select * from countries_phone_code s", nativeQuery = true)
    public List<CountryCodeModel> fetchCountryCode();
}
