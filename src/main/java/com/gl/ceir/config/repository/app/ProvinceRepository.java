package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.ProvinceDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<ProvinceDb, Long>, JpaSpecificationExecutor<ProvinceDb> {
    // Custom query to find a Province by ID
    List<ProvinceDb> findById(long id);

    // Retrieves all ProvinceDb records
    List<ProvinceDb> findAll();

    @Modifying
    @Query("UPDATE ProvinceDb SET province =:newValue,modifiedOn=CURRENT_TIMESTAMP WHERE province =:oldValue")
    public int updateEnColumns(@Param("oldValue") String oldValue, @Param("newValue") String newValue);

    @Modifying
    @Query("UPDATE ProvinceDb SET provinceKm =:newValue,modifiedOn=CURRENT_TIMESTAMP WHERE province =:oldValue")
    public int updateKmColumns(@Param("oldValue") String oldValue, @Param("newValue") String newValue);

    Optional<ProvinceDb> findByProvince(String province);
    //ProvinceDb findByIds(long id);
}

