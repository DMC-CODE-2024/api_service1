package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gl.ceir.config.model.app.LostStolenMgmt;


public interface StolenLostDetailRepo extends JpaRepository<LostStolenMgmt, Long>, JpaSpecificationExecutor<LostStolenMgmt> {

	
/*	@Modifying
	 @Query("update LostStolenMgmt a  set a.requestType='Recovery' ,MODIFIED_ON=SYSTIMESTAMP where a.requestId=:requestId")
		public void updateByRequestId(String requestId);*/
	public LostStolenMgmt findByRequestId(String requestId);
	public LostStolenMgmt findByImei(String imei);
}
