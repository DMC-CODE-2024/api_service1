package com.gl.ceir.config.model.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mobile_device_repository")
public class MDRModel {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="device_id", length=8, columnDefinition="varchar(8) DEFAULT '0'", unique=true)
	private String deviceId = "0";

	@Column(name="brand_name")
	private String brandName;

	@Column(name="model_name")
	private String modelName;

	@Column(name="device_type")
	private String deviceType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public String toString() {
		return "MDRModel{" +
				"id=" + id +
				", deviceId='" + deviceId + '\'' +
				", brandName='" + brandName + '\'' +
				", modelName='" + modelName + '\'' +
				", deviceType='" + deviceType + '\'' +
				'}';
	}
}
