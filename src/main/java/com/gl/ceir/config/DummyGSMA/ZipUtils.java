package com.gl.ceir.config.DummyGSMA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public ByteArrayInputStream createZipFileInMemory() throws IOException {
        String jsonlData = "{\"tac\":\"00100100\",\"manufacturer\":\"Mitsubishi\",\"modelName\":\"G410\",\"marketingName\":\"G410 \",\"brandName\":\"Not Known\",\"allocationDate\":\"19-Apr-2000\",\"organisationId\":\"208/MANU/205049\",\"deviceType\":\"Handheld \",\"bluetooth\":\"YES \",\"nfc\":\"Not Known\",\"wlan\":\"Not Known\",\"removableUICC\":\"00\",\"removableEUICC\":\"00\",\"nonremovableUICC\":\"00\",\"nonremovableEUICC\":\"00\",\"networkSpecificIdentifier\":\"00\",\"simSlot\":\"00\",\"imeiQuantity\":\"00\",\"operatingSystem\":\"Not Known\",\"oem\":\"Not Known\",\"bandDetails\":[{\"categoryName\":\"2G/3G\",\"bandInfo\":[{\"bandName\":\"GSM 900,GSM 1800\"}]},{\"categoryName\":\"Radio Interface\",\"bandInfo\":[{\"bandName\":\"NONE\"}]}]}";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            zipOutputStream.putNextEntry(new ZipEntry("DeviceDatabase.jsonl"));
            zipOutputStream.write(jsonlData.getBytes());
            zipOutputStream.closeEntry();
        }

        byte[] zipData = byteArrayOutputStream.toByteArray();
        return new ByteArrayInputStream(zipData);
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

    public String unzipData(ByteArrayInputStream inputStream) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry = zipInputStream.getNextEntry();
            if (entry != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = zipInputStream.read(buffer)) > 0) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
                return byteArrayOutputStream.toString(); // Convert to String
            }
        }
        return null; // Return null if no entry found
    }
}
