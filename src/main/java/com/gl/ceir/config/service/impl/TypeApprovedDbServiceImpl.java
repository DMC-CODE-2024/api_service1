package com.gl.ceir.config.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.BrandRepoModel;
import com.gl.ceir.config.model.app.ModelRepoPojo;
import com.gl.ceir.config.model.app.TypeApprovedDb;
import com.gl.ceir.config.repository.app.BrandRepository;
import com.gl.ceir.config.repository.app.ModelRepository;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.util.DateUtil;

@Service
public class TypeApprovedDbServiceImpl {

	private static final Logger logger = LogManager.getLogger(TypeApprovedDbServiceImpl.class);

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	DateUtil dateUtil;

	@Autowired
	BrandRepository brandRepository;

	@Autowired
	ModelRepository modelRepository;

	public void setBrandInterp(TypeApprovedDb typeApprovedDb) {
		Optional<BrandRepoModel> brandNameOptional = brandRepository.findById(typeApprovedDb.getProductName());

		if(brandNameOptional.isPresent()) {
			BrandRepoModel brandRepoModel = brandNameOptional.get();
			logger.info("Brand name with id ["+ typeApprovedDb.getProductName() +"] is [" + brandRepoModel.getBrandName() + "]");
			typeApprovedDb.setProductNameInterp(brandRepoModel.getBrandName());
		}else {
			logger.warn("Brand with id ["+ typeApprovedDb.getProductName() +"] is not available.");
			typeApprovedDb.setProductNameInterp("");
		}

	}

	public void setModelInterp(TypeApprovedDb typeApprovedDb) {
		Optional<ModelRepoPojo> modelNameOptional = modelRepository.getById(typeApprovedDb.getModelNumber());

		if(modelNameOptional.isPresent()) {
			ModelRepoPojo modelRepoPojo = modelNameOptional.get();
			logger.info("Model name with id ["+ typeApprovedDb.getModelNumber() +"] is [" + modelRepoPojo.getModelName() + "]");
			typeApprovedDb.setModelNumberInterp(modelRepoPojo.getModelName());
		}else {
			logger.warn("Model with id ["+ typeApprovedDb.getModelNumber() +"] is not available.");
			typeApprovedDb.setModelNumberInterp("");
		}
	}

}
