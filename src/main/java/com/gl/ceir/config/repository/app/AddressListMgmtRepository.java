package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.AddressEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface AddressListMgmtRepository extends JpaRepository<AddressEntity, Long>, JpaSpecificationExecutor<AddressEntity> {

    AddressEntity findByProvinceAndDistrictAndCommune(String province, String district, String commune);

    AddressEntity findByProvinceAndDistrict(String province, String district);

    @Modifying
    @Query("UPDATE AddressEntity SET province =:#{#a.province},district =:#{#a.district},  commune =:#{#a.commune},modifiedOn=CURRENT_TIMESTAMP WHERE id =:#{#a.id}")
    public int updateEnColumns(@Param("a") AddressEntity address);

    @Modifying
    @Query("UPDATE AddressEntity SET provinceKm =:#{#a.provinceKm},districtKm =:#{#a.districtKm},  communeKm =:#{#a.communeKm},modifiedOn=CURRENT_TIMESTAMP WHERE id =:#{#a.id}")
    public int updateKmColumns(@Param("a") AddressEntity address);
}
