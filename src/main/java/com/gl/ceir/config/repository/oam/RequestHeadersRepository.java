package com.gl.ceir.config.repository.oam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.oam.RequestHeaders;



@Repository
public interface RequestHeadersRepository extends 
JpaRepository<RequestHeaders, Integer>, JpaSpecificationExecutor<RequestHeaders>{
	
	@SuppressWarnings("unchecked")
	public RequestHeaders save(RequestHeaders requestHeaders);
	
		
}
