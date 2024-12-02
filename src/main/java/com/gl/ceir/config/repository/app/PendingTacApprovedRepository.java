package com.gl.ceir.config.repository.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.gl.ceir.config.model.app.PendingTacApprovedDb;

public interface PendingTacApprovedRepository extends CrudRepository<PendingTacApprovedDb, Long>, 
JpaRepository<PendingTacApprovedDb, Long>, JpaSpecificationExecutor<PendingTacApprovedDb>{
	
	public PendingTacApprovedDb getById(long id);
	
	public List<PendingTacApprovedDb> getByTxnId(String txnId);
	
	public long deleteByTxnId(String txnId);
	
	public long deleteByTacAndUserId(String tac, Long userId);
	
}
