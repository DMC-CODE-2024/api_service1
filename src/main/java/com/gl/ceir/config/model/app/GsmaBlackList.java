package com.gl.ceir.config.model.app;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "gsma_blacklist_db")
public class GsmaBlackList {

	@Id
	private Integer id; 
    
    private Integer refcode;
    
    private String deviceid;
    private String partnerid;
    private String branchid;
    private String recordidentifier; 
    private String blackliststatus;
    private String greyliststatus;
    private String manufacturer;
    private String brandname;
    private String marketingname;
    private String modelname;
    private String band;
    private String operatingsys;
    private String nfc;
    private String bluetooth;
    
    @Column(name = "WLAN")
    private String wlan; 
    private String devicetype;
    
    @Column(length = 30, nullable = false, updatable = false)
    private String createdOn;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRefcode() {
		return refcode;
	}

	public void setRefcode(Integer refcode) {
		this.refcode = refcode;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getRecordidentifier() {
		return recordidentifier;
	}

	public void setRecordidentifier(String recordidentifier) {
		this.recordidentifier = recordidentifier;
	}

	public String getBlackliststatus() {
		return blackliststatus;
	}

	public void setBlackliststatus(String blackliststatus) {
		this.blackliststatus = blackliststatus;
	}

	public String getGreyliststatus() {
		return greyliststatus;
	}

	public void setGreyliststatus(String greyliststatus) {
		this.greyliststatus = greyliststatus;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getBrandname() {
		return brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	public String getMarketingname() {
		return marketingname;
	}

	public void setMarketingname(String marketingname) {
		this.marketingname = marketingname;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public String getOperatingsys() {
		return operatingsys;
	}

	public void setOperatingsys(String operatingsys) {
		this.operatingsys = operatingsys;
	}

	public String getNfc() {
		return nfc;
	}

	public void setNfc(String nfc) {
		this.nfc = nfc;
	}

	public String getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(String bluetooth) {
		this.bluetooth = bluetooth;
	}

	public String getWlan() {
		return wlan;
	}

	public void setWlan(String wlan) {
		this.wlan = wlan;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GsmaBlackList [id=");
		builder.append(id);
		builder.append(", refcode=");
		builder.append(refcode);
		builder.append(", deviceid=");
		builder.append(deviceid);
		builder.append(", partnerid=");
		builder.append(partnerid);
		builder.append(", branchid=");
		builder.append(branchid);
		builder.append(", recordidentifier=");
		builder.append(recordidentifier);
		builder.append(", blackliststatus=");
		builder.append(blackliststatus);
		builder.append(", greyliststatus=");
		builder.append(greyliststatus);
		builder.append(", manufacturer=");
		builder.append(manufacturer);
		builder.append(", brandname=");
		builder.append(brandname);
		builder.append(", marketingname=");
		builder.append(marketingname);
		builder.append(", modelname=");
		builder.append(modelname);
		builder.append(", band=");
		builder.append(band);
		builder.append(", operatingsys=");
		builder.append(operatingsys);
		builder.append(", nfc=");
		builder.append(nfc);
		builder.append(", bluetooth=");
		builder.append(bluetooth);
		builder.append(", wlan=");
		builder.append(wlan);
		builder.append(", devicetype=");
		builder.append(devicetype);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append("]");
		return builder.toString();
	}
   
}
