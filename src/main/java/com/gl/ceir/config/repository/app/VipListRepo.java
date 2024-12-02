package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.VIPListModel;

public interface VipListRepo  extends JpaRepository<VIPListModel, Long>, JpaSpecificationExecutor<VIPListModel>{

	public VIPListModel getBySno(long  sno);
}
