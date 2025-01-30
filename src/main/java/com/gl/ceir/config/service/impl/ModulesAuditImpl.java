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

import com.gl.ceir.config.config.ConfigTags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.aud.ModulesAuditModel;
import com.gl.ceir.config.model.app.SearchCriteria;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.model.file.ModulesAuditFile;
import com.gl.ceir.config.model.file.SystemMgtFileModel;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.repository.aud.ModulesAuditRepo;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

@Service
public class ModulesAuditImpl {

    private static final Logger logger = LogManager.getLogger(ModulesAuditImpl.class);

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    ModulesAuditRepo modulesAuditRepo;

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    ConfigurationManagementServiceImpl configurationManagementServiceImpl;

    public Page<ModulesAuditModel> filterModulesAudit(FilterRequest filterRequest, Integer pageNo,
                                                      Integer pageSize) {
        try {
            String orderColumn = null;
//			createdOn,taxPaidStatus,quantity,deviceQuantity,supplierName,consignmentStatus
            logger.info("column Name :: " + filterRequest.getColumnName());

            orderColumn = "Created On".equalsIgnoreCase(filterRequest.getColumnName()) ? "createdOn"
                    : "Modified On".equalsIgnoreCase(filterRequest.getColumnName()) ? "modifiedOn"
                    : "Feature Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "featureName"
                    : "Module Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "moduleName"
                    : "Status".equalsIgnoreCase(filterRequest.getColumnName()) ? "moduleStatus"
                    : "Status Code".equalsIgnoreCase(filterRequest.getColumnName()) ? "statusCode"
                    : "Execution Time".equalsIgnoreCase(filterRequest.getColumnName()) ? "executionTime"
                    : "Error Message".equalsIgnoreCase(filterRequest.getColumnName()) ? "errorMessage"
                    : "Action".equalsIgnoreCase(filterRequest.getColumnName()) ? "action"
                    : "Count".equalsIgnoreCase(filterRequest.getColumnName()) ? "count"
                    : "Info".equalsIgnoreCase(filterRequest.getColumnName()) ? "info"
                    : "Server Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "serverName"
                    : "Count2".equalsIgnoreCase(filterRequest.getColumnName()) ? "count2"
                    : "Failure Count".equalsIgnoreCase(filterRequest.getColumnName()) ? "failureCount"

                    : "modifiedOn";

            Sort.Direction direction;
            if ("modifiedOn".equalsIgnoreCase(orderColumn)) {
                direction = Sort.Direction.DESC;
            } else {
                direction = SortDirection.getSortDirection(filterRequest.getSort());
            }
            if ("modifiedOn".equalsIgnoreCase(orderColumn) && SortDirection.getSortDirection(filterRequest.getSort()).equals(Sort.Direction.ASC)) {
                direction = Sort.Direction.ASC;
            }
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            logger.info("column Name :: " + filterRequest.getColumnName() + "---system.getSort() : " + filterRequest.getSort());


            //Pageable pageable = PageRequest.of(pageNo, pageSize, new Sort(Sort.Direction.DESC, "modifiedOn"));
            Page<ModulesAuditModel> page = modulesAuditRepo.findAll(buildSpecification_system(filterRequest).build(), pageable);

            logger.info("-------");
            auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
                    Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
                    Long.valueOf(filterRequest.getFeatureId()), Features.Modules_audit_trail, SubFeatures.VIEW_ALL, "", "NA",
                    filterRequest.getRoleType(), filterRequest.getPublicIp(), filterRequest.getBrowser()));
            logger.info("Check IMEI Messages : successfully inserted in Audit trail.");
            return page;

        } catch (Exception e) {
            logger.info("Exception found=" + e.getMessage());
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    private GenericSpecificationBuilder<ModulesAuditModel> buildSpecification_system(FilterRequest filterRequest) {

        GenericSpecificationBuilder<ModulesAuditModel> sb = new GenericSpecificationBuilder<ModulesAuditModel>(
                propertiesReader.dialect);

        if (Objects.nonNull(filterRequest.getStartDate()) && !filterRequest.getStartDate().isEmpty())
            sb.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN,
                    Datatype.DATE));

        if (Objects.nonNull(filterRequest.getEndDate()) && !filterRequest.getEndDate().isEmpty())
            sb.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN,
                    Datatype.DATE));


        if (Objects.nonNull(filterRequest.getModuleName()) && !filterRequest.getModuleName().isEmpty())
            sb.with(new SearchCriteria("moduleName", filterRequest.getModuleName(), SearchOperation.EQUALITY,
                    Datatype.STRING));

        if (Objects.nonNull(filterRequest.getFeatureName()) && !filterRequest.getFeatureName().isEmpty())
            sb.with(new SearchCriteria("featureName", filterRequest.getFeatureName(), SearchOperation.EQUALITY,
                    Datatype.STRING));

        if (Objects.nonNull(filterRequest.getModuleStatus()) && !filterRequest.getModuleStatus().isEmpty())
            sb.with(new SearchCriteria("status", filterRequest.getModuleStatus(), SearchOperation.EQUALITY, Datatype.STRING));

        if (Objects.nonNull(filterRequest.getStatusCode()) && !filterRequest.getStatusCode().equals(0))
            sb.with(new SearchCriteria("statusCode", filterRequest.getStatusCode(), SearchOperation.EQUALITY, Datatype.INT));

        if (Objects.nonNull(filterRequest.getActionName()) && !filterRequest.getActionName().isEmpty())
            sb.with(new SearchCriteria("action", filterRequest.getActionName(), SearchOperation.EQUALITY, Datatype.STRING));


        if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
            sb.orSearch(
                    new SearchCriteria("moduleName", filterRequest.getSearchString(), SearchOperation.EQUALITY, Datatype.STRING));
            sb.orSearch(new SearchCriteria("featureName", filterRequest.getSearchString(), SearchOperation.EQUALITY,
                    Datatype.STRING));
            sb.orSearch(new SearchCriteria("status", filterRequest.getSearchString(), SearchOperation.EQUALITY,
                    Datatype.STRING));
            sb.orSearch(new SearchCriteria("statusCode", filterRequest.getSearchString(), SearchOperation.EQUALITY,
                    Datatype.INT));
        }
        return sb;
    }

    public FileDetails exportFile_System(FilterRequest filterRequest) {
        String fileName = null;
        Writer writer = null;

        ModulesAuditFile fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
        logger.info("CONFIG : file_systemMgt_download_dir [" + filepath + "]");
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
        logger.info("CONFIG : file_systemMgt_download_link [" + link + "]");

        StatefulBeanToCsvBuilder<ModulesAuditFile> builder = null;
        StatefulBeanToCsv<ModulesAuditFile> csvWriter = null;
        List<ModulesAuditFile> fileRecords = null;
        CustomMappingStrategy<ModulesAuditFile> mappingStrategy = new CustomMappingStrategy<>();

        try {

            Page<ModulesAuditModel> modulesAuditModel = getAll_system(filterRequest);

            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_ModulesAuditTrail.csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(ModulesAuditFile.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();

            if (!modulesAuditModel.isEmpty()) {
                fileRecords = new ArrayList<>();

                for (ModulesAuditModel systemConfigurationDB : modulesAuditModel) {

                    LocalDateTime creation = systemConfigurationDB.getCreatedOn() == null ? LocalDateTime.now()
                            : systemConfigurationDB.getCreatedOn();
                    LocalDateTime modified = systemConfigurationDB.getModifiedOn() == null ? LocalDateTime.now()
                            : systemConfigurationDB.getModifiedOn();

                    fileModel = new ModulesAuditFile(creation.format(dtf), modified.format(dtf),
                            systemConfigurationDB.getFeatureName() == null ? "NA"
                                    : systemConfigurationDB.getFeatureName(),
                            systemConfigurationDB.getModuleName() == null ? "NA"
                                    : systemConfigurationDB.getModuleName(),
                            systemConfigurationDB.getStatus() == null ? "NA"
                                    : systemConfigurationDB.getStatus(),
                            systemConfigurationDB.getStatusCode(),
                            systemConfigurationDB.getExecutionTime() == null ? "NA"
                                    : systemConfigurationDB.getExecutionTime(),
                            systemConfigurationDB.getErrorMessage() == null ? "NA"
                                    : systemConfigurationDB.getErrorMessage(),
                            systemConfigurationDB.getAction() == null ? "NA"
                                    : systemConfigurationDB.getAction(),
                            systemConfigurationDB.getCount(),
                            systemConfigurationDB.getCount2(),
                            systemConfigurationDB.getFailureCount(),
                            systemConfigurationDB.getInfo() == null ? "NA"
                                    : systemConfigurationDB.getInfo(),
                            systemConfigurationDB.getServerName() == null ? "NA"
                                    : systemConfigurationDB.getServerName());

                    fileRecords.add(fileModel);
                }


                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new ModulesAuditFile());
            }
            auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
                    Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
                    Long.valueOf(filterRequest.getFeatureId()), Features.Modules_audit_trail, SubFeatures.EXPORT, "", "NA",
                    filterRequest.getRoleType(), filterRequest.getPublicIp(), filterRequest.getBrowser()));
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

    private Page<ModulesAuditModel> getAll_system(FilterRequest filterRequest) {
        try {
        	 SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag("file.max-file-record");
			 Pageable pageable = PageRequest.of(0, Integer.valueOf(filepath.getValue()),  Sort.by(Sort.Direction.DESC, "modifiedOn"));
            Page<ModulesAuditModel> list = modulesAuditRepo.findAll(
                    buildSpecification_system(filterRequest).build(), pageable);

            return list;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }
}
