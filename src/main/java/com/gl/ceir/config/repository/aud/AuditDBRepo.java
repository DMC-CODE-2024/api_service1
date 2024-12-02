package com.gl.ceir.config.repository.aud;

import com.gl.ceir.config.model.aud.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditDBRepo extends JpaRepository<AuditTrail,Long>{

}
