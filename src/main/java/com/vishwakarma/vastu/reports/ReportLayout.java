package com.vishwakarma.vastu.reports;

import java.util.ArrayList;
import java.util.List;

public class ReportLayout {

	static class Column {
		String name;
		String field;
		String className = String.class.getName();
		int width = 80;
		
		public Column(String name, String field, String className, int width) {
			super();
			this.name = name;
			this.field = field;
			this.className = className;
			this.width = width;
		}
		
		public Column(String name, String field) {
			super();
			this.name = name;
			this.field = field;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}
	
		
	}
	
	private int pageWidth = 800;
	private int pageHeight = 1000;
	private String title;
	private String subTitle;	
	private List<Column> columns = new ArrayList<ReportLayout.Column>();
	
	public int getPageWidth() {
		return pageWidth;
	}
	public ReportLayout setPageWidth(int pageWidth) {
		this.pageWidth = pageWidth;
		return this;
	}
	public int getPageHeight() {
		return pageHeight;
	}
	public ReportLayout setPageHeight(int pageHeight) {
		this.pageHeight = pageHeight;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public ReportLayout setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public ReportLayout setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		return this;
	}
	
	public ReportLayout addColumn (String name, String field, String className, int width) {
		columns.add(new Column(name, field, className, width));
		return this;
	}
	
	public List<Column> getColumns() {
		return this.columns;
	}
	
}
