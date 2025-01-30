package com.gl.ceir.config.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.RuleEngine;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.file.RuleEngineFileModel;
import com.gl.ceir.config.model.file.SystemMgtFileModel;
import com.gl.ceir.config.repository.app.RuleEngineRepository;
import com.gl.ceir.config.repository.app.SystemConfigurationDbRepository;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.gl.ceir.config.util.InterpSetter;
import com.gl.ceir.config.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.util.Optional;

@Service
public class RuleEngineServiceImpl {

	private static final Logger logger = LogManager.getLogger(RuleEngineServiceImpl.class);

	@Autowired
	RuleEngineRepository ruleEngineRepository;
	
	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	Utility utility;

	

	@Autowired
	InterpSetter interpSetter;
	
	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;

	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;
	
	public Optional<RuleEngine> findById(long id){
		try {
			return  ruleEngineRepository.findById(id);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public GenricResponse updateById(RuleEngine ruleEngine){
		try {
			RuleEngine ruleEngineOld =  ruleEngineRepository.getById(ruleEngine.getId());
			logger.info("UserName while Updating Rule Mapping" +ruleEngine.getUserName());
			ruleEngine.setId(ruleEngineOld.getId());
			ruleEngine.setModifiedBy(ruleEngine.getUserName());
			ruleEngineRepository.save(ruleEngine);
			
			return new GenricResponse(0, "Rule list updated successfully", "");
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	public Page<RuleEngine> filterRuleEngine(FilterRequest filterRequest, Integer pageNo, 
			Integer pageSize,String operation) {
			
		String orderColumn;
		
		if(operation.equals("Export")) {
			orderColumn = "modifiedOn";
			
		}else {
			orderColumn = "Created On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "createdOn"
				: "Modified On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "modifiedOn"
					: "Name".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "name"
						:"Description".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "description" :
							"Status".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "state"
						: "modifiedOn";
		}
		Sort.Direction direction;
		direction= SortDirection.getSortDirection(filterRequest.getOrder() == null ? "desc" : filterRequest.getOrder());
		logger.info("orderColumn is : "+orderColumn+ " & direction is : "+direction);
		
		try {
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));

			Page<RuleEngine> page = ruleEngineRepository.findAll( buildSpecification(filterRequest).build(), pageable );
			String operationType= "view".equalsIgnoreCase(operation) ? SubFeatures.VIEW_ALL : SubFeatures.EXPORT;
			
			auditTrailRepository.save( new AuditTrail(Long.valueOf(filterRequest.getUserId()),
					  filterRequest.getUserName(), Long.valueOf(filterRequest.getUserTypeId()),
					   "SystemAdmin", Long.valueOf(filterRequest.getFeatureId()),
					  Features.RULE_LIST, operationType, "","NA",
					  filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
					
			
			return page;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}
	
	
	
	public List<RuleEngine> allRuleNames() {

		try {
			
			return ruleEngineRepository.findAll();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	private GenericSpecificationBuilder<RuleEngine> buildSpecification(FilterRequest filterRequest){
		//ranjeet

		GenericSpecificationBuilder<RuleEngine> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);
		if(Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			cmsb.with(new SearchCriteria("createdOn", filterRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));

		if(Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			cmsb.with(new SearchCriteria("createdOn", filterRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));
		
		/*
		 * if(Objects.nonNull(filterRequest.getState())) cmsb.with(new
		 * SearchCriteria("state", filterRequest.getState(), SearchOperation.EQUALITY,
		 * Datatype.STRING));
		 */
		
		if(filterRequest.getState()==null) {
			logger.info("inside if state : " +filterRequest.getState());
		}else{
			logger.info("state Recieved =" +filterRequest.getState());
			cmsb.with(new SearchCriteria("state",filterRequest.getState(), SearchOperation.EQUALITY, Datatype.STRING));
		}
		
		/*
		 * if(Objects.nonNull(filterRequest.getDescription())) cmsb.with(new
		 * SearchCriteria("description", filterRequest.getDescription(),
		 * SearchOperation.EQUALITY, Datatype.STRING));
		 */
		if(filterRequest.getDescription()==null) {
			logger.info("inside if description: " +filterRequest.getDescription());
		}else{
			logger.info("description Recieved =" +filterRequest.getDescription());
			cmsb.with(new SearchCriteria("description",filterRequest.getDescription(), SearchOperation.LIKE, Datatype.STRING));
		}
		/*
		 * if(Objects.nonNull(filterRequest.getName())) cmsb.with(new
		 * SearchCriteria("name", filterRequest.getName(), SearchOperation.EQUALITY,
		 * Datatype.STRING));
		 */
		
		if(filterRequest.getName()==null) {
			logger.info("inside if Name : " +filterRequest.getName());
		}else{
			logger.info("name Recieved =" +filterRequest.getName());
			cmsb.with(new SearchCriteria("name",filterRequest.getName(), SearchOperation.LIKE, Datatype.STRING));
		}
		
		 if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
			 
		     cmsb.orSearch(new SearchCriteria("name", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			 cmsb.orSearch(new SearchCriteria("state", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			 cmsb.orSearch(new SearchCriteria("description", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			 
	

		 }
		

		return cmsb;
	
		
		/*
		GenericSpecificationBuilder<RuleEngine> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);
		
		if(Objects.nonNull(filterRequest.getState()))
			cmsb.with(new SearchCriteria("state", filterRequest.getState(), SearchOperation.EQUALITY, Datatype.STRING));
		
		 if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){
			 
		     cmsb.orSearch(new SearchCriteria("name", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			 cmsb.orSearch(new SearchCriteria("state", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			 cmsb.orSearch(new SearchCriteria("description", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			 
	

		 }
		

		return cmsb;
	*/}

	private void setInterp(RuleEngine ruleEngine) {
		/*if(Objects.nonNull(consignmentMgmt.getExpectedArrivalPort()))
			consignmentMgmt.setExpectedArrivalPortInterp(interpSetter.setConfigInterp(Tags.CUSTOMS_PORT, consignmentMgmt.getExpectedArrivalPort()));
		 */
	}

	public FileDetails getFile(FilterRequest filter) {
		logger.info("inside rule engine ");
		logger.info("filter data:  "+filter);
		String fileName = null;
		Writer writer   = null;
		RuleEngineFileModel uPFm = null;
		SystemConfigurationDb dowlonadDir=systemConfigurationDbRepository.getByTag("file.download-dir");
		SystemConfigurationDb dowlonadLink=systemConfigurationDbRepository.getByTag("file.download-link");
		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		Integer pageNo = 0;
		Integer pageSize = Integer.valueOf(systemConfigurationDbRepository.getByTag("file.max-file-record").getValue());
		String filePath  = dowlonadDir.getValue();
		StatefulBeanToCsvBuilder<RuleEngineFileModel> builder = null;
		StatefulBeanToCsv<RuleEngineFileModel> csvWriter      = null;
		List<RuleEngineFileModel> fileRecords       = null;
		MappingStrategy<RuleEngineFileModel> mappingStrategy = new CustomMappingStrategy<>();	
		
		try {
			
			mappingStrategy.setType(RuleEngineFileModel.class);
			List<RuleEngine> list = filterRuleEngine(filter, pageNo, pageSize,"Export").getContent();
			
			if( list.size()> 0 ) {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_RuleEngine.csv";
			}else {
				fileName = LocalDateTime.now().format(dtf).replace(" ", "_")+"_RuleEngine.csv";
			}
			logger.info(" file path plus file name: "+Paths.get(filePath+fileName));
			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			mappingStrategy.setType(RuleEngineFileModel.class);
//			
			builder = new StatefulBeanToCsvBuilder<>(writer);
			//csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			if( list.size() > 0 ) {
				//List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
				fileRecords = new ArrayList<RuleEngineFileModel>(); 
				for( RuleEngine ruleEngine : list ) {
					uPFm = new RuleEngineFileModel();
					uPFm.setCreatedOn(ruleEngine.getCreatedOn().format(dtf));
					uPFm.setModifiedOn(ruleEngine.getModifiedOn().format(dtf));
					uPFm.setState(ruleEngine.getState());
					uPFm.setName(ruleEngine.getName());
					uPFm.setDescription(ruleEngine.getDescription());
					
					fileRecords.add(uPFm);
				}
				csvWriter.write(fileRecords);
			}
			logger.info("fileName::"+fileName);
			logger.info("filePath::::"+filePath);
			logger.info("link:::"+dowlonadLink.getValue());
			return new FileDetails(fileName, filePath,dowlonadLink.getValue().replace("$LOCAL_IP",propertiesReader.localIp)+fileName ); 
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}finally {
			try {

				if( writer != null )
					writer.close();
			} catch (IOException e) {}
		}

	}
}