package com.gl.ceir.config.model.oam;

import com.gl.ceir.config.model.app.CommuneDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(rollbackOn = {SQLException.class})
public interface CommuneRepository extends JpaRepository<CommuneDb, Long>, JpaSpecificationExecutor<CommuneDb> {
    List<CommuneDb> findByDistrictId(int id);

    List<CommuneDb> findByCommuneOrCommuneKm(String commune, String communeKm);
}
