/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import swf.core.SwfApproverEngine;
import swf.item.Item;
import swf.item.ItemDriver;
import swf.core.SadmSchedulesTempTable;

/**
 * applyTxn 紀錄立案的ITEM_TXN_ID, nextTxnId 建立流程時紀錄下一筆ITEM_TXN_ID, lastTxnId
 * 建立流程時紀錄上一筆ITEM_TXN_ID, nowTxnId 建立流程時紀錄當前ITEM_TXN_ID, insertTxnSql
 * 建立流程時紀錄PL/SQL, mutiApplDeptId 紀錄填單人或是多申請人同部門值為最高的Dept_Id, mutiApplOrgId
 * 紀錄填單人或是多申請人同部門值為最高的Org_Id mutiAppl, JobId 紀錄填單人或是多申請人同部門值為最高的Job_Id,
 * mutiApplEmpId 紀錄填單人或是多申請人同部門值為最高的Emp_Id, mutiApplPositionCode
 * 紀錄填單人或是多申請人同部門值為最高的mutiApplPositionCode,SYS_MANAGER 為系統管理員
 * 當自動簽核時approverId為它, FLOW_MANAGER 為流程管理員 當該流程找不到簽核人時approverId為他,
 * isManager是判斷在尋找簽核人時是否需要尋找管理職系 hdrRS為SWF_ITEM_HDR_ALL 的 ResultSet
 * stationRS為SWF_STATION_ALL 的 ResultSet txnRS為SWF_ITEM_TXN_ALL 的 ResultSet
 * applRS為 SWF_ITEM_APPL_ALL 的 ResultSet
 *
 * @author andy.dh.chen
 */
public class SwfFlowEngine /*implements ServletContextListener*/ {

    private static final String SWF_ITEM_HDR_ALL = "SWF_ITEM_HDR_ALL";
    private static final String SWF_ITEM_TXN_ALL = "SWF_ITEM_TXN_ALL";
    private static final String SWF_ITEM_APPL_ALL = "SWF_ITEM_APPL_ALL";
    //private static final String SWF_ITEM_LINE_TXN_ALL = "SWF_ITEM_LINE_TXN_ALL";*
    private static final String SWF_ITEM_LINE_ALL = "SWF_ITEM_LINE_ALL";
    private static final String SWF_STATION_ALL = "SWF_STATION_ALL";
    private static final String SWF_STATION_TXN_ALL = "SWF_STATION_TXN_ALL";
    private static final String SWF_STATION_RULE_ALL = "SWF_STATION_RULE_ALL";
    private static final String SWF_EMPS_ALL = "SWF_EMPS_ALL";
    private static final String SWF_DEPTS_ALL = "SWF_DEPTS_ALL";
    private static final String SWF_DEPT_ALL = "SWF_DEPT_ALL";
    private static final String SWF_AUTH_ALL = "SWF_AUTH_ALL";
    private static final String SWF_SPECIAL_STATION_ALL = "SWF_SPECIAL_STATION_ALL";
    private static final String SWF_OFFICE_DUTY_ALL = "SWF_OFFICE_DUTY_ALL";
    private static final String SWF_EMP_MANAGER_V = "SWF_EMP_MANAGER_V";
    private static final String SWF_LOOKUPS_V = "SWF_LOOKUPS_V";
    //private static final String SWF_JOB_ALL = "SWF_JOB_ALL";
    private static String nextTxnId, lastTxnId, nowTxnId, insertTxnSql, applyTxn;
    private static String mutiApplDeptId, mutiApplOrgId, mutiApplJobId, mutiApplEmpId, mutiApplPositionCode, mutiApplEmpNum;
    public static final String SYS_MANAGER = "-2";
    public static final String FLOW_MANAGER = "-1";
    private static int count = -1;//計算 該表單在 swf_item_txn_all的筆數
    private static String serviceActivityType;
    private static String stationId;
    private static String itemCompId;
    private static String ruleValue;
    private static String topLevel;
    private static String deptId;
    private static String jobId;
    private static String empId;
    private static String autoFlag;
    private static String ruleTopLevel;
    private static String ruleDeptId;
    private static String ruleJobId;
    private static String ruleEmpId;
    private static String ruleAutoFlag;
    private static boolean isManager;
    private static boolean isApproved;
    //private final static SwfFlowEngine sfe = new SwfFlowEngine();

    private static Connection conn;

    private static ResultSet hdrRS;
    private static ResultSet stationRS;
    //private ResultSet stationRuleRS;
    private static ResultSet txnRS;
    private static ResultSet applRS;
    private static ItemDriver itemDriver;
    private static ResultSet specialRS;

    public SwfFlowEngine() {
    }

    /*public static void pushItem(Item item) {
        if (item.getTxns() == null) {
            sfe.createFlow(null, null, String.valueOf(item.getHdrId()), item.getWritter().getUserName(), "APPLY");

        } else if (item.getTxns().get(0).getStation().getServiceActivityType() == 10) {
            sfe.createFlow(item.getNowTxnId(), "60", String.valueOf(item.getHdrId()), item.getWritter().getUserName(), "APPLY");
        }
        SwfApproverEngine sae = new SwfApproverEngine();
        sae.pushTask(item.getNowTxnId(),
                String.valueOf(item.getTxns().get(Integer.parseInt(item.getNowTxnId())).getServiceAppType()),
                String.valueOf(item.getHdrId()),
                item.getWritter().getUserName());

    }*/
    //接收JSP推送的資訊
    public static void pushTask(ArrayList<String> txnId, ArrayList<String> approveType, ArrayList<String> hdr, ArrayList<String> user, ArrayList<String> type, ArrayList<String> comment) {
        try {
            for (int i = 0; i < hdr.size(); i++) {
                createFlow(txnId.get(i), approveType.get(i), hdr.get(i), user.get(i), type.get(i), comment.get(i));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 因為一個Statement 執行不同的SQL所產生的ResultSet會互相衝突所以需要一個ResultSet建立一個Statement,
     * txnStmt 為tnxRS的Statement, hdrStmt 為hdrRS的Statement, stationStmt
     * 為stationRS的Statement, applStmt 為 applRS的statement, txnSql 紀錄
     * 搜尋SWF_ITEM_TXN_ALL 的 SQL, hdrSql 紀錄 搜尋SWF_ITEM_HDR_ALL 的 SQL, applSql 紀錄
     * 搜尋SWF_ITEM_APPL_ALL 的 SQL, stationSql 紀錄 搜尋SWF_STATION_ALL 的 SQL
     *
     * @author andy.dh.chen
     */
    //建立流程
    public static void createFlow(String txnId, String approveType, String hdrId, String userId, String type, String comment) {
        String url = "";
        String username = "";
        String password = "";
        Statement txnStmt = null;
        Statement hdrStmt = null;
        Statement stationStmt = null;
        Statement applStmt = null;
        Statement specialStmt = null;
        HashMap specialMap = new HashMap();
        ArrayList specialList = new ArrayList();
        BufferedReader reader = null;
        //狀態為提交時執行

        String txnSql = "SELECT * FROM " + SWF_ITEM_TXN_ALL + " WHERE 1=1 AND HDR_ID = " + hdrId + " ORDER BY LAST_TXN_ID";
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
            conn = DriverManager.getConnection(url, username, password);
            try {
                System.out.println(txnSql);
                txnStmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                txnRS = txnStmt.executeQuery(txnSql);

                while (txnRS.next()) {

                    //當狀態為提交且至少有一筆時代表至少有立案 所以紀錄下ITEM_TXN_ID之後更新立案ApprovedDate
                    if (txnRS.isFirst()) {
                        applyTxn = txnRS.getString("ITEM_TXN_ID");
                        System.out.println("立案ITEM_TXN_ID:" + applyTxn);
                    }
                    count++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (type.equals("APPLY")) {
                System.out.println("狀態:提交");
                try {
                    if (count <= 0) {
                        System.out.println("-----開始建立流程-----");
                        /* -1 代表 TXNS 沒有資料 需要建立TXNS
                     * 0 代表 儲存未提交 
                     * 有多申請人 要在此去判斷是否同部門 否的話以填單人為主 是的話 以多申請人職位最高去尋找上層主管
                         */

                        String hdrSql = "SELECT * FROM " + SWF_ITEM_HDR_ALL + " WHERE 1=1 AND HDR_ID = " + hdrId;
                        try {
                            hdrStmt = SwfFlowEngine.conn.createStatement();
                            hdrRS = hdrStmt.executeQuery(hdrSql);

                            while (hdrRS.next()) {
                                System.out.println("-----開始尋找是否有多申請人-----");
                                //多申請人SQL 找出該表單的是否有多申請人
                                String applSql = "SELECT sea.EMP_ID, sda.DEPT_ID, soda.JOB_LEVEL ,soda.position_code,sda.ORG_ID,sea.emp_num FROM " + SWF_ITEM_APPL_ALL + " siaa, " + SWF_EMPS_ALL + " sea, "
                                        + SWF_OFFICE_DUTY_ALL + " soda, " + SWF_DEPT_ALL + " sda WHERE 1=1 AND DECODE(sea.org_id,175, sea.org_dept_id || '10',82, sea.org_dept_id || '20',217, sea.org_dept_id || '30',sea.org_dept_id"
                                        + " ) = TO_CHAR(sda.dept_id) AND siaa.emp_id = sea.emp_id AND sea.duty_id = soda.duty_id AND HDR_ID = " + hdrId;
                                System.out.println(applSql);
                                applStmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                applRS = applStmt.executeQuery(applSql);

                                String mutiJobLevel = "";

                                applRS.last();
                                //先判斷是否大於0 大於0有多申請人 進入迴圈 等於0 直接去取得填單人去取得填單人的 EMP_ID DEPT_ID DEPT_LEVEL_CODE 儲存 JOB_ID目前暫無 所以設定成null
                                if (applRS.getRow() > 0) {
                                    applRS.beforeFirst();
                                    //當有多申請人時執行迴圈
                                    while (applRS.next()) {
                                        //當applRS為第一筆的時候去儲存第一筆的EMP_ID DEPT_ID JOB_LEVEL ORG_ID, JOB_ID目前暫無 所以設定成null
                                        if (applRS.isFirst()) {
                                            mutiApplEmpId = applRS.getString("EMP_ID");
                                            mutiApplJobId = "";
                                            mutiApplPositionCode = applRS.getString("POSITION_CODE");
                                            mutiApplDeptId = applRS.getString("DEPT_ID");
                                            mutiApplOrgId = applRS.getString("ORG_ID");
                                            mutiJobLevel = applRS.getString("JOB_LEVEL");
                                            mutiApplEmpNum = applRS.getString("EMP_NUM");
                                        } //當儲存的JOB_LEVEL小於當前applRS的JOB_LEVEL時 將當前的EMP_ID DEPT_ID JOB_LEVEL ORG_ID 儲存, JOB_ID目前暫無 所以設定成null
                                        else if (Integer.parseInt(mutiJobLevel) < Integer.parseInt(applRS.getString("JOB_LEVEL"))) {
                                            mutiApplEmpId = applRS.getString("EMP_ID");
                                            mutiApplJobId = "";
                                            mutiApplPositionCode = applRS.getString("POSITION_CODE");
                                            mutiApplDeptId = applRS.getString("DEPT_ID");
                                            mutiApplOrgId = applRS.getString("ORG_ID");
                                            mutiApplEmpNum = applRS.getString("EMP_NUM");
                                            mutiJobLevel = applRS.getString("DEPT_LEVEL_CODE");

                                            //當多申請人有不同部門時 去取得填單人的 EMP_ID DEPT_ID JOB_LEVEL ORG_ID 儲存, JOB_ID目前暫無 所以設定成null 並跳出迴圈
                                        } else if (!mutiApplDeptId.equals(applRS.getString("DEPT_ID"))) {
                                            System.out.println("-----多申請人有不同部門以填單人為主-----");
                                            applSql = "SELECT sea.EMP_ID, sda.DEPT_ID, sda.DEPT_LEVEL_CODE ,soda.position_code,sda.ORG_ID FROM " + SWF_EMPS_ALL + " sea, " + SWF_DEPTS_ALL + " sda"
                                                    + " WHERE　1=1 AND sea.EMP_ID = " + hdrRS.getString("PREPARED_BY") + "AND sea.ORG_DEPT_ID = sda.DEPT_ID ";
                                            System.out.println(applSql);
                                            applRS = applStmt.executeQuery(applSql);

                                            applRS.next();

                                            mutiApplEmpId = applRS.getString("EMP_ID");
                                            mutiApplJobId = "";
                                            mutiApplPositionCode = applRS.getString("POSITION_CODE");
                                            mutiApplDeptId = applRS.getString("DEPT_ID");
                                            mutiApplOrgId = applRS.getString("ORG_ID");
                                            break;
                                        }

                                    }
                                }

                                System.out.println("mutiApplEmpId:" + mutiApplEmpId);
                                System.out.println("mutiApplDeptId:" + mutiApplDeptId);
                                System.out.println("mutiApplOrgId:" + mutiApplOrgId);
                                System.out.println("mutiApplJobId:" + mutiApplJobId);
                                System.out.println("mutiApplPositionCode:" + mutiApplPositionCode);

                                String specialSql = "SELECT * FROM " + SWF_SPECIAL_STATION_ALL + " sssa," + SWF_ITEM_LINE_ALL + " sila WHERE 1=1 AND sssa.ENABLE_FLAG ='Y'"
                                        + " AND sssa.SERVICE_ITEM_ID = " + hdrRS.getString("SERVICE_ITEM_ID") + " AND DEPT_ID = " + mutiApplDeptId
                                        + " AND TO_NUMBER(sssa.attribute1) = sila.item_comp_id AND sila.attribute1 = sssa.ATTRIBUTE2 AND sila.hdr_id = " + hdrRS.getString("HDR_ID");

                                specialStmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                specialRS = specialStmt.executeQuery(specialSql);
                                System.out.println(specialSql);

                                while (specialRS.next()) {
                                    specialMap.put(specialRS.getString("STATION_ID"), specialRS.getString("EMP_ID"));
                                }

                                String stationSql;

                                //-1時找出完整的核決權限表 不等於-1代表已有立案 找出排除立案的核決權限表
                                if (count == -1) {
                                    stationSql = "SELECT ssa.station_id,ssa.service_activity_type,ssa.emp_id,ssa.dept_id,ssa.job_id,"
                                            + "ssa.top_level,ssa.auto_flag,aaa.item_comp_id,aaa.rule_value,aaa.rule_emp_id,aaa.rule_dept_id,"
                                            + "aaa.rule_job_id,aaa.rule_top_level,aaa.rule_auto_flag FROM " + SWF_STATION_ALL + " ssa "
                                            + " LEFT OUTER JOIN (SELECT ssta.station_id,ssra.item_comp_id,ssra.rule_value,ssra.emp_id rule_emp_id,"
                                            + "ssra.dept_id rule_dept_id,ssra.job_id rule_job_id,ssra.top_level rule_top_level,ssra.auto_flag rule_auto_flag"
                                            + " FROM " + SWF_STATION_TXN_ALL + " ssta," + SWF_STATION_RULE_ALL + " ssra WHERE 1 = 1 AND ssta.station_rule_id = ssra.station_rule_id"
                                            + " AND ssra.end_time IS NULL) aaa ON(ssa.station_id = aaa.station_id) WHERE 1 = 1 AND ssa.end_time IS NULL"
                                            + " AND ssa.service_item_id = " + hdrRS.getString("SERVICE_ITEM_ID") + " ORDER BY ssa.service_activity_type,ssa.seq";
                                } else {
                                    stationSql = "SELECT ssa.station_id,ssa.service_activity_type,ssa.emp_id,ssa.dept_id,ssa.job_id,"
                                            + "ssa.top_level,ssa.auto_flag,aaa.item_comp_id,aaa.rule_value,aaa.rule_emp_id,aaa.rule_dept_id,"
                                            + "aaa.rule_job_id,aaa.rule_top_level,aaa.rule_auto_flag FROM " + SWF_STATION_ALL + " ssa "
                                            + " LEFT OUTER JOIN (SELECT ssta.station_id,ssra.item_comp_id,ssra.rule_value,ssra.emp_id rule_emp_id,"
                                            + "ssra.dept_id rule_dept_id,ssra.job_id rule_job_id,ssra.top_level rule_top_level,ssra.auto_flag rule_auto_flag"
                                            + " FROM " + SWF_STATION_TXN_ALL + " ssta," + SWF_STATION_RULE_ALL + " ssra WHERE 1 = 1 AND ssta.station_rule_id = ssra.station_rule_id"
                                            + " AND ssra.end_time IS NULL) aaa ON(ssa.station_id = aaa.station_id) WHERE 1 = 1 AND ssa.SERVICE_ACTIVITY_TYPE != '10' AND ssa.end_time IS NULL"
                                            + " AND ssa.service_item_id = " + hdrRS.getString("SERVICE_ITEM_ID") + " ORDER BY ssa.service_activity_type,ssa.seq";

                                }
                                System.out.println(stationSql);
                                stationStmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                                stationRS = stationStmt.executeQuery(stationSql);

                                while (stationRS.next()) {
                                    serviceActivityType = stationRS.getString("SERVICE_ACTIVITY_TYPE");
                                    stationId = stationRS.getString("STATION_ID");
                                    itemCompId = stationRS.getString("ITEM_COMP_ID");
                                    ruleValue = stationRS.getString("RULE_VALUE");

                                    topLevel = stationRS.getString("TOP_LEVEL");
                                    deptId = stationRS.getString("DEPT_ID");
                                    jobId = stationRS.getString("JOB_ID");
                                    empId = stationRS.getString("EMP_ID");
                                    autoFlag = stationRS.getString("AUTO_FLAG");
                                    ruleTopLevel = stationRS.getString("RULE_TOP_LEVEL");
                                    ruleDeptId = stationRS.getString("RULE_DEPT_ID");
                                    ruleJobId = stationRS.getString("RULE_JOB_ID");
                                    ruleEmpId = stationRS.getString("RULE_EMP_ID");
                                    ruleAutoFlag = stationRS.getString("RULE_AUTO_FLAG");

                                    //審核 會審 知會 核決需要找管理職系
                                    if (serviceActivityType.equals("20")
                                            || serviceActivityType.equals("30")
                                            || serviceActivityType.equals("40")
                                            || serviceActivityType.equals("50")) {
                                        isManager = true;
                                        //核決只需找最終簽核人
                                        if (serviceActivityType.equals("40")) {
                                            isApproved = true;
                                        } else {
                                            isApproved = false;
                                        }
                                    } else {
                                        isManager = false;
                                        isApproved = false;
                                    }

                                    if (specialMap.containsKey(stationId)) {
                                        if (!specialList.contains(stationId)) {
                                            specialTxn(stationId, serviceActivityType, hdrRS.getString("HDR_ID"), userId, specialMap.get(stationId).toString());
                                            specialList.add(stationId);
                                        } else {
                                            continue;
                                        }
                                    } else {
                                        createItemTxn(stationId, serviceActivityType, itemCompId, ruleValue, hdrRS.getString("HDR_ID"), userId, type);
                                    }
                                }

                                System.out.println("-----建立流程結束-----");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println(ex.getMessage() + ":Line=" + ex.getStackTrace()[0].getLineNumber());
                        }
                    } else {
                        System.out.println("-----已有建立流程故不建立任何流程-----");
                    }

                    System.out.println("-----往SwfApproverEngine拋轉-----");
                    // 大於0 代表 已有流程 往簽核引擎拋轉
                    System.out.println("---------" + applyTxn);
                    if (txnId == null) {
                        SwfApproverEngine.pushTask(applyTxn, "10", hdrId, userId, comment);
                    } else {
                        SwfApproverEngine.pushTask(txnId, approveType, hdrId, userId, comment);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
                }
            } else if (type.equals("SAVE")) {
                //沒有建立任何流程
                System.out.println("狀態:儲存");
                if (count == -1) {
                    String hdrSql = " SELECT * FROM " + SWF_ITEM_HDR_ALL + " WHERE 1=1 AND HDR_ID = " + hdrId;
                    try {
                        hdrStmt = SwfFlowEngine.conn.createStatement();
                        hdrRS = hdrStmt.executeQuery(hdrSql);
                        while (hdrRS.next()) {
                            /*只找立案狀態*/
                            String stationSql = " SELECT ssa.STATION_ID, ssa.SERVICE_ACTIVITY_TYPE,ssra.ITEM_COMP_ID, ssra.RULE_VALUE FROM " + SWF_STATION_ALL + " ssa," + SWF_STATION_TXN_ALL + " ssta," + SWF_STATION_RULE_ALL + " ssra"
                                    + " WHERE 1=1 AND ssa.END_TIME IS NULL AND ssta.END_TIME IS NULL AND ssra.END_TIME IS NULL AND ssa.STATION_ID = ssta.STATION_ID(+) AND ssta.STATION_RULE_ID = ssra.STATION_RULE_ID(+) "
                                    + " AND ssa.SERVICE_ACTIVITY_TYPE = '10' AND ssa.SERVICE_ITEM_ID = " + hdrRS.getString("SERVICE_ITEM_ID") + " ORDER BY ssa.SERVICE_ACTIVITY_TYPE, ssa.SEQ";
                            System.out.println(stationSql);
                            stationStmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            stationRS = stationStmt.executeQuery(stationSql);

                            stationRS.next();

                            String serviceActivityType = stationRS.getString("SERVICE_ACTIVITY_TYPE");
                            String stationId = stationRS.getString("STATION_ID");
                            String itemCompId = stationRS.getString("ITEM_COMP_ID");
                            String ruleValue = stationRS.getString("RULE_VALUE");

                            //儲存
                            createItemTxn(stationId, serviceActivityType, itemCompId, ruleValue, hdrRS.getString("HDR_ID"), userId, type);
                        }
                        System.out.println("-----儲存完成-----");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
                    }
                    //已有儲存流程
                } else if (count == 0) {
                    System.out.println("-----狀態是儲存 但是已有儲存TXN 不觸動SwfFlowEngine-----");
                }
            } else {
                System.out.println("------狀態不是儲存或是提交 不觸動SwfFlowEngine------");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {

                if (hdrRS != null) {
                    hdrRS.close();
                    hdrRS = null;
                }
                if (stationRS != null) {
                    stationRS.close();
                    stationRS = null;
                }
                if (txnRS != null) {
                    txnRS.close();
                    txnRS = null;
                }
                if (applRS != null) {
                    applRS.close();
                    applRS = null;
                }

                if (applRS != null) {
                    applRS.close();
                    applRS = null;
                }

                if (txnStmt != null) {
                    txnStmt.close();
                    txnStmt = null;
                }

                if (hdrStmt != null) {
                    hdrStmt.close();
                    hdrStmt = null;
                }

                if (stationStmt != null) {
                    stationStmt.close();
                    stationStmt = null;
                }

                if (applStmt != null) {
                    applStmt.close();
                    applStmt = null;
                }

                if (specialStmt != null) {
                    specialStmt.close();
                    specialStmt = null;
                }

                if (conn != null) {
                    conn.commit();
                    conn.close();
                }

                nextTxnId = null;
                lastTxnId = null;
                nowTxnId = null;
                applyTxn = null;
                count = -1;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }
        }
    }

    //建立每個流程透過SWF_DML_PKG INSERT 到 SWF_ITEM_TXN_ALL裡面
    public static void createItemTxn(String stationId, String serviceActivityType, String itemCompId, String ruleValue, String hdrId, String userId, String type) {
        Boolean flag = true;
        String itemLineSql;
        String updateApplySql = "";
        ResultSet lineRS = null;
        Statement itemLineStmt = null;
        Statement insertTxnStmt = null;
        ArrayList<String> approver = new ArrayList<String>();
        if (type.equals("APPLY")) {
            //當itmeCompId有值時代表有特殊規則 
            if (itemCompId != null) {
                //去尋找 swf_item_line_all 用 hdrid跟itemcompid去找出該欄位值
                itemLineSql = "SELECT * FROM " + SWF_ITEM_LINE_ALL + " WHERE 1=1 AND ITEM_COMP_ID = " + itemCompId + " AND HDR_ID = " + hdrId;
                try {
                    itemLineStmt = SwfFlowEngine.conn.createStatement();
                    lineRS = itemLineStmt.executeQuery(itemLineSql);
                    lineRS.next();

                    //當欄位值不等於特殊規則的值時 flag = false
                    if (!lineRS.getString("ATTRIBUTE1").equals(ruleValue)) {
                        flag = false;
                    } else if (lineRS.getString("ATTRIBUTE1").equals(ruleValue)) {
                        if (!(serviceActivityType.equals("10") || serviceActivityType.equals("70"))) {
                            approver = getApproverId(ruleTopLevel, ruleDeptId, ruleJobId, ruleEmpId, ruleAutoFlag);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
                } finally {
                    try {
                        if (lineRS != null) {
                            lineRS.close();
                            lineRS = null;
                        }

                        itemLineSql = null;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else if (itemCompId == null) {
                if (!(serviceActivityType.equals("10") || serviceActivityType.equals("70"))) {
                    approver = getApproverId(topLevel, deptId, jobId, empId, autoFlag);
                }
            }

            /*if (approver.isEmpty()) {
                approver = null;
            }*/
            try {
                if (flag) {
                    /*
                    *立案狀態 last_txn_id 填入 -1
                    *ApplyTxn紀錄立案的itemTxnId等建立完所有流程會往下推送下一關
                     */
                    if (serviceActivityType.equals("10")) {
                        nowTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                        nextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                        applyTxn = nowTxnId;
                        System.out.println("立案ITEM_TXN_ID:" + applyTxn);
                        insertTxnSql = "BEGIN SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>-1" + ", P_ITEM_TXN_ID=>" + nowTxnId
                                + ", P_NEXT_TXN_ID=>" + nextTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>1,P_APPROVE_TYPE=>'10'" + ",P_APPROVER_ID=>" + hdrRS.getString("PREPARED_BY") + ",P_ATTRIBUTE1=>'" + hdrRS.getString("PREPARED_BY") + "'); END;";
                        lastTxnId = nowTxnId;
                        nowTxnId = nextTxnId;
                        nextTxnId = null;

                        //結案狀態 next_txn_id 填入 null
                    } else if (stationRS.isLast()) {
                        for (int i = 0; i < approver.size(); i++) {
                            String approverId = approver.get(i);
                            if (!approverId.equals("null")) {
                                insertTxnSql = "BEGIN SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + lastTxnId
                                        + ", P_ITEM_TXN_ID=>" + nowTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>2,P_APPROVE_TYPE=>'50',P_APPROVER_ID =>" + approverId + "); END;";
                            }
                        }
                        //驗收 當驗收關卡沒有設定以SWF_ITEM_APPL_ALL裡該表單有多少申請人建立多少個驗收人
                    } else if (serviceActivityType.equals("70")) {
                        applRS.beforeFirst();
                        while (applRS.next()) {
                            if (applRS.isFirst()) {
                                insertTxnSql = "BEGIN ";
                            }
                            nextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                            insertTxnSql += "SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + lastTxnId + ", P_ITEM_TXN_ID=>" + nowTxnId
                                    + ", P_NEXT_TXN_ID=>" + nextTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>2,P_APPROVE_TYPE=>'50',P_APPROVER_ID =>" + applRS.getString("EMP_ID") + "); ";
                            lastTxnId = nowTxnId;
                            nowTxnId = nextTxnId;
                            if (applRS.isLast()) {
                                insertTxnSql = "END; ";
                            }

                        }
                    }//其他簽核狀態
                    else {
                        for (int i = 0; i < approver.size(); i++) {
                            String approverId = approver.get(i);
                            if (i == 0) {
                                insertTxnSql = "BEGIN ";
                            }

                            /*
                    *lastTxnId如果為null代表已有立案因此lastTxnId要取得立案的ITEM_TXN_ID 
                    *nowTxnId也必須重新取得Sequence
                    *必須更新立案的lastTxnId
                             */
                            if (!approver.get(i).equals("null")) {
                                if (lastTxnId == null) {
                                    lastTxnId = applyTxn;
                                    System.out.println(applyTxn);
                                    nowTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                                    insertTxnSql += " SWF_DML_PKG.UPDATETXN(P_USER_ID =>'" + userId + "',P_NEXT_TXN_ID=>" + nowTxnId + ",P_ITEM_TXN_ID=>" + lastTxnId + ");";
                                    System.out.println(insertTxnSql);
                                }

                                nextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                                insertTxnSql += " SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + lastTxnId + ", P_ITEM_TXN_ID=>" + nowTxnId
                                        + ", P_NEXT_TXN_ID=>" + nextTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>2,P_APPROVE_TYPE=>'50',P_APPROVER_ID =>" + approverId + "); ";
                                if ((i + 1) == approver.size()) {
                                    insertTxnSql += " END; ";
                                }
                                lastTxnId = nowTxnId;
                                nowTxnId = nextTxnId;
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }
        } else if (type.equals("SAVE")) {
            nowTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
            try {
                insertTxnSql = "BEGIN SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>-1" + ", P_ITEM_TXN_ID=>" + nowTxnId
                        + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>1,P_APPROVE_TYPE=>'10'" + ",P_APPROVER_ID=>" + hdrRS.getString("PREPARED_BY") + "); END;";
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }

        }

        try {

            insertTxnStmt = SwfFlowEngine.conn.createStatement();
            if (insertTxnSql != "" && insertTxnSql != null) {
                System.out.println(insertTxnSql);
                insertTxnStmt.execute(insertTxnSql);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
        } finally {
            try {
                if (insertTxnStmt != null) {
                    insertTxnStmt.close();
                    insertTxnStmt = null;
                }
                if (itemLineStmt != null) {
                    itemLineStmt.close();
                    itemLineStmt = null;
                }
                flag = null;
                insertTxnSql = "";
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }

        }

    }

    public static void specialTxn(String stationId, String serviceActivityType, String hdrId, String userId, String empId) {
        Statement insertTxnStmt = null;
        try {
            insertTxnStmt = SwfFlowEngine.conn.createStatement();
            if (serviceActivityType.equals("10")) {
                nowTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                nextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                applyTxn = nowTxnId;
                System.out.println("立案ITEM_TXN_ID:" + applyTxn);
                insertTxnSql = "BEGIN SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>-1" + ", P_ITEM_TXN_ID=>" + nowTxnId
                        + ", P_NEXT_TXN_ID=>" + nextTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>1,P_APPROVE_TYPE=>'10'" + ",P_APPROVER_ID=>" + hdrRS.getString("PREPARED_BY") + "); END;";
                lastTxnId = nowTxnId;
                nowTxnId = nextTxnId;
                nextTxnId = null;

                //結案狀態 next_txn_id 填入 null
            } else if (stationRS.isLast()) {

                insertTxnSql = "BEGIN SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + lastTxnId
                        + ", P_ITEM_TXN_ID=>" + nowTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>2,P_APPROVE_TYPE=>'50',P_APPROVER_ID =>" + empId + "); END;";

                //驗收 當驗收關卡沒有設定以SWF_ITEM_APPL_ALL裡該表單有多少申請人建立多少個驗收人
            } else if (serviceActivityType.equals("70")) {
                applRS.beforeFirst();
                while (applRS.next()) {
                    if (applRS.isFirst()) {
                        insertTxnSql = "BEGIN ";
                    }
                    nextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                    insertTxnSql += "SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + lastTxnId + ", P_ITEM_TXN_ID=>" + nowTxnId
                            + ", P_NEXT_TXN_ID=>" + nextTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>2,P_APPROVE_TYPE=>'50',P_APPROVER_ID =>" + applRS.getString("EMP_ID") + "); ";
                    lastTxnId = nowTxnId;
                    nowTxnId = nextTxnId;
                    if (applRS.isLast()) {
                        insertTxnSql = "END; ";
                    }

                }
            }//其他簽核狀態
            else {

                insertTxnSql = "BEGIN ";

                /*
                    *lastTxnId如果為null代表已有立案因此lastTxnId要取得立案的ITEM_TXN_ID 
                    *nowTxnId也必須重新取得Sequence
                    *必須更新立案的lastTxnId
                 */
                if (lastTxnId == null) {
                    lastTxnId = applyTxn;
                    System.out.println(applyTxn);
                    nowTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                    insertTxnSql += " SWF_DML_PKG.UPDATETXN(P_USER_ID =>'" + userId + "',P_NEXT_TXN_ID=>" + nowTxnId + ",P_ITEM_TXN_ID=>" + lastTxnId + ");";
                    System.out.println(insertTxnSql);
                }

                nextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");
                insertTxnSql += " SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_USER_ID=>'" + userId + "',P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + lastTxnId + ", P_ITEM_TXN_ID=>" + nowTxnId
                        + ", P_NEXT_TXN_ID=>" + nextTxnId + ", P_HDR_ID=>" + hdrId + ",P_PROCESS_FLAG=>2,P_APPROVE_TYPE=>'50',P_APPROVER_ID =>" + empId + "); ";

                insertTxnSql += " END; ";

                lastTxnId = nowTxnId;
                nowTxnId = nextTxnId;

            }

            if (insertTxnSql != "" && insertTxnSql != null) {
                System.out.println(insertTxnSql);
                insertTxnStmt.execute(insertTxnSql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
        } finally {
            try {
                if (insertTxnStmt != null) {
                    insertTxnStmt.close();
                    insertTxnStmt = null;
                }
                insertTxnSql = "";
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }
        }
    }

    /**
     * 將topLevel,deptId,jobId,empId,autoFlag傳入判斷找出最終簽核人, approverId
     * 紀錄經過所有判斷後的最終簽核人
     *
     * @author andy.dh.chen
     */
    //取得簽核人
    public static ArrayList<String> getApproverId(String topLevel, String deptId, String jobId, String empId, String autoFlag) {
        ArrayList<String> approverId = new ArrayList<String>();
        System.out.println("-----取得簽核人-----");
        do {
            //自動簽核的時候 approveId為系統管理員 代表這各站別的簽核人為系統自動簽核
            if (autoFlag.equals("Y")) {
                System.out.println("-----自動簽核-----");
                approverId.add(SYS_MANAGER);
                System.out.println("approverId:" + SYS_MANAGER);
                //不是自動簽核的時候去以核決權限表的設定去找出簽核人
            } else if (autoFlag.equals("N")) {
                //當empId不是null的時候 approveId為empId
                if (empId != null) {
                    System.out.println("-----指定人員-----");
                    System.out.println("approverId:" + empId);
                    approverId.add(empId);
                    System.out.println("-----指定人員結束-----");
                }//當JobId 不等於null 其他為null時執行這段 
                else if (jobId != null && empId == null && topLevel == null && deptId == null) {
                    System.out.println("-----依據職務-----");
                }//當topLevel不等於null 其他為null時執行這段
                else if (topLevel != null && empId == null && deptId == null && jobId == null) {
                    System.out.println("-----依據最高層級-----");
                    ArrayList<String> temp = getApproverIdByDeptIdTopLevelVer20("", topLevel);
                    //當approverId的size等於0 或是 等於null時 將approverId填入流程管理員
                    if (temp == null) {
                        System.out.println("-----找不到簽核人approverId填入流程管理員-----");
                        approverId.add(FLOW_MANAGER);
                    } else if (temp.isEmpty()) {
                        System.out.println("-----找不到簽核人approverId填入流程管理員-----");
                        approverId.add(FLOW_MANAGER);
                    } else {
                        approverId = temp;
                    }
                    System.out.println("-----依據最高層級結束-----");
                }//當deptId 及 topLevel不為null 其他為null時執行這段
                else if (deptId != null && topLevel != null && empId == null && jobId == null) {
                    System.out.println("-----依據部門及最高層級-----");
                    ArrayList<String> temp = getApproverIdByDeptIdTopLevelVer20(deptId, topLevel);
                    //當approverId的size等於0 或是 等於null時 將approverId填入流程管理員
                    if (temp == null) {
                        System.out.println("-----找不到簽核人approverId填入流程管理員-----");
                        approverId.add(FLOW_MANAGER);
                    } else if (temp.isEmpty()) {
                        System.out.println("-----找不到簽核人approverId填入流程管理員-----");
                        approverId.add(FLOW_MANAGER);
                    } else {
                        approverId = temp;
                    }
                    System.out.println("-----依據部門及最高層級-----");
                }//當JobId 及 deptId不為null 其他為null時執行這段
                else if (jobId != null && deptId != null && empId == null && topLevel == null) {
                    System.out.println("-----依據職務及部門-----");
                }//所有條件都不成立代表此流程的簽核人有問題 approveId為流程管理員
                if (!"".equals(approverId) && approverId.isEmpty()) {
                    System.out.println("-----核決權限表未設定完全-----");
                    approverId.add(FLOW_MANAGER);
                }
            }
        } while (approverId.isEmpty());

        return approverId;
    }

    /**
     * 最高層級跟部門加最高層級共用function , 最高層級傳入的deptId為填單人或同部門多申請人職位最高的deptId 部門,
     * 部門加最高層級傳入的是核決權限表上設定的deptId, sql 紀錄 SQL,approverId 紀錄簽核人,stmt 為 rs 的
     * ResultSet, rs 為 sql至DB執行的結果集
     *
     * @author andy.dh.chen
     */
    /* public ArrayList<String> getApproverIdByDeptIdTopLevel(String deptId, String topLevel) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> approverId = new ArrayList<String>();
        Statement stmt = null;
        ResultSet rs = null;
        sql.append(" SELECT DISTINCT sea.emp_id,sea.emp_cname, so.job_level,sd.dept_id,sd.dept_name,sd.dept_level_code,sd.parent_dept_id,LEVEL"
                + " FROM  ( " + SWF_DEPT_ALL + " sd LEFT OUTER JOIN " + SWF_EMPS_ALL + " sea ON(sd.dept_id = TO_NUMBER(DECODE(sea.org_id,175, sea.org_dept_id || '10',"
                + "82, sea.org_dept_id || '20', 217, sea.org_dept_id || '30',sea.org_dept_id)))) LEFT OUTER JOIN "
                + SWF_OFFICE_DUTY_ALL + " so ON(sea.duty_id = so.duty_id) WHERE 1=1");

        //判斷是否需要尋找管理職系
        if (isManager) {
            sql.append(" AND so.position_code LIKE 'M%'");
        }

        //判斷deptId跟topLevel不為null及空字串
        if (!"".equals(deptId) && deptId != null && !"".equals(topLevel) && topLevel != null) {
            sql.append(" AND SYSDATE BETWEEN sea.take_office_date AND NVL(sea.leave_office_date, SYSDATE)");

            if (isApproved) {
                sql.append(" AND sd.dept_level_code = " + topLevel);
            } else {
                sql.append(" AND (sd.dept_level_code BETWEEN " + topLevel + " AND (SELECT dept_level_code FROM swf_dept_all WHERE  dept_id = " + deptId + ") AND SUBSTR('" + mutiApplPositionCode + "', 1, 1) != 'M')");
            }
            sql.append(" START WITH sd.dept_id = " + deptId);
            sql.append(" CONNECT BY dept_id = PRIOR parent_dept_id ORDER BY LEVEL ASC,so.job_level asc");
        }

        try {
            stmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println(sql);
            rs = stmt.executeQuery(sql.toString());

            rs.last();

            //判斷是否有筆數
            if (rs.getRow() > 0) {
                rs.beforeFirst();
                while (rs.next()) {
                    approverId.add(rs.getString("EMP_ID"));
                    System.out.println("簽核人:" + rs.getString("EMP_CNAME"));
                    System.out.println("approverId:" + rs.getString("EMP_ID"));
                }
                //沒有尋找上一層主管
            } else {
                System.out.println("-----未找到簽核人尋找上一層主管-----");
                sql.delete(0, sql.length());

                sql.append(" SELECT DISTINCT sea.emp_id,sea.emp_cname, so.job_level,sd.dept_id,sd.dept_name,sd.dept_level_code,sd.parent_dept_id,LEVEL"
                        + " FROM  ( " + SWF_DEPT_ALL + " sd LEFT OUTER JOIN " + SWF_EMPS_ALL + " sea ON(sd.dept_id = TO_NUMBER(DECODE(sea.org_id,175, sea.org_dept_id || '10',"
                        + "82, sea.org_dept_id || '20', 217, sea.org_dept_id || '30',sea.org_dept_id)))) LEFT OUTER JOIN "
                        + SWF_OFFICE_DUTY_ALL + " so ON(sea.duty_id = so.duty_id) WHERE 1=1");

                //判斷是否需要尋找管理職系
                if (isManager) {
                    sql.append(" AND so.position_code LIKE 'M%'");
                }

                //判斷deptId跟topLevel不為null及空字串
                if (!"".equals(deptId) && deptId != null && !"".equals(topLevel) && topLevel != null) {
                    sql.append(" AND SYSDATE BETWEEN sea.take_office_date AND NVL(sea.leave_office_date, SYSDATE)");
                    sql.append(" AND sd.dept_level_code BETWEEN " + (Integer.parseInt(topLevel) - 1) + " AND (SELECT dept_level_code FROM swf_dept_all WHERE  dept_id = " + deptId + ")");
                    sql.append(" AND sd.dept_level_code < " + topLevel);
                    sql.append(" START WITH sd.dept_id =" + deptId);
                    sql.append(" CONNECT BY dept_id = PRIOR parent_dept_id ORDER BY LEVEL ASC,so.job_level asc");
                }
                System.out.println(sql);
                rs = stmt.executeQuery(sql.toString());

                rs.last();

                //判斷是否有筆數
                if (rs.getRow() > 0) {
                    rs.beforeFirst();
                    while (rs.next()) {
                        approverId.add(rs.getString("EMP_ID"));
                        System.out.println("簽核人:" + rs.getString("EMP_CNAME"));
                        System.out.println("approverId:" + rs.getString("EMP_ID"));
                    }
                    //上層主管也找不到時return null
                } else {
                    approverId = null;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            approverId = null;
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }
        }
        return approverId;
    }*/
    public static ArrayList<String> getApproverIdByDeptIdTopLevelVer20(String deptId, String topLevel) {
        StringBuilder sql = new StringBuilder();
        ArrayList<String> approverId = new ArrayList<String>();
        Statement stmt = null;
        ResultSet rs = null;
        sql.append("SELECT DISTINCT sd.dept_id, sd.dept_name, nvl(to_char(sea.emp_id),'-1') emp_id,"
                + " nvl(sea.emp_cname,'null') emp_cname,sd.dept_level_code,LEVEL,sea.job_level"
                + " FROM swf_dept_all se,(swf_dept_all sd LEFT OUTER JOIN (SELECT se.emp_id,se.emp_cname,"
                + " se.org_id,se.org_dept_id,so.job_level FROM   swf_emps_all se,swf_office_duty_all so");

        //判斷是否需要尋找管理職系
        if (isManager) {
            sql.append(" WHERE so.position_code LIKE 'M%'");
        } else {
            sql.append(" WHERE 1=1 ");
        }

        sql.append(" AND SYSDATE BETWEEN se.take_office_date AND NVL(se.leave_office_date, SYSDATE)"
                + " AND se.duty_id = so.duty_id  UNION ALL SELECT se.emp_id,se.emp_cname,"
                + " se.org_id,TO_NUMBER(SUBSTR(TO_CHAR(saa.dept_id), 0, LENGTH(TO_CHAR(saa.dept_id)) - 2)) org_dept_id,"
                + " so.job_level FROM swf_auth_all saa,swf_emps_all se,swf_office_duty_all so WHERE 1 = 1"
                + " AND se.emp_id = saa.emp_id AND SYSDATE BETWEEN se.take_office_date AND NVL(se.leave_office_date, SYSDATE)"
                + " AND SYSDATE BETWEEN saa.start_time AND saa.end_time AND se.duty_id = so.duty_id");

        //判斷是否需要尋找管理職系
        if (isManager) {
            sql.append(" AND so.position_code LIKE 'M%'");
        }

        sql.append(") sea ON(sd.dept_id = TO_NUMBER(DECODE(sea.org_id,"
                + " 175, sea.org_dept_id || '10',82, sea.org_dept_id || '20',217, sea.org_dept_id || '30',"
                + " sea.org_dept_id )))) WHERE se.dept_id = sd.dept_id");

        //判斷deptId跟topLevel不為null及空字串
        if (deptId.equals("")) {
            if (isApproved) {
                sql.append(" AND se.dept_level_code = " + topLevel);
            } else {
                sql.append(" AND (se.dept_level_code BETWEEN " + topLevel + " AND (SELECT dept_level_code FROM swf_dept_all WHERE  dept_id = " + mutiApplDeptId + "))");
            }

            sql.append("START WITH se.dept_id = (SELECT CASE WHEN so.position_code LIKE 'M%' THEN sda.parent_dept_id"
                    + " ELSE sda.dept_id END FROM  swf_emps_all sea,swf_dept_all sda,swf_office_duty_all so"
                    + " WHERE  TO_NUMBER(DECODE(sea.org_id,175, sea.org_dept_id || '10',82, sea.org_dept_id || '20',"
                    + " 217, sea.org_dept_id || '30',sea.org_dept_id )) = sda.dept_id AND sea.duty_id = so.duty_id"
                    + " AND sea.emp_num = '" + mutiApplEmpNum + "') CONNECT BY se.dept_id = PRIOR se.PARENT_dept_ID ORDER BY LEVEL ASC,sea.job_level");

        } else {
            if (isApproved) {
                sql.append(" AND se.dept_level_code = " + topLevel);
            } else {
                sql.append(" AND (se.dept_level_code BETWEEN " + topLevel + " AND (SELECT dept_level_code FROM swf_dept_all WHERE  dept_id = " + mutiApplDeptId + "))");
            }

            sql.append("START WITH se.dept_id = " + deptId + " CONNECT BY se.dept_id = PRIOR se.PARENT_dept_ID ORDER BY LEVEL ASC,sea.job_level");
        }

        try {
            stmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println(sql);
            rs = stmt.executeQuery(sql.toString());

            rs.last();

            //判斷是否有筆數
            if (rs.getRow() > 0) {
                rs.beforeFirst();
                while (rs.next()) {

                    if (!rs.getString("EMP_ID").equals("null")) {
                        approverId.add(rs.getString("EMP_ID"));
                        System.out.println("簽核人:" + rs.getString("EMP_CNAME"));
                        System.out.println("approverId:" + rs.getString("EMP_ID"));
                    }
                    /*if (!rs.getString("EMP_ID").equals("-1") || !rs.getString("EMP_CNAME").equals("null")) {
                        approverId.add(rs.getString("EMP_ID"));
                        System.out.println("簽核人:" + rs.getString("EMP_CNAME"));
                        System.out.println("approverId:" + rs.getString("EMP_ID"));
                    } else {
                        System.out.println("-----找不到簽核人 尋找兼任-----");
                        approverId.add(getConcurrentPost(rs.getString("DEPT_ID")));
                        System.out.println("-----尋找兼任結束-----");
                    }*/
                }

            }
            rs.last();
            /*判斷有無簽核人 無簽核人尋找上一層主管*/
            if ((rs.getRow() == 1 && approverId.get(0).equals("null")) || rs.getRow() == 0) {
                System.out.println("-----未找到簽核人尋找上一層主管-----");
                sql.delete(0, sql.length());

                sql.append("SELECT DISTINCT sd.dept_id, sd.dept_name, nvl(to_char(sea.emp_id),'-1') emp_id,"
                        + " nvl(sea.emp_cname,'null') emp_cname,sd.dept_level_code,LEVEL,sea.job_level"
                        + " FROM swf_dept_all se,(swf_dept_all sd LEFT OUTER JOIN (SELECT se.emp_id,se.emp_cname,"
                        + " se.org_id,se.org_dept_id,so.job_level FROM   swf_emps_all se,swf_office_duty_all so");

                //判斷是否需要尋找管理職系
                if (isManager) {
                    sql.append(" WHERE so.position_code LIKE 'M%'");
                } else {
                    sql.append(" WHERE 1=1 ");
                }

                sql.append(" AND SYSDATE BETWEEN se.take_office_date AND NVL(se.leave_office_date, SYSDATE)"
                        + " AND se.duty_id = so.duty_id  UNION ALL SELECT se.emp_id,se.emp_cname,"
                        + " se.org_id,TO_NUMBER(SUBSTR(TO_CHAR(saa.dept_id), 0, LENGTH(TO_CHAR(saa.dept_id)) - 2)) org_dept_id,"
                        + " so.job_level FROM swf_auth_all saa,swf_emps_all se,swf_office_duty_all so WHERE 1 = 1"
                        + " AND se.emp_id = saa.emp_id AND SYSDATE BETWEEN se.take_office_date AND NVL(se.leave_office_date, SYSDATE)"
                        + " AND SYSDATE BETWEEN saa.start_time AND saa.end_time AND se.duty_id = so.duty_id");

                //判斷是否需要尋找管理職系
                if (isManager) {
                    sql.append(" AND so.position_code LIKE 'M%'");
                }

                sql.append(") sea ON(sd.dept_id = TO_NUMBER(DECODE(sea.org_id,"
                        + " 175, sea.org_dept_id || '10',82, sea.org_dept_id || '20',217, sea.org_dept_id || '30',"
                        + " sea.org_dept_id )))) WHERE se.dept_id = sd.dept_id");

                sql.append(" AND sd.dept_level_code < " + topLevel);

                //判斷deptId跟topLevel不為null及空字串
                if (deptId.equals("")) {
                    sql.append(" AND (se.dept_level_code BETWEEN " + (Integer.parseInt(topLevel) - 1) + " AND (SELECT dept_level_code FROM swf_dept_all WHERE  dept_id = " + mutiApplDeptId + "))");
                    sql.append("START WITH se.dept_id = (SELECT CASE WHEN so.position_code LIKE 'M%' THEN sda.parent_dept_id"
                            + " ELSE sda.dept_id END FROM  swf_emps_all sea,swf_dept_all sda,swf_office_duty_all so"
                            + " WHERE  TO_NUMBER(DECODE(sea.org_id,175, sea.org_dept_id || '10',82, sea.org_dept_id || '20',"
                            + " 217, sea.org_dept_id || '30',sea.org_dept_id )) = sda.dept_id AND sea.duty_id = so.duty_id"
                            + " AND sea.emp_num = '" + mutiApplEmpNum + "') CONNECT BY se.dept_id = PRIOR se.PARENT_dept_ID ORDER BY LEVEL ASC,sea.job_level");
                } else {
                    sql.append(" AND (se.dept_level_code BETWEEN " + (Integer.parseInt(topLevel) - 1) + " AND (SELECT dept_level_code FROM swf_dept_all WHERE  dept_id = " + deptId + "))");
                    sql.append("START WITH se.dept_id = " + deptId + " CONNECT BY se.dept_id = PRIOR se.PARENT_dept_ID ORDER BY LEVEL ASC,sea.job_level");
                }

                System.out.println(sql);
                rs = stmt.executeQuery(sql.toString());

                rs.last();

                //判斷是否有筆數
                if (rs.getRow() > 0) {
                    rs.beforeFirst();
                    while (rs.next()) {

                        if (!rs.getString("EMP_ID").equals("null")) {
                            approverId.add(rs.getString("EMP_ID"));
                            System.out.println("簽核人:" + rs.getString("EMP_CNAME"));
                            System.out.println("approverId:" + rs.getString("EMP_ID"));
                        }
                        /*if (!rs.getString("EMP_ID").equals("null") || !rs.getString("EMP_CNAME").equals("null")) {
                            approverId.add(rs.getString("EMP_ID"));
                            System.out.println("簽核人:" + rs.getString("EMP_CNAME"));
                            System.out.println("approverId:" + rs.getString("EMP_ID"));
                        } else {
                            System.out.println("-----找不到簽核人 尋找兼任-----");
                            approverId.add(getConcurrentPost(rs.getString("DEPT_ID")));
                            System.out.println("-----尋找兼任結束-----");
                        }*/
                    }
                    //上層主管也找不到時return null
                } else {
                    approverId = null;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            approverId = null;
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
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }
        }
        return approverId;
    }

    /**
     * nowNextTxnId 當前ITEM_TXN_ID的NEXT_TXN_ID, addNextTxnId 新增加的ITEM_TXN_ID,
     * stationId 當前ITEM_TXN_ID的STATION_ID, HdrId 當前ITEM_TXN_ID的HDR_ID,
     * addStmt為Connection建立的Statement,addRS為SWF_ITEM_TXN_ALL 的
     * ResultSet,updateTxnSql為儲存PL/SQL的StringBuilder,ApproverId為所有會簽人,user為當前的使用者
     *
     * @author andy.dh.chen
     */
    /*加簽*/
    public void addApproverTxn(String nowTxnId, ArrayList<String> ApproverId, String userId) {

        String addSql, nowNextTxnId, addNextTxnId, stationId, HdrId;
        Statement addStmt = null;
        ResultSet addRS = null;
        String firstApproverId = "";
        StringBuilder updateTxnSql = new StringBuilder();
        try {
            //從SWF_ITEM_TXN_ALL裡找出當前的ITEM_TXN_ID
            addSql = "SELECT * FROM " + SWF_ITEM_TXN_ALL + " WHERE 1=1 AND ITEM_TXN_ID = " + nowTxnId;
            addStmt = SwfFlowEngine.conn.createStatement();
            System.out.println(addSql);

            addRS = addStmt.executeQuery(addSql);

            addRS.next();
            System.out.println(ApproverId.size());
            for (int i = 0; i < ApproverId.size(); i++) {

                //當第一次去取得當前ITEM_TXN_ID的approverId
                if (i == 0) {
                    firstApproverId = addRS.getString("APPROVER_ID");
                    updateTxnSql.append("BEGIN");
                }

                //取得當前ITEM_TXN_ID的station_id,next_txn_id,hdr_id
                stationId = addRS.getString("STATION_ID");
                nowNextTxnId = addRS.getString("NEXT_TXN_ID");
                HdrId = addRS.getString("HDR_ID");

                addNextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");//取得新增的ITEM_TXN_ID

                //先將當前的ITEM_TXN_ID 的NEXT_TXN_ID改成新增的ITEM_TXN_ID
                updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>" + userId + ",P_NEXT_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowTxnId + ");");

                //新增一筆TXN
                updateTxnSql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_ITEM_TXN_ID=>" + addNextTxnId + ",P_USER_ID=>" + userId + ",P_NEXT_TXN_ID=>" + nowNextTxnId + ",P_STATION_ID=>" + stationId
                        + ",P_LAST_TXN_ID=>" + nowTxnId + ",P_HDR_ID=>" + HdrId + ",P_APPROVER_ID=>" + ApproverId.get(i) + ",P_PROCESS_FLAG=>2,p_approve_type=> '50',P_ATTRIBUTE4=>'Y');");

                //將原本下一筆的ITEM_TXN_ID的LAST_TXN_ID改成新增的ITEM_TXN_ID
                updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>" + userId + ",P_LAST_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowNextTxnId + "); ");

                //當最後一筆時要再額外建立一筆當前的站別
                if (i == (ApproverId.size() - 1)) {

                    nowTxnId = addNextTxnId;
                    addNextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");

                    //先將當前的ITEM_TXN_ID 的NEXT_TXN_ID改成新增的ITEM_TXN_ID
                    updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>" + userId + ",P_NEXT_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowTxnId + ");");;

                    //新增一筆TXN
                    updateTxnSql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_ITEM_TXN_ID=>" + addNextTxnId + ",P_USER_ID=>" + userId + ",P_NEXT_TXN_ID=>" + nowNextTxnId + ",P_STATION_ID=>" + stationId
                            + ",P_LAST_TXN_ID=>" + nowTxnId + ",P_HDR_ID=>" + HdrId + ",P_APPROVER_ID=>" + firstApproverId + ",P_PROCESS_FLAG=>2,p_approve_type=> '50',P_ATTRIBUTE2=>'Y');");

                    //將原本下一筆的ITEM_TXN_ID的LAST_TXN_ID改成新增的ITEM_TXN_ID
                    updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>" + userId + ",P_LAST_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowNextTxnId + "); ");
                    updateTxnSql.append("END;");
                }

                /*
                *當加簽人數大於一人時將nowTxnId更改為上一筆新增的addNextTxnId
                 */
                nowTxnId = addNextTxnId;
            }
            System.out.println(updateTxnSql);
            addStmt.executeQuery(updateTxnSql.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
        } finally {

            try {
                updateTxnSql = null;
                addSql = null;
                if (addRS != null) {
                    addRS.close();
                    addRS = null;
                }
                if (addStmt != null) {
                    addStmt = null;
                    addStmt.close();
                }

                nowNextTxnId = null;
                addNextTxnId = null;
                stationId = null;
                HdrId = null;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }

        }

    }

    /**
     * nowNextTxnId 當前ITEM_TXN_ID的NEXT_TXN_ID, addNextTxnId 新增加的ITEM_TXN_ID,
     * stationId 當前ITEM_TXN_ID的STATION_ID, HdrId 當前ITEM_TXN_ID的HDR_ID,
     * addCommonApproverStmt為Connection建立的Statement,addCommonApproverRS為SWF_ITEM_TXN_ALL
     * 的
     * ResultSet,updateTxnSql為儲存PL/SQL的StringBuilder,ApproverId為所有會簽人,user為當前的使用者
     *
     * @author andy.dh.chen
     */
    /*會簽*/
    public void addCommonApproverTxn(String nowTxnId, ArrayList<String> ApproverId, String user) {
        Statement addCommonApproverStmt = null;
        ResultSet addCommonApproverRS = null;
        StringBuilder updateTxnSql = new StringBuilder();
        String firstApproverId = "", addSql, stationId, nowNextTxnId, HdrId, addNextTxnId;
        try {
            for (int i = 0; i < ApproverId.size(); i++) {
                updateTxnSql.append("BEGIN");
                //從SWF_ITEM_TXN_ALL裡找出當前的ITEM_TXN_ID
                addSql = "SELECT * FROM " + SWF_ITEM_TXN_ALL + " WHERE 1=1 AND ITEM_TXN_ID = " + nowTxnId;
                addCommonApproverStmt = SwfFlowEngine.conn.createStatement();
                System.out.println(addSql);
                addCommonApproverRS = addCommonApproverStmt.executeQuery(addSql);

                addCommonApproverRS.next();

                //取得當前ITEM_TXN_ID的station_id,next_txn_id,hdr_id
                stationId = addCommonApproverRS.getString("STATION_ID");
                nowNextTxnId = addCommonApproverRS.getString("NEXT_TXN_ID");
                HdrId = addCommonApproverRS.getString("HDR_ID");

                //當第一次去取得當前ITEM_TXN_ID的approverId
                if (i == 0) {
                    firstApproverId = addCommonApproverRS.getString("APPROVER_ID");
                }

                addNextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");//取得新增的ITEM_TXN_ID

                //先將當前的ITEM_TXN_ID 的NEXT_TXN_ID改成新增的ITEM_TXN_ID
                updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>'" + user + "',P_NEXT_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowTxnId + ");");;

                //新增一筆TXN
                updateTxnSql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_ITEM_TXN_ID=>" + addNextTxnId + ",P_USER_ID=>'" + user + "',P_NEXT_TXN_ID=>" + nowNextTxnId + ",P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + nowTxnId + ",P_HDR_ID=>" + HdrId + ",P_APPROVER_ID=>" + ApproverId.get(i) + ");");

                //將原本下一筆的ITEM_TXN_ID的LAST_TXN_ID改成新增的ITEM_TXN_ID
                updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>'" + user + "',P_LAST_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowNextTxnId + "); ");
                //當最後一筆時要再額外建立一筆當前的站別
                if (i == ApproverId.size() - 1) {

                    nowTxnId = addNextTxnId;
                    addNextTxnId = getSequence("SWF_ITEM_TXN_ALL_S1");

                    //先將當前的ITEM_TXN_ID 的NEXT_TXN_ID改成新增的ITEM_TXN_ID
                    updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>'" + user + "',P_NEXT_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowTxnId + ");");;

                    //新增一筆TXN
                    updateTxnSql.append(" SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(P_ITEM_TXN_ID=>" + addNextTxnId + ",P_USER_ID=>'" + user + "',P_NEXT_TXN_ID=>" + nowNextTxnId + ",P_STATION_ID=>" + stationId + ",P_LAST_TXN_ID=>" + nowTxnId + ",P_HDR_ID=>" + HdrId + ",P_APPROVER_ID=>" + firstApproverId + ");");

                    //將原本下一筆的ITEM_TXN_ID的LAST_TXN_ID改成新增的ITEM_TXN_ID
                    updateTxnSql.append(" SWF_DML_PKG.UPDATETXN(P_USER_ID =>'" + user + "',P_LAST_TXN_ID=>" + addNextTxnId + ",P_ITEM_TXN_ID=>" + nowNextTxnId + "); ");
                }
                updateTxnSql.append("END;");
                System.out.println(updateTxnSql);
                addCommonApproverStmt.executeQuery(updateTxnSql.toString());

                /*
                *當會簽人數大於一人時將nowTxnId更改為上一筆新增的addNextTxnId
                *並將 updateTxnSql所紀錄的PL/SQL語法清空
                 */
                updateTxnSql.delete(0, updateTxnSql.length());
                nowTxnId = addNextTxnId;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
        } finally {

            try {
                updateTxnSql = null;
                addSql = null;
                if (addCommonApproverRS == null) {
                    addCommonApproverRS = null;
                    addCommonApproverRS.close();
                }

                if (addCommonApproverStmt == null) {
                    addCommonApproverStmt.close();
                    addCommonApproverStmt = null;
                }
                nowNextTxnId = null;
                addNextTxnId = null;
                stationId = null;
                HdrId = null;
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }

        }
    }

    /*
    傳入SequenceName取得Sequence的值
     */
    public static String getSequence(String seqName) {
        String sequenceSql = "SELECT " + seqName + ".NEXTVAL FROM DUAL";
        Statement seqStmt = null;
        ResultSet seqRS = null;
        String seq = "";
        try {
            seqStmt = SwfFlowEngine.conn.createStatement();
            seqRS = seqStmt.executeQuery(sequenceSql);
            seqRS.next();
            seq = seqRS.getString(1);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            return null;
        } finally {
            try {
                if (seqRS == null) {
                    seqRS.close();
                    seqRS = null;
                }

                if (seqStmt == null) {
                    seqStmt.close();
                    seqStmt = null;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }

            return seq;
        }
    }

    public String getConcurrentPost(String deptId) {
        Statement postStmt = null;
        ResultSet postStmtRS = null;
        String approverId = "";
        try {
            String sql = "select saa.emp_id,sea.emp_cname\n"
                    + "from " + SWF_AUTH_ALL + " saa," + SWF_EMPS_ALL + " sea \n"
                    + "where 1=1\n"
                    + "and saa.emp_id = sea.emp_id\n"
                    + "and sysdate between saa.start_time and saa.end_time\n"
                    + "and dept_id = " + deptId;

            postStmt = SwfFlowEngine.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println(sql);
            postStmtRS = postStmt.executeQuery(sql);
            postStmtRS.last();
            if (postStmtRS.getRow() == 0) {
                approverId = "-1";
                System.out.println("兼任簽核人:null");
                System.out.println("兼任approverId:null");
            } else {
                postStmtRS.beforeFirst();
                postStmtRS.next();
                approverId = postStmtRS.getString("EMP_ID");
                System.out.println("兼任簽核人:" + postStmtRS.getString("EMP_CNAME"));
                System.out.println("兼任approverId:" + postStmtRS.getString("EMP_ID"));
            }
        } catch (Exception ex) {
            approverId = "-1";
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            return null;
        } finally {
            try {
                if (postStmtRS != null) {
                    postStmtRS.close();
                }

                if (postStmt != null) {
                    postStmt.close();
                }

                postStmtRS = null;
                postStmt = null;

            } catch (Exception ex) {
                approverId = "-1";
                ex.printStackTrace();
                System.out.println(ex.getMessage() + ": Line =" + ex.getStackTrace()[0].getLineNumber());
            }

            return approverId;
        }
    }

    //當tomcat啟動時會去取得一個JDBC
    /*public void contextInitialized(ServletContextEvent sce) {

        try {

            System.out.println("-----------------------------");
            System.out.println("SwfFlowEngine初始化連線資訊成功");
            System.out.println("-----------------------------");

        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SwfFlowEngine初始化連線資訊失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }*/
    //當tomcat關閉時會將JDBC給關閉
    /*public void contextDestroyed(ServletContextEvent sce) {
        try {

            System.out.println("-----------------------------");
            System.out.println("SwfFlowEngine關閉連線資訊成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SwfFlowEngine關閉連線資訊失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }*/
}
