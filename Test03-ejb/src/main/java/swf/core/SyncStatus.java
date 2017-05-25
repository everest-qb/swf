/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.core;

import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import swf.io.SwfDebugLogs;

/**
 *
 * @author andy.dh.chen
 */
public class SyncStatus extends Thread implements ServletContextListener {

    public boolean terminate = true;
    private static SwfDebugLogs sd = null;
    private Connection conn = null;

    public void run() {

        while (this.terminate) {
            this.sd = new SwfDebugLogs(System.getenv("CATALINA_HOME") + "/logs/SyncStatus.txt");
            SimpleDateFormat sf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
            Calendar recordDate = Calendar.getInstance();
            Statement stmt = null;
            Statement stmt1 = null;
            ResultSet rs = null;
            BufferedReader reader = null;
            String url = "";
            String username = "";
            String password = "";
            try {
                try {
                    String txtFilePath = "/home/webprotal/JDBC.ini";
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
                    StackTraceElement[] ste = ex.getStackTrace();
                    this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                        this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                    }
                    this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    this.terminate = false;
                } finally {
                    try {
                        if (reader != null) {
                            reader.close();
                            reader = null;
                        }
                    } catch (Exception ex) {
                        this.terminate = false;
                        StackTraceElement[] ste = ex.getStackTrace();
                        this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                        for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                            this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                        }
                        this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    }
                }
                Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                conn = DriverManager.getConnection(url, username, password);

                stmt = conn.createStatement();
                stmt1 = conn.createStatement();
                this.sd.setMessage("-----[SyncStatus] [START]: " + sf.format(recordDate.getTime()) + "-----\r\n");

                String sql = "SELECT seta.exterior_id,seta.service_item_id,flv1.meaning item_group,ssit.service_item_name,"
                        + " seta.swf_no,seta.status,seta.approved_id,seta.created_by,sea.emp_cname,flv.meaning,"
                        + " NVL(sita.approve_comment, 'null') approve_comment,sea1.emp_cname approved_name,NVL(sea.email_address, 'null') email_address,"
                        + " NVL(sea1.email_address, 'null') p_email,sea2.emp_cname sign_name,sea2.emp_id,flv2.meaning request_level, sila.attribute1 item_summary,"
                        + "NVL(TO_CHAR(siha.request_enable_date, 'yyyy/mm/dd hh24:mi:ss'), 'null') request_enable_date,"
                        + "NVL(TO_CHAR(siha.request_expire_date, 'yyyy/mm/dd hh24:mi:ss'), 'null') request_expire_date"
                        + " FROM swf_exterior_txn_all seta,swf_item_hdr_all siha,swf_emps_all sea,fnd_lookups flv,swf_item_txn_all sita,"
                        + " swf_emps_all sea1,swf_emps_all sea2,swf_service_item_all ssit,fnd_lookups flv1,fnd_lookups flv2,swf_item_line_all sila"
                        + " WHERE  1 = 1 AND seta.swf_no = siha.hdr_no AND siha.prepared_by = sea.emp_id AND seta.approved_id = sea1.emp_id"
                        + " AND flv.lookup_type = 'SWF:SERVICE_ACTIVITY_TYPE' AND seta.status = flv.lookup_code AND sita.hdr_id = siha.hdr_id"
                        + " AND siha.HDR_ID = sila.HDR_ID AND TO_NUMBER(seta.attribute1) = sita.item_txn_id AND TO_NUMBER(sita.attribute1) = sea2.emp_id(+)"
                        + " AND siha.service_item_id = ssit.service_item_id AND flv1.lookup_type = 'SWF:FLOW_GROUP' AND SUBSTR(ssit.attribute1, 0, 3) = flv1.lookup_code"
                        + " AND flv2.lookup_type = 'SWF:REQUEST_LEVEL' AND siha.request_level = flv2.lookup_code AND EXISTS(SELECT 1"
                        + " FROM swf_item_comp_all sica WHERE  1 = 1 AND siha.service_item_id = sica.service_item_id AND sica.item_comp_id = sila.item_comp_id"
                        + " AND sica.attribute11 = 'Y') AND sync_flag = 'N' ORDER BY seta.exterior_id";

                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    this.sd.setMessage("EXTERIOR_ID:" + rs.getString("EXTERIOR_ID") + "\r\n");
                    this.sd.setMessage("SERVICE_ITEM_ID:" + rs.getString("SERVICE_ITEM_ID") + "\r\n");
                    this.sd.setMessage("SWF_NO:" + rs.getString("SWF_NO") + "\r\n");
                    this.sd.setMessage("STATUS:" + rs.getString("STATUS") + "\r\n");
                    this.sd.setMessage("APPROVED_ID:" + rs.getString("APPROVED_ID") + "\r\n");
                    String plSql = "BEGIN";
                    if (!rs.getString("APPROVED_NAME").equals("系統")) {
                        if (rs.getString("MEANING").indexOf("退回") > 0) {
                            plSql += " SADM_VISIT_DML.update_statusandapprovedid(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_SWF_NO=>'" + rs.getString("SWF_NO")
                                    + "',P_STATUS=>'" + rs.getString("STATUS") + "',P_APPROVED_ID=>'" + rs.getString("EMP_ID") + "'); ";
                            if (!rs.getString("P_EMAIL").equals("null")) {
                                plSql += " SWF_DML_PKG.SEND_MAIL(P_SUBJECT=>'【通知】工作流程-簽核狀態通知-" + rs.getString("REQUEST_LEVEL") + "',p_email=>'" + rs.getString("P_EMAIL") + "',p_status=>'" + rs.getString("MEANING")
                                        + "',p_approve_name=>'" + rs.getString("SIGN_NAME") + "',P_HDR_NO=>'" + rs.getString("SWF_NO") + "'"
                                        + ",P_APPROVE_COMMENT=>'" + rs.getString("APPROVE_COMMENT") + "',P_ITEM_GROUP=>'" + rs.getString("ITEM_GROUP")
                                        + "',P_SERVICE_ITEM_NAME=>'" + rs.getString("SERVICE_ITEM_NAME") + "',P_ITEM_SUMMARY=>'" + rs.getString("ITEM_SUMMARY")
                                        + "',P_START_DATE=>'" + rs.getString("REQUEST_ENABLE_DATE") + "',P_END_DATE=>'" + rs.getString("REQUEST_EXPIRE_DATE") + " ');";
                            }
                        } else {
                            plSql += " SADM_VISIT_DML.update_statusandapprovedid(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_SWF_NO=>'" + rs.getString("SWF_NO")
                                    + "',P_STATUS=>'" + rs.getString("STATUS") + "',P_APPROVED_ID=>'" + rs.getString("APPROVED_ID") + "'); ";
                            if (!rs.getString("P_EMAIL").equals("null")) {
                                plSql += " SWF_DML_PKG.SEND_MAIL(P_SUBJECT=>'【通知】工作流程-待辦事項通知-" + rs.getString("REQUEST_LEVEL") + "',p_email=>'" + rs.getString("P_EMAIL") + "',p_status=>'" + rs.getString("MEANING")
                                        + "',p_approve_name=>'" + rs.getString("APPROVED_NAME") + "',P_HDR_NO=>'" + rs.getString("SWF_NO") + "'"
                                        + ",P_ITEM_GROUP=>'" + rs.getString("ITEM_GROUP")
                                        + "',P_SERVICE_ITEM_NAME=>'" + rs.getString("SERVICE_ITEM_NAME") + "',P_ITEM_SUMMARY=>'" + rs.getString("ITEM_SUMMARY")
                                        + "',P_START_DATE=>'" + rs.getString("REQUEST_ENABLE_DATE") + "',P_END_DATE=>'" + rs.getString("REQUEST_EXPIRE_DATE") + " ');";
                            }
                        }
                    } else {
                        plSql += " SADM_VISIT_DML.update_statusandapprovedid(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_SWF_NO=>'" + rs.getString("SWF_NO")
                                + "',P_STATUS=>'" + rs.getString("STATUS") + "',P_APPROVED_ID=>'" + rs.getString("APPROVED_ID") + "'); ";
                    }

                    plSql += " SWF_DML_PKG.update_swf_exterior_txn_all(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_EXTERIOR_ID=>" + rs.getString("EXTERIOR_ID") + "); ";

                    plSql += " END;";
                    this.sd.setMessage("[PL/SQL]:" + plSql + "\r\n");

                    stmt1.execute(plSql);

                }
            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                        rs = null;
                    }
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                    if (stmt1 != null) {
                        stmt1.close();
                        stmt1 = null;
                    }
                    if (conn != null) {
                        conn.commit();
                        conn.close();
                        conn = null;
                    }
                    SimpleDateFormat sf1 = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
                    Calendar endDate = Calendar.getInstance();
                    this.sd.setMessage("-----[SyncStatus] [END]: " + sf1.format(endDate.getTime()) + "-----\r\n");
                    this.sleep(1 * 30000);

                } catch (Exception ex) {
                    this.terminate = false;
                    StackTraceElement[] ste = ex.getStackTrace();
                    this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                        this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                    }
                    this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    SimpleDateFormat sf1 = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
                    Calendar endDate = Calendar.getInstance();
                    this.sd.setMessage("-----[SyncStatus] [END]: " + sf1.format(endDate.getTime()) + "-----\r\n");
                }

            }
        }
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            this.start();
            System.out.println("-----------------------------");
            System.out.println("SyncStatus開啟成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SyncStatus開啟失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();

        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            this.stop();
            System.out.println("-----------------------------");
            System.out.println("SyncStatus關閉成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SyncStatus關閉失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
        } finally {
            this.stop();
        }
    }
}
