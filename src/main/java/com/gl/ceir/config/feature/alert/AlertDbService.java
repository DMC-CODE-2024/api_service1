package com.gl.ceir.config.feature.alert;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Datatype;
import com.gl.ceir.config.model.constants.SearchOperation;
import com.gl.ceir.config.repository.app.AlertDbRepository;
import com.gl.ceir.config.repository.app.SystemConfigurationDbRepository;
import com.gl.ceir.config.repository.aud.AuditDBRepo;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.gl.ceir.config.util.HttpResponse;
import com.gl.ceir.config.util.Utility;
import com.opencsv.CSVWriter;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AlertDbService {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    SystemConfigDbRepoService systemConfigurationDbRepoImpl;


    @Autowired
    Utility utility;

    @Autowired
    AlertDbRepository alertDbRepo;

    @Autowired
    AlertRepoService alertRepoService;

    @Autowired
    SystemConfigurationDbRepository systemConfigDbRepository;

    @Autowired
    AuditDBRepo auditDb;


    private GenericSpecificationBuilder<AlertDb> buildSpecification(AlertDbFilter filterRequest) {
        GenericSpecificationBuilder<AlertDb> uPSB = new GenericSpecificationBuilder<AlertDb>(propertiesReader.dialect);

        if (Objects.nonNull(filterRequest.getStartDate()) && filterRequest.getStartDate() != "")
            uPSB.with(new SearchCriteria("createdOn", filterRequest.getStartDate(), SearchOperation.GREATER_THAN, Datatype.DATE));

        if (Objects.nonNull(filterRequest.getEndDate()) && filterRequest.getEndDate() != "")
            uPSB.with(new SearchCriteria("createdOn", filterRequest.getEndDate(), SearchOperation.LESS_THAN, Datatype.DATE));

        if (Objects.nonNull(filterRequest.getAlertId()) && !filterRequest.getAlertId().isEmpty())
            uPSB.with(new SearchCriteria("alertId", filterRequest.getAlertId(), SearchOperation.EQUALITY, Datatype.STRING));


        if (filterRequest.getFeature() == null) {

        } else {/*(Objects.nonNull(filterRequest.getFeature()))*/
            uPSB.with(new SearchCriteria("feature", filterRequest.getFeature(), SearchOperation.EQUALITY, Datatype.STRING));
        }

        if (filterRequest.getDescription() == null) {

        } else {/*(Objects.nonNull(filterRequest.getFeature()))*/
            log.info("Description Recieved =" + filterRequest.getFeature());
            uPSB.with(new SearchCriteria("description", filterRequest.getDescription(), SearchOperation.LIKE, Datatype.STRING));
        }

        if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
            uPSB.orSearch(new SearchCriteria("description", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
            uPSB.orSearch(new SearchCriteria("alertId", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
            uPSB.orSearch(new SearchCriteria("feature", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
        }

        return uPSB;
    }

    public List<AlertDb> getAll(AlertDbFilter filterRequest) {

        try {
            List<AlertDb> systemConfigListDbs = alertDbRepo.findAll(buildSpecification(filterRequest).build(), Sort.by(Sort.Direction.ASC, "alertId"));

            return systemConfigListDbs;

        } catch (Exception e) {
            log.info("Exception found =" + e.getMessage());
            log.info(e.getClass().getMethods().toString());
            log.info(e.toString());
            return null;
        }

    }


    public Page<AlertDb> viewAllAlertData(AlertDbFilter filterRequest, Integer pageNo, Integer pageSize, String source) {
        try {
            log.info("filter data:  " + filterRequest);
            //RequestHeaders header=new RequestHeaders(filterRequest.getUserAgent(),filterRequest.getPublicIp(),filterRequest.getUsername());
            //headerService.saveRequestHeader(header);


            String orderColumn = "Created On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "createdOn" : "Modified On".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "modifiedOn" : "Alert ID".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "alertId" : "Feature Name".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "feature" : "Description".equalsIgnoreCase(filterRequest.getOrderColumnName()) ? "description" : "modifiedOn";

            /*
             * Sort.Direction direction = getSortDirection(filterRequest.getOrder() == null
             * ? "desc" : filterRequest.getOrder());
             *
             * log.info("orderColumn Name is : "+orderColumn+
             * "  -------------  direction is : "+direction);
             *
             * Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by(direction,
             * orderColumn));
             */
            log.info("orderColumn data:  " + orderColumn);
            log.info("---system.getSort() : " + filterRequest.getSort());
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

            log.info("column Name :: " + filterRequest.getColumnName() + "---system.getSort() : " + filterRequest.getSort());


            //Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by(Sort.Direction.DESC, "modifiedOn"));
            Page<AlertDb> page = alertDbRepo.findAll(buildSpecification(filterRequest).build(), pageable);

            if (source.equalsIgnoreCase("menu")) {
                /*
                 * userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername
                 * (), filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.
                 * Alert_Management,SubFeatures.VIEW_ALL,filterRequest.getFeatureId(),
                 * filterRequest.getPublicIp(),filterRequest.getBrowser());
                 */

                auditDb.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUsername(), Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(), Long.valueOf(filterRequest.getFeatureId()), Features.Alert_Management, SubFeatures.VIEW_ALL, "", "NA", "SystemAdmin", filterRequest.getPublicIp(), filterRequest.getBrowser()));

            } else if (source.equalsIgnoreCase("filter")) {
                /*
                 * userService.saveUserTrail(filterRequest.getUserId(),filterRequest.getUsername
                 * (), filterRequest.getUserType(),filterRequest.getUserTypeId(),Features.
                 * Alert_Management,SubFeatures.FILTER,filterRequest.getFeatureId(),
                 * filterRequest.getPublicIp(),filterRequest.getBrowser());
                 */
                auditDb.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUsername(), Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(), Long.valueOf(filterRequest.getFeatureId()), Features.Alert_Management, SubFeatures.FILTER, "", "NA", "SystemAdmin", filterRequest.getPublicIp(), filterRequest.getBrowser()));
            } else if (source.equalsIgnoreCase("ViewExport")) {
                log.info("for " + source + " no entries in Audit Trail");
            }

            return page;

        } catch (Exception e) {
            log.info("Exception found =" + e.getMessage());
            log.info(e.getClass().getMethods().toString());
            log.info(e.toString());
            return null;

        }
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }


    public FileDetails getAlertDbInFile(AlertDbFilter alertAbFilter, String source) {
        log.info("export data:  " + alertAbFilter);
        String fileName = null;
        Writer writer = null;
        AlertDbFile adFm = null;
        SystemConfigurationDb dowlonadDir = systemConfigurationDbRepoImpl.getDataByTag("file.download-dir");
        SystemConfigurationDb dowlonadLink = systemConfigurationDbRepoImpl.getDataByTag("file.download-link");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(systemConfigDbRepository.findByTag("file.max-file-record").getValue());
        String filePath = dowlonadDir.getValue();
        StatefulBeanToCsvBuilder<AlertDbFile> builder = null;
        StatefulBeanToCsv<AlertDbFile> csvWriter = null;
        List<AlertDbFile> fileRecords = null;
        MappingStrategy<AlertDbFile> mapStrategy = new CustomMappingStrategy<>();


        try {
            mapStrategy.setType(AlertDbFile.class);
            alertAbFilter.setSort("");
            List<AlertDb> list = viewAllAlertData(alertAbFilter, pageNo, pageSize, source).getContent();
            if (list.size() > 0) {
                fileName = LocalDateTime.now().format(dtf).replace(" ", "_") + "_AlertDb.csv";
            } else {
                fileName = LocalDateTime.now().format(dtf).replace(" ", "_") + "_AlertDb.csv";
            }
            log.info(" file path plus file name: " + Paths.get(filePath + fileName));
            writer = Files.newBufferedWriter(Paths.get(filePath + fileName));
//			builder = new StatefulBeanToCsvBuilder<UserProfileFileModel>(writer);
//			csvWriter = builder.withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
//			
            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mapStrategy).withSeparator(',').withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            /*
             * userService.saveUserTrail(alertAbFilter.getUserId(),alertAbFilter.getUsername
             * (), alertAbFilter.getUserType(),alertAbFilter.getUserTypeId(),Features.
             * Alert_Management,SubFeatures.EXPORT,alertAbFilter.getFeatureId(),
             * alertAbFilter.getPublicIp(),alertAbFilter.getBrowser());
             */

            auditDb.save(new AuditTrail(alertAbFilter.getUserId(), alertAbFilter.getUsername(), Long.valueOf(alertAbFilter.getUserTypeId()), alertAbFilter.getUserType(), Long.valueOf(alertAbFilter.getFeatureId()), Features.Alert_Management, SubFeatures.EXPORT, "", "NA", "SystemAdmin", alertAbFilter.getPublicIp(), alertAbFilter.getBrowser()));
            if (list.size() > 0) {
                //List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag("GRIEVANCE_CATEGORY");
                fileRecords = new ArrayList<AlertDbFile>();
                for (AlertDb alertDb : list) {
                    adFm = new AlertDbFile();
                    adFm.setCreatedOn(utility.converedtlocalTime(alertDb.getCreatedOn()));
                    adFm.setModifiedOn(utility.converedtlocalTime(alertDb.getModifiedOn()));
                    adFm.setAlertId(alertDb.getAlertId());
                    adFm.setFeature(alertDb.getFeature());
                    adFm.setDescription(alertDb.getDescription());
                    System.out.println(adFm.toString());
                    fileRecords.add(adFm);
                }
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new AlertDbFile());
            }
            log.info("fileName::" + fileName);
            log.info("filePath::::" + filePath);
            log.info("link:::" + dowlonadLink.getValue());
            return new FileDetails(fileName, filePath, dowlonadLink.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        } finally {
            try {

                if (writer != null) writer.close();
            } catch (IOException e) {
            }
        }

    }

    public ResponseEntity<?> getAlertData() {
        try {
            List<AlertDb> alertDb = alertDbRepo.findAll();
            alertDb.sort((f1, f2) -> f1.getAlertId().compareTo(f2.getAlertId()));
            return new ResponseEntity<>(alertDb, HttpStatus.OK);
        } catch (Exception e) {
            log.info("exception occurs");
            log.info(e.toString());
            HttpResponse response = new HttpResponse();
            response.setResponse("Oop something wrong happened");
            response.setStatusCode(409);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> findById(AllRequest request) {
        try {
            log.info("given data:  " + request);
//			RequestHeaders header=new RequestHeaders(request.getUserAgent(),request.getPublicIp(),request.getUsername());
//			headerService.saveRequestHeader(header);
            AlertDb alertDb = alertDbRepo.getById(request.getDataId());
            /*
             * userService.saveUserTrail(request.getUserId(),request.getUsername(),
             * request.getUserType(),request.getUserTypeId(),Features.Alert_Management,
             * SubFeatures.VIEW,request.getFeatureId(),
             * request.getPublicIp(),request.getBrowser());
             */

            auditDb.save(new AuditTrail(request.getUserId(), request.getUsername(), Long.valueOf(request.getUserTypeId()), request.getUserType(), Long.valueOf(request.getFeatureId()), Features.Alert_Management, SubFeatures.VIEW, "", "NA", "SystemAdmin", request.getPublicIp(), request.getBrowser()));
            if (alertDb != null) {
                GenricResponse response = new GenricResponse(200, "", "", alertDb);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                GenricResponse response = new GenricResponse(500, AlertDbTags.Alert_Data_By_Id_Fail.getMessage(), AlertDbTags.Alert_Data_By_Id_Fail.getTag());
                return new ResponseEntity<>(response, HttpStatus.OK);

            }

        } catch (Exception e) {
            log.info("exception occurs");
            log.info(e.toString());
            GenricResponse response = new GenricResponse(409, RegistrationTags.COMMAN_FAIL_MSG.getTag(), RegistrationTags.COMMAN_FAIL_MSG.getMessage(), "");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> updateAlertDb(AlertDb alertDb) {
        log.info("inside update alertDb controller");
        log.info("given data:  " + alertDb);
//		RequestHeaders header=new RequestHeaders(alertDb.getUserAgent(),alertDb.getPublicIp(),alertDb.getUsername());
//		headerService.saveRequestHeader(header);
        AlertDb data = alertRepoService.getById(alertDb.getId());
        if (data != null) {
            data.setDescription(alertDb.getDescription());
            LocalDateTime now = LocalDateTime.now();
            data.setModifiedOn(now);

            AlertDb output = alertRepoService.save(data);
            /*
             * userService.saveUserTrail(alertDb.getUserId(),alertDb.getUsername(),
             * alertDb.getUserType(),alertDb.getUserTypeId(),Features.Alert_Management,
             * SubFeatures.UPDATE,alertDb.getFeatureId(),
             * alertDb.getPublicIp(),alertDb.getBrowser());
             */

            auditDb.save(new AuditTrail(alertDb.getUserId(), alertDb.getUsername(),
                    Long.valueOf(alertDb.getUserTypeId()), alertDb.getUserType(),
                    Long.valueOf(alertDb.getFeatureId()), Features.Alert_Management, SubFeatures.UPDATE, "", "NA",
                    "SystemAdmin", alertDb.getPublicIp(), alertDb.getBrowser()));

            if (output != null) {
                GenricResponse response = new GenricResponse(200, AlertDbTags.Alert_Update_Sucess.getMessage(), AlertDbTags.Alert_Update_Sucess.getTag(), "");
                log.info("exit from  update AlertDb  controller");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                GenricResponse response = new GenricResponse(500, AlertDbTags.Alert_Update_Fail.getMessage(), AlertDbTags.Alert_Update_Fail.getTag(), "");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            GenricResponse response = new GenricResponse(500, AlertDbTags.Alert_Wrong_ID.getMessage(), AlertDbTags.Alert_Wrong_ID.getTag(), "");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

}
