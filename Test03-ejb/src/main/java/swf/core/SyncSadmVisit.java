/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.core;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import swf.io.SwfDebugLogs;
import swf.view.SwfView;
import swf.core.SwfFlowEngine;

/**
 *
 * @author andy.dh.chen
 */
public class SyncSadmVisit extends Thread implements ServletContextListener {

    public boolean terminate = true;
    private static SwfDebugLogs sd = null;
    private Connection conn = null;
    private HashMap compId = null;

    public void run() {

        while (this.terminate) {
            this.sd = new SwfDebugLogs(System.getenv("CATALINA_HOME") + "/logs/SyncSadmVisit.txt");
            //this.sd = new SwfDebugLogs("D:\\apache-tomcat-7.0.68\\logs\\SyncSadmVisit.txt");
            SimpleDateFormat sf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
            Calendar recordDate = Calendar.getInstance();
            Statement stmt = null;
            Statement stmt1 = null;
            ResultSet rs = null;
            ResultSet rs1 = null;
            BufferedReader reader = null;
            String url = "";
            String username = "";
            String password = "";
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
                this.sd.setMessage("-----[SyncSadmVisit] [START]: " + sf.format(recordDate.getTime()) + "-----\r\n");

                /*取得訪客申請表頭相關資訊*/
                String sql = "SELECT svm.visit_id,emp_id,TO_CHAR(svm.creation_date, 'YYYY/MM/DD HH24:MI') creation_date,"
                        + "svm.created_by,svm.status,TO_CHAR(svm.start_date, 'YYYY/mm/dd hh24:mi') start_date,"
                        + "TO_CHAR(svm.end_date, 'yyyy/mm/dd hh24:mi') end_date,fls1.meaning visit_type,"
                        + "visit_reason,fls2.meaning traffic_type,NVL(svm.description, '') description,"
                        + "CASE WHEN svm.room_total = 0 THEN '不需安排住宿' ELSE '需安排住宿' END room,"
                        + "CASE WHEN fls3.meaning = '無須用餐' THEN '不需安排用餐' ELSE '需安排用餐'"
                        + " END meal,visit_number,svm.attribute2,CASE WHEN svm.attribute3 = 'N' THEN '不需安排行程'"
                        + " ELSE '需安排行程' END schedule,NVL(scra.conf_name, 'null') conf_name,"
                        + " CASE WHEN svm.attribute5 = 'N' THEN '不需安排推播' ELSE '需安排推播'"
                        + " END push,svra.route_name,fls4.meaning meeting_location,svm.attribute8,"
                        + " svm.attribute9,fls5.meaning meal_type,fls3.meaning meal_location,"
                        + " svm.meal_total,svm.attribute10,svm.attribute11 FROM sadm_visit_mains svm,fnd_lookups fls1,fnd_lookups fls2,"
                        + " fnd_lookups fls3,sadm_conf_rooms_all scra,fnd_lookups fls4,sadm_visit_route_all svra,"
                        + " fnd_lookups fls5 WHERE  1 = 1 AND fls1.lookup_type = 'SADM_VISIT_TYPE'"
                        + " AND svm.visit_type = fls1.lookup_code AND fls2.lookup_type = 'SADM_TRAFFIC_TYPE'"
                        + " AND svm.traffic_type = fls2.lookup_code AND fls3.lookup_type = 'SADM_MEAL_LOCATION'"
                        + " AND svm.meal_location = fls3.lookup_code AND fls4.lookup_type = 'SADM_VISIT_MEETING_LOCATION'"
                        + " AND fls4.lookup_code = svm.attribute7 AND fls5.lookup_type(+) = 'SADM_MEAL_TYPE'"
                        + " AND svm.meal_type = fls5.lookup_code(+) AND TO_NUMBER(svm.attribute4) = scra.conf_room_id(+)\n"
                        + " AND TO_NUMBER(svm.attribute6) = svra.visit_route_id AND swf_no IS NULL"
                        + " AND swf_flag = 'P' AND sync_flag IS NULL";
                rs = stmt.executeQuery(sql);

                while (rs.next()) {

                    /*取得訪客申請的COMPID*/
                    compId = this.getCompId(rs.getString("ATTRIBUTE2"));
                    /*取得SWF單號*/
                    String swfNo = getSwfHo();

                    /*建立SWF表頭並回傳HDRID*/
                    String hdrId = this.insertSwfItemHdrAll(rs.getString("CREATED_BY"), rs.getString("EMP_ID"), swfNo,
                            rs.getString("CREATION_DATE"), rs.getString("END_DATE"), rs.getString("START_DATE"), rs.getString("STATUS"),
                            rs.getString("ATTRIBUTE8"), rs.getString("ATTRIBUTE9"), rs.getString("ATTRIBUTE2"));
                    System.out.println(hdrId);

                    /*在多申請人TABLE INSERT 一筆ROW*/
                    this.insertSwfItemApplAll(rs.getString("CREATED_BY"), hdrId, rs.getString("ATTRIBUTE9"));

                    /*將SADM_VISIT_MAINS內容相關資訊寫入SWF_ITEM_LINE_ALL 組成PL/SQL*/
                    String plsql = " BEGIN";
                    /*來訪事由*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("textarea_1")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("VISIT_REASON") + "');";
                    /*訪客類型*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("form-control1")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("VISIT_TYPE") + "');";
                    /*交通類型*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_7")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("TRAFFIC_TYPE") + "');";
                    /*注意事項*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("t_1")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("DESCRIPTION") + "');";
                    /*是否安排住宿*/
                    if (rs.getString("ROOM").equals("需安排住宿")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check7")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check8")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                        plsql += this.getSadmVisitHotel(rs.getString("VISIT_ID"), rs.getString("CREATED_BY"), hdrId);
                    } else if (rs.getString("ROOM").equals("不需安排住宿")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check8")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check7")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    }
                    /*是否安排用餐*/
                    if (rs.getString("MEAL").equals("需安排用餐")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check5")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check6")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_4-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("MEAL_TYPE") + "',P_ATTRIBUTE2=>'table_4',P_ATTRIBUTE3=>'1',P_ATTRIBUTE4=>'1');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_5-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("MEAL_LOCATION") + "',P_ATTRIBUTE2=>'table_4',P_ATTRIBUTE3=>'1',P_ATTRIBUTE4=>'2');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("table_input_12-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("MEAL_TOTAL") + "',P_ATTRIBUTE2=>'table_4',P_ATTRIBUTE3=>'1',P_ATTRIBUTE4=>'1');";
                    } else if (rs.getString("MEAL").equals("不需安排用餐")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check6")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check5")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    }

                    /*來訪人數*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("input_1")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("VISIT_NUMBER") + "');";

                    /*是否安排行程*/
                    if (rs.getString("SCHEDULE").equals("需安排行程")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check9")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check10")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                        plsql += this.getSadmVisitSchedules(rs.getString("VISIT_ID"), rs.getString("CREATED_BY"), hdrId);
                    } else if (rs.getString("SCHEDULE").equals("不需安排行程")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check10")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check9")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    }

                    /*會議地點*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("confRoom")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("CONF_NAME") + "');";

                    /*是否安排推播*/
                    if (rs.getString("PUSH").equals("需安排推播")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check11")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check12")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    } else if (rs.getString("PUSH").equals("不需安排推播")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check12")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check11")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    }

                    /*參訪路線*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("route_name")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("ROUTE_NAME") + "');";

                    /*接待地點*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("meetingLocation")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("MEETING_LOCATION") + "');";

                    /*攜入/出物品*/
 /*if (rs.getString("ATTRIBUTE10").equals("Y")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check13")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check14")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    } else if (rs.getString("ATTRIBUTE10").equals("N")) {
                        plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check14")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'Y');";
                                                plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("check13")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'N');";
                    }*/
                    plsql += " SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + rs.getString("CREATED_BY") + ",P_ITEM_COMP_ID=>" + compId.get("input_5")
                            + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("ATTRIBUTE11") + "');";

                    /*將SADM_VISIT_TRAVELS內容相關資訊寫入SWF_ITEM_LINE_ALL 回傳PL/SQL*/
                    if (rs.getString("TRAFFIC_TYPE").equals("機車") || rs.getString("TRAFFIC_TYPE").equals("汽車") || rs.getString("TRAFFIC_TYPE").equals("安排接送")) {
                        plsql += this.getSadmVisitTravels(rs.getString("VISIT_ID"), rs.getString("ATTRIBUTE2"), hdrId, rs.getString("CREATED_BY"));
                    }

                    /*將SADM_VISIT_PERSONS內容相關資訊寫入SWF_ITEM_LINE_ALL 組成PL/SQL回傳*/
                    plsql += this.getSadmVisitPersons(rs.getString("VISIT_ID"), rs.getString("CREATED_BY"), hdrId);


                    /*將SWF單號寫回SADM_VISIT_MAINS*/
                    plsql += this.updateSwfNo(rs.getString("VISIT_ID"), swfNo);

                    plsql += " END;";

                    System.out.println(plsql);
                    stmt1.execute(plsql);

                    ArrayList<String> hdr = new ArrayList<String>();
                    ArrayList<String> user = new ArrayList<String>();
                    ArrayList<String> type = new ArrayList<String>();
                    ArrayList<String> comment = new ArrayList<String>();
                    ArrayList<String> nowTxnId = new ArrayList<String>();
                    ArrayList<String> approveType = new ArrayList<String>();
                    hdr.add(hdrId);
                    user.add(rs.getString("CREATED_BY"));
                    type.add("APPLY");
                    comment.add(null);
                    nowTxnId.add(null);
                    approveType.add(null);
                    /*建立簽核流程*/
                    SwfFlowEngine.pushTask(nowTxnId, approveType, hdr, user, type, comment);
                }
                /**
                 * 將SWF中訪客申請已結案及不同意的狀態更新至SADM_VISIT_MAINS *SWF_ITEM_HDR_ALL
                 * ATTRIBUTE1填入Y代表已處理過 *SAMD_VISIT_MAINS SWF_FLAG 填入C代表已結案 *不同意
                 * SWF_FLAG 則填入SWF_SERVICE_ACTITVITY_TYPE狀態碼
                 *
                 */
                updateSwfItemHdrAllVisitFlagAndSadmVisitMainsSwfFlag();

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
                    if (rs1 != null) {
                        rs1.close();
                        rs1 = null;
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
                    this.sd.setMessage("-----[SyncSadmVisit] [END]: " + sf1.format(endDate.getTime()) + "-----\r\n");
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
                    this.sd.setMessage("-----[SyncSadmVisit] [END]: " + sf1.format(endDate.getTime()) + "-----\r\n");
                }

            }
        }
    }

    private String insertSwfItemHdrAll(String createdBy, String empId, String swfNo, String creationDate,
            String endDate, String startDate, String status, String requestLevel, String AempId, String type) {
        String serviceItemId = "";
        if (type.equals("S")) {
            serviceItemId = "46";
        } else if (type.equals("M")) {
            serviceItemId = "45";
        }
        String sql = "SWF_DML_PKG.INSERT_SWF_ITEM_HDR_ALL(P_USER_ID=>" + createdBy + ",P_PREPARED_BY=>" + empId + ",P_HDR_NO=>'" + swfNo
                + "',p_request_level=>'" + requestLevel + "',p_service_item_id=>" + serviceItemId + ",p_applied_date=>to_date('" + creationDate + "','yyyy/mm/dd hh24:mi'),p_request_expire_type=>'N',"
                + "p_request_expire_date=>to_date('" + endDate + "','yyyy/mm/dd hh24:mi'),p_request_enable_date=>"
                + "to_date('" + startDate + "','yyyy/mm/dd hh24:mi'),p_process_status=>'10',p_service_activity_code=>" + status + ",P_ATTRIBUTE2=>'" + AempId + "')";
        System.out.println(sql);// return pk(HDR_ID)
        CallableStatement cStmt = null;
        String message = "";
        try {
            cStmt = conn.prepareCall("{? = call " + sql + "}");
            cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
            cStmt.execute();
            message = cStmt.getString(1);
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
                if (cStmt != null) {
                    cStmt.close();
                    cStmt = null;
                }
            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
        }
        return message;
    }

    private void insertSwfItemApplAll(String userId, String hdrId, String empId) {
        String sql = " BEGIN SWF_DML_PKG.INSERT_SWF_ITEM_APPL_ALL(P_USER_ID=>" + userId
                + ",P_HDR_ID=>" + hdrId + ",P_SEQ=>1,P_EMP_ID=>" + empId + "); END;";
        Statement stmt = null;
        try {
            stmt = this.conn.createStatement();
            stmt.execute(sql);
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
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
        }
    }

    private String getSwfHo() {
        String sql = "Select lpad(SWF_ITEM_NO_S1.nextval,3,0) SWF_HO from dual";
        Statement stmt = null;
        ResultSet rs = null;
        String swfHo = "";
        SimpleDateFormat swfHoFormat = new SimpleDateFormat("YYYYMMdd");
        Calendar date = Calendar.getInstance();
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            rs.next();
            swfHo = "SWF" + swfHoFormat.format(date.getTime()) + rs.getString("SWF_HO");
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

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
            return swfHo;
        }
    }

    private HashMap getCompId(String type) {
        String serviceItemId = "";
        if (type.equals("S")) {
            serviceItemId = "46";
        } else if (type.equals("M")) {
            serviceItemId = "45";
        }
        String sql = "SELECT item_comp_id,attribute5 FROM swf_item_comp_all WHERE attribute10 <> 'Y'"
                + " AND attribute7 = 'Y' AND service_item_id = " + serviceItemId + " ORDER BY service_item_id,"
                + "TO_NUMBER(attribute3),TO_NUMBER(attribute1),TO_NUMBER(attribute2),item_comp_id";
        Statement stmt = null;
        ResultSet rs = null;
        String swfHo = "";
        HashMap compId = new HashMap();
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                compId.put(rs.getString(2), rs.getString(1));
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

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
            return compId;
        }
    }

    private StringBuilder getSadmVisitTravels(String visitId, String type, String hdrId, String userId) {
        StringBuilder plsql = new StringBuilder();
        String sql = "SELECT flv.meaning traffic_type,svt.car_number,"
                + " Nvl(TO_CHAR(svt.travel_time, 'yyyy/mm/dd hh24:mi'),'null') travel_time,"
                + " NVL(travel_location, 'null') travel_location,"
                + " NVL(scm.nickname, 'null') nick_name,"
                + " NVL(sca.company_name, 'null') company_name,"
                + " NVL(svt.attribute1, 'N') attribute1,"
                + " NVL(svt.attribute2, 'N') attribute2"
                + " FROM sadm_visit_travels svt,"
                + " sadm_car_mains scm,"
                + " sadm_companies_all sca,fnd_lookups flv"
                + " WHERE 1 = 1 AND svt.car_id = scm.car_id(+)"
                + " AND svt.company_id = sca.company_id(+)"
                + " AND flv.lookup_type = 'SADM_TRAFFIC_TYPE'"
                + " AND svt.traffic_type = flv.lookup_code"
                + " AND visit_id = " + visitId;

        Statement stmt = null;
        ResultSet rs = null;
        int index = 1;
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                if (type.equals("S")) {
                    if (rs.getString("TRAFFIC_TYPE").equals("機車") || rs.getString("TRAFFIC_TYPE").equals("汽車")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_4-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("CAR_NUMBER") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'1');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_21-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("ATTRIBUTE1") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'2');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_22-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("ATTRIBUTE2") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'3');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("s_traffic-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("TRAFFIC_TYPE") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'4');");
                    } else if (rs.getString("TRAFFIC_TYPE").equals("安排接送")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_9-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("TRAVEL_TIME") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'1');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_10-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("TRAVEL_LOCATION") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'2');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_2-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("NICK_NAME") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'3');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_3-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("COMPANY_NAME") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'4');");
                    }
                } else if (type.equals("M")) {
                    if (rs.getString("TRAFFIC_TYPE").equals("汽車")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_4-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("CAR_NUMBER") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'1');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_21-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("ATTRIBUTE1") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'2');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_22-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("ATTRIBUTE2") + "',P_ATTRIBUTE2=>'table_2',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'3');");
                    } else if (rs.getString("TRAFFIC_TYPE").equals("安排接送")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_9-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("TRAVEL_TIME") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'1');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_10-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("TRAVEL_LOCATION") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'2');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_2-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("NICK_NAME") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'3');");
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_3-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString("COMPANY_NAME") + "',P_ATTRIBUTE2=>'table_3',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'4');");
                    }
                }
                index++;
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

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
            return plsql;
        }
    }

    private StringBuilder getSadmVisitPersons(String visitId, String userId, String hdrId) {
        StringBuilder plsql = new StringBuilder();
        String sql = "SELECT visit_name,visit_position,visit_company,"
                + "NVL(fls1.meaning, 'null') sex_type FROM sadm_visit_persons svp,"
                + "fnd_lookups fls1 WHERE 1 = 1"
                + " AND fls1.lookup_type(+) = 'SADM_SEX_TYPE'"
                + " AND svp.sex_type = fls1.lookup_code(+)"
                + " AND visit_id =" + visitId;
        System.out.println(sql);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int index = 1;
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rsmd.getColumnName(i).equals("VISIT_NAME")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_1-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_1',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("VISIT_POSITION")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_2-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_1',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("VISIT_COMPANY")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_3-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_1',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("SEX_TYPE")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_1-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_1',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    }
                }
                index++;
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

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
            System.out.println(plsql);
            return plsql;
        }
    }

    private StringBuilder getSadmVisitSchedules(String visitId, String userId, String hdrId) {
        StringBuilder plsql = new StringBuilder();
        String sql = "SELECT TO_CHAR(schedules_start, 'yyyy/mm/dd hh24:mi') schedules_start,TO_CHAR(schedules_end, 'yyyy/mm/dd hh24:mi') schedules_end,"
                + "discuss_subject,attribute2 MEMBER,NVL(description, '') description FROM sadm_visit_schedules WHERE 1 = 1 AND visit_id =" + visitId;
        System.out.println(sql);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int index = 1;
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rsmd.getColumnName(i).equals("SCHEDULES_START")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_15-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_6',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("SCHEDULES_END")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_16-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_6',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("DISCUSS_SUBJECT")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_17-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_6',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("MEMBER")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_19-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_6',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("DESCRIPTION")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_18-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_6',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    }
                }
                index++;
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

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
            System.out.println(plsql);
            return plsql;
        }
    }

    private StringBuilder getSadmVisitHotel(String visitId, String userId, String hdrId) {
        StringBuilder plsql = new StringBuilder();
        String sql = "SELECT TO_CHAR(svh.start_date, 'yyyy/mm/dd') start_date,"
                + "sca.company_name,svh.room_number FROM sadm_visit_hotels svh,"
                + "sadm_companies_all sca"
                + " WHERE 1 = 1 AND svh.company_id = sca.company_id"
                + " AND svh.visit_id = " + visitId;
        System.out.println(sql);
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int index = 1;
            while (rs.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rsmd.getColumnName(i).equals("START_DATE")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_13-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_5',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("COMPANY_NAME")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_select_5_6-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_5',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    } else if (rsmd.getColumnName(i).equals("ROOM_NUMBER")) {
                        plsql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_LINE_ALL(P_USER_ID=>" + userId + ",P_ITEM_COMP_ID=>" + compId.get("table_input_14-1")
                                + ",P_HDR_ID=>" + hdrId + ",P_ATTRIBUTE1=>'" + rs.getString(i) + "',P_ATTRIBUTE2=>'table_5',P_ATTRIBUTE3=>'" + index + "',P_ATTRIBUTE4=>'" + i + "');");
                    }
                }
                index++;
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

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
            System.out.println(plsql);
            return plsql;
        }
    }

    private StringBuilder updateSwfNo(String visitId, String swfNo) {
        StringBuilder plsql = new StringBuilder();
        plsql.append(" SADM_VISIT_DML.UPDATE_SWF_NO(P_VISIT_ID=>" + visitId + ",P_SWF_NO=>'" + swfNo + "');");
        return plsql;
    }

    private void updateSwfItemHdrAllVisitFlagAndSadmVisitMainsSwfFlag() {
        /*取得SWF_ITEM_HDR_ALL 訪客申請已辦理的SQL*/
        String sql = "SELECT siha.hdr_id,siha.hdr_no FROM swf_item_hdr_all siha"
                + " WHERE 1 = 1 AND siha.service_activity_code = '60'"
                + " AND siha.attribute1 IS NULL AND siha.service_item_id in(45,46)";

        /*取得SWF_ITEM_HDR_ALL 訪客申請不同意退回的SQL*/
        String sql1 = "SELECT siha.hdr_id,siha.hdr_no,siha.service_activity_code FROM swf_item_hdr_all siha,"
                + "swf_item_txn_all sita WHERE 1 = 1 AND siha.hdr_id = sita.hdr_id AND siha.attribute1 IS NULL"
                + " AND siha.service_item_id in(45,46) "
                + " AND sita.hdr_id = siha.hdr_id AND sita.approve_type = '40'";
        Statement stmt = null;
        ResultSet rs = null;
        String swfHo = "";
        String plsql = "";
        try {
            stmt = this.conn.createStatement();
            rs = stmt.executeQuery(sql);
            /*更新已結案 SWF_ITEM_HDR_ALL 的 ATTRIBUTE1為Y 及 SADM_VISIT_MAINS SWF_FLAG 為A*/
            while (rs.next()) {
                plsql = "BEGIN SWF_DML_PKG.UPDATE_SADM_VISIT_FLAG(P_HDR_ID=>" + rs.getString("HDR_ID") + ",P_ATTRIBUTE1=>'Y'); ";
                plsql += " SADM_VISIT_DML.UPDATE_SWF_FLAG(P_SWF_NO=>'" + rs.getString("HDR_NO") + "',P_SWF_FLAG=>'A');  END;";
                System.out.println(plsql);
                stmt.execute(plsql);
            }
            rs = stmt.executeQuery(sql1);
            /*更新不同意退回 SWF_ITEM_HDR_ALL 的 ATTRIBUTE1為Y 及 SADM_VISIT_MAINS SWF_FLAG 為SWF的SERVICE_ACTIVITY_CODE*/
            while (rs.next()) {
                plsql = "BEGIN SWF_DML_PKG.UPDATE_SADM_VISIT_FLAG(P_HDR_ID=>" + rs.getString("HDR_ID") + ",P_ATTRIBUTE1=>'Y'); ";
                plsql += " SADM_VISIT_DML.UPDATE_SWF_FLAG(P_SWF_NO=>'" + rs.getString("HDR_NO") + "',P_SWF_FLAG=>'"
                        + rs.getString("SERVICE_ACTIVITY_CODE") + "');  END;";
                stmt.execute(plsql);
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
            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                this.terminate = false;
            }
        }
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            this.start();
            System.out.println("-----------------------------");
            System.out.println("SyncSadmVisit開啟成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SyncSadmVisit開啟失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();

        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            this.stop();
            System.out.println("-----------------------------");
            System.out.println("SyncSadmVisit關閉成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SyncSadmVisit關閉失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
        } finally {
            this.stop();
        }
    }
}
