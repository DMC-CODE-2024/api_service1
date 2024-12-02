package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "province_db")
public class ProvinceDb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    @Column(name = "modified_on")
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime modifiedOn;
    @Column(name = "province")
    private String province;

    @Column(name = "province_km")
    private String provinceKm;

    private String country = "Cambodia";

    public String getProvinceKm() {
        return provinceKm;
    }

    public void setProvinceKm(String provinceKm) {
        this.provinceKm = provinceKm;
    }

    public long getId() {
        return id;
    }

    public ProvinceDb setId(long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public ProvinceDb setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public ProvinceDb setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ProvinceDb setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        String sb = "ProvinceEntity{" + "id=" + id +
                ", createdOn=" + createdOn +
                ", province='" + province + '\'' +
                ", provinceKm='" + provinceKm + '\'' +
                ", country='" + country + '\'' +
                '}';
        return sb;
    }
}
