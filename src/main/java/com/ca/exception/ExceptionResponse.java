package com.ca.exception;

import java.util.Date;
import java.util.List;

/**
 * @author shubham gadekar
 * e-gov CDAC NOIDA
 */

public class ExceptionResponse {

	private String companyName="Deamon Thread";
	private Date timeStamp;
	private List<String> error;
	private String details;
	
	public ExceptionResponse() {}

	public ExceptionResponse(Date timeStamp, List<String> error, String details) {
		super();
		this.timeStamp = timeStamp;
		this.error = error;
		this.details = details;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
