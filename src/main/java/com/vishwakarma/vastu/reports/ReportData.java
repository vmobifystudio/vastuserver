package com.vishwakarma.vastu.reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ReportData implements JRDataSource {

	//protected Map<String, List<Object>> reportData = new HashMap<String, List<Object>>();
	protected List<Map<String, Object>> reportData = new ArrayList<Map<String,Object>>();
	
	private int currentRecordIndex = 0;
	
	@Override
	public Object getFieldValue(JRField arg0) throws JRException {
		return reportData.get(currentRecordIndex).get(arg0);
		//return reportData.get(arg0.getName()).get(currentRecordIndex);
	}

	public void add(Map<String, Object> row) {
		
	}
	
	public void addAll(List<Object> beanList) {
		
	}
	
	public void addBean(Object bean) {
		
	}
	
	@Override
	public boolean next() throws JRException {
		currentRecordIndex++;
		if (currentRecordIndex>=reportData.size()) {
			return false;
		}
		return true;
	}

}
