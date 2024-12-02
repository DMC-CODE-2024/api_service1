package com.gl.ceir.config.repository.app;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.BrandRepoModel;

public interface BrandRepository  extends JpaRepository<BrandRepoModel, Long>, JpaSpecificationExecutor<BrandRepoModel>  {

	public Optional<BrandRepoModel> findById(Long id);
}
