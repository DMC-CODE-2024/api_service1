package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.gl.ceir.config.model.app.GreyListView;

public interface GreyListRepo extends JpaRepository<GreyListView, Long>, JpaSpecificationExecutor<GreyListView>{
	public GreyListView getBySno(long  sno);
}
