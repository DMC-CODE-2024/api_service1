package com.gl.ceir.config.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigListDb;
import com.gl.ceir.config.model.app.TagsMapping;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.repository.app.SystemConfigListRepository;
import com.gl.ceir.config.repository.app.TagsMappingRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.Utility;

@Service
public class TagsMappingServiceImpl {

	private static final Logger logger = LogManager.getLogger(TagsMappingServiceImpl.class);

	@Autowired
	private TagsMappingRepository tagsMappingRepository;
	
	@Autowired
	private SystemConfigListRepository systemConfigListRepository;

	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;

	@Autowired
	Utility utility;

	

	/*
	 * 
	 * @future_scope : must use a join between state_mgmt_db and states_interpretation_db
	 */
	public List<SystemConfigListDb> getByFilter(FilterRequest filterRequest) {
		try {
			List<SystemConfigListDb> systemConfigListDbResult = new ArrayList<>();

			List<TagsMapping> tagsMappings = tagsMappingRepository.findAll(buildSpecification(filterRequest).build());
			logger.info(tagsMappings);
			
			List<SystemConfigListDb> systemConfigListDbs = systemConfigListRepository
					.findByTag(filterRequest.getChildTag(),  Sort.by(Sort.Direction.ASC, "listOrder"));
			logger.debug(systemConfigListDbs);

			for(SystemConfigListDb systemConfigListDb : systemConfigListDbs) {

				for(TagsMapping tagsMapping : tagsMappings) {
					if(Integer.valueOf(systemConfigListDb.getValue()) == tagsMapping.getChildValue()) {
						systemConfigListDbResult.add(systemConfigListDb);
					}
				}
			}

			return configurationManagementServiceImpl.addOtherToLastPostion(systemConfigListDbResult);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	private GenericSpecificationBuilder<TagsMapping> buildSpecification(FilterRequest filterRequest){
		GenericSpecificationBuilder<TagsMapping> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);

		if(Objects.nonNull(filterRequest.getFeatureId()))
			cmsb.with(new SearchCriteria("featureId", filterRequest.getFeatureId(), SearchOperation.EQUALITY, Datatype.STRING));
		
		if(Objects.nonNull(filterRequest.getUserTypeId()))
			cmsb.with(new SearchCriteria("userTypeId", filterRequest.getUserTypeId() , SearchOperation.EQUALITY, Datatype.STRING));
		
		if(Objects.nonNull(filterRequest.getTag()))
			cmsb.with(new SearchCriteria("parentTag", filterRequest.getTag() , SearchOperation.EQUALITY, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getChildTag()))
			cmsb.with(new SearchCriteria("childTag", filterRequest.getChildTag(), SearchOperation.EQUALITY, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getParentValue()))
			cmsb.with(new SearchCriteria("parentValue", filterRequest.getParentValue(), SearchOperation.EQUALITY, Datatype.STRING));

		return cmsb;
	}

}
