package com.gl.ceir.config.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation. constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gl.ceir.config.model.constants.RulesNames;

//import io.swagger.annotations.ApiModel;

//@ApiModel
@Entity
@Audited
@AuditTable(value = "feature_rule_aud", schema = "aud")
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name = "feature_rule")
public class RuleEngineMapping implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@Column(updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime modifiedOn;
	
//	@CreationTimestamp
//	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
//	private LocalDateTime createdOn;
//
//	@UpdateTimestamp
//	private LocalDateTime modifiedOn;

	@NotNull
	@Column(length = 20)
	private String feature;

	@NotNull
	//@Enumerated(EnumType.STRING)
	private String name; 
	
	@NotNull
	@Column(length = 20)
	private String graceAction;
	
	@NotNull
	@Column(length = 20)
	private String postGraceAction;
	
	@NotNull
	private Integer ruleOrder;
	
	@NotNull
	@Column(length = 20)
	private String userType;
	
	@Column(length = 10)
	private String failedRuleActionGrace;
	
	@Column(length = 10)
	private String failedRuleActionPostGrace;

	@Column(length = 1)
	private String output;
	
	
	private String modifiedBy;
	
	//@Transient parameters 	
	@Transient
	public String userName;
	@Transient
	private Integer featureId;
	@Transient
	private Integer userTypeId;
	@Transient
	private String roleType;
	
	@Transient
	private String publicIp;
	
	@Transient
	private String browser;
	@Transient
	private String existOrNot;
	
	@Transient
	private long userId;
	
	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getGraceAction() {
		return graceAction;
	}

	public void setGraceAction(String graceAction) {
		this.graceAction = graceAction;
	}

	public String getPostGraceAction() {
		return postGraceAction;
	}

	public void setPostGraceAction(String postGraceAction) {
		this.postGraceAction = postGraceAction;
	}

	

	public Integer getRuleOrder() {
		return ruleOrder;
	}

	
	public void setRuleOrder(Integer ruleOrder) {
		this.ruleOrder = ruleOrder;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFailedRuleActionGrace() {
		return failedRuleActionGrace;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setFailedRuleActionGrace(String failedRuleActionGrace) {
		this.failedRuleActionGrace = failedRuleActionGrace;
	}

	public String getFailedRuleActionPostGrace() {
		return failedRuleActionPostGrace;
	}

	public void setFailedRuleActionPostGrace(String failedRuleActionPostGrace) {
		this.failedRuleActionPostGrace = failedRuleActionPostGrace;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getExistOrNot() {
		return existOrNot;
	}

	public void setExistOrNot(String existOrNot) {
		this.existOrNot = existOrNot;
	}

	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleEngineMapping [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", name=");
		builder.append(name);
		builder.append(", graceAction=");
		builder.append(graceAction);
		builder.append(", postGraceAction=");
		builder.append(postGraceAction);
		builder.append(", ruleOrder=");
		builder.append(ruleOrder);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", failedRuleActionGrace=");
		builder.append(failedRuleActionGrace);
		builder.append(", failedRuleActionPostGrace=");
		builder.append(failedRuleActionPostGrace);
		builder.append(", output=");
		builder.append(output);
		builder.append(", modifiedBy=");
		builder.append(modifiedBy);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", userTypeId=");
		builder.append(userTypeId);
		builder.append(", roleType=");
		builder.append(roleType);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append(", browser=");
		builder.append(browser);
		builder.append(", existOrNot=");
		builder.append(existOrNot);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

	

	 

	 

}
