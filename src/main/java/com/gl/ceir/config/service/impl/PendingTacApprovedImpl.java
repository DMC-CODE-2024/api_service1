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
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.PendingTacApprovedDb;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.GenericMessageTags;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.file.PendingTacApprovedFileModel;
import com.gl.ceir.config.repository.app.PendingTacApprovedRepository;
import com.gl.ceir.config.repository.app.UserRepository;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.gl.ceir.config.util.InterpSetter;
import com.gl.ceir.config.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class PendingTacApprovedImpl {

	private static final Logger logger = LogManager.getLogger(PendingTacApprovedImpl.class);

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	Utility utility;

	@Autowired
	InterpSetter interpSetter;

	@Autowired
	PendingTacApprovedRepository pendingTacApprovedRepository;

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;



	public GenricResponse saveSystemConfigList(PendingTacApprovedDb pendingTacApprovedDb){
		try {
			if(Objects.isNull(pendingTacApprovedDb.getTac())) {
				return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(), 
						GenericMessageTags.NULL_REQ.getMessage(), null);
			}

			pendingTacApprovedRepository.save(pendingTacApprovedDb);
			return new GenricResponse(0);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public GenricResponse findById(FilterRequest filterRequest){
		try {
			if(Objects.isNull(filterRequest.getId())) {
				return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(), 
						GenericMessageTags.NULL_REQ.getMessage(), null);
			}

			PendingTacApprovedDb pendingTacApprovedDb = pendingTacApprovedRepository.getById(filterRequest.getId());
			return new GenricResponse(0, "SUCCESS", "SUCCESS", pendingTacApprovedDb);

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public GenricResponse findByTxnId(FilterRequest filterRequest){
		try {
			if(Objects.isNull(filterRequest.getTxnId())) {
				return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(), 
						GenericMessageTags.NULL_REQ.getMessage(), null);
			}

			List<PendingTacApprovedDb> pendingTacApprovedDb = pendingTacApprovedRepository.getByTxnId(filterRequest.getTxnId());
			if( pendingTacApprovedDb != null && !pendingTacApprovedDb.isEmpty()) {
				logger.info("Pending tacs available for consignment with txnId : " + filterRequest.getTxnId());
				return new GenricResponse(0, "SUCCESS", "SUCCESS", pendingTacApprovedDb);
			}else {
				logger.info("No pending tacs found for consignment with txnId : " + filterRequest.getTxnId());
				return new GenricResponse(1, "Not Found", "Not Found", "");
			}

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}


	//	@Transactional
	public boolean updatePendingApproval(FilterRequest filterRequest){
		try {
			List<PendingTacApprovedDb> pendingTacApproveDbs = pendingTacApprovedRepository.getByTxnId(filterRequest.getTxnId());

			for(PendingTacApprovedDb pendingTacApproveDb : pendingTacApproveDbs) {
				pendingTacApproveDb.setRemark(filterRequest.getRemark());
			}
			logger.info("[Trying to update PendingTacApprovedDb] | Model [" + pendingTacApproveDbs + "]");
			pendingTacApprovedRepository.saveAll(pendingTacApproveDbs);
			logger.info("[Updation of PendingTacApprovedDb is successful]  | Model [" + pendingTacApproveDbs + "]");
			return Boolean.TRUE;
		} catch (Exception e) {
			logger.error("[Error while updating PendingTacApprovedDb] | Model ["+filterRequest+"] | Error ["+e+"]");
			return Boolean.FALSE;
		}
	}

	


	//	@Transactional
	public GenricResponse deletePendingApproval(FilterRequest filterRequest){
		try {
			if(Objects.isNull(filterRequest.getUserId())) {
				return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(), 
						GenericMessageTags.NULL_REQ.getMessage(), null);
			}

			if(Objects.nonNull(filterRequest.getTxnId())) {

				if(pendingTacApprovedRepository.deleteByTxnId(filterRequest.getTxnId()) > 0) {
					logger.info("Delete of tac is successful for txnId[" + filterRequest.getTxnId() + "] by only txnId.");
				}else {
					logger.info("Delete of tac is failed for txnId[" + filterRequest.getTxnId() + "] by only txnId.");
				}

				logger.info("filter audit tail through pendic tac API  audit trail req=" + filterRequest);
				auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
						Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getRoleType(),
						Long.valueOf(filterRequest.getFeatureId()), Features.pending_tac, SubFeatures.DELETE, "", "NA",
						filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
				return new GenricResponse(0, "Deleted Successully.", "", "");

			}else if(Objects.nonNull(filterRequest.getTac()) && Objects.nonNull(filterRequest.getImporterId())){
				if(pendingTacApprovedRepository.deleteByTacAndUserId(filterRequest.getTac(), filterRequest.getImporterId()) > 0) {
					logger.info("Delete of tac is successful for txnId[" + filterRequest.getTxnId() + "] by tac and importerid.");
				}else {
					logger.info("Delete of tac is failed for tac[" + filterRequest.getTac() + "] by tac and importerid.");
				}
				return new GenricResponse(0, "Deleted Successully.", "", "");
			}else {
				logger.info("No Deletion of tac is allowed for invalid request [" + filterRequest + "]");
				return new GenricResponse(3, "No Deletion Allowed.", "", "");
			}

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	public Page<PendingTacApprovedDb> filterPendingTacApprovedDb(FilterRequest filterRequest, Integer pageNo,
			Integer pageSize, String Operation,String source) {
		try {

			String orderColumn = "Created On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "createdOn"
					: "Modified On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "modifiedOn"
							:"Transaction ID".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "txnId"
									: "TAC".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "tac"
											:"modifiedOn";

			Sort.Direction direction;
			direction= SortDirection.getSortDirection(filterRequest.getOrder() == null ? "desc" : filterRequest.getOrder());
			logger.info("orderColumn is : "+orderColumn+ " & direction is : "+direction);
			Pageable pageable = PageRequest.of(pageNo, pageSize,  Sort.by(direction, orderColumn));

			logger.info("orderColumn Name is : "+orderColumn+ "  -------------  direction is : "+direction+" & source is -----"+source);

			//Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
			Page<PendingTacApprovedDb> page = pendingTacApprovedRepository.findAll( buildSpecification(filterRequest,Operation).build(), pageable );

			if(source.equalsIgnoreCase("menu")) {
				auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
						Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
						Long.valueOf(filterRequest.getFeatureId()), Features.pending_tac, SubFeatures.VIEW_ALL, "", "NA",
						filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			} else if(source.equalsIgnoreCase("filter")){
				auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
						Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
						Long.valueOf(filterRequest.getFeatureId()), Features.pending_tac, SubFeatures.FILTER, "", "NA",
						filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));
			}else if(source.equalsIgnoreCase("ViewExport")) {
				logger.info("for "+source+" no entries in Audit Trail");
			}
			return page;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	private GenericSpecificationBuilder<PendingTacApprovedDb> buildSpecification(FilterRequest filterRequest,String Operation){
		GenericSpecificationBuilder<PendingTacApprovedDb> cmsb = new GenericSpecificationBuilder<>(propertiesReader.dialect);

		if(Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
			cmsb.with(new SearchCriteria("createdOn", filterRequest.getStartDate() , SearchOperation.GREATER_THAN, Datatype.DATE));

		if(Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
			cmsb.with(new SearchCriteria("createdOn", filterRequest.getEndDate() , SearchOperation.LESS_THAN, Datatype.DATE));

		if(Objects.nonNull(filterRequest.getTxnId()) && !filterRequest.getTxnId().isEmpty())
			cmsb.with(new SearchCriteria("txnId", filterRequest.getTxnId(), SearchOperation.LIKE, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getTac()) && !filterRequest.getTac().isEmpty())
			cmsb.with(new SearchCriteria("tac", filterRequest.getTac(), SearchOperation.LIKE, Datatype.STRING));

		if(Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()){

			cmsb.orSearch(new SearchCriteria("txnId", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));

			cmsb.orSearch(new SearchCriteria("tac", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));

		}
		return cmsb;
	}

	public FileDetails getFilteredPendingTacApprovedDbInFile(FilterRequest filterRequest,String Operation,String source) {
		logger.info("method executed");
		String fileName = null;
		Writer writer   = null;

		PendingTacApprovedFileModel atfm = null;

		DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dtf2  = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
		logger.info("CONFIG : file_consignment_download_dir [" + filepath + "]");
		SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
		logger.info("CONFIG : file_consignment_download_link [" + link + "]");

		String filePath = filepath.getValue();

		StatefulBeanToCsvBuilder<PendingTacApprovedFileModel> builder = null;
		StatefulBeanToCsv<PendingTacApprovedFileModel> csvWriter = null;
		List<PendingTacApprovedFileModel> fileRecords = null;
		CustomMappingStrategy<PendingTacApprovedFileModel> mappingStrategy = new CustomMappingStrategy<>();

		try {
			logger.info("going to fetch the data");
			List<PendingTacApprovedDb> pendingTacApprovedDbs = getAll(filterRequest,Operation,source);

			logger.info("Data:"+pendingTacApprovedDbs);


			auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
					Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
					Long.valueOf(filterRequest.getFeatureId()), Features.pending_tac, SubFeatures.EXPORT, "", "NA",
					filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));

			if( !pendingTacApprovedDbs.isEmpty() ) {
				if(Objects.nonNull(filterRequest.getUserId()) && (filterRequest.getUserId() != -1 && filterRequest.getUserId() != 0)) {
					fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "PendingTacApprovedDbs.csv";
				}else {
					fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "PendingTacApprovedDbs.csv";
				}
			}else {
				fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "PendingTacApprovedDbs.csv";
			}

			writer = Files.newBufferedWriter(Paths.get(filePath+fileName));
			mappingStrategy.setType(PendingTacApprovedFileModel.class);

			builder = new StatefulBeanToCsvBuilder<>(writer);
			csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			if( !pendingTacApprovedDbs.isEmpty() ) {
				fileRecords = new ArrayList<>(); 
				for(PendingTacApprovedDb pendingTacApprovedDb : pendingTacApprovedDbs ) { 
					atfm = new PendingTacApprovedFileModel();
					if(Objects.isNull(pendingTacApprovedDb)) {
						continue;
					}

					atfm.setCreatedOn(pendingTacApprovedDb.getCreatedOn().format(dtf));
					atfm.setModifiedOn(pendingTacApprovedDb.getModifiedOn().format(dtf));
					atfm.setTxnId(pendingTacApprovedDb.getTxnId()); 
					atfm.setTac(pendingTacApprovedDb.getTac());
					//					atfm.setUserType(pendingTacApprovedDb.getUserType());
					atfm.setFeatureName(pendingTacApprovedDb.getFeatureName());

					logger.debug(atfm);

					fileRecords.add(atfm);
				}
				csvWriter.write(fileRecords);
			}
			logger.info("returning file object");
			return new FileDetails( fileName, filePath, link.getValue().replace("$LOCAL_IP",
					propertiesReader.localIp) + fileName ); 

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}finally {
			try {
				if( Objects.nonNull(writer) )
					writer.close();
			} catch (IOException e) {}
		}
	}

	private List<PendingTacApprovedDb> getAll(FilterRequest filterRequest, String Operation, String source) {
		try {
			List<PendingTacApprovedDb> pendingTacApprovedDbs = pendingTacApprovedRepository.findAll(buildSpecification(filterRequest,Operation).build());

			/*
			 * for(AuditTrail auditTrail : auditTrails ) { setInterp(auditTrail); }
			 */

			return pendingTacApprovedDbs;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
}
