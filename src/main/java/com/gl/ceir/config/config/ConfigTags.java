package com.gl.ceir.config.config;

public interface ConfigTags {
	// Message

	// System
	String page_size_for_Notification = "page_size_for_Notification";
	String sample_file_link = "sample_file_link";
	String manuals_link = "manuals_link";
	String manuals_file_name = "manuals_file_name";

	String system_error_filepath = "system_error_filepath";
	String system_error_file_link = "system_error_file_link";

	String system_upload_filepath = "system_upload_filepath";
	String upload_file_link = "upload_file_link";
	
	String new_year_date_register_device = "new_year_date_register_device";
	String grace_period_for_rgister_device = "grace_period_for_rgister_device";
	String GREY_TO_BLACK_MOVE_PERIOD_IN_DAY = "GREY_TO_BLACK_MOVE_PERIOD_IN_DAY";

	//----------------
	String file_download_dir = "file.download-dir";
	String file_download_link = "file.download-link";

	// User
	String default_visa_expiration_days = "default_visa_expiration_days";

	// Policy
	String max_end_user_device_count = "max_end_user_device_count";
    String max_foreigner_end_user_device_count="max_foreigner_end_user_device_count";
   
}
