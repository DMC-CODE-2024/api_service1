package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gl.ceir.config.model.app.BlacklistDbHistory;

public interface BlackListTrackDetailsRepository extends JpaRepository<BlacklistDbHistory, Long> {


	public BlacklistDbHistory save(BlacklistDbHistory blackListTrackDetails);


}
