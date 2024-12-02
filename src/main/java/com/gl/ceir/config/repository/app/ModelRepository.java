package com.gl.ceir.config.repository.app;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.ModelRepoPojo;

public  interface ModelRepository extends JpaRepository<ModelRepoPojo, Long>, JpaSpecificationExecutor<ModelRepoPojo>  {

	public Optional<ModelRepoPojo> getById(int id);
	
	public List<ModelRepoPojo> getByBrandNameId(int brandNameId);

}
