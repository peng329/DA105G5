package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Lesson {

	public static String get_aCondition_For_Oracle(String columnName, String value) {
		
		String aCondition = null;
		
		if ("les_name".equals(columnName) || "ds_name".equals(columnName) || "les_state".equals(columnName) ||"less_state".equals(columnName))
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
		map.put("les_name", new String[] {"OW"});
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from lesson "
				          + jdbcUtil_CompositeQuery_Lesson.get_WhereCondition(map)
				          + "order by les_no";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
