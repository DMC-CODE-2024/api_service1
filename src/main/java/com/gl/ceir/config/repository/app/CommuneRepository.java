package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.AddressEntity;
import com.gl.ceir.config.model.app.CommuneDb;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface CommuneRepository extends JpaRepository<CommuneDb, Long>, JpaSpecificationExecutor<CommuneDb> {

    List<CommuneDb> findByDistrictId(int id);

    CommuneDb findById(int id);

    void deleteByCommune(String commune);

    Optional<CommuneDb> findByCommuneAndDistrictId(String commune, int districtId);

    @Modifying
    @Query("UPDATE CommuneDb SET commune =:#{#a.commune} ,modifiedOn=CURRENT_TIMESTAMP WHERE id =:id")
    public int updateEnColumns(@Param("a") AddressEntity addressEntity, @Param("id") Long id);

    @Modifying
    @Query("UPDATE CommuneDb SET communeKm =:#{#a.communeKm}, modifiedOn=CURRENT_TIMESTAMP WHERE id =:id")
    public int updateKmColumns(@Param("a") AddressEntity addressEntity, @Param("id") Long id);

}
