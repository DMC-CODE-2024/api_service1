package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class RuleEngineMappingFileModel {

	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	@CsvBindByName(column = "Rule Name")
	@CsvBindByPosition(position = 2)
	private String name;
	@CsvBindByName(column = "Feature Name")
	@CsvBindByPosition(position = 3)
	private String feature;
	@CsvBindByName(column = "UserType")
	@CsvBindByPosition(position = 4)
	private String userType;
	@CsvBindByName(column = "Order")
	@CsvBindByPosition(position = 5)
	private Integer ruleOrder;
	@CsvBindByName(column = "Action in Grace Period")
	@CsvBindByPosition(position = 6)
	private String graceAction;
	@CsvBindByName(column = "Action in Post Grace Period")
	@CsvBindByPosition(position = 7)
	private String postGraceAction;
	@CsvBindByName(column = "Move to in Grace Period")
	@CsvBindByPosition(position = 8)
	private String failedRuleActionGrace;
	@CsvBindByName(column = "Move to Post Grace Period")
	@CsvBindByPosition(position = 9)
	private String failedRuleActionPostGrace;
	@CsvBindByName(column = "Expected Output")
	@CsvBindByPosition(position = 10)
	private String output;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RuleEngineMappingFileModel [createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", name=");
		builder.append(name);
		builder.append(", feature=");
		builder.append(feature);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", ruleOrder=");
		builder.append(ruleOrder);
		builder.append(", graceAction=");
		builder.append(graceAction);
		builder.append(", postGraceAction=");
		builder.append(postGraceAction);
		builder.append(", failedRuleActionGrace=");
		builder.append(failedRuleActionGrace);
		builder.append(", failedRuleActionPostGrace=");
		builder.append(failedRuleActionPostGrace);
		builder.append(", output=");
		builder.append(output);
		builder.append("]");
		return builder.toString();
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Integer getRuleOrder() {
		return ruleOrder;
	}

	public void setRuleOrder(Integer ruleOrder) {
		this.ruleOrder = ruleOrder;
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

	public String getFailedRuleActionGrace() {
		return failedRuleActionGrace;
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

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
}
