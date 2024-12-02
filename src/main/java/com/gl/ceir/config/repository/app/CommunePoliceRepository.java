package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.PoliceStationDb;

import com.gl.ceir.config.model.app.StolenLostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface CommunePoliceRepository extends JpaRepository<PoliceStationDb, Long>, JpaSpecificationExecutor<PoliceStationDb> {
    List<PoliceStationDb> findByCommuneId(int id);
    PoliceStationDb findById(int id);

    @Query(value="select * from police_station s where s.commune_id=:communeId", nativeQuery = true)
    public PoliceStationDb findByCommune(int communeId );


}
