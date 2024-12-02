package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class PoliceDetailExport {



    @CsvBindByName(column = "Province")
    @CsvBindByPosition(position = 0)
    private String province;

    @CsvBindByName(column = "District")
    @CsvBindByPosition(position = 1)
    private String district;

    @CsvBindByName(column = "Commune")
    @CsvBindByPosition(position = 2)
    private String commune;

    @CsvBindByName(column = "Police Station")
    @CsvBindByPosition(position = 3)
    private String policeStation;

    @CsvBindByName(column = "Name")
    @CsvBindByPosition(position = 4)
    private String name;

    @CsvBindByName(column = "Contact Number")
    @CsvBindByPosition(position = 5)
    private String phoneNumber;

    public PoliceDetailExport(String province, String district, String commune, String policeStation, String name,String phoneNumber ) {
        super();
        this.province = province;
        this.district = district;
        this.commune = commune;
        this.policeStation = policeStation;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public PoliceDetailExport() {

    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PoliceDetailExport{" +
                "province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", commune='" + commune + '\'' +
                ", policeStation='" + policeStation + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
