package com.vishwakarma.vastu.web;

import java.util.Arrays;

import com.vishwakarma.vastu.model.PickListItem;


public class CustomTags {

	public static boolean contains (Object[] array, Object item) {
		if (null!=array && null!=item) {
			if(item instanceof Integer) {
				Integer[] integers = (Integer[]) array;
				for (Integer integer : integers) {
					if(integer.equals(item)) {
						return true;
					}
				}
				return false;
			} else if(item instanceof Long) {
				Long itemValue = (Long) item;
				Long[] longs = (Long[]) array;
				for (Long integer : longs) {
					if(integer.equals(itemValue)) {
						return true;
					}
				}
			} else if(item instanceof PickListItem){
				String[] stringArray = (String[]) array;
				for (String string : stringArray) {
					if(string.equals(((PickListItem) item).getDisplayValue())) {
						return true;
					}
				}
			} else {
				return Arrays.asList(array).contains(item);
			}
		}
		return false;
	}
}
