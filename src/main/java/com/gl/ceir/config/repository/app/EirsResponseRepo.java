package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.EirsResponse;
import org.springframework.data.jpa.repository.Query;


public interface EirsResponseRepo extends JpaRepository<EirsResponse, Long>, JpaSpecificationExecutor<EirsResponse> {

	public EirsResponse getByTagAndLanguage(String tag, String language);
	@Query(value="select value  from eirs_response_param  where tag=:tag and language=:lang", nativeQuery = true)
	public String getByTag(String tag,String lang);
}
