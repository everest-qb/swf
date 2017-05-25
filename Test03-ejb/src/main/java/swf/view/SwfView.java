/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.view;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import javax.servlet.http.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import swf.sys.SwfSession;

/**
 *
 * @author User
 */
public class SwfView extends SwfSession {

    protected Connection conn = null; 	//與資料庫的連線資訊
    protected Statement stmt = null;		//Session內容
    protected ResultSet rs = null;		//結果集
    protected String queryCommand = null;         //Query Command
    protected CallableStatement cStmt = null;      //PL/SQL DBMS回傳值使用

    public SwfView() {

    }

    /**
     * 此函數將建立與資料庫的Session 建立連線，由 JSP 傳入 HttpServletRequest 與 HttpServletResponse
     *
     * @param request
     * @param response
     */
    public void initial(HttpServletRequest request, HttpServletResponse response) {

        try {
            super.initial(request, response);
            super.checkConnection();
            this.conn = (Connection) request.getSession().getAttribute("dbcon");
            System.out.println("Connection Initial ok...");
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Statement Initial ok...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    /**
     * 測試用的Method 測試此java的連線資訊
     *
     * @return Connection
     */
    public void testConnection() {
        String url = "jdbc:oracle:thin:@portal.sunspring.com.tw:1521:PTPROD";
        String username = "apps";
        String password = "apps";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            this.conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Initial ok...");
            this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println("Statement Initial ok...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    /**
     * 此函數將關閉與資料庫的Connection
     */
    public void closeConnection() {
        try {
            if (conn != null) {
                conn.commit();
                conn.close();
                conn = null;
                System.out.println("Connection close ok...");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    /**
     * 此函數將關閉與資料庫的Statement
     */
    public void closeStatement() {
        try {
            if (stmt != null) {
                stmt.close();
                stmt = null;
                System.out.println("Statement close ok...");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    /**
     * 查詢設定檔
     *
     * @param param 欲查詢的設定檔名稱。Ex:SWF:XML_PATH
     * @return
     */
    public String getProfile(String param) {
        String sqlCmd = " SELECT FND_PROFILE.VALUE('" + param + "') FROM DUAL ";
        String value;

        try {
            rs = stmt.executeQuery(sqlCmd);
            rs.next();
            value = rs.getString(1);
        } catch (SQLException ex) {
            System.out.println("Exception :" + ex.getMessage());
            return null;
        }
        return value;
    }

    public void setQuery(String query) {
        this.queryCommand = query;
    }

    public String getQuery() {
        return this.queryCommand;
    }

    public ResultSet executeQuery() {
        //boolean flag;

        try {
            System.out.println("Query " + queryCommand);
            rs = stmt.executeQuery(queryCommand);
            return rs;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
            return rs;
        }
    }

    public String userLogin(String userName, String passWord) {
        CallableStatement cStmt = null;
        String message = "";
        try {
            cStmt = conn.prepareCall("{? = call SWF_DML_PKG.validatelogin(?, ?)}");
            cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cStmt.setString(2, userName.toUpperCase());
            cStmt.setString(3, passWord);
            cStmt.execute();
            message = cStmt.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                cStmt.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return message;
    }

    public String functionReturnVarchar2(String sql) {
        System.out.println(sql);
        CallableStatement cStmt = null;
        String message = "";
        try {
            cStmt = conn.prepareCall("{? = call " + sql + "}");
            cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cStmt.execute();
            message = cStmt.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                cStmt.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return message;
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public static void main(String arg[]) {
        /*SwfView dml = new SwfView();
            dml.initial(dml.connection());
            dml.getResultSet("SWF_APPLY_ITEM_LINES_ALL", "*", "AND LINE_ID IS NULL", null);
            
            ArrayList<String> col = new ArrayList<String>();
            ArrayList<String> pamCol = new ArrayList<String>();
            ArrayList<String> pamOper = new ArrayList<String>();
            ArrayList<String> pamVal = new ArrayList<String>();
            ArrayList<String> orderby = new ArrayList<String>();
            
            col.add("ATTRIBUTE1");
            col.add("ATTRIBUTE2");
            pamCol.add("LINE_ID");
            pamCol.add("HDR_ID");
            pamOper.add("IS");
            pamOper.add("IS");
            pamVal.add("NULL");
            pamVal.add("NULL");      
            orderby.add("ATTRIBUTE1 DESC");
            orderby.add("ATTRIBUTE2 ASC");
            
            dml.getResultSet("SWF_APPLY_ITEM_LINES_ALL", col, pamCol, pamOper, pamVal, orderby);
            dml.getResultSet("SWF_APPLY_ITEM_LINES_ALL", "*", pamCol, pamOper, pamVal, "ATTRIBUTE1 DESC");
            dml.getRowCount("SWF_APPLY_ITEM_LINES_ALL", " AND LINE_ID IS NULL");
            dml.insertRow("SWF_APPLY_ITEM_LINES_ALL", "LINE_ID", "999");
            
            ArrayList<String> insertColName = new ArrayList<String>();
            ArrayList<String> insertValue = new ArrayList<String>();
            
            insertColName.add("LINE_ID");
            insertColName.add("HDR_ID");
            insertValue.add("9999");
            insertValue.add("9999");
            
            dml.insertRow("SWF_APPLY_ITEM_LINES_ALL", insertColName, insertValue);
            
            dml.updateRow("SWF_APPLY_ITEM_LINES_ALL", "HDR_ID = 9999", "AND LINE_ID = 999");
            
            dml.deleteRow("SWF_APPLY_ITEM_LINES_ALL", "AND LINE_ID IS NULL");
            
            System.out.println(dml.getProfile("SWF:XML_PATH"));
            
            dml.closeStatement();
            dml.closeConnection();*/

    }

    /**
     * @param tableName 傳入預計算的Table
     * @param colName 傳入顯示的欄位
     * @param whereClause where 條件式，可為Null
     * @param orderBy 排序方式，可為Null
     * @return 此函數將會回傳結果集 ResultSet
     */
    /*public ResultSet getResultSet(String tableName, String colName, String whereClause, String orderBy){
		String sql;
		if(tableName == null || colName == null){
			return null;
		}else{
			sql = " SELECT "+ colName + " FROM " + tableName;
			if(whereClause != null)
				sql = sql + " WHERE 1=1 "+whereClause;
			if(orderBy != null)
				sql = sql + " ORDER BY "+orderBy;
		}
		try{
                        System.out.println(sql);
			rs = stmt.executeQuery(sql);
                        System.out.println("getResultSet  ok...");
		}catch(Exception ex){
                        System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
		}
            return rs;
	}*/
    /**
     *
     * @param tableName 傳入預計算的Table
     * @param colList 傳入顯示的欄位
     * @param paramCol where 條件式的 欄位，可為Null
     * @param paramOper where 條件式的 運算元，可為Null
     * @param paramVal where 條件式的 值，可為Null
     * @param orderByList 傳入排序方式，可為Null
     * @return 此函數將會回傳結果集 ResultSet
     */
    /*public ResultSet getResultSet(String tableName, ArrayList colList, ArrayList paramCol, ArrayList paramOper, ArrayList paramVal, ArrayList orderByList){
		if(tableName == null){
			return null;
		}else{
			boolean flag 		= true;
			StringFormat sf 	= new StringFormat();
			String selectCmd 	= null;
			String whereClause 	= null;
			String orderBy		= null;
			
			if(colList != null) 
				selectCmd = sf.sqlListToString(colList);
                        else
                                selectCmd = "*";
                        
			if(orderByList != null)
				orderBy = sf.sqlListToString(orderByList);
			if(paramCol != null && paramOper != null) 
				whereClause = sf.sqlListToString(paramCol, paramOper, paramVal);
				
			return this.getResultSet(tableName, selectCmd, whereClause, orderBy);
		}
	}*/
    /**
     *
     * @param tableName 傳入預計算的Table
     * @param cols 傳入顯示的欄位
     * @param paramCol where 條件式的 欄位，可為Null
     * @param paramOper where 條件式的 運算元，可為Null
     * @param paramVal where 條件式的 值，可為Null
     * @param orderBy 傳入排序方式，可為Null
     * @return 此函數將會回傳結果集 ResultSet
     */
    /*public ResultSet getResultSet(String tableName, String cols, ArrayList paramCol, ArrayList paramOper, ArrayList paramVal, String orderBy){

                if(tableName == null){
			return null;
		}else{
			boolean flag 		= true;
			StringFormat sf 	= new StringFormat();
			String whereClause 	= null;
			
			if(cols == null) 
                            cols = " * ";
                        
			if(paramCol != null && paramOper != null) 
                            whereClause = sf.sqlListToString(paramCol, paramOper, paramVal);

				
			return this.getResultSet(tableName, cols, whereClause, orderBy);
		}
                
	}*/
    /**
     *
     * @param tableName 傳入預計算的Table
     * @param whereClause 傳入條件式，此參數可為Null
     * @return 此函數將會回傳資料總筆數 int
     */
    /*public int getRowCount(String tableName , String whereClause){
		String sql = null;
		int count = -1;
		if(tableName == null){
			return count;
		}else{
			sql = "Select COUNT(*) FROM "+tableName;
			if(whereClause != null)
				sql = sql + " WHERE 1=1 "+ whereClause;
		}
		
		try{
                        System.out.println(sql);
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
		}catch(Exception ex){
			ex.printStackTrace();
		}
                System.out.println("getRowCount ok...");
		return count;
	}*/
    /**
     *
     * @param tableName 傳入需要插入Row的 Table 名稱
     * @param colName 傳入欲插入的欄位
     * @param colValue 傳入欲插入的值
     * @return 此函數將會回傳執行結果 boolean
     */
    /*public boolean insertRow(String tableName, String colName, String colValue){
		boolean flag = true;

		String sql = null;
		if(tableName == null || colName == null || colValue == null){
			flag = false;
		}else{
			sql = "INSERT INTO " + tableName + " ( " + colName + " )VALUES( "+ colValue +")";
		}
		try{
                        System.out.println(sql);
		   	stmt.executeQuery(sql);
                        System.out.println("insertRow ok...");
		}catch(Exception ex){
			flag = false;
			System.out.println("Insert throw exception:---------------------------------------");
			ex.printStackTrace();
		}
		return flag;	
	}*/
    /**
     *
     * @param tableName 傳入需要更新的 Table 名稱
     * @param insertColName 傳入需要插入的欄位 < 欄位名稱>
     * @param insertColValue 傳入需要插入的值 	 < 值>
     * @return 此函數將會回傳執行結果 boolean
     */
    /*public boolean insertRow(String tableName, ArrayList insertColName, ArrayList insertColValue){
		boolean flag 		= true;
		StringFormat sf 	= new StringFormat();
		String colName 		= null;
		String colValue 	= null;
		if(insertColName.size() == insertColValue.size()) {
			
			colName = sf.sqlListToString(insertColName);
			colValue = sf.sqlListToString(insertColValue);
		}else{
			flag = false;
                        return flag;
		}
		
		flag = this.insertRow(tableName, colName, colValue);
		return flag;
	}*/
    /**
     *
     * @param tableName 傳入需要更新的 Table 名稱
     * @param updateCmd 傳入欲更新的欄位與值
     * @param whereClause where 條件式
     * @return 此函數將會回傳執行結果 boolean
     */
    /*public boolean updateRow(String tableName, String updateCmd, String whereClause){
		boolean flag = true;

		String sql = null;
		if(tableName == null || updateCmd == null || whereClause == null){
			flag = false;
		}else{
			sql = "UPDATE " + tableName + " SET " + updateCmd + " WHERE 1 = 1 " + whereClause;
		}
		try{
                        System.out.println(sql);
		   	stmt.executeQuery(sql);
                        System.out.println("updateRow ok...");
		}catch(Exception ex){
			flag = false;
			System.out.println("Update throw exception:---------------------------------------");
			ex.printStackTrace();
		}
		return flag;	
	}*/
    /**
     * **********************************************
     * paramCol, Oper, Val * 需要透過另一個jsp進行重組並回傳 *
     * **********************************************
     */
    /**
     *
     * @param tableName 傳入需要更新的 Table 名稱
     * @param param 傳入需要更新的值 < 欄位 : 值 >
     * @param paramCol where 條件式的 欄位1
     * @param paramOper where 條件式的 運算元
     * @param paramVal where 條件式的 值
     * @return 此函數將會回傳執行結果 boolean
     */
    /*public boolean updateRow(String tableName, HashMap param, ArrayList paramCol, ArrayList paramOper, ArrayList paramVal){
		boolean flag 		= true;
		StringFormat sf 	= new StringFormat();
		String updateCmd 	= null;
		String whereClause 	= null;
		if(paramCol != null) 
			updateCmd = sf.sqlMapToString(param);
		if(paramCol != null && paramOper != null) 
			whereClause = sf.sqlListToString(paramCol, paramOper, paramVal);
		if(updateCmd == null) 
			return false;
		flag = this.updateRow(tableName, updateCmd, whereClause);
		return flag;
	}*/
    /**
     *
     * @param tableName 傳入需要刪除的 Table 名稱
     * @param whereClause where 條件式
     * @return 此函數將會回傳執行結果 boolean
     */
    /*public boolean deleteRow(String tableName, String whereClause){
		boolean flag = true;
		String sql;
		
		if(tableName == null || whereClause == null){
			flag = false;
			return flag;
		}else{
			 sql = "DELETE FROM " + tableName + " WHERE 1 = 1 " + whereClause;
		}
		try{
                        System.out.println(sql);
		   	stmt.executeQuery(sql);
                        System.out.println("deleteRow ok...");
		}catch(Exception ex){
			flag = false;
			System.out.println("Delete throw exception:---------------------------------------");
			ex.printStackTrace();
		}
		return flag;	
	}*/
    /**
     * **********************************************
     * paramCol, Oper, Val * 需要透過另一個jsp進行重組並回傳 *
     * **********************************************
     */
    /**
     *
     * @param tableName 傳入需要刪除的 Table 名稱
     * @param paramCol where 條件式的 欄位1
     * @param paramOper where 條件式的 運算元
     * @param paramVal where 條件式的 值
     * @return 此函數將會回傳執行結果 boolean
     */
    /*public boolean deleteRow(String tableName, ArrayList paramCol, ArrayList paramOper, ArrayList paramVal){
		boolean flag 		= true;
		StringFormat sf 	= new StringFormat();
		String whereClause 	= null;

                if(paramCol != null && paramOper != null) 
        		whereClause = sf.sqlListToString(paramCol, paramOper, paramVal);
		flag = this.deleteRow(tableName, whereClause);
       		return flag;
	}*/
}
