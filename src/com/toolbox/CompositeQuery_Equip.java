package com.toolbox;
import java.util.*;
public class CompositeQuery_Equip {

	public static String get_aCondition_From_Oracle(String columnName,String value) {
		String condition = null;
		
		if("ep_name".equals(columnName)||"epc_no".equals(columnName)||"ds_name".equals(columnName)) {
			condition = columnName +" like '%"+value+"%' ";
		}else if("ep_rp".equals(columnName)){
			condition = columnName + " = " + value;
		}else {
			condition = " ";
		}
		return condition + " ";	
	}
	
	public static String get_whereCondition(Map<String,String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count=0;
		for(String key:keys) {
			String value = map.get(key)[0];
			if(value!=null && value.trim().length()!=0 && !"action".equals(key)) {
				String condition = get_aCondition_From_Oracle(key,value.trim());
				if(count==1) {
					whereCondition.append(" and " + condition);
				}else {
					whereCondition.append(" where " + condition);
				}
			}
		}
		return whereCondition.toString();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,String[]> map = new TreeMap<String,String[]>();
		map.put("ep_name", new String[]{"手套"});
		map.put("epc_no",new String[]{"EQAH"});
		
		String finalSQL="select * from equip " + CompositeQuery_Equip.get_whereCondition( map ) + " order by ep_name";
		System.out.println("finalSQL =" + finalSQL);
	}

}
