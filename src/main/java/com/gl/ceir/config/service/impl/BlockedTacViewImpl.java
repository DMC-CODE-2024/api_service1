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

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.BlockedTacModel;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.file.AllowedTacFile;

import com.gl.ceir.config.repository.app.BlockedTacRepo;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class BlockedTacViewImpl {
	
	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	BlockedTacRepo blockedTacRepo;
	
	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;
	
	private static final Logger logger = LogManager.getLogger(CheckIMEIParamImpl.class);

	public Page<BlockedTacModel> filterBlockListIMEI(FilterRequest filterRequest, Integer pageNo,
			Integer pageSize) {
		try {
			String orderColumn =null;
//			createdOn,taxPaidStatus,quantity,deviceQuantity,supplierName,consignmentStatus
			logger.info("column Name :: " + filterRequest.getColumnName());
			
			orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "creationDate"
					         : "TAC".equalsIgnoreCase(filterRequest.getColumnName()) ? "tac"
					        		     : "creationDate";
			
			Sort.Direction direction;
			if("creationDate".equalsIgnoreCase(orderColumn)) {
				direction=Sort.Direction.DESC;
			}
			else {
				direction= SortDirection.getSortDirection(filterRequest.getSort());
			}
			if("creationDate".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
				direction=Sort.Direction.ASC;
			}
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));
			logger.info("column Name :: " + filterRequest.getColumnName()+"---system.getSort() : "+filterRequest.getSort());
			
			
			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			Page<BlockedTacModel> page = blockedTacRepo.findAll(buildSpecification_system(filterRequest).build(), pageable);

			
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.Blocked_TAC, SubFeatures.VIEW_ALL, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("Blocked TAC : successfully inserted in Audit trail.");
			return page;

		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	private GenericSpecificationBuilder<BlockedTacModel> buildSpecification_system(FilterRequest filterRequest) {

		GenericSpecificationBuilder<BlockedTacModel> sb = new GenericSpecificationBuilder<BlockedTacModel>(
				propertiesReader.dialect);
		
		if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			sb.with(new SearchCriteria("creationDate", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
					Datatype.DATE));

		if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			sb.with(new SearchCriteria("creationDate", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
					Datatype.DATE));
		
		if (Objects.nonNull(filterRequest.getTac()) && !filterRequest.getTac().isEmpty())
			sb.with(new SearchCriteria("tac", filterRequest.getTac(), SearchOperation.LIKE,
					Datatype.STRING));

		
		if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
			sb.orSearch(
					new SearchCriteria("tac", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
			
		}
		return sb;
	}
	
	public FileDetails exportFile_System(FilterRequest filterRequest) {
		String fileName = null;
		Writer writer = null;

		AllowedTacFile fileModel = null;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
		logger.info("CONFIG : file_systemMgt_download_dir [" + filepath + "]");
		SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
		logger.info("CONFIG : file_systemMgt_download_link [" + link + "]");

		StatefulBeanToCsvBuilder<AllowedTacFile> builder = null;
		StatefulBeanToCsv<AllowedTacFile> csvWriter = null;
		List<AllowedTacFile> fileRecords = null;
		CustomMappingStrategy<AllowedTacFile> mappingStrategy = new CustomMappingStrategy<>();

		try {
			List<BlockedTacModel> checkIMEIResponseParamExport = getAll_blockedIMEI(filterRequest);
			fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_BlockedTAC.csv";
			writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
			mappingStrategy.setType(AllowedTacFile.class);

			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
					.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
			
			if (!checkIMEIResponseParamExport.isEmpty()) {
				fileRecords = new ArrayList<>();
				for (BlockedTacModel systemConfigurationDB : checkIMEIResponseParamExport) {

					LocalDateTime creation = systemConfigurationDB.getCreationDate() == null ? LocalDateTime.now()
							: systemConfigurationDB.getCreationDate();
				

					fileModel = new AllowedTacFile(creation.format(dtf),
							systemConfigurationDB.getTac() == null ? "NA"
									: systemConfigurationDB.getTac());
							
					fileRecords.add(fileModel);
				}

				csvWriter.write(fileRecords);
			} else {
				csvWriter.write(new AllowedTacFile());
			}
			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.Blocked_TAC, SubFeatures.EXPORT, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			logger.info("Blocked tac : successfully inserted in Audit trail.");
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
	
	private List<BlockedTacModel> getAll_blockedIMEI(FilterRequest filterRequest) {
		try {
			List<BlockedTacModel> list = blockedTacRepo.findAll(
					buildSpecification_system(filterRequest).build(), Sort.by(Sort.Direction.DESC, "creationDate"));
			
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	public BlockedTacModel findByTac(BlockedTacModel blockedTacModel) {
		try {
			
			BlockedTacModel tacResponseParam = blockedTacRepo.getBySno(blockedTacModel.getSno());
			auditTrailRepository.save(new AuditTrail(blockedTacModel.getUserId(), blockedTacModel.getUserName(),
					Long.valueOf(blockedTacModel.getUserTypeId()), "system",
					Long.valueOf(blockedTacModel.getFeatureId()), Features.Blocked_TAC, SubFeatures.VIEW, "", "NA",
					blockedTacModel.getRoleType(),blockedTacModel.getPublicIp(),blockedTacModel.getBrowser()));
			logger.info("blocked tac view by tac : successfully inserted in Audit trail.");
		return tacResponseParam;
		} catch (Exception e) {
			logger.info("Exception found=" + e.getMessage());
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
}
