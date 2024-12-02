package com.gl.ceir.config.feature.addressmanagement.csv_file_model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class AddressListManagementFileModel {
    @CsvBindByName(column = "Created On")
    @CsvBindByPosition(position = 0)
    private String createdOn;
    @CsvBindByName(column = "Modified On")
    @CsvBindByPosition(position = 1)
    private String modifiedOn;
    @CsvBindByName(column = "Province")
    @CsvBindByPosition(position = 2)
    private String province;
    @CsvBindByName(column = "District")
    @CsvBindByPosition(position = 3)
    private String district;

    @CsvBindByName(column = "Commune")
    @CsvBindByPosition(position = 4)
    private String commune;

    public String getCreatedOn() {
        return createdOn;
    }

    public AddressListManagementFileModel setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public AddressListManagementFileModel setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public AddressListManagementFileModel setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public AddressListManagementFileModel setDistrict(String district) {
        this.district = district;
        return this;
    }

    public String getCommune() {
        return commune;
    }

    public AddressListManagementFileModel setCommune(String commune) {
        this.commune = commune;
        return this;
    }
}
