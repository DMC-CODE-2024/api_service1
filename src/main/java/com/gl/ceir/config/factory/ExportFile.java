package com.gl.ceir.config.factory;

import java.time.format.DateTimeFormatter;

import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.SystemConfigurationDb;

public interface ExportFile {
	public FileDetails export(FilterRequest filterRequest, String source, DateTimeFormatter dtf, DateTimeFormatter dtf2,
			String filePath, SystemConfigurationDb link);
}
