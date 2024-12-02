package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.SystemConfigListDb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface SystemConfigDbListRepository extends JpaRepository<SystemConfigListDb, Long> {


    public ArrayList<SystemConfigListDb> getByTag(String tag);

    public SystemConfigListDb getById(Long id);


} 
