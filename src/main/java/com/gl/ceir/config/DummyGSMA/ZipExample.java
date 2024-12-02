package com.gl.ceir.config.DummyGSMA;

import java.io.IOException;

public class ZipExample {
    public static void main(String[] args) {
        ZipUtils zipUtils = new ZipUtils();
        
        try {
            // Define the path to save the ZIP file
            String zipFilePath = "D:\\ZipFile\\DeviceDatabase.zip"; // Change the path as needed
            
            // Save the ZIP file to the specified location
            zipUtils.saveZipToFile(zipFilePath);
            
            // Optionally, unzip the data back to a string (if needed)
            // ByteArrayInputStream zipStream = new ByteArrayInputStream(zipData);
            // String jsonData = zipUtils.unzipData(zipStream);
            // System.out.println("Unzipped JSON data: " + jsonData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
