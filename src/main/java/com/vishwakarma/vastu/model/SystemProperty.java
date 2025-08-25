package com.vishwakarma.vastu.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="SystemProperty")
@Table(name="SystemProperty")
public class SystemProperty   {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@NotEmpty
	private String propName;
	
	private String propValue;

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	
	public static final String MASTER_DATA_VERSION = "MASTER_DATA_VERSION";
	public static final String DEFAULT_TIMEZONE = "DEFAULT_TIMEZONE";
	public static final String API_KEY = "API_KEY";
	public static final String ORG_NAME = "ORG_NAME";
	
	/* Reports */
	public static final String SCAN_WINDOW_FACULTY_IN_LEFT = "SCAN_WINDOW_FACULTY_IN_LEFT";
	public static final String SCAN_WINDOW_FACULTY_IN_RIGHT = "SCAN_WINDOW_FACULTY_IN_RIGHT";
	public static final String SCAN_WINDOW_FACULTY_OUT_LEFT = "SCAN_WINDOW_FACULTY_OUT_LEFT";
	public static final String SCAN_WINDOW_FACULTY_OUT_RIGHT = "SCAN_WINDOW_FACULTY_OUT_RIGHT";
	public static final String SCAN_WINDOW_STUDENT_LEFT ="SCAN_WINDOW_STUDENT_LEFT";
	public static final String SCAN_WINDOW_STUDENT_RIGHT = "SCAN_WINDOW_STUDENT_RIGHT";
	
	public static final String ENABLE_SCHEDULED_REPORTS = "ENABLE_SCHEDULED_REPORTS";
	public static final String STUDENT_REPORT_FREQUENCY = "STUDENT_REPORT_FREQUENCY";
	public static final String FACULTY_REPORT_FREQUENCY = "FACULTY_REPORT_FREQUENCY";
	
	public static final String DISABLE_PUNCTUALITY_REMARK = "DISABLE_PUNCTUALITY_REMARK";
	
	
	/* Messaging */
	public static final String ENABLE_SCAN_NOTIFICATION_SMS = "ENABLE_SCAN_NOTIFICATION_SMS";
	public static final String ENABLE_SCAN_NOTIFICATION_EMAIL = "ENABLE_SCAN_NOTIFICATION_EMAIL";
	
	public static final String SMS_SERVICE_CREDITS_THRESHOLD = "SMS_SERVICE_CREDITS_THRESHOLD";
	public static final String EMAIL_SERVICE_CREDITS_THRESHOLD = "EMAIL_SERVICE_CREDITS_THRESHOLD";

	public static final String TEMPLATE_SMS_SCAN_STUDENT = "TEMPLATE_SMS_SCAN_STUDENT";
	public static final String TEMPLATE_SMS_ABSENT_STUDENT = "TEMPLATE_SMS_ABSENT_STUDENT";
	public static final String TEMPLATE_SMS_SCAN_FACULTY = "TEMPLATE_SMS_SCAN_FACULTY";
	
	public static final String ENABLE_ABSENTEE_NOTIFICATION = "ENABLE_ABSENTEE_NOTIFICATION";
	public static final String ABSENTEE_NOTIFICATION_INTERVAL = "ABSENTEE_NOTIFICATION_INTERVAL";
	public static final String ABSENTEE_SESSION_AGE_LIMIT = "ABSENTEE_SESSION_AGE_LIMIT";
	
	public static final String ENABLE_PASSWORD_EMAIL = "ENABLE_PASSWORD_EMAIL";
	public static final String ENABLE_PASSWORD_SMS = "ENABLE_PASSWORD_SMS";
	
}
