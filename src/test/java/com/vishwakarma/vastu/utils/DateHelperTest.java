package com.vishwakarma.vastu.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.vishwakarma.vastu.utils.DateHelper;

public class DateHelperTest {
	
	@Test
	public void testSetToStartOfDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse("2012-10-09 01:01:01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		DateHelper.setToStartOfDay(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 0);
		Assert.assertEquals(cal.get(Calendar.MINUTE), 0);
		Assert.assertEquals(cal.get(Calendar.SECOND), 0);
		
	}
	
	@Test
	public void testSetToEndOfDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse("2012-10-09 01:01:01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		DateHelper.setToEndOfDay(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Assert.assertEquals(cal.get(Calendar.HOUR_OF_DAY), 23);
		Assert.assertEquals(cal.get(Calendar.MINUTE), 59);
		Assert.assertEquals(cal.get(Calendar.SECOND), 59);
		
	}
}
