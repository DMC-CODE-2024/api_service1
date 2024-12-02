package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.AddressEntity;
import com.gl.ceir.config.model.app.DistrictDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictDb, Long> {

    DistrictDb findById(long id);

    List<DistrictDb> findByProvinceId(long id);

    void deleteByDistrict(String district);

    @Modifying
    @Query("UPDATE DistrictDb SET district =:#{#a.district},modifiedOn=CURRENT_TIMESTAMP WHERE id =:id")
    public int updateEnColumns(@Param("a") AddressEntity addressEntity, @Param("id") Long id);

    @Modifying
    @Query("UPDATE DistrictDb SET modifiedOn=CURRENT_TIMESTAMP, districtKm =:#{#a.districtKm} WHERE id =:id")
    public int updateKmColumns(@Param("a") AddressEntity addressEntity, @Param("id") Long id);

    Optional<DistrictDb> findByDistrict(String district);

    Optional<DistrictDb> findByDistrictOrDistrictKm(String district, String districtKm);
}