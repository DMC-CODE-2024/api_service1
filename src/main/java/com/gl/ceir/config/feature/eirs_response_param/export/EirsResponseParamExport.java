package com.gl.ceir.config.feature.eirs_response_param.export;

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.feature.common.CustomMappingStrategy;
import com.gl.ceir.config.feature.eirs_response_param.csv_file_model.EirsResponseParamFileModel;
import com.gl.ceir.config.feature.eirs_response_param.paging.EirsResponseParamPaging;
import com.gl.ceir.config.model.app.EirsResponse;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.service.impl.ConfigurationManagementServiceImpl;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class EirsResponseParamExport {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ConfigurationManagementServiceImpl configurationManagementServiceImpl;
    private EirsResponseParamPaging eirsResponseParamPaging;

    public EirsResponseParamExport(ConfigurationManagementServiceImpl configurationManagementServiceImpl, EirsResponseParamPaging eirsResponseParamPaging) {
        this.configurationManagementServiceImpl = configurationManagementServiceImpl;
        this.eirsResponseParamPaging = eirsResponseParamPaging;
    }

    @Autowired
    private PropertiesReader propertiesReader;

    public FileDetails export(EirsResponse eirsResponse, String subFeature) {
        String fileName = null;
        Writer writer = null;
        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(configurationManagementServiceImpl.findByTag("file.max-file-record").getValue());

        EirsResponseParamFileModel fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
        logger.info("File Path :  [" + filepath + "] & Link : [" + link + "]");

        StatefulBeanToCsvBuilder<EirsResponseParamFileModel> builder = null;
        StatefulBeanToCsv<EirsResponseParamFileModel> csvWriter = null;
        List<EirsResponseParamFileModel> fileRecords = null;
        CustomMappingStrategy<EirsResponseParamFileModel> mappingStrategy = new CustomMappingStrategy<>();

        try {
            List<EirsResponse> list = eirsResponseParamPaging.findAll(eirsResponse, pageNo, pageSize).getContent();
            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_" + subFeature + ".csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(EirsResponseParamFileModel.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            String lang = eirsResponse.getLanguage();
            if (!list.isEmpty()) {
                fileRecords = new ArrayList<EirsResponseParamFileModel>();
                for (EirsResponse data : list) {
                    fileModel = new EirsResponseParamFileModel();
                    // fileModel.setCreatedOn(data.getCreatedOn().format(dtf));
                    fileModel.setModifiedOn(data.getModifiedOn().format(dtf));
                    fileModel.setDescription(data.getDescription());
                    // fileModel.setTag(data.getTag());
                    // fileModel.setType(data.getType());
                    fileModel.setValue(data.getValue());
                    // fileModel.setActive(data.getActive());
                    fileModel.setFeatureName(data.getFeatureName());
//                     fileModel.setRemark(data.getRemark());
                    // fileModel.setUserType(data.getUserType());
                    // fileModel.setModifiedBy(data.getModifiedBy());
                    fileModel.setLanguage(data.getLanguage());
                    fileModel.setSubject(data.getSubject());

                    fileRecords.add(fileModel);
                }
                logger.info("Exported data : [" + fileRecords + "]");
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new EirsResponseParamFileModel());
            }
            logger.info("fileName [" + fileName + "] filePath [" + filepath + "] download link [" + link.getValue() + "]");

            FileDetails fileDetails = new FileDetails(fileName, filepath.getValue(), link.getValue().replace("$LOCAL_IP", propertiesReader.localIp) + fileName);
            logger.info("export file Details [" + fileDetails + "]");
            return fileDetails;

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}