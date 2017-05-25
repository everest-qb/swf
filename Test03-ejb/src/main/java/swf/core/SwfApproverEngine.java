/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;
import java.net.URLDecoder;

/**
 *
 * @author andy.dh.chen
 */
public class SwfApproverEngine /*implements ServletContextListener */ {

    private static Connection conn;

    private static final String SWF_STATION_TXN_ALL = "SWF_STATION_TXN_ALL";
    private static final String SWF_ITEM_TXN_ALL = "SWF_ITEM_TXN_ALL";

    private static ResultSet txnRS;
    private static ResultSet stationRS;

    public static void pushTask(String nowTxnId, String nowApproveType, String hdrId, String user, String comment) {
        Statement stmt = null;
        String url = "";
        String username = "";
        String password = "";
        BufferedReader reader = null;
        try {
            try {
                String txtFilePath = "/home/webprotal/JDBC.ini";
                //String txtFilePath = "D:\\workspaces\\JDBC.ini";
                reader = new BufferedReader(new FileReader(txtFilePath));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    int symbol = line.indexOf('=');
                    int length = line.length();
                    String tmp_a = line.substring(0, symbol);
                    String tmp_b = line.substring(symbol + 1, length);
                    if (tmp_a.equals("url")) {
                        url = tmp_b;
                    } else if (tmp_a.equals("username")) {
                        username = tmp_b;
                    } else if (tmp_a.equals("password")) {
                        password = tmp_b;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                        reader = null;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            SwfApproverEngine.conn = DriverManager.getConnection(url, username, password);
            stmt = SwfApproverEngine.conn.createStatement();
            if (!nowApproveType.equals("40")) {
                String sql = "Begin swf_dml_pkg.next_txn(P_USER_ID=>" + user + ",P_ITEM_TXN_ID=>" + nowTxnId
                        + ",P_HDR_ID=>" + hdrId + ",P_APPROVE_TYPE=>'" + nowApproveType + "'";
                /*判斷有無簽核意見*/
                if (comment != null) {
                    sql = sql + ",p_approve_comment=>'" + URLDecoder.decode(comment, "UTF-8") + "'";
                }

                sql = sql + "); End;";
                System.out.println(sql);
                stmt.execute(sql);
            } else {
                String sql = "Begin swf_dml_pkg.recede_txn(P_USER_ID=>" + user + ",P_TXN_ID=>" + nowTxnId
                        + ",p_hdr_id=>" + hdrId + ",p_approve_type=>'" + nowApproveType + "'";

                /*判斷有無簽核意見*/
                if (comment != null) {
                    sql = sql + ",p_approve_comment=>'" + URLDecoder.decode(comment, "UTF-8") + "'";
                }
                sql = sql + "); End;";
                System.out.println(sql);
                stmt.execute(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }

                if (SwfApproverEngine.conn != null) {
                    SwfApproverEngine.conn.commit();
                    SwfApproverEngine.conn.close();
                    SwfApproverEngine.conn = null;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        /*String stationSql;
        Statement stationStmt = null;
        if (nowTxnId == null) {
            stationSql = "SELECT sita.item_txn_id,ssa.dept_id, ssa.top_level,ssa.job_id,ssa.emp_id,ssa.auto_flag FROM swf_item_txn_all sita,"
                    + "swf_station_all ssa,swf_item_hdr_all siha WHERE  sita.station_id = ssa.station_id AND sita.hdr_id = siha.hdr_id"
                    + "AND siha.hdr_id = " + hdrId;

            System.out.println(stationSql);
            try {
                stationStmt = SwfApproverEngine.conn.createStatement();
                this.stationRS = stationStmt.executeQuery(stationSql);
                while (this.stationRS.next()) {
                    String topLevel = this.stationRS.getString("TOP_LEVEL");
                    String deptId = this.stationRS.getString("DEPT_ID");
                    String jobType = this.stationRS.getString("JOB_TYPE_CODE");
                    String empId = this.stationRS.getString("EMP_ID");
                    
                }
            } catch (Exception ex) {

            }

        } else {
            stationSql = "SELECT ssa.dept_id,ssa.top_level,ssa.job_id,ssa.emp_id,ssa.AUTO_FLAG FROM swf_item_txn_all sita,swf_station_all ssa "
                    + " WHERE  sita.station_id = ssa.station_id AND sita.item_txn_id = " + nowTxnId;
        }*/
    }

    public void findApprover(String hdrId, String userId) {

    }

    /*public void contextInitialized(ServletContextEvent sce) {
       
        try {

            System.out.println(conn);
            System.out.println("-----------------------------");
            System.out.println("SwfApproverEngine初始化連線資訊成功");
            System.out.println("-----------------------------");

        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SwfApproverEngine初始化連線資訊失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            SwfApproverEngine.conn.close();
            System.out.println("-----------------------------");
            System.out.println("SwfApproverEngine關閉連線資訊成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SwfApproverEngine關閉連線資訊失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());

        }
    }*/
}
