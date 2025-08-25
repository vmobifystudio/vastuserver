package com.vishwakarma.vastu.utils;

import junit.framework.Assert;

import org.junit.Test;

import com.vishwakarma.vastu.utils.StringHelper;

public class StringHelperTest {

	@Test
	public void testArrayToCsv() {
		String[] elems = {"one", "two", "three"};
		Assert.assertEquals("one,two,three", StringHelper.arrayToCsv(elems));	
		Assert.assertEquals("", StringHelper.arrayToCsv(null));
		Assert.assertEquals("one", StringHelper.arrayToCsv(new String[] {"one"}));
	}
}
