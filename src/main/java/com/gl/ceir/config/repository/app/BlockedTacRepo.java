package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.gl.ceir.config.model.app.BlockedTacModel;

public interface BlockedTacRepo extends JpaRepository<BlockedTacModel, Long>, JpaSpecificationExecutor<BlockedTacModel> {

	public BlockedTacModel getBySno(long  sno);
}
