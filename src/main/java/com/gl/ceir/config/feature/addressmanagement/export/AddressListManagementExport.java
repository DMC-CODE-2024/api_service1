package com.gl.ceir.config.feature.addressmanagement.export;

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.feature.addressmanagement.csv_file_model.AddressListManagementFileModel;
import com.gl.ceir.config.feature.addressmanagement.paging.AddressListManagementPaging;
import com.gl.ceir.config.feature.common.CustomMappingStrategy;
import com.gl.ceir.config.model.app.AddressEntity;
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
public class AddressListManagementExport {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ConfigurationManagementServiceImpl configurationManagementServiceImpl;
    private AddressListManagementPaging addressListManagementPaging;

    public AddressListManagementExport(ConfigurationManagementServiceImpl configurationManagementServiceImpl, AddressListManagementPaging addressListManagementPaging) {
        this.configurationManagementServiceImpl = configurationManagementServiceImpl;
        this.addressListManagementPaging = addressListManagementPaging;
    }

    @Autowired
    private PropertiesReader propertiesReader;

    public FileDetails export(AddressEntity addressEntity, String subFeature) {
        String fileName = null;
        Writer writer = null;
        Integer pageNo = 0;
        Integer pageSize = Integer.valueOf(configurationManagementServiceImpl.findByTag("file.max-file-record").getValue());

        AddressListManagementFileModel fileModel = null;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

        SystemConfigurationDb filepath = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_dir);
        SystemConfigurationDb link = configurationManagementServiceImpl.findByTag(ConfigTags.file_download_link);
        logger.info("File Path :  [" + filepath + "] & Link : [" + link + "]");

        StatefulBeanToCsvBuilder<AddressListManagementFileModel> builder = null;
        StatefulBeanToCsv<AddressListManagementFileModel> csvWriter = null;
        List<AddressListManagementFileModel> fileRecords = null;
        CustomMappingStrategy<AddressListManagementFileModel> mappingStrategy = new CustomMappingStrategy<>();

        try {
            List<AddressEntity> list = addressListManagementPaging.findAll(addressEntity, pageNo, pageSize).getContent();
            fileName = LocalDateTime.now().format(dtf2).replace(" ", "_") + "_" + subFeature + ".csv";
            writer = Files.newBufferedWriter(Paths.get(filepath.getValue() + fileName));
            mappingStrategy.setType(AddressListManagementFileModel.class);

            builder = new StatefulBeanToCsvBuilder<>(writer);
            csvWriter = builder.withMappingStrategy(mappingStrategy).withSeparator(',').withQuotechar(CSVWriter.DEFAULT_QUOTE_CHARACTER).build();
            String lang = addressEntity.getLanguage();
            if (!list.isEmpty()) {
                fileRecords = new ArrayList<AddressListManagementFileModel>();
                for (AddressEntity data : list) {
                    fileModel = new AddressListManagementFileModel();
                    fileModel.setCreatedOn(data.getCreatedOn().format(dtf));
                    fileModel.setModifiedOn(data.getModifiedOn().format(dtf));

                    if (lang.equals("en")) {
                        fileModel.setProvince(data.getProvince());
                        fileModel.setDistrict(data.getDistrict());
                        fileModel.setCommune(data.getCommune());
                    } else if (lang.equals("km")) {
                        fileModel.setProvince(data.getProvinceKm());
                        fileModel.setDistrict(data.getDistrictKm());
                        fileModel.setCommune(data.getCommuneKm());
                    }

                    fileRecords.add(fileModel);
                }
                logger.info("Exported data : [" + fileRecords + "]");
                csvWriter.write(fileRecords);
            } else {
                csvWriter.write(new AddressListManagementFileModel());
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
            }
        }
    }
}
