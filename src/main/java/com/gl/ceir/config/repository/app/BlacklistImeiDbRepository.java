package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.app.BlacklistImeiDb;

@Repository
public interface BlacklistImeiDbRepository extends JpaRepository<BlacklistImeiDb, Long>, 
JpaSpecificationExecutor<BlacklistImeiDb> {

	public BlacklistImeiDb getByDeviceid(String imei);
	
	@Query("SELECT r FROM BlacklistImeiDb r WHERE (deviceid = :imei OR substr(deviceid,1,14) =:imei) and blacklistStatus =:blacklistStatus")
	public BlacklistImeiDb getByDeviceidAndBlacklistStatus(String imei,String blacklistStatus);

}
