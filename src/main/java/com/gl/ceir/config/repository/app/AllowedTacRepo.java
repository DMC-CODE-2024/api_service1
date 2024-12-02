package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.AllowedTacModel;


public interface AllowedTacRepo extends JpaRepository<AllowedTacModel, Long>, JpaSpecificationExecutor<AllowedTacModel> {
	public AllowedTacModel getBySno(long  sno);
}
