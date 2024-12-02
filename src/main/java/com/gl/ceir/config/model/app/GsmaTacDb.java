/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gl.ceir.config.model.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity   
public class GsmaTacDb {    

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	@Column
	private Integer statusCode;
	private Integer status;

	@Column
	private String statusMessage;
	private String deviceId;
	
	@Column(name="band_name")
	private String brandName;
	
	private String modelName;
	private String internalModelName;
	private String marketingName; 
	private String equipmentType; 
	private String simSupport; 
	private String nfc; 
	private String wlan;
	private String bluetooth; 
	private String lpwan;
	private String manufacturerOrApplicant;
	private String tacApprovedDate;
	private String gsmaApprovedTac;
	private String created_on;
	private String updated_on;
	private String deviceCertifybody; 
	private String radioInterface;
	private String operatingSystem;
	
	@Transient
	private Long imei;
	@Transient
	private Long imsi;
	@Transient
	private Long msisdn;
	@Transient
	private  String identifierType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
	public String getInternalModelName() {
		return internalModelName;
	}
	public void setInternalModelName(String internalModelName) {
		this.internalModelName = internalModelName;
	}
	public String getMarketingName() {
		return marketingName;
	}
	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getSimSupport() {
		return simSupport;
	}
	public void setSimSupport(String simSupport) {
		this.simSupport = simSupport;
	}
	public String getLpwan() {
		return lpwan;
	}
	public void setLpwan(String lpwan) {
		this.lpwan = lpwan;
	}
	public String getTacApprovedDate() {
		return tacApprovedDate;
	}
	public void setTacApprovedDate(String tacApprovedDate) {
		this.tacApprovedDate = tacApprovedDate;
	}
	public String getGsmaApprovedTac() {
		return gsmaApprovedTac;
	}
	public void setGsmaApprovedTac(String gsmaApprovedTac) {
		this.gsmaApprovedTac = gsmaApprovedTac;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}
	public String getDeviceCertifybody() {
		return deviceCertifybody;
	}
	public void setDeviceCertifybody(String deviceCertifybody) {
		this.deviceCertifybody = deviceCertifybody;
	}
	public String getRadioInterface() {
		return radioInterface;
	}
	public void setRadioInterface(String radioInterface) {
		this.radioInterface = radioInterface;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public String getNfc() {
		return nfc;
	}
	public void setNfc(String nfc) {
		this.nfc = nfc;
	}
	public String getWlan() {
		return wlan;
	}
	public void setWlan(String wlan) {
		this.wlan = wlan;
	}
	public String getBluetooth() {
		return bluetooth;
	}
	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}
	public String getManufacturerOrApplicant() {
		return manufacturerOrApplicant;
	}
	public void setManufacturerOrApplicant(String manufacturerOrApplicant) {
		this.manufacturerOrApplicant = manufacturerOrApplicant;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Long getImei() {
		return imei;
	}
	public void setImei(Long imei) {
		this.imei = imei;
	}
	public Long getImsi() {
		return imsi;
	}
	public void setImsi(Long imsi) {
		this.imsi = imsi;
	}
	public Long getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(Long msisdn) {
		this.msisdn = msisdn;
	}
	public String getIdentifierType() {
		return identifierType;
	}
	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GsmaTacDb [id=");
		builder.append(id);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", status=");
		builder.append(status);
		builder.append(", statusMessage=");
		builder.append(statusMessage);
		builder.append(", deviceId=");
		builder.append(deviceId);
		builder.append(", brandName=");
		builder.append(brandName);
		builder.append(", modelName=");
		builder.append(modelName);
		builder.append(", internalModelName=");
		builder.append(internalModelName);
		builder.append(", marketingName=");
		builder.append(marketingName);
		builder.append(", equipmentType=");
		builder.append(equipmentType);
		builder.append(", simSupport=");
		builder.append(simSupport);
		builder.append(", nfc=");
		builder.append(nfc);
		builder.append(", wlan=");
		builder.append(wlan);
		builder.append(", bluetooth=");
		builder.append(bluetooth);
		builder.append(", lpwan=");
		builder.append(lpwan);
		builder.append(", manufacturerOrApplicant=");
		builder.append(manufacturerOrApplicant);
		builder.append(", tacApprovedDate=");
		builder.append(tacApprovedDate);
		builder.append(", gsmaApprovedTac=");
		builder.append(gsmaApprovedTac);
		builder.append(", created_on=");
		builder.append(created_on);
		builder.append(", updated_on=");
		builder.append(updated_on);
		builder.append(", deviceCertifybody=");
		builder.append(deviceCertifybody);
		builder.append(", radioInterface=");
		builder.append(radioInterface);
		builder.append(", operatingSystem=");
		builder.append(operatingSystem);
		builder.append("]");
		return builder.toString();
	}
	
}