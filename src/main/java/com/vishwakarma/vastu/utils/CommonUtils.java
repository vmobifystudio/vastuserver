package com.vishwakarma.vastu.utils;

/**
 * @author Vishal
 *
 */
public class CommonUtils {

	public static final Double EARTH_RADIUS = 6378.137;

	/**
	 * http://stackoverflow.com/questions/639695/how-to-convert-latitude-or-longitude-to-meters
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 * @author Vishal
	 */
	public static Double getLatLngDifferenceInKms(Double lat1, Double lng1, Double lat2, Double lng2) {
		
		Double latDifference = (lat2 - lat1) * Math.PI / 180;
		Double lngDifference = (lng2 - lng1) * Math.PI / 180;
		Double a = Math.sin(latDifference/2) * Math.sin(latDifference/2) + 
				 Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
				 Math.sin(lngDifference/2) * Math.sin(lngDifference/2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		Double d = EARTH_RADIUS * c;
		return d;
	}
}
