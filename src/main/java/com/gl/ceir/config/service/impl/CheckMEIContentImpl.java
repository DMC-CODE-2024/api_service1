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
import com.gl.ceir.config.model.app.ChecKIMEIContent;
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
import com.gl.ceir.config.model.file.IMEIContentModel;
import com.gl.ceir.config.repository.app.CheckIMEIContentRepo;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class CheckMEIContentImpl {

	private static final Logger logger = LogManager.getLogger(CheckIMEIParamImpl.class);
	
	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;
	
	@Autowired
	CheckIMEIContentRepo checkIMEIContentRepo;
	
	public Page<ChecKIMEIContent> filterCheckIMEIConfiguration(FilterRequest filterRequest, Integer pageNo,
			Integer pageSize) {
		try {
			String orderColumn =null;
//			createdOn,taxPaidStatus,quantity,deviceQuantity,supplierName,consignmentStatus
			logger.info("column Name :: " + filterRequest.getColumnName());
			
			orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "createdOn"
					         : "Feature Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "featureName"
					        		     : "Label".equalsIgnoreCase(filterRequest.getColumnName()) ? "label"
					        				  : "English".equalsIgnoreCase(filterRequest.getColumnName()) ? "english_name"
					        						  : "Khmer".equalsIgnoreCase(filterRequest.getColumnName()) ? "khmer_name"
					        								
					 : "createdOn";
			
			Sort.Direction direction;
			if("createdOn".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(filterRequest.getSort());
			}
			if("createdOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));
			logger.info("column Name :: " + filterRequest.getColumnName()+"---system.getSort() : "+filterRequest.getSort());
			
			
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			Page<ChecKIMEIContent> page = checkIMEIContentRepo.findAll(buildSpecification_system(filterRequest).build(), pageable);

			
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.Check_IMEI_Content, SubFeatures.VIEW_ALL, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("Check IMEI Content : successfully inserted in Audit trail.");
			return page;

		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	private GenericSpecificationBuilder<ChecKIMEIContent> buildSpecification_system(FilterRequest filterRequest) {

		GenericSpecificationBuilder<ChecKIMEIContent> sb = new GenericSpecificationBuilder<ChecKIMEIContent>(
				propertiesReader.dialect);
		
		if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));

		if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));

		
		

		
		if (Objects.nonNull(filterRequest.getFeatureName()))
			sb.with(new SearchCriteria("featureName", filterRequest.getFeatureName(), SearchOperation.EQUALITY,
					Datatype.STRING));

		if (Objects.nonNull(filterRequest.getLabel()) && !filterRequest.getLabel().isEmpty() )
			sb.with(new SearchCriteria("label", filterRequest.getLabel(), SearchOperation.LIKE,Datatype.STRING));
		
		if (Objects.nonNull(filterRequest.getEnglishName()) && !filterRequest.getEnglishName().isEmpty() )
			sb.with(new SearchCriteria("englishName", filterRequest.getEnglishName(), SearchOperation.LIKE,
					Datatype.STRING));
		
		if (Objects.nonNull(filterRequest.getKhmerName()) && !filterRequest.getKhmerName().isEmpty() )
			sb.with(new SearchCriteria("khmerName", filterRequest.getKhmerName(), SearchOperation.LIKE,
					Datatype.STRING));
		
	
		
		if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
			sb.orSearch(
					new SearchCriteria("label", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			sb.orSearch(new SearchCriteria("englishName", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			sb.orSearch(new SearchCriteria("khmerName", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
		}
		return sb;
	}
	
	public FileDetails exportFile_System(FilterRequest filterRequest) {
		String fileName = null;
		Writer writer = null;

		IMEIContentModel fileModel = null;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
		logger.info("CONFIG : file_systemMgt_download_dir [" + filepath + "]");
		SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
		logger.info("CONFIG : file_systemMgt_download_link [" + link + "]");

		StatefulBeanToCsvBuilder<IMEIContentModel> builder = null;
		StatefulBeanToCsv<IMEIContentModel> csvWriter = null;
		List<IMEIContentModel> fileRecords = null;
		CustomMappingStrategy<IMEIContentModel> mappingStrategy = new CustomMappingStrategy<>();

		try {
			Page<ChecKIMEIContent> checkIMEIResponseParamExport = getAll_system(filterRequest);
			fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_CheckIMEIContext.csv";
			writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
			mappingStrategy.setType(IMEIContentModel.class);

			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			
			if (!checkIMEIResponseParamExport.isEmpty()) {
				fileRecords = new ArrayList<>();
				for (ChecKIMEIContent systemConfigurationDB : checkIMEIResponseParamExport) {

					LocalDateTime creation = systemConfigurationDB.getCreatedOn() == null ? LocalDateTime.now()
							: systemConfigurationDB.getCreatedOn();
				

					fileModel = new IMEIContentModel(creation.format(dtf),systemConfigurationDB.getFeatureName(),
							systemConfigurationDB.getLabel() == null ? "NA"
									: systemConfigurationDB.getLabel(),
							systemConfigurationDB.getEnglishName() == null ? "NA" : systemConfigurationDB.getEnglishName(),
									systemConfigurationDB.getKhmerName() == null ? "NA" : systemConfigurationDB.getKhmerName());
					fileRecords.add(fileModel);
				}

				csvWriter.write(fileRecords);
			} else {
				csvWriter.write(new IMEIContentModel());
			}
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.Check_IMEI_Content, SubFeatures.EXPORT, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("Check_IMEI_Content : successfully inserted in Audit trail.");
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
	
	private Page<ChecKIMEIContent> getAll_system(FilterRequest filterRequest) {
		try {
			 SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag("file.max-file-record");
			 Pageable pageable = PageRequest.of(0, Integer.valueOf(filepath.getValue()),  Sort.by(Sort.Direction.DESC, "createdOn"));
			Page<ChecKIMEIContent> list = checkIMEIContentRepo.findAll(
					buildSpecification_system(filterRequest).build(),pageable);
			
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	public ChecKIMEIContent findById(ChecKIMEIContent CheckIMEIContentRequest) {
		try {
			
			ChecKIMEIContent ChecKIMEIContentResponse = checkIMEIContentRepo.getById(CheckIMEIContentRequest.getId());
			auditTrailRepository.save(new AuditTrail(CheckIMEIContentRequest.getUserId(), CheckIMEIContentRequest.getUserName(),
					Long.valueOf(CheckIMEIContentRequest.getUserTypeId()), "SystemAdmin",
					Long.valueOf(CheckIMEIContentRequest.getFeatureId()), Features.Check_IMEI_Content, SubFeatures.VIEW, "", "NA",
					CheckIMEIContentRequest.getRoleType(),CheckIMEIContentRequest.getPublicIp(),CheckIMEIContentRequest.getBrowser()));
			logger.info("Check IMEI Content : successfully inserted in Audit trail.");
		return ChecKIMEIContentResponse;
		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	@Transactional
	public GenricResponse updateCheckIMEIInfo(ChecKIMEIContent checkIMEIResponseParam) {
		try {
		

			ChecKIMEIContent checkIMEIResponseParam2 = checkIMEIContentRepo.getById(checkIMEIResponseParam.getId());
				logger.info("Persisted data " + checkIMEIResponseParam2);
				if (Objects.isNull(checkIMEIResponseParam2)) {
					return new GenricResponse(15, "This Id does not exist", "");
				}
				
				checkIMEIResponseParam2.setLabel(checkIMEIResponseParam.getLabel());
				checkIMEIResponseParam2.setEnglishName(checkIMEIResponseParam.getEnglishName());
				checkIMEIResponseParam2.setKhmerName(checkIMEIResponseParam.getKhmerName());
				checkIMEIContentRepo.save(checkIMEIResponseParam2);
				
				return new GenricResponse(200, "check_imei_content_update", "Check IMEI content configuration updated successfully",
						"");

			
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}
}
