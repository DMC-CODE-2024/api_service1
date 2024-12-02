package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.MDRModel;

public interface MDRRepo extends JpaRepository<MDRModel, Long>, JpaSpecificationExecutor<MDRModel> {
	public MDRModel findByDeviceId(String tac);
}
