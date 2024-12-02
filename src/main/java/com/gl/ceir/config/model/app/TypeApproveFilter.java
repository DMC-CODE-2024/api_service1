package com.gl.ceir.config.model.app;

import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

public class TypeApproveFilter {
	//@Type(type="date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate  startDate;
	
	//@Type(type="date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate   endDate;
	
	private Integer  status;
	private String  tac;
	private long userId;
	private String searchString;
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTac() {
		return tac;
	}
	public void setTac(String tac) {
		this.tac = tac;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	@Override
	public String toString() {
		return "TypeApproveFilter [startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", tac="
				+ tac + ", userId=" + userId + ", searchString="+searchString+"]";
	}
	
}
