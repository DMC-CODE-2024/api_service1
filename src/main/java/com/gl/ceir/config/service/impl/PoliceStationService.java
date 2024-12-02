package com.gl.ceir.config.service.impl;

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.configuration.SortDirection;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.*;
import com.gl.ceir.config.model.file.IMEIContentModel;
import com.gl.ceir.config.model.file.PoliceDetailExport;
import com.gl.ceir.config.repository.app.CommunePoliceRepository;
import com.gl.ceir.config.repository.app.PoliceStationDetails;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.specificationsbuilder.GenericSpecificationBuilder;
import com.gl.ceir.config.util.CustomMappingStrategy;
import com.gl.ceir.config.util.InterpSetter;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PoliceStationService {
    private static final Logger logger = LogManager.getLogger(PoliceStationService.class);
    @Autowired
    AuditTrailRepository auditTrailRepository;
    @Autowired
    PropertiesReader propertiesReader;
    @Autowired
    InterpSetter interpSetter;

    @Autowired
    ConfigurationManagementServiceImpl configurationManagementServiceImpl;

    @Autowired
    PoliceStationDetails policeStationDetails;

    @Autowired
    DistinctParamService distinctParamService;

    @Autowired
    CommunePoliceRepository communePoliceRepository;


    public Page<UserProfile> filterPoliceStationDetail(FilterRequest filterRequest, Integer pageNo, Integer pageSize) {
        try {
            String orderColumn = null;
            logger.info("column Name :: " + filterRequest.getColumnName());
            orderColumn = "Province".equalsIgnoreCase(filterRequest.getColumnName()) ? "province"
                    : "Commune".equalsIgnoreCase(filterRequest.getColumnName()) ? "commune"
                    : "District".equalsIgnoreCase(filterRequest.getColumnName()) ? "district"
                    : "Police".equalsIgnoreCase(filterRequest.getColumnName()) ? "police"
                    : "Name".equalsIgnoreCase(filterRequest.getColumnName()) ? "name"
                    : "Contact".equalsIgnoreCase(filterRequest.getColumnName()) ? "msisdn"
                    : "province";
            Sort.Direction direction;
            direction = Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(direction, orderColumn));
            logger.info("column Name sorted :: " + filterRequest.getColumnName() + "---system.getSort() : " + filterRequest.getSort());
            Page<UserProfile> page = policeStationDetails.findAll(buildSpecification_system(filterRequest).build(), pageable);
            logger.info("page ------- {}",page.getContent());
            return page;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    private GenericSpecificationBuilder<UserProfile> buildSpecification_system(FilterRequest filterRequest) {
        GenericSpecificationBuilder<UserProfile> sb = new GenericSpecificationBuilder<UserProfile>(
                propertiesReader.dialect);
        if (Objects.nonNull(filterRequest.getProvince()))
            sb.with(new SearchCriteria("province", filterRequest.getProvince(), SearchOperation.EQUALITY_CASE_INSENSITIVE,
                    Datatype.STRING));

        if (Objects.nonNull(filterRequest.getCommune()))
            sb.with(new SearchCriteria("commune", filterRequest.getCommune(), SearchOperation.EQUALITY, Datatype.STRING));

        if (Objects.nonNull(filterRequest.getDistrict()))
            sb.with(new SearchCriteria("district", filterRequest.getDistrict(), SearchOperation.EQUALITY,
                    Datatype.STRING));

        if (!Objects.nonNull(filterRequest.getPolice())) {
            //  sb.with(new SearchCriteria("police", filterRequest.getPolice(), SearchOperation.EQUALITY, Datatype.STRING));
            logger.info("police name is blank=");

        }
        else{
            logger.info("police name inside filter=", filterRequest.getPolice());
            sb.addSpecification(sb.<PoliceStationDb>joinWithMultiple(new SearchCriteria("id", Long.valueOf(filterRequest.getPolice()), SearchOperation.EQUALITY, Datatype.LONG)));
        }
        if (Objects.nonNull(filterRequest.getName()) && !filterRequest.getName().isEmpty())
            sb.with(new SearchCriteria("firstName", filterRequest.getName(), SearchOperation.LIKE, Datatype.STRING));

        if (Objects.nonNull(filterRequest.getMsisdn()) && !filterRequest.getMsisdn().isEmpty())
            sb.with(new SearchCriteria("phoneNo", filterRequest.getMsisdn(), SearchOperation.LIKE, Datatype.STRING));


        if (Objects.nonNull(filterRequest.getSearchString()) && !filterRequest.getSearchString().isEmpty()) {
            sb.orSearch(
                    new SearchCriteria("province", filterRequest.getSearchString(), SearchOperation.LIKE, Datatype.STRING));
            sb.orSearch(new SearchCriteria("commune", filterRequest.getSearchString(), SearchOperation.LIKE,
                    Datatype.STRING));
            sb.orSearch(new SearchCriteria("district", filterRequest.getSearchString(), SearchOperation.LIKE,
                    Datatype.STRING));
        }
        logger.info("group id="+propertiesReader.groupId);
        sb.addSpecification(sb.hasGroupId(propertiesReader.groupId));
        return sb;
    }

    public FileDetails exportFile_System(FilterRequest filterRequest) {
        String fileName = null;
        Writer writer = null;

        PoliceDetailExport fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
        logger.info("CONFIG : file_systemMgt_download_dir [" + filepath + "]");
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
        logger.info("CONFIG : file_systemMgt_download_link [" + link + "]");

        StatefulBeanToCsvBuilder<PoliceDetailExport> builder = null;
        StatefulBeanToCsv<PoliceDetailExport> csvWriter = null;
        List<PoliceDetailExport> fileRecords = null;
        CustomMappingStrategy<PoliceDetailExport> mappingStrategy = new CustomMappingStrategy<>();

        try {
            Page<UserProfile> checkIMEIResponseParamExport = getAll_system(filterRequest);
            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_PoliceStation.csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(PoliceDetailExport.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',')
                    .withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();

            if (!checkIMEIResponseParamExport.isEmpty()) {
                fileRecords = new ArrayList<>();
                for (UserProfile userProfile : checkIMEIResponseParamExport) {
                    fileModel = new PoliceDetailExport(userProfile.getProvinceDb().getProvince(), userProfile.getDistrictDb().getDistrict(), userProfile.getPoliceStationDb().getCommuneDb().getCommune(), userProfile.getPoliceStationDb().getPolice(), userProfile.getFirstName(), userProfile.getPhoneNo());
                    fileRecords.add(fileModel);
                }
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new PoliceDetailExport());
            }
            /*auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(),
                    Long.valueOf(filterRequest.getUserTypeId()), filterRequest.getUserType(),
                    Long.valueOf(filterRequest.getFeatureId()), Features.Check_IMEI_Content, SubFeatures.EXPORT, "", "NA",
                    filterRequest.getRoleType(),filterRequest.getPublicIp(),filterRequest.getBrowser()));*/
            /*logger.info("Check_IMEI_Content : successfully inserted in Audit trail.");*/
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

    private Page<UserProfile> getAll_system(FilterRequest filterRequest) {
        try {
            SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag("file.max-file-record");
            Pageable pageable = PageRequest.of(0, Integer.valueOf(filepath.getValue()), Sort.by(Sort.Direction.DESC, "createdOn"));
            Page<UserProfile> list = policeStationDetails.findAll(
                    buildSpecification_system(filterRequest).build(), pageable);

            return list;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        }
    }

    public Map<String, List<?>> distinct(List<String> param, Class<?> entity) {
        return distinctParamService.distinct(param, entity);
    }

}
