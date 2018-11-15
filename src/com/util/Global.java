
package com.util;

public interface Global 
{
	
	//Connecting to localhost without Internet Connection By Using Android localhost ip 10.0.2.2
	//public static final String URL = "http://192.168.1.17:8080/MobNavSys4VisuallyImpairedV1/";
	
	
	public static final String URL = "http://192.168.43.192:8080/MobNavSys4VisuallyImpairedV1/";
	
	//public static final String URL = "http://192.168.0.102:8080/MobNavSys4VisuallyImpairedV1/";
	

	
	// Time Interval To Auto Update Location
	//public static final long TIME_INTERVAL = 10 * 1000;// 1 * 1000 = 1 s (1000ms = 1s) 
	public static final long TIME_INTERVAL = 20 * 1000;// 1 * 1000 = 1 s (1000ms = 1s) 
}

