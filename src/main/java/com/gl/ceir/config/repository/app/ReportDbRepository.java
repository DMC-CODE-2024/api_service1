package com.gl.ceir.config.repository.app;

import com.gl.ceir.config.model.app.ReportDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDbRepository extends JpaRepository< ReportDb, Long>{
	
	public ReportDb findByReportName( String reportName );
	
	//public ReportDb findByReportNameId( Long reportnameId );
	
	public ReportDb findByReportnameId( Long reportnameId );
	
	public List<ReportDb> findByStatus( Integer status );
	
	public List<ReportDb> findByViewFlagOrderByReportOrder( Integer viewflag );
	
	public List<ReportDb> findByViewFlagAndReportCategoryOrderByReportOrder( Integer viewflag, Integer reportCategory );
}
