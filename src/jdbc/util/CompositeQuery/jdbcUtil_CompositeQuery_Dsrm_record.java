package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Dsrm_record {

	public static String get_aCondition_For_Oracle(String columnName, String value) {
		
		String aCondition = null;
		
		if ("ds_no".equals(columnName) || "mem_no".equals(columnName) || "rep_state".equals(columnName))
			aCondition = columnName + " like '%" + value + "%'";
			
		return aCondition + " ";
	}
	
	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if(value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());
				
				if(count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" and " + aCondition);
				
				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}
	
	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("rep_state", new String[] {"未審核"});
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from dsrm_record "
				          + jdbcUtil_CompositeQuery_Dsrm_record.get_WhereCondition(map)
				          + "order by rdsr_no";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
