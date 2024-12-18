package com.gl.ceir.config.repository.app;


import java.sql.SQLException;
import java.util.List;

import com.gl.ceir.config.model.app.GenricResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.gl.ceir.config.model.app.StolenLostModel;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface LostStolenRepo extends JpaRepository<StolenLostModel, Long>, JpaSpecificationExecutor<StolenLostModel> {

	public StolenLostModel findByRequestId(String requestId);


	@Query(value="select * from lost_device_mgmt s where s.status NOT IN ('INIT_START') and contact_number=:contactNumber", nativeQuery = true)
	public List<StolenLostModel> findByContactNumber1(String contactNumber);
	@Query(value="select * from lost_device_mgmt s where s.status NOT IN ('INIT_START') and request_id=:requestId", nativeQuery = true)
	public List<StolenLostModel> findByRequestId1(String requestId );
	
	@Query(value="select * from lost_device_mgmt s where s.status NOT IN ('INIT_START') and request_id=:requestId", nativeQuery = true)
	public StolenLostModel findByRequestId2(String requestId );
	
	@Query(value="select * from lost_device_mgmt s where request_id=:requestId", nativeQuery = true)
	public StolenLostModel findByRequestId3(String requestId );
	
	@Query(value="select * from lost_device_mgmt s where request_id=:requestId", nativeQuery = true)
	public  List<StolenLostModel> findByRequestId4(String requestId );
	
	public StolenLostModel findByImei1AndStatusIgnoreCaseAndRequestTypeIgnoreCase(String requestId,String status,String requestType);
	//public List<StolenLostModel> findByRequestId(String requestId);
	public List<StolenLostModel> findByContactNumberAndStatusIgnoreCaseAndRequestTypeIgnoreCase(String requestId,String status,String requestType);

	@Query(value="select * from lost_device_mgmt s where contact_number_for_otp=:contact or contact_number_for_otp=:acctualContact", nativeQuery = true)
	public List<StolenLostModel> findByContactNumberForOtp(String contact,String acctualContact);
	@Modifying	
	@Query("update StolenLostModel a  set a.requestType=:requestType ,a.createdOn=CURRENT_TIMESTAMP  where a.requestId=:requestId")
		public void updateByRequestId(String requestId,String requestType);
	
	
	@Query(value="select DISTRICT from districts_db  where ID=:id", nativeQuery = true)
	public String getDistrict(String id );
	
	@Query(value="select COMMUNE from commune_db where ID=:id", nativeQuery = true)
	public String getCommune(String id );

	@Query(value="select PROVINCE from province_db  where ID=:id", nativeQuery = true)
	public String getProvince(String id );

	@Query(value="select police from police_station where ID=:id", nativeQuery = true)
	public String getPolice(String id );


	@Query(value="select interpretation from sys_param_list_value where tag='nationality' and value=:id", nativeQuery = true)
	public String getNationality(String id );
	
	@Query(value="select interpretation from sys_param_list_value where tag='category' and list_order==:id", nativeQuery = true)
	public String getcategory(String id );

	@Query(value="select * from lost_device_mgmt s where s.status IN ('INIT','VERIFY_MOI','APPROVE_MOI','START','DONE') and request_type='Stolen' and :imei in (imei1,imei2,imei3,imei4)", nativeQuery = true)
	public StolenLostModel findByImei1(String imei );

	@Query(value="select * from lost_device_mgmt s where s.status  IN ('CANCEL','Done') and request_type='Recover'  and :imei in (imei1,imei2,imei3,imei4)", nativeQuery = true)
	public StolenLostModel checkduplicateRecovery(String imei );

	@Query(value="select * from lost_device_mgmt s where s.status NOT IN ('START','DONE') and request_id=:requestId", nativeQuery = true)
	public StolenLostModel checkForCancelRequest(String requestId );

	public StolenLostModel findByImei4(String imei );
	
	@Modifying	
	@Query("update StolenLostModel a  set a.otp=:otp ,a.modified_on=CURRENT_TIMESTAMP  where a.requestId=:requestId")
		public void updateOtp(String otp,String requestId);

	@Modifying
	@Query("update StolenLostModel a  set a.remarks=:remark ,a.firCopyUrl=:firFileName,a.status='VERIFY_MOI',a.userStatus='Pending MOI'  where a.requestId=:requestId")
	public void approveStolenRequest(String remark, String firFileName, String requestId);


/*	@Modifying
	@Query("update StolenLostModel a  set a.otp=:otp ,a.modified_on=CURRENT_TIMESTAMP,status='CANCEL',user_status='CANCEL'  where a.requestId=:requestId")
	public void updateOtpForCancelRequest(String otp,String requestId);*/
}
