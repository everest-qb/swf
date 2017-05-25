/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf;

import java.util.*;
/**
 *
 * @author User
 */
public class StringFormat {
    
        public StringFormat(){}
	/****************************************************************
	* 提供 SQL 重新組字, 傳入繼承 Map 型態的內容			*
	* 由程式將所有集合的值整合成一個字串, 中間用逗號區分		*
	****************************************************************/
        /**
         * 
         * @param map
         * @return 
         */
	public String sqlMapToString(Map<String, String> map){
		StringBuilder sb = new StringBuilder();
		if(!map.isEmpty()){
			Set set = map.keySet();				//取得所有的key
			for(Object obj : set.toArray()){		//將key轉為陣列型態
				sb.append(obj).append("=").append(map.get(obj)).append(",");//把 value全部組合
			}
			sb.replace(sb.length()-1, sb.length(), "");	//移除最後一個逗號
		}
		return sb.toString();
	}
	
        /********************************************************
	* 提供 SQL 重組Select欄位或Order by 欄位			*
	* 由程式將所有集合的值整合成一個字串, 中間用逗號區分	*
	********************************************************/
        /**
         * 
         * @param param1
         * @return 
         */
	public String sqlListToString(List<String> param1){
		StringBuilder sb = new StringBuilder();
			if(param1 != null)	
			return null;	//若集合中無任何值，直接回傳 NULL 不執行程式
			for(int i = 0 ; i < param1.size() ; i++){
                            sb.append(param1.get(i)).append(", ");
		}
		sb.replace(sb.length()-1, sb.length(), "");		//移除最後一個逗號
			return sb.toString();
	}
	/********************************************************
	* 提供 SQL 重組Where條件式，需傳入三個參數			*
	* 由程式將所有集合的值整合成一個字串, 中間用AND區分		*
	********************************************************/
        /**
         * 
         * @param param1
         * @param param2
         * @param param3
         * @return 
         */
	public String sqlListToString(List<String> param1, List<String> param2, List<String> param3){
		StringBuilder sb = new StringBuilder();
			if(param1.size() != param2.size() || param1.size() != param3.size())	
			return null;	//若參數數量不一致，直接回傳 NULL 不執行程式
			for(int i = 0 ; i < param1.size() ; i++){
                            sb.append(" AND ").append(param1.get(i)).append(" ").append(param2.get(i)).append(" ").append(param3.get(i)).append(" ");
		}
			return sb.toString();
	}
		public String sqlSetToString(Set set){
		return null;
	}
}
