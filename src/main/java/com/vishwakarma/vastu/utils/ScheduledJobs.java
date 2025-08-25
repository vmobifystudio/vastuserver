package com.vishwakarma.vastu.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vishwakarma.vastu.model.SystemProperty;
import com.vishwakarma.vastu.reports.ReportScheduler;

@Component
public class ScheduledJobs {
	
	private final static Logger log = LoggerFactory.getLogger(ScheduledJobs.class);
	
	@Autowired 
	private SystemPropertyHelper systemPropertyHelper;
	
	@Autowired 
	private ReportScheduler reportScheduler;
	
	@Async
	@Scheduled(cron="0 0 * * * ?")
	public void runHourlyScheduler() {
		
	}
	
	@Async
	@Scheduled(cron="0 0 0 * * ? ")
	public void runDailyScheduler() {

		if (!Boolean.parseBoolean(systemPropertyHelper.getSystemProperty(SystemProperty.ENABLE_SCHEDULED_REPORTS, "false"))) {
			log.info("Scheduled Reports Not Enabled - skipping Daily scheduled Reports");
			return;
		}

		Date currDate = new Date();
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date fromDate = cal.getTime();
		DateHelper.setToStartOfDay(fromDate);
		
		cal.clear();
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date toDate = cal.getTime();
		DateHelper.setToEndOfDay(toDate);
		

		if (systemPropertyHelper.getSystemProperty(SystemProperty.STUDENT_REPORT_FREQUENCY, "").equalsIgnoreCase("daily")) {
			//reportScheduler.generateScheduledReportsForStudents(fromDate, toDate);
		}
		if (systemPropertyHelper.getSystemProperty(SystemProperty.FACULTY_REPORT_FREQUENCY, "").equalsIgnoreCase("daily")) {
			//reportScheduler.generateScheduledReportsForFaculties(fromDate, toDate);
		}
	}

	@Async
	@Scheduled(cron="0 0 0 ? * SUN")
//	@Scheduled(cron="*/5 * * * * ?")
	public void runWeeklyScheduler() {
		
		if (!Boolean.parseBoolean(systemPropertyHelper.getSystemProperty(SystemProperty.ENABLE_SCHEDULED_REPORTS, "false"))) {
			log.info("Scheduled Reports Not Enabled - skipping Weekly scheduled Reports");
			return;
		}
		
		Date currDate = new Date();
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_MONTH, -7);
		Date fromDate = cal.getTime();
		DateHelper.setToStartOfDay(fromDate);
		
		cal.clear();
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date toDate = cal.getTime();
		DateHelper.setToEndOfDay(toDate);
		
		if (systemPropertyHelper.getSystemProperty(SystemProperty.STUDENT_REPORT_FREQUENCY, "").equalsIgnoreCase("weekly")) {
			//reportScheduler.generateScheduledReportsForStudents(fromDate, toDate);
		}
		if (systemPropertyHelper.getSystemProperty(SystemProperty.FACULTY_REPORT_FREQUENCY, "").equalsIgnoreCase("weekly")) {
			//reportScheduler.generateScheduledReportsForFaculties(fromDate, toDate);
		}
	}

//	@Async
//	@Scheduled(cron="0 0 0 1,15 * ?")
//	@Scheduled(cron="*/5 * * * * ?")
	/*	public void runFortNightlyScheduler() {
		
		if (!Boolean.parseBoolean(systemPropertyHelper.getSystemProperty(SystemProperty.ENABLE_SCHEDULED_REPORTS, "false"))) {
			log.info("Scheduled Reports Not Enabled - skipping FortNightly scheduled Reports");
			return;
		}
	
		Date currDate = new Date();
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		Date fromDate = cal.getTime();
		DateHelper.setToStartOfDay(fromDate);
		
		cal.clear();
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date toDate = cal.getTime();
		DateHelper.setToEndOfDay(toDate);
		
		if (systemPropertyHelper.getSystemProperty(SystemProperty.STUDENT_REPORT_FREQUENCY, "").equalsIgnoreCase("hourly")) {
			reportScheduler.generateScheduledReportsForStudents(fromDate, toDate);
		}
		if (systemPropertyHelper.getSystemProperty(SystemProperty.FACULTY_REPORT_FREQUENCY, "").equalsIgnoreCase("hourly")) {
			reportScheduler.generateScheduledReportsForFaculties(fromDate, toDate);
		}
	}*/
	
	@Async
	@Scheduled(cron="0 0 0 1 * ?")
//	@Scheduled(cron="*/5 * * * * ?")
	public void runMonthlyReportScheduler() {
		
		if (!Boolean.parseBoolean(systemPropertyHelper.getSystemProperty(SystemProperty.ENABLE_SCHEDULED_REPORTS, "false"))) {
			log.info("Scheduled Reports Not Enabled - skipping Monthly scheduled Reports");
			return;
		}

		Date currDate = new Date();
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, -1);
		Date fromDate = cal.getTime();
		DateHelper.setToStartOfDay(fromDate);
		
		cal.clear();
		cal.setTime(currDate);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date toDate = cal.getTime();
		DateHelper.setToEndOfDay(toDate);
		
		if (systemPropertyHelper.getSystemProperty(SystemProperty.STUDENT_REPORT_FREQUENCY, "").equalsIgnoreCase("monthly")) {
			//reportScheduler.generateScheduledReportsForStudents(fromDate, toDate);
		}
		if (systemPropertyHelper.getSystemProperty(SystemProperty.FACULTY_REPORT_FREQUENCY, "").equalsIgnoreCase("monthly")) {
			//reportScheduler.generateScheduledReportsForFaculties(fromDate, toDate);
		}
	}
	
	/*private void runReportScheduledJobs(String jobFrequency) {
		//TODO: report scheduling logic ...
	}*/

}
