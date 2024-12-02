package com.gl.ceir.config.model.app;

public class GenericResponseObject {
	private int id;
   private String createdOn;
   private String modifiedOn;
   private String category;
   private String name;
   private String logo;
   private String link;
@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("GenericResponseObject [id=");
	builder.append(id);
	builder.append(", createdOn=");
	builder.append(createdOn);
	builder.append(", modifiedOn=");
	builder.append(modifiedOn);
	builder.append(", category=");
	builder.append(category);
	builder.append(", name=");
	builder.append(name);
	builder.append(", logo=");
	builder.append(logo);
	builder.append(", link=");
	builder.append(link);
	builder.append("]");
	return builder.toString();
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
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
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLogo() {
	return logo;
}
public void setLogo(String logo) {
	this.logo = logo;
}
public String getLink() {
	return link;
}
public void setLink(String link) {
	this.link = link;
}
}
