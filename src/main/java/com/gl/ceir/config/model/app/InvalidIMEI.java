package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eirs_invalid_imei")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InvalidIMEI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdOn;

    @Column(name = "imei")
    private String imei;

    @Column(name = "actual_imei")
    private String actualImei;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getActualImei() {
        return actualImei;
    }

    public void setActualImei(String actualImei) {
        this.actualImei = actualImei;
    }


    @Override
    public String toString() {
        return "InvalidIMEI{" +
                "id=" + id +
                ", createdOn=" + createdOn +
                ", imei='" + imei + '\'' +
                ", actualImei='" + actualImei + '\'' +
                '}';
    }
}
