package com.cis.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertyUtil {

	public static String getPropertiesValue(String key) {
		ResourceBundle resource = ResourceBundle.getBundle("project");
		String value = resource.getString(key);
		return value == null ? "" : value;
	}
	
	public static Map<String,String> getPropertiesAllValue(){
		Map<String,String> map = new HashMap<String,String>();
		ResourceBundle resource = ResourceBundle.getBundle("project");
		Enumeration<String> keys = resource.getKeys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			map.put(key, resource.getString(key));
		}
		return map;
	} 
}