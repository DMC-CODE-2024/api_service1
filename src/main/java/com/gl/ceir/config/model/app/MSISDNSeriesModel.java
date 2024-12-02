package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.ceir.config.dto.AuditTraildDTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operator_series")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MSISDNSeriesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime modifiedOn;



	@Column(name = "series_start")
	private int seriesStart;


	@Column(name = "series_end")
	private int seriesEnd;


	@Column(name = "series_type")
	private String seriesType;

	@Column(name = "operator_name")
	private String operatorName;

	@Column(name = "remark")
	private String remarks;

	@Column(name = "user_id")
	private Long userId;
	@Column(name = "length")
	private String length;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id" ,insertable = false, updatable = false)
	@JsonProperty(value = "user", access = JsonProperty.Access.READ_ONLY)
	private User user;

	@Transient
	private String userName;

	@Transient
	private AuditTraildDTO auditTraildDTO;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

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

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public int getSeriesStart() {
		return seriesStart;
	}

	public void setSeriesStart(int seriesStart) {
		this.seriesStart = seriesStart;
	}

	public int getSeriesEnd() {
		return seriesEnd;
	}

	public void setSeriesEnd(int seriesEnd) {
		this.seriesEnd = seriesEnd;
	}

	public String getSeriesType() {
		return seriesType;
	}

	public void setSeriesType(String seriesType) {
		this.seriesType = seriesType;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public AuditTraildDTO getAuditTraildDTO() {
		return auditTraildDTO;
	}

	public void setAuditTraildDTO(AuditTraildDTO auditTraildDTO) {
		this.auditTraildDTO = auditTraildDTO;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "MSISDNSeriesModel{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", modifiedOn=" + modifiedOn +
				", seriesStart=" + seriesStart +
				", seriesEnd=" + seriesEnd +
				", seriesType='" + seriesType + '\'' +
				", operatorName='" + operatorName + '\'' +
				", remarks='" + remarks + '\'' +
				", userId=" + userId +
				", length='" + length + '\'' +
				", user=" + user +
				", userName='" + userName + '\'' +
				", auditTraildDTO=" + auditTraildDTO +
				'}';
	}
}
