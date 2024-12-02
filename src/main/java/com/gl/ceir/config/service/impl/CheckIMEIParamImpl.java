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

import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.file.IMEIMessageMgmtFile;
import com.gl.ceir.config.model.file.SystemMgtFileModel;
import com.gl.ceir.config.repository.app.CheckIMEIResponse;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
@Service
public class CheckIMEIParamImpl {

	private static final Logger logger = LogManager.getLogger(CheckIMEIParamImpl.class);
	
	@Autowired
	CheckIMEIResponse checkIMEIResponse;

	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;
	
	
	public Page<CheckIMEIResponseParam> filterCheckIMEIConfiguration(FilterRequest filterRequest, Integer pageNo,
			Integer pageSize) {
		try {
			String orderColumn =null;
//			createdOn,taxPaidStatus,quantity,deviceQuantity,supplierName,consignmentStatus
			logger.info("column Name :: " + filterRequest.getColumnName());
			
			orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "createdOn"
					          : "Modified On".equalsIgnoreCase(filterRequest.getColumnName()) ? "modifiedOn"
					        		  : "Feature Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "featureName"
					        		     : "Description".equalsIgnoreCase(filterRequest.getColumnName()) ? "description"
					        				  : "Tag".equalsIgnoreCase(filterRequest.getColumnName()) ? "tag"
					        						  : "Type".equalsIgnoreCase(filterRequest.getColumnName()) ? "type"
					        								  : "Tag".equalsIgnoreCase(filterRequest.getColumnName()) ? "tag"
					        										  : "Language".equalsIgnoreCase(filterRequest.getColumnName()) ? "language"
					        								
					 : "modifiedOn";
			
			Sort.Direction direction;
			if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(filterRequest.getSort());
			}
			if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));
			logger.info("column Name :: " + filterRequest.getColumnName()+"---system.getSort() : "+filterRequest.getSort());
			
			
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			Page<CheckIMEIResponseParam> page = checkIMEIResponse.findAll(buildSpecification_system(filterRequest).build(), pageable);

			
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.Check_IMEI_Messages, SubFeatures.VIEW_ALL, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("Check IMEI Messages : successfully inserted in Audit trail.");
			return page;

		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	private GenericSpecificationBuilder<CheckIMEIResponseParam> buildSpecification_system(FilterRequest filterRequest) {

		GenericSpecificationBuilder<CheckIMEIResponseParam> sb = new GenericSpecificationBuilder<CheckIMEIResponseParam>(
				propertiesReader.dialect);
		
		if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));

		if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));

		
		if (Objects.nonNull(filterRequest.getTag()))
			sb.with(new SearchCriteria("tag", filterRequest.getTag(), SearchOperation.LIKE,
					Datatype.STRING));

		if (Objects.nonNull(filterRequest.getType()))
			sb.with(new SearchCriteria("type", filterRequest.getType(), SearchOperation.EQUALITY, Datatype.STRING));
		if (Objects.nonNull(filterRequest.getLanguage()))
			sb.with(new SearchCriteria("language", filterRequest.getLanguage(), SearchOperation.EQUALITY, Datatype.STRING));

		if (Objects.nonNull(filterRequest.getFeatureName()))
			sb.with(new SearchCriteria("featureName", filterRequest.getFeatureName(), SearchOperation.EQUALITY,
					Datatype.STRING));

		if (Objects.nonNull(filterRequest.getDescription()) && !filterRequest.getDescription().isEmpty() )
			sb.with(new SearchCriteria("description", filterRequest.getDescription(), SearchOperation.LIKE,Datatype.STRING));
		
		if (Objects.nonNull(filterRequest.getValue()) && !filterRequest.getValue().isEmpty() )
			sb.with(new SearchCriteria("value", filterRequest.getValue(), SearchOperation.LIKE,
					Datatype.STRING));
		
	
		
		if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
			sb.orSearch(
					new SearchCriteria("tag", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			sb.orSearch(new SearchCriteria("description", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			sb.orSearch(new SearchCriteria("value", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
		}
		return sb;
	}
	
	public FileDetails exportFile_System(FilterRequest filterRequest) {
		String fileName = null;
		Writer writer = null;

		IMEIMessageMgmtFile fileModel = null;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
		logger.info("CONFIG : file_systemMgt_download_dir [" + filepath + "]");
		SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
		logger.info("CONFIG : file_systemMgt_download_link [" + link + "]");

		StatefulBeanToCsvBuilder<IMEIMessageMgmtFile> builder = null;
		StatefulBeanToCsv<IMEIMessageMgmtFile> csvWriter = null;
		List<IMEIMessageMgmtFile> fileRecords = null;
		CustomMappingStrategy<IMEIMessageMgmtFile> mappingStrategy = new CustomMappingStrategy<>();

		try {
			Page<CheckIMEIResponseParam> checkIMEIResponseParamExport = getAll_system(filterRequest);
			fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_CheckIMEIParam.csv";
			writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
			mappingStrategy.setType(IMEIMessageMgmtFile.class);

			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			
			if (!checkIMEIResponseParamExport.isEmpty()) {
				fileRecords = new ArrayList<>();
				for (CheckIMEIResponseParam systemConfigurationDB : checkIMEIResponseParamExport) {

					LocalDateTime creation = systemConfigurationDB.getCreatedOn() == null ? LocalDateTime.now()
							: systemConfigurationDB.getCreatedOn();
					LocalDateTime modified = systemConfigurationDB.getModifiedOn() == null ? LocalDateTime.now()
							: systemConfigurationDB.getModifiedOn();

					fileModel = new IMEIMessageMgmtFile(creation.format(dtf), modified.format(dtf),
							systemConfigurationDB.getFeatureName() == null ? "NA" : systemConfigurationDB.getFeatureName(),
							systemConfigurationDB.getTag() == null ? "NA" : systemConfigurationDB.getTag(),
							systemConfigurationDB.getDescription() == null ? "NA"
									: systemConfigurationDB.getDescription(),
							systemConfigurationDB.getValue() == null ? "NA" : systemConfigurationDB.getValue(),
									systemConfigurationDB.getLanguage() == null ? "NA" : systemConfigurationDB.getLanguage());
					fileRecords.add(fileModel);
				}

				csvWriter.write(fileRecords);
			} else {
				csvWriter.write(new IMEIMessageMgmtFile());
			}
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.Check_IMEI_Messages, SubFeatures.EXPORT, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("SYSTEM_MANAGEMENT : successfully inserted in Audit trail.");
			FileDetails fileDetails = new FileDetails(fileName, filepath.getValue(),
					link.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);
			logger.info(fileDetails);
			return fileDetails;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		} finally {
			try {

				if (writer != null)
					writer.close();
			} catch (IOException e) {
			}
		}

	}
	
	private Page<CheckIMEIResponseParam> getAll_system(FilterRequest filterRequest) {
		try {
			 SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag("file.max-file-record");
			 Pageable pageable = PageRequest.of(0, Integer.valueOf(filepath.getValue()),  Sort.by(Sort.Direction.DESC, "modifiedOn"));
			Page<CheckIMEIResponseParam> list = checkIMEIResponse.findAll(
					buildSpecification_system(filterRequest).build(),pageable);
			
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	public CheckIMEIResponseParam findById(CheckIMEIResponseParam CheckIMEIResponseParamRequest) {
		try {
			long id=Long.parseLong(CheckIMEIResponseParamRequest.getTag());
			CheckIMEIResponseParam checkIMEIResponseParam = checkIMEIResponse.getById(id);
			auditTrailRepository.save(new AuditTrail(CheckIMEIResponseParamRequest.getUserId(), CheckIMEIResponseParamRequest.getUserName(),
					Long.valueOf(CheckIMEIResponseParamRequest.getUserTypeId()), CheckIMEIResponseParamRequest.getUserType(),
					Long.valueOf(CheckIMEIResponseParamRequest.getFeatureId()), Features.Check_IMEI_Messages, SubFeatures.VIEW, "", "NA",
					CheckIMEIResponseParamRequest.getRoleType(),CheckIMEIResponseParamRequest.getPublicIp(),CheckIMEIResponseParamRequest.getBrowser()));
			logger.info("Check IMEI Messages : successfully inserted in Audit trail.");
		return checkIMEIResponseParam;
		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	@Transactional
	public GenricResponse updateCheckIMEIInfo(CheckIMEIResponseParam checkIMEIResponseParam) {
		try {
		

			CheckIMEIResponseParam checkIMEIResponseParam2 = checkIMEIResponse
						.getById(checkIMEIResponseParam.getId());
				logger.info("Persisted data " + checkIMEIResponseParam2);

				if (Objects.isNull(checkIMEIResponseParam2)) {
					return new GenricResponse(15, "This Id does not exist", "");
				}
				logger.info("ModifiedBy when Updating :" + checkIMEIResponseParam2.getUserName());
				checkIMEIResponseParam2.setValue(checkIMEIResponseParam.getValue());
				checkIMEIResponseParam2.setDescription(checkIMEIResponseParam.getDescription());
				checkIMEIResponseParam2.setRemarks(checkIMEIResponseParam.getRemarks());
				checkIMEIResponseParam2.setModifiedBy(checkIMEIResponseParam.getUserName());
				checkIMEIResponse.save(checkIMEIResponseParam2);

				return new GenricResponse(200, "check_imei_param_update", "Check IMEI param configuration updated successfully",
						"");

			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}
}
