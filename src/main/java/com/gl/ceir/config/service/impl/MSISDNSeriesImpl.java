package com.gl.ceir.config.service.impl;

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.externalproperties.FeatureNameMap;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.*;
import com.gl.ceir.config.model.file.MSISDNFileModel;
import com.gl.ceir.config.repository.app.MSISDNSeriesModelRepository;
import com.gl.ceir.config.repository.app.UserRepository;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;


@Service
public class MSISDNSeriesImpl {

    private MSISDNSeriesModelRepository msisdnSeriesModelRepository;
    private UserRepository userRepository;

    private ConfigurationManagementServiceImpl configurationManagementServiceImpl;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public MSISDNSeriesImpl(MSISDNSeriesModelRepository msisdnSeriesModelRepository, UserRepository userRepository, ConfigurationManagementServiceImpl configurationManagementServiceImpl) {
        this.msisdnSeriesModelRepository = msisdnSeriesModelRepository;
        this.userRepository = userRepository;
        this.configurationManagementServiceImpl = configurationManagementServiceImpl;
    }

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    private static final Logger logger = LogManager.getLogger(MSISDNSeriesImpl.class);

    @Autowired
    FeatureNameMap featureNameMap;

    @Transactional
    public GenricResponse save(MSISDNSeriesModel msisdnSeriesModel) {
        if (Objects.isNull(msisdnSeriesModel)) {
            logger.info("MSISDNSeriesModel request payload is empty for request " + msisdnSeriesModel);
            return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(), GenericMessageTags.NULL_REQ.getMessage(), "NA");
        }
        GenricResponse genricResponse;
        try {
            MSISDNSeriesModel save = msisdnSeriesModelRepository.save(msisdnSeriesModel);
            logger.info("MSISDNSeriesModel request payload saved : " + save);

            auditTrailOperation(msisdnSeriesModel, featureNameMap.get("OPERATOR_SERIES"), featureNameMap.get("ADD"));
            return genricResponse = new GenricResponse(0, "MSISDN Series successfully inserted", "NA", save);

        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            genricResponse = new GenricResponse(1, "FAILED", "NA", new ResourceServicesException(this.getClass().getName(), e.getMessage()));
        }
        return genricResponse;
    }

    @Transactional
    public GenricResponse update(MSISDNSeriesModel msisdnSeriesModel) {
        if (Objects.isNull(msisdnSeriesModel)) {
            logger.info("MSISDNSeriesModel request payload is empty for request " + msisdnSeriesModel);
            return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(), GenericMessageTags.NULL_REQ.getMessage(), "NA");
        }
        Optional<MSISDNSeriesModel> result = msisdnSeriesModelRepository.findById(msisdnSeriesModel.getId());
        logger.info("result : " + result);
        if (result.isEmpty()) {
            return new GenricResponse(1, "NO_USER_FOUND", "User doesn't exist in DB", "NA");
        }

     /*   MSISDNSeriesModel byId = result.get();
        // byId.setCreatedOn(result.get().getCreatedOn());
        byId.setSeriesEnd(msisdnSeriesModel.getSeriesEnd());
        byId.setSeriesStart(msisdnSeriesModel.getSeriesStart());
        byId.setSeriesType(msisdnSeriesModel.getSeriesType());
        byId.setOperatorName(msisdnSeriesModel.getOperatorName());
        byId.setRemarks(msisdnSeriesModel.getRemarks());
        byId.setLength(msisdnSeriesModel.getLength());*/
        msisdnSeriesModel.setCreatedOn(result.get().getCreatedOn());
        GenricResponse genricResponse;
        try {
            MSISDNSeriesModel save = msisdnSeriesModelRepository.save(msisdnSeriesModel);

            logger.info("MSISDNSeriesModel payload saved : " + msisdnSeriesModel);

            auditTrailOperation(msisdnSeriesModel, featureNameMap.get("OPERATOR_SERIES"), featureNameMap.get("UPDATE"));

            genricResponse = new GenricResponse(0, "MSISDN Series successfully updated", "NA", save);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            genricResponse = new GenricResponse(1, "FAILED", "NA", new ResourceServicesException(this.getClass().getName(), e.getMessage()));
        }
        return genricResponse;
    }


    public GenricResponse delete(MSISDNSeriesModel msisdnSeriesModel) {
        long userId = msisdnSeriesModel.getUserId();
        if (userId < 1) {
            logger.info("UserId cann't be less than 1 ");
            return new GenricResponse(1, GenericMessageTags.NULL_REQ.getTag(),
                    "UserId cann't be less than 1", "NA");
        }

        auditTrailOperation(msisdnSeriesModel,  featureNameMap.get("OPERATOR_SERIES"), featureNameMap.get("DELETE"));

        Predicate<Long> isIdExist = (x) -> msisdnSeriesModelRepository.existsById(x);

        if (isIdExist.test(msisdnSeriesModel.getId())) {
            msisdnSeriesModelRepository.deleteById(msisdnSeriesModel.getId());
            return new GenricResponse(0, "DELETED", "NA");
        }
        return new GenricResponse(1, "User does not exist in database", "NA");

    }

    public Page<MSISDNSeriesModel> paging(MSISDNSeriesModel msisdnSeriesModel, Integer pageNo, Integer pageSize) {

        try {
            logger.info("request received : " + msisdnSeriesModel + " [pageNo] ...." + pageNo + " [pageNo]...." + pageNo);

            String sort = null, orderColumn = null;
            if (Objects.nonNull(msisdnSeriesModel.getAuditTraildDTO())) {
                String columnName = msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getColumnName();
                sort = Objects.nonNull(msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getSort()) ? msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getSort() : "desc";
                orderColumn = Objects.nonNull(columnName) ? columnName : "Modified On";
            } else {
                sort = "desc";
                orderColumn = "Modified On";
            }
            orderColumn = sortColumnName(orderColumn);
            Sort.Direction direction = SortDirection.getSortDirection(sort);

            logger.info("orderColumn is : " + orderColumn + " & direction is : " + direction);

            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            Page<MSISDNSeriesModel> page;
            page = msisdnSeriesModelRepository.findAll(buildSpecification(msisdnSeriesModel).build(), pageable);
            return page;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    public MappingJacksonValue pagingAndExport(MSISDNSeriesModel msisdnSeriesModel, int pageNo, int pageSize, int file) {
        MappingJacksonValue mappingJacksonValue = null;
        if (pageNo < 0 || pageSize < 0 || file < 0) {
            return new MappingJacksonValue(new GenricResponse(1, GenericMessageTags.INVALID_REQUEST.getTag(),
                    "Invalid parameter value", "NA"));
        }

        switch (file) {
            case 1:
                FileDetails export = export(msisdnSeriesModel);
                mappingJacksonValue = new MappingJacksonValue(export);
                break;
            default:
                Page<MSISDNSeriesModel> paging = paging(msisdnSeriesModel, pageNo, pageSize);
                mappingJacksonValue = new MappingJacksonValue(paging);
        }
        return mappingJacksonValue;
    }

    public FileDetails export(MSISDNSeriesModel msisdnSeriesModel) {
        String fileName = null;
        Writer writer = null;
        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(configurationManagementServiceImpl.findByTag("file.max-file-record").getValue());

        MSISDNFileModel fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
        logger.info("File Path :  [" + filepath + "]");
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
        logger.info("Link : [" + link + "]");

        StatefulBeanToCsvBuilder<MSISDNFileModel> builder = null;
        StatefulBeanToCsv<MSISDNFileModel> csvWriter = null;
        List<MSISDNFileModel> fileRecords = null;
        CustomMappingStrategy<MSISDNFileModel> mappingStrategy = new CustomMappingStrategy<>();

        try {
            List<MSISDNSeriesModel> list = paging(msisdnSeriesModel, pageNo, pageSize).getContent();
            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_MSISDNSeries.csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(MSISDNFileModel.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();

            if (list.size() > 0) {
                fileRecords = new ArrayList<MSISDNFileModel>();
                for (MSISDNSeriesModel data : list) {
                    fileModel = new MSISDNFileModel();
                    fileModel.setCreatedOn(data.getCreatedOn().format(dtf));
                    fileModel.setModifiedOn(data.getModifiedOn().format(dtf));
                    fileModel.setOperatorName(data.getOperatorName());
                    fileModel.setUserId(data.getUser().getUsername());
                    fileModel.setSeriesType(data.getSeriesType());
                    fileModel.setSeriesStart(data.getSeriesStart());
                    // fileModel.setSeriesEnd(data.getSeriesEnd());
                    fileRecords.add(fileModel);
                }
                logger.info("Exported data : [" + fileRecords + "]");
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new MSISDNFileModel());
            }
            logger.info("fileName [" + fileName + "] filePath [" + filepath + "] download link [" + link.getValue() + "]");

            auditTrailOperation(msisdnSeriesModel,  featureNameMap.get("OPERATOR_SERIES"), featureNameMap.get("EXPORT"));

            FileDetails fileDetails = new FileDetails(fileName, filepath.getValue(),
                    link.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);
            logger.info("export file Details [" + fileDetails + "]");
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

    private GenericSpecificationBuilder<MSISDNSeriesModel> buildSpecification(MSISDNSeriesModel msisdnSeriesModel) {
        logger.info("FilterRequest payload : [" + msisdnSeriesModel + "]");
        GenericSpecificationBuilder<MSISDNSeriesModel> cmsb = new GenericSpecificationBuilder<>(
                propertiesReader.dialect);
        String subFeature = msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().isFilter() == true ? SubFeatures.FILTER : featureNameMap.get("VIEWALL");

        auditTrailOperation(msisdnSeriesModel, featureNameMap.get("OPERATOR_SERIES"), subFeature);

        if (Objects.nonNull(msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getStartDate()) && !msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getStartDate().isEmpty())
            cmsb.with(new SearchCriteria("createdOn", msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getStartDate(), SearchOperation.GREATER_THAN,
                    Datatype.DATE));

        if (Objects.nonNull(msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getEndDate()) && !msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getEndDate().isEmpty())
            cmsb.with(new SearchCriteria("createdOn", msisdnSeriesModel.getAuditTraildDTO().getFilterDTO().getEndDate(), SearchOperation.LESS_THAN,
                    Datatype.DATE));

        if (Objects.nonNull(msisdnSeriesModel.getOperatorName()) && msisdnSeriesModel.getOperatorName() != "") {
            cmsb.with(new SearchCriteria("operatorName", msisdnSeriesModel.getOperatorName(), SearchOperation.LIKE, Datatype.STRING));
        }
        if (Objects.nonNull(msisdnSeriesModel.getUserName()) && msisdnSeriesModel.getUserName() != "") {
            cmsb.addSpecification(cmsb.joinWithUser(new SearchCriteria("username", msisdnSeriesModel.getUserName(), SearchOperation.LIKE, Datatype.STRING)));
        }


        if (Objects.nonNull(msisdnSeriesModel.getSeriesType()) && msisdnSeriesModel.getSeriesType() != "") {
            cmsb.with(new SearchCriteria("seriesType", msisdnSeriesModel.getSeriesType(), SearchOperation.LIKE, Datatype.STRING));
        }
        if (msisdnSeriesModel.getSeriesStart() > 0) {
            cmsb.with(new SearchCriteria("seriesStart", msisdnSeriesModel.getSeriesStart(), SearchOperation.EQUALITY,
                    Datatype.INT));
        }
        if (msisdnSeriesModel.getSeriesEnd() > 0) {
            cmsb.with(new SearchCriteria("seriesEnd", msisdnSeriesModel.getSeriesEnd(), SearchOperation.EQUALITY,
                    Datatype.INT));
        }
        if (Objects.nonNull(msisdnSeriesModel.getRemarks()) && msisdnSeriesModel.getRemarks() != "") {
            cmsb.with(new SearchCriteria("remarks", msisdnSeriesModel.getRemarks(), SearchOperation.LIKE, Datatype.STRING));
        }
        return cmsb;
    }


    public String sortColumnName(String columnName) {
        Map<String, String> map = new HashMap<>();
        if (Objects.nonNull(columnName) && !columnName.isEmpty()) {
            map.put("Created On", "createdOn");
            map.put("Modified On", "modifiedOn");
            map.put("Operator Name", "operatorName");
            map.put("User ID", "user.username");
            map.put("Series Type", "seriesType");
            map.put("Series End", "seriesEnd");
        }
        return map.get(columnName);
    }

    public Map<String, List<?>> distinct(List<String> param, Class<?> entity) {

        Map<String, List<?>> map = new HashMap<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<?> query = criteriaBuilder.createQuery(entity);
        Root<?> c = query.from(entity);
        Consumer<String> consumer = (s) -> {
            logger.info("Parameter : [" + s + "]");
            List<?> resultList = null;
            CriteriaQuery<?> criteriaQuery;
            try {
                criteriaQuery = query.select(c.get(s)).distinct(true)
                        // .where(criteriaBuilder.notEqual(c.get(s), ""))
                        .orderBy(criteriaBuilder.asc(c.get(s)));
                resultList = em.createQuery(criteriaQuery).getResultList();
                resultList.removeIf(Objects::isNull);
                map.put(s, resultList);
            } catch (Exception e) {
                map.put(s, resultList);
            }
        };
        param.forEach(consumer);

        return map;
    }

    public <T extends MSISDNSeriesModel> void auditTrailOperation(T t, String feature, String subFeature) {
        AuditTrail auditTrailPayload = new AuditTrail(t.getAuditTraildDTO().getUserId(), t.getAuditTraildDTO().getUserName(), t.getAuditTraildDTO().getUserTypeId(), t.getAuditTraildDTO().getUserType(), t.getAuditTraildDTO().getFeatureId(), feature, subFeature, "", "NA", t.getAuditTraildDTO().getRoleType(), t.getAuditTraildDTO().getPublicIp(), t.getAuditTraildDTO().getBrowser());
        auditTrailRepository.save(auditTrailPayload);
        logger.info("auditTrail saved for Feature in [" + feature + "] and Sub Feature [" + subFeature + "] with payload " + auditTrailPayload);
    }

    public Optional<MSISDNSeriesModel> find(Long id) {
        return msisdnSeriesModelRepository.findById(id);
    }
}