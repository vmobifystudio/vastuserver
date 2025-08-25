package com.vishwakarma.vastu.reports;

import java.util.List;
import java.util.Map;

public interface ReportDataProvider {

	public List<? extends Object> fetchReportData(Map<String, Object> params);
	public ReportLayout getReportLayout(Map<String, Object> params);
	public String getTitle(Map<String, Object> params);
	public String getSubTitle(Map<String, Object> params);
	public String getFooter(Map<String, Object> params);
	
}
