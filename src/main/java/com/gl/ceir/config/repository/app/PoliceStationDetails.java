package com.gl.ceir.config.repository.app;


import com.gl.ceir.config.model.app.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PoliceStationDetails extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {


}
