package com.gl.ceir.config.repository.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gl.ceir.config.model.app.ChecKIMEIContent;
public interface CheckIMEIContentRepo  extends JpaRepository<ChecKIMEIContent, Long>, JpaSpecificationExecutor<ChecKIMEIContent>{

	public ChecKIMEIContent getById(long id);
	@Query("SELECT DISTINCT s.featureName FROM ChecKIMEIContent s")
	public List<String> findDistinctFeatureName();
}
