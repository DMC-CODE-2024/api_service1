package com.gl.ceir.config.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.MessageConfigurationDb;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigListDb;
import com.gl.ceir.config.model.app.SystemConfigUserwiseDb;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.constants.Tags;
import com.gl.ceir.config.model.file.MessageMgtFileModel;
import com.gl.ceir.config.model.file.SystemMgtFileModel;
import com.gl.ceir.config.repository.app.MessageConfigurationDbRepository;
import com.gl.ceir.config.repository.app.SystemConfigListRepository;
import com.gl.ceir.config.repository.app.SystemConfigUserwiseRepository;
import com.gl.ceir.config.repository.app.SystemConfigurationDbRepository;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.systemValidation.impl.SystemValidation;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.gl.ceir.config.util.InterpSetter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class ConfigurationManagementServiceImpl {
	private static final Logger logger = LogManager.getLogger(
ConfigurationManagementServiceImpl.class);

	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;

	@Autowired
	MessageConfigurationDbRepository messageConfigurationDbRepository;

	

	@Autowired
	SystemConfigListRepository systemConfigListRepository;

	@Autowired
	SystemConfigUserwiseRepository systemConfigUserwiseRepository;

	

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	InterpSetter interpSetter;

	/*
	 * @Autowired ConfigurationManagementServiceImpl
	 * configurationManagementServiceImpl;
	 */

	@Autowired
	StateMgmtServiceImpl stateMgmtServiceImpl;

	@Autowired
	SystemValidation systemValidation;

	public List<SystemConfigurationDb> getAllInfo() {
		try {
			return systemConfigurationDbRepository.findAll();
		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public Page<SystemConfigurationDb> filterSystemConfiguration(FilterRequest filterRequest, Integer pageNo,
			Integer pageSize) {
		try {
			String orderColumn =null;
//			createdOn,taxPaidStatus,quantity,deviceQuantity,supplierName,consignmentStatus
			logger.info("column Name :: " + filterRequest.getColumnName());
			
			orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "createdOn"
					          : "Modified On".equalsIgnoreCase(filterRequest.getColumnName()) ? "modifiedOn"
					        		  : "Feature Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "featureName"
					        		     : "Description".equalsIgnoreCase(filterRequest.getColumnName()) ? "description"
					        				  : "Value".equalsIgnoreCase(filterRequest.getColumnName()) ? "value"
					        						  : "Type".equalsIgnoreCase(filterRequest.getColumnName()) ? "type"
					        								
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
			Page<SystemConfigurationDb> page = systemConfigurationDbRepository
					.findAll(buildSpecification_system(filterRequest).build(), pageable);

			for (SystemConfigurationDb systemConfigurationDb : page.getContent()) {
				systemConfigurationDb
						.setTypeInterp(interpSetter.setConfigInterp(Tags.CONFIG_TYPE, systemConfigurationDb.getType()));
			}

			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.SYSTEM_MANAGEMENT, SubFeatures.VIEW_ALL, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("SYSTEM_MANAGEMENT : successfully inserted in Audit trail.");
			return page;

		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public SystemConfigurationDb findByTag(SystemConfigurationDb systemConfigurationDb) {
		try {
			SystemConfigurationDb systemConfigurationDb2 = systemConfigurationDbRepository
					.getByTag(systemConfigurationDb.getTag());
			systemConfigurationDb2
					.setTypeInterp(interpSetter.setConfigInterp(Tags.CONFIG_TYPE, systemConfigurationDb2.getType()));
			return systemConfigurationDb2;
		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public SystemConfigurationDb findByTag(String tag) {
		try {
			return systemConfigurationDbRepository.getByTag(tag);
		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	@Transactional
	public GenricResponse updateSystemInfo(SystemConfigurationDb systemConfigurationDb) {
		try {

			logger.info("Everything is valid. Processed for Update");
				GenricResponse genricResponse = validateUpdateRequest(systemConfigurationDb.getTag(),
						systemConfigurationDb.getValue());
				if (genricResponse.getErrorCode() != 0) {
					return genricResponse;
				}

				SystemConfigurationDb systemConfigurationDb2 = systemConfigurationDbRepository
						.getByTag(systemConfigurationDb.getTag());
				logger.info("Persisted data " + systemConfigurationDb2);

				if (Objects.isNull(systemConfigurationDb2)) {
					return new GenricResponse(15, "This Id does not exist", "");
				}
				logger.info("ModifiedBy when Updating :" + systemConfigurationDb.getUserName());
				systemConfigurationDb2.setValue(systemConfigurationDb.getValue());
				systemConfigurationDb2.setDescription(systemConfigurationDb.getDescription());
				systemConfigurationDb2.setRemark(systemConfigurationDb.getRemark());
				systemConfigurationDb2.setModifiedBy(systemConfigurationDb.getUserName());
				systemConfigurationDbRepository.save(systemConfigurationDb2);

				return new GenricResponse(200, "System_configuration_update", "System configuration updated successfully",
						"");

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	public List<MessageConfigurationDb> getMessageConfigAllDetails() {

		try {

			return messageConfigurationDbRepository.findAll();

		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	public Page<MessageConfigurationDb> filterMessageConfiguration(FilterRequest filterRequest, Integer pageNo,
			Integer pageSize) {
		try {
			String orderColumn =null;
//			createdOn,taxPaidStatus,quantity,deviceQuantity,supplierName,consignmentStatus
			logger.info("column Name :: " + filterRequest.getColumnName());
			
			orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "createdOn"
					          : "Modified On".equalsIgnoreCase(filterRequest.getColumnName()) ? "modifiedOn"
					        		  : "Feature".equalsIgnoreCase(filterRequest.getColumnName()) ? "featureName"
					        				  : "Subject".equalsIgnoreCase(filterRequest.getColumnName()) ? "subject"
					        						  : "Description".equalsIgnoreCase(filterRequest.getColumnName()) ? "description"
					        				                    : "Value".equalsIgnoreCase(filterRequest.getColumnName()) ? "value"
					        						               : "Channel".equalsIgnoreCase(filterRequest.getColumnName()) ? "channel"
					                                                     : "modifiedOn";
			 
			Sort.Direction direction;
			logger.info("direction and column name:  "+SortDirection.getSortDirection(filterRequest.getSort())+"-----"+orderColumn);
			if("modifiedOn".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(filterRequest.getSort());
			}
			if("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			logger.info("final column :  "+orderColumn+"  direction--"+direction);
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			Page<MessageConfigurationDb> page = messageConfigurationDbRepository
					.findAll(buildSpecification(filterRequest).build(), pageable);
			logger.info("page info before loop start++++++++++++"+page.getContent().toString());				
			for (MessageConfigurationDb messageConfigurationDb : page.getContent()) {
				messageConfigurationDb.setChannelInterp(
						interpSetter.setConfigInterp(Tags.CHANNEL, messageConfigurationDb.getChannel()));
			}
			logger.info("page info after loop start--------------"+page.getContent().toString());
//audit trail entry
			auditTrailRepository.save(new AuditTrail(Long.valueOf(filterRequest.getUserId()),
					filterRequest.getUserName(), Long.valueOf(filterRequest.getUserTypeId()),
					filterRequest.getUserType(), Long.valueOf(filterRequest.getFeatureId()),
					Features.MESSAGE_MANAGEMENT, SubFeatures.VIEW_ALL, "", "NA", filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("MESSAGE_MANAGEMENT : successfully inserted in Audit trail.");

			return page;

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public MessageConfigurationDb getMessageConfigDetailsByTag(MessageConfigurationDb messageConfigurationDb) {
		try {

			MessageConfigurationDb messageConfigurationDb2 = messageConfigurationDbRepository
					.getByTag(messageConfigurationDb.getTag());
			messageConfigurationDb2
					.setChannelInterp(interpSetter.setConfigInterp(Tags.CHANNEL, messageConfigurationDb2.getChannel()));
			auditTrailRepository.save(new AuditTrail(Long.valueOf(messageConfigurationDb.getUserId()),
					messageConfigurationDb.getUserName(), Long.valueOf(messageConfigurationDb.getUserTypeId()),
					messageConfigurationDb.getUserType(), Long.valueOf(messageConfigurationDb.getFeatureId()),
					Features.MESSAGE_MANAGEMENT, SubFeatures.VIEW, "", "NA", messageConfigurationDb.getUserType(),messageConfigurationDb.getPublicIp(),messageConfigurationDb.getBrowser()));
			return messageConfigurationDb2;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	@Transactional
	public GenricResponse updateMessageInfo(MessageConfigurationDb messageConfigurationDb) {
		try {
			GenricResponse genricResponse = validateUpdateRequest(messageConfigurationDb.getTag(),
					messageConfigurationDb.getValue());
			if (genricResponse.getErrorCode() != 0) {
				return genricResponse;
			}

			MessageConfigurationDb mcd = messageConfigurationDbRepository.getByTag(messageConfigurationDb.getTag());

			if (Objects.isNull(mcd)) {
				return new GenricResponse(15, "This id does not exist", "");
			}

			mcd.setValue(messageConfigurationDb.getValue());
			mcd.setDescription(messageConfigurationDb.getDescription());
			mcd.setSubject(messageConfigurationDb.getSubject());
			mcd.setModifiedBy(messageConfigurationDb.getUserName());
			// mcd.setFeatureName(mcd.getFeatureName() == null ? "NA" :
			// mcd.getFeatureName());
			mcd.setActive(0);
			logger.info("Persisted message data " + messageConfigurationDb);
			messageConfigurationDbRepository.save(mcd);

			return new GenricResponse(0, "Message config info update sucessfully", "");

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	





	@Transactional
	public GenricResponse saveAudit(AuditTrail auditTrail) {
		try {

			auditTrailRepository.save(auditTrail);

			return new GenricResponse(0, "Audit trail save Sucess fully", "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	

	

	

	public List<SystemConfigListDb> getSystemConfigListByTag(String tag) {
		try {

			logger.debug("getSystemConfigListByTag : " + tag);

			return systemConfigListRepository.findByTag(tag,  Sort.by(Sort.Direction.ASC, "listOrder"));

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public List<SystemConfigListDb> getSystemConfigListByTagAndUserType(String tagId, int userTypeId) {
		try {

			logger.debug("getSystemConfigListByTag : " + tagId);

			List<SystemConfigListDb> systemConfigListDbs = getSystemConfigListDb(
					systemConfigListRepository.findByTag(tagId,  Sort.by(Sort.Direction.ASC, "listOrder")),
					systemConfigUserwiseRepository.findByTagIdAndUserTypeId(tagId, userTypeId));

			return addOtherToLastPostion(systemConfigListDbs);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public List<SystemConfigListDb> getSystemConfigListByTagAndFeatureId(String tagId, int featureId) {
		try {

			logger.debug("getSystemConfigListByTag : " + tagId);

			List<SystemConfigListDb> systemConfigListDbs = getSystemConfigListDb(
					systemConfigListRepository.findByTag(tagId,  Sort.by(Sort.Direction.ASC, "listOrder")),
					systemConfigUserwiseRepository.findByTagIdAndFeatureId(tagId, featureId));

			return addOtherToLastPostion(systemConfigListDbs);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	private List<SystemConfigListDb> getSystemConfigListDb(List<SystemConfigListDb> systemConfigListDbs,
			List<SystemConfigUserwiseDb> systemConfigUserwiseDbs) {

		List<SystemConfigListDb> systemConfigListDbResult = new LinkedList<>();

		for (SystemConfigListDb systemConfigListDb : systemConfigListDbs) {

			for (SystemConfigUserwiseDb systemConfigUserwiseDb : systemConfigUserwiseDbs) {

				if (Integer.valueOf(systemConfigListDb.getValue()) == systemConfigUserwiseDb.getValue()) {
					systemConfigListDbResult.add(systemConfigListDb);
					break;
				}
			}
		}

		return systemConfigListDbResult;
	}

	private GenricResponse validateUpdateRequest(String tag, String value) {
		if (Objects.isNull(tag)) {
			logger.info("Receiving tag as null.");
			return new GenricResponse(1, "Receiving tag as null.", "");
		}
		if (Objects.isNull(value) && !value.isEmpty()) {
			logger.info("Value of a tag can't be set to null or empty.");
			return new GenricResponse(2, "Value of a tag can't be set to null or empty.", "");
		}

		return new GenricResponse(0, "", "");
	}

	public SystemConfigurationDb saveSystemConfiguration(SystemConfigurationDb systemConfigurationDb) {
		try {
			return systemConfigurationDbRepository.save(systemConfigurationDb);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public MessageConfigurationDb saveMessageConfiguration(MessageConfigurationDb messageConfigurationDb) {
		try {
			return messageConfigurationDbRepository.save(messageConfigurationDb);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	
	public List<SystemConfigListDb> addOtherToLastPostion(List<SystemConfigListDb> systemConfigListDbs) {
		// Others list
		List<SystemConfigListDb> others = systemConfigListDbs.stream()
				.filter(e -> "other".equalsIgnoreCase(e.getInterpretation())).collect(Collectors.toList());

		// Removed Other
		systemConfigListDbs = systemConfigListDbs.stream().filter(e -> !"other".equalsIgnoreCase(e.getInterpretation()))
				.collect(Collectors.toList());
		// Other list appended
		systemConfigListDbs.addAll(others);

		return systemConfigListDbs;
	}

	private GenericSpecificationBuilder<MessageConfigurationDb> buildSpecification(FilterRequest filterRequest) {
		GenericSpecificationBuilder<MessageConfigurationDb> sb = new GenericSpecificationBuilder<>(
				propertiesReader.dialect);

		if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));

		if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));
		
		if (Objects.nonNull(filterRequest.getTag()))
			sb.with(new SearchCriteria("tag", filterRequest.getTag(), SearchOperation.EQUALITY_CASE_INSENSITIVE,
					Datatype.STRING));

		if (Objects.nonNull(filterRequest.getChannel()))
			sb.with(new SearchCriteria("channel", filterRequest.getChannel(), SearchOperation.EQUALITY,
					Datatype.STRING));

		
		
		if (Objects.nonNull(filterRequest.getFeatureName()))
			sb.with(new SearchCriteria("featureName", filterRequest.getFeatureName(), SearchOperation.EQUALITY,
					Datatype.STRING));

		if (Objects.nonNull(filterRequest.getSubject()) && !filterRequest.getSubject().isEmpty() )
			sb.with(new SearchCriteria("subject", filterRequest.getSubject(), SearchOperation.LIKE,Datatype.STRING));	
		
		if (Objects.nonNull(filterRequest.getDescription()) && !filterRequest.getDescription().isEmpty() )
			sb.with(new SearchCriteria("description", filterRequest.getDescription(), SearchOperation.LIKE,Datatype.STRING));
		
		if (Objects.nonNull(filterRequest.getValue()) && !filterRequest.getValue().isEmpty() )
			sb.with(new SearchCriteria("value", filterRequest.getValue(), SearchOperation.LIKE,
					Datatype.STRING));
		
		/*
		 * if (Objects.nonNull(filterRequest.getFeatureName())) sb.with(new
		 * SearchCriteria("subject", filterRequest.getSubject(), SearchOperation.LIKE,
		 * Datatype.STRING));
		 */
		if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
			sb.orSearch(
					new SearchCriteria("tag", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			sb.orSearch(new SearchCriteria("description", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			sb.orSearch(new SearchCriteria("value", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			sb.orSearch(new SearchCriteria("featureName", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
			sb.orSearch(new SearchCriteria("subject", filterRequest.getSearchString(), SearchOperation.LIKE,
					Datatype.STRING));
		}
		return sb;
	}

	private GenericSpecificationBuilder<SystemConfigurationDb> buildSpecification_system(FilterRequest filterRequest) {

		GenericSpecificationBuilder<SystemConfigurationDb> sb = new GenericSpecificationBuilder<SystemConfigurationDb>(
				propertiesReader.dialect);
		
		if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));

		if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			sb.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));

		
		if (Objects.nonNull(filterRequest.getTag()))
			sb.with(new SearchCriteria("tag", filterRequest.getTag(), SearchOperation.EQUALITY_CASE_INSENSITIVE,
					Datatype.STRING));

		if (Objects.nonNull(filterRequest.getType()))
			sb.with(new SearchCriteria("type", filterRequest.getType(), SearchOperation.EQUALITY, Datatype.STRING));

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

	public void setInterp_system(SystemConfigurationDb systemConfigurationDb) {
		if (Objects.nonNull(systemConfigurationDb.getType())) {
			systemConfigurationDb
					.setTypeInterp(interpSetter.setConfigInterp(Tags.CONFIG_TYPE, systemConfigurationDb.getType()));
		}
	}

	public void setInterp(MessageConfigurationDb messageConfigurationDb) {
		if (Objects.nonNull(messageConfigurationDb.getChannel())) {
			messageConfigurationDb
					.setChannelInterp(interpSetter.setConfigInterp(Tags.CHANNEL, messageConfigurationDb.getChannel()));
		}
	}

	private List<MessageConfigurationDb> getAll(FilterRequest filterRequest) {
		try {
			List<MessageConfigurationDb> list = messageConfigurationDbRepository
					.findAll(buildSpecification(filterRequest).build(),  Sort.by(Sort.Direction.DESC, "modifiedOn"));
			logger.info("consignmentMgmts " + list);

			for (MessageConfigurationDb messageConfigurationDb : list) {
				setInterp(messageConfigurationDb);
			}

			logger.info("MessageConfigurationDb list : " + list);
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	private Page<SystemConfigurationDb> getAll_system(FilterRequest filterRequest) {
		try {
			SystemConfigurationDb filepath = this.findByTag("file.max-file-record");
			 Pageable pageable = PageRequest.of(0, Integer.valueOf(filepath.getValue()),  Sort.by(Sort.Direction.DESC, "modifiedOn"));
				
			Page<SystemConfigurationDb> page = systemConfigurationDbRepository
					.findAll(buildSpecification_system(filterRequest).build(), pageable);
			for (SystemConfigurationDb systemConfigurationDb : page.getContent()) {
				systemConfigurationDb
						.setTypeInterp(interpSetter.setConfigInterp(Tags.CONFIG_TYPE, systemConfigurationDb.getType()));
			}
			return page;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	/***************************************
	 * Export File for System Mgt ********************************
	 *************************************************************************************************/

	public FileDetails exportFile_System(FilterRequest filterRequest) {
		String fileName = null;
		Writer writer = null;

		SystemMgtFileModel fileModel = null;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		SystemConfigurationDb filepath = this.findByTag(ConfigTags.file_download_dir);
		logger.info("CONFIG : file_systemMgt_download_dir [" + filepath + "]");
		SystemConfigurationDb link = this.findByTag(ConfigTags.file_download_link);
		logger.info("CONFIG : file_systemMgt_download_link [" + link + "]");

		StatefulBeanToCsvBuilder<SystemMgtFileModel> builder = null;
		StatefulBeanToCsv<SystemMgtFileModel> csvWriter = null;
		List<SystemMgtFileModel> fileRecords = null;
		CustomMappingStrategy<SystemMgtFileModel> mappingStrategy = new CustomMappingStrategy<>();

		try {
			Page<SystemConfigurationDb> systemConfigurationDBList = getAll_system(filterRequest);
			fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_SystemMgt.csv";
			writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
			mappingStrategy.setType(SystemMgtFileModel.class);

			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			
			if (!systemConfigurationDBList.isEmpty()) {
				fileRecords = new ArrayList<>();
				for (SystemConfigurationDb systemConfigurationDB : systemConfigurationDBList) {

					LocalDateTime creation = systemConfigurationDB.getCreatedOn() == null ? LocalDateTime.now()
							: systemConfigurationDB.getCreatedOn();
					LocalDateTime modified = systemConfigurationDB.getModifiedOn() == null ? LocalDateTime.now()
							: systemConfigurationDB.getModifiedOn();

					fileModel = new SystemMgtFileModel(creation.format(dtf), modified.format(dtf),
							systemConfigurationDB.getDescription() == null ? "NA"
									: systemConfigurationDB.getDescription(),
							systemConfigurationDB.getValue() == null ? "NA" : systemConfigurationDB.getValue(),
							systemConfigurationDB.getTypeInterp(),
							systemConfigurationDB.getFeatureName());
					fileRecords.add(fileModel);
				}

				csvWriter.write(fileRecords);
			} else {
				csvWriter.write(new SystemMgtFileModel());
			}
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.SYSTEM_MANAGEMENT, SubFeatures.EXPORT, "", "NA",
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

	/***************************************
	 * Export File for Message Mgt ******************************** Ranjeet
	 ****************************/

	public FileDetails exportFile_Message(FilterRequest filterRequest) {

		String fileName = null;

		Writer writer = null;

		MessageMgtFileModel fileModel = null;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		SystemConfigurationDb filepath = this.findByTag(ConfigTags.file_download_dir);
		logger.info("CONFIG : file_messageMgt_download_dir [" + filepath + "]");
		SystemConfigurationDb link = this.findByTag(ConfigTags.file_download_link);
		logger.info("CONFIG : file_messageMgt_download_link [" + link + "]");

		StatefulBeanToCsvBuilder<MessageMgtFileModel> builder = null;
		StatefulBeanToCsv<MessageMgtFileModel> csvWriter = null;
		List<MessageMgtFileModel> fileRecords = null;
		CustomMappingStrategy<MessageMgtFileModel> mappingStrategy = new CustomMappingStrategy<>();

		try {
			List<MessageConfigurationDb> messageConfigurationDbList = getAll(filterRequest);
			fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_MessageMgt.csv";
			writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
			mappingStrategy.setType(MessageMgtFileModel.class);

			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();

			// csvWriter =
			// builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			/*
			 * if( !messageConfigurationDbList.isEmpty() ) { fileRecords = new
			 * ArrayList<>(); for( MessageConfigurationDb messageConfigurationDb :
			 * messageConfigurationDbList ) {
			 * 
			 * 
			 * LocalDateTime creation = messageConfigurationDb.getCreatedOn() == null ?
			 * LocalDateTime.now() : messageConfigurationDb.getCreatedOn(); LocalDateTime
			 * modified = messageConfigurationDb.getModifiedOn() == null ?
			 * LocalDateTime.now() : messageConfigurationDb.getModifiedOn();
			 * 
			 * fileModel = new MessageMgtFileModel(creation.format(dtf),
			 * modified.format(dtf), messageConfigurationDb.getDescription() == null ? "NA"
			 * : messageConfigurationDb.getDescription(), messageConfigurationDb.getValue()
			 * == null ? "NA" : messageConfigurationDb.getValue(),
			 * messageConfigurationDb.getChannelInterp() == null ? "NA" :
			 * messageConfigurationDb.getChannelInterp()); fileRecords.add(fileModel); }
			 * 
			 * 
			 * 
			 * csvWriter.write(fileRecords); }
			 */
			
			auditTrailRepository.save(new AuditTrail(Long.valueOf(filterRequest.getUserId()),
					filterRequest.getUserName(), Long.valueOf(filterRequest.getUserTypeId()),
					filterRequest.getUserType(), Long.valueOf(filterRequest.getFeatureId()),
					Features.MESSAGE_MANAGEMENT, SubFeatures.EXPORT, "", "NA", filterRequest.getUserType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			if (!messageConfigurationDbList.isEmpty()) {
				fileRecords = new ArrayList<>();
				for (MessageConfigurationDb messageConfigurationDb : messageConfigurationDbList) {
					fileModel = new MessageMgtFileModel();
					LocalDateTime creation = messageConfigurationDb.getCreatedOn() == null ? LocalDateTime.now()
							: messageConfigurationDb.getCreatedOn();
					LocalDateTime modified = messageConfigurationDb.getModifiedOn() == null ? LocalDateTime.now()
							: messageConfigurationDb.getModifiedOn();
					fileModel.setCreatedOn(creation.format(dtf));
					fileModel.setModifiedOn(modified.format(dtf));
					fileModel.setFeatureName(messageConfigurationDb.getFeatureName() == null ? "NA"
							: messageConfigurationDb.getFeatureName());
					fileModel.setSubject(messageConfigurationDb.getSubject());

					fileModel.setDescription(messageConfigurationDb.getDescription() == null ? "NA"
							: messageConfigurationDb.getDescription());
					fileModel.setValue(
							messageConfigurationDb.getValue() == null ? "NA" : messageConfigurationDb.getValue());
					fileModel.setUserType(messageConfigurationDb.getChannelInterp());

					/* fileModel.setUserType(messageConfigurationDb.getChannelInterp()); */
					logger.debug(fileModel);
					fileRecords.add(fileModel);
				}

				csvWriter.write(fileRecords);
			}

			else {
				csvWriter.write(new MessageMgtFileModel());
			}

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

	
}
