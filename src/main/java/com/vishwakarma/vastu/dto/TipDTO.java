package com.vishwakarma.vastu.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vishwakarma.vastu.model.Tip;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(Include.NON_NULL)
public class TipDTO implements Serializable {

	private String code;
	
	private String tipDescription;

	private String tipTitle;
	
	TipDTO() {
		
	}
	
	public TipDTO(Tip tip) {
		setCode(tip.getCode());
		setTipDescription(tip.getTipDescription());
		setTipTitle(tip.getTipTitle());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTipDescription() {
		return tipDescription;
	}

	public void setTipDescription(String tipDescription) {
		this.tipDescription = tipDescription;
	}

	public String getTipTitle() {
		return tipTitle;
	}

	public void setTipTitle(String tipTitle) {
		this.tipTitle = tipTitle;
	}
}
