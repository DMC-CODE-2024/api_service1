package com.gl.ceir.config.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class GSMADummyDownload {

    private static final Logger logger = LogManager.getLogger(GSMADummyDownload.class);
    @PostMapping("/downloadJsonZip")
    public void downloadZipFile(@RequestParam String username,
                                                               @RequestParam String password) throws IOException {

        ByteArrayInputStream zipInMemory = null;
        String zipFilePath = "/u02/eirsdata/gsma_tac_module/input/DeviceDatabase.zip"; // Change the path as needed
         // Save the ZIP file to the specified location
        saveZipToFile(zipFilePath);

    }

    public void saveZipToFile(String filePath) throws IOException {
        try (ByteArrayInputStream zipInputStream = createZipFileInMemory();
             FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("ZIP file saved to: " + filePath);
    }

    public ByteArrayInputStream createZipFileInMemory() throws IOException {
        //String jsonlData = "{\"tac\":\"00100100\",\"manufacturer\":\"Mitsubishi\",\"modelName\":\"G410\",\"marketingName\":\"G410 \",\"brandName\":\"Not Known\",\"allocationDate\":\"19-Apr-2000\",\"organisationId\":\"208/MANU/205049\",\"deviceType\":\"Handheld \",\"bluetooth\":\"NO \",\"nfc\":\"Not Known\",\"wlan\":\"Not Known\",\"removableUICC\":\"00\",\"removableEUICC\":\"00\",\"nonremovableUICC\":\"00\",\"nonremovableEUICC\":\"00\",\"networkSpecificIdentifier\":\"00\",\"simSlot\":\"00\",\"imeiQuantity\":\"00\",\"operatingSystem\":\"Not Known\",\"oem\":\"Not Known\",\"bandDetails\":[{\"categoryName\":\"2G/3G\",\"bandInfo\":[{\"bandName\":\"GSM 900,GSM 1800\"}]},{\"categoryName\":\"Radio Interface\",\"bandInfo\":[{\"bandName\":\"NONE\"}]}]}";
        String jsonlData = "[{\"tac\":\"00100100\",\"manufacturer\":\"Mitsubishi\",\"modelName\":\"G410\",\"marketingName\":\"G410 \",\"brandName\":\"Not Known\",\"allocationDate\":\"19-Apr-2000\",\"organisationId\":\"208/MANU/205049\",\"deviceType\":\"Handheld \",\"bluetooth\":\"NO \",\"nfc\":\"Not Known\",\"wlan\":\"Not Known\",\"removableUICC\":\"00\",\"removableEUICC\":\"00\",\"nonremovableUICC\":\"00\",\"nonremovableEUICC\":\"00\",\"networkSpecificIdentifier\":\"00\",\"simSlot\":\"00\",\"imeiQuantity\":\"00\",\"operatingSystem\":\"Not Known\",\"oem\":\"Not Known\",\"bandDetails\":[{\"categoryName\":\"2G/3G\",\"bandInfo\":[{\"bandName\":\"GSM 900,GSM 1800\"}]},{\"categoryName\":\"Radio Interface\",\"bandInfo\":[{\"bandName\":\"NONE\"}]}]},{\"tac\":\"00100101\",\"manufacturer\":\"Mitsubishi\",\"modelName\":\"G410\",\"marketingName\":\"G410 \",\"brandName\":\"Samsung\",\"allocationDate\":\"19-Apr-2000\",\"organisationId\":\"208/MANU/205049\",\"deviceType\":\"Handheld \",\"bluetooth\":\"NO \",\"nfc\":\"Not Known\",\"wlan\":\"Not Known\",\"removableUICC\":\"00\",\"removableEUICC\":\"00\",\"nonremovableUICC\":\"00\",\"nonremovableEUICC\":\"00\",\"networkSpecificIdentifier\":\"00\",\"simSlot\":\"00\",\"imeiQuantity\":\"00\",\"operatingSystem\":\"Not Known\",\"oem\":\"Not Known\",\"bandDetails\":[{\"categoryName\":\"2G/3G\",\"bandInfo\":[{\"bandName\":\"GSM 900,GSM 1800\"}]},{\"categoryName\":\"Radio Interface\",\"bandInfo\":[{\"bandName\":\"NONE\"}]}]},{\"tac\":\"00100102\",\"manufacturer\":\"Mitsubishi\",\"modelName\":\"G410\",\"marketingName\":\"G410 \",\"brandName\":\"Apple\",\"allocationDate\":\"19-Apr-2000\",\"organisationId\":\"208/MANU/205049\",\"deviceType\":\"Handheld \",\"bluetooth\":\"NO \",\"nfc\":\"Not Known\",\"wlan\":\"Not Known\",\"removableUICC\":\"00\",\"removableEUICC\":\"00\",\"nonremovableUICC\":\"00\",\"nonremovableEUICC\":\"00\",\"networkSpecificIdentifier\":\"00\",\"simSlot\":\"00\",\"imeiQuantity\":\"00\",\"operatingSystem\":\"Not Known\",\"oem\":\"Not Known\",\"bandDetails\":[{\"categoryName\":\"2G/3G\",\"bandInfo\":[{\"bandName\":\"GSM 900,GSM 1800\"}]},{\"categoryName\":\"Radio Interface\",\"bandInfo\":[{\"bandName\":\"NONE\"}]}]}]";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            zipOutputStream.putNextEntry(new ZipEntry("DeviceDatabase.jsonl"));
            zipOutputStream.write(jsonlData.getBytes());
            zipOutputStream.closeEntry();
        }

        byte[] zipData = byteArrayOutputStream.toByteArray();
        return new ByteArrayInputStream(zipData);
    }
}

