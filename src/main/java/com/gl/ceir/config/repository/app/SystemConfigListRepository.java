package com.gl.ceir.config.repository.app;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.gl.ceir.config.model.app.SystemConfigListDb;

public interface SystemConfigListRepository extends CrudRepository<SystemConfigListDb, Long>, 
JpaRepository<SystemConfigListDb, Long>, JpaSpecificationExecutor<SystemConfigListDb>{
	
	public List<SystemConfigListDb> findByTag(String tag, Sort sort);
	
	public SystemConfigListDb findByTagAndInterpretationIgnoreCase(String tag, String interp);
	
	@Query("SELECT DISTINCT a.tag FROM SystemConfigListDb a")
	public List<String> findDistinctTags();
	
        
        //sys_param_list_value
	//@Query("SELECT NEW com.gl.ceir.config.model.SystemConfigListDb(a.tag, a.description, a.displayName) FROM SystemConfigListDb a group by a.tag, a.description, a.displayName")
	// @Query("SELECT NEW com.gl.ceir.config.model.SystemConfigListDb(a.tag, a.displayName) FROM SystemConfigListDb a group by a.tag, a.displayName")
	// @Query(value="SELECT tag, displayName FROM sys_param_list  group by tag, displayName",nativeQuery = true)
	// @Query("SELECT NEW com.gl.ceir.config.model.app.SystemConfigListDb(tag, displayName) FROM SystemConfigListDb  group by tag, displayName")
	 @Query("SELECT new SystemConfigListDb (a.tag, a.displayName) FROM SystemConfigListDb  a  group by a.tag, a.displayName")
	public List<SystemConfigListDb> findDistinctTagsWithDescription();
	
	public SystemConfigListDb getById(long id);
	
}
