package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.AllowedTacModel;
import com.gl.ceir.config.model.app.BlackListModel;


public interface BlackListRepo extends JpaRepository<BlackListModel, Long>, JpaSpecificationExecutor<BlackListModel>{

	public BlackListModel getBySno(long  sno);
}
