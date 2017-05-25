/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.view;

import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 *
 * @author andy.dh.chen
 */
public class SwfSQLList {

    private String sql;

    /*SWF_Mainpage 歷史紀錄SQL*/
    public String getHistroySQL(String empId/*, String start, String end*/) {
        /*this.sql = "SELECT hdr_id,hdr_no,service_item_name,now_activity_type,applied_date"
                + " FROM ( SELECT ROWNUM num,hdr_id,hdr_no,service_item_name,slv1.meaning now_activity_type,"
                + "TO_CHAR(applied_date, 'YYYY/MM/DD') applied_date FROM swf_item_hdr_all siha,"
                + "swf_lookups_v slv1,swf_service_item_all ssia WHERE 1 = 1 AND slv1.lookup_type = 'SWF:SERVICE_ACTIVITY_TYPE'"
                + " AND siha.service_activity_code = slv1.lookup_code"
                + " AND siha.service_item_id = ssia.service_item_id AND service_activity_code != '10'"
                + " AND prepared_by = " + empId + " ) history WHERE 1=1 AND num BETWEEN " + start + " AND " + end;*/
 /*this.sql = "SELECT hdr_id,hdr_no,service_item_name,slv1.meaning now_activity_type,"
                + "TO_CHAR(applied_date, 'YYYY/MM/DD') applied_date FROM swf_item_hdr_all siha,"
                + "swf_lookups_v slv1,swf_service_item_all ssia WHERE 1 = 1 AND slv1.lookup_type = 'SWF:SERVICE_ACTIVITY_TYPE'"
                + " AND siha.service_activity_code = slv1.lookup_code"
                + " AND siha.service_item_id = ssia.service_item_id AND service_activity_code != '10'"
                + " AND prepared_by = " + empId;*/

 /*this.sql = "SELECT todolist.item_txn_id,todolist.hdr_id,todolist.hdr_no,todolist.service_item_name,todolist.applied_date,"
                + "todolist.request_level_code,todolist.process_status_code,CASE WHEN todolist.next_txn_id IS NULL AND todolist.last_txn_id = -1 "
                + "THEN 'SAVE' ELSE 'APPLY' END editmode,todolist.approve_type,todolist.emp_cname,todolist.now_activity_type,todolist.request_level,"
                + "todolist.request_enable_date,todolist.request_expire_type,todolist.service_item_id,todolist.dept_short_cname,todolist.item_summary"
                + " FROM (SELECT item_txn_id,process_status_code,approver_id,hdr_id,hdr_no,service_item_name,TO_CHAR(applied_date, 'YYYY-MM-DD') applied_date,"
                + "request_level_code,next_txn_id,last_txn_id,approve_type,emp_cname,now_activity_type,request_level,request_enable_date,request_expire_type,"
                + "service_item_id,dept_short_cname,item_summary FROM swf_item_list_v WHERE 1 = 1 AND  prepared_by = " + empId + " AND ( process_flag = 1"
                + " OR service_activity_code = '80' AND service_activity_type = '10' OR approve_type = '60') AND service_activity_code != '10') todolist";*/
 /*this.sql = "SELECT todolist.item_txn_id,todolist.hdr_id,todolist.hdr_no,todolist.service_item_name,todolist.applied_date,todolist.request_level_code,"
                + "todolist.process_status_code,CASE WHEN todolist.next_txn_id IS NULL AND todolist.last_txn_id = -1 THEN 'SAVE' ELSE 'APPLY' END editmode,"
                + "todolist.approve_type,todolist.emp_cname,todolist.now_activity_type,todolist.request_level,todolist.request_enable_date,todolist.request_expire_type,"
                + "todolist.service_item_id,todolist.dept_short_cname,todolist.item_summary FROM (SELECT DISTINCT hdr_no,'null' item_txn_id,process_status_code,"
                + "approver_id,hdr_id,service_item_name,TO_CHAR(applied_date, 'YYYY-MM-DD') applied_date,request_level_code,'null' next_txn_id,'null' last_txn_id,"
                + "approve_type,emp_cname,now_activity_type,request_level,request_enable_date,request_expire_type,service_item_id,dept_short_cname,item_summary"
                + " FROM swf_item_list_v WHERE 1 = 1 AND approver_id = " + empId + " AND process_flag = 0 AND approved_date IS NOT NULL) todolist";*/
        this.sql = "SELECT DISTINCT todolist.item_txn_id,\n"
                + "                todolist.hdr_id,\n"
                + "                todolist.hdr_no,\n"
                + "                todolist.service_item_name,\n"
                + "                todolist.applied_date,\n"
                + "                todolist.request_level_code,\n"
                + "                todolist.process_status_code,\n"
                + "                CASE\n"
                + "                   WHEN todolist.next_txn_id IS NULL\n"
                + "                AND    todolist.last_txn_id = -1 THEN 'SAVE'\n"
                + "                   ELSE 'APPLY'\n"
                + "                END editmode,\n"
                + "                'null' approve_type,\n"
                + "                todolist.emp_cname,\n"
                + "                todolist.now_activity_type,\n"
                + "                todolist.request_level,\n"
                + "                todolist.request_enable_date,\n"
                + "                todolist.request_expire_type,\n"
                + "                todolist.service_item_id,\n"
                + "                todolist.dept_short_cname,\n"
                + "                todolist.item_summary,\n"
                + "                todolist.write_emp_cname,\n"
                + "                todolist.write_dept_short_cname\n"
                + "FROM            (SELECT DISTINCT silv.hdr_no,\n"
                + "                                 'null' item_txn_id,\n"
                + "                                 silv.process_status_code,\n"
                + "                                 silv.approver_id,\n"
                + "                                 silv.hdr_id,\n"
                + "                                 silv.service_item_name,\n"
                + "                                 TO_CHAR(silv.applied_date, 'YYYY-MM-DD') applied_date,\n"
                + "                                 silv.request_level_code,\n"
                + "                                 'null' next_txn_id,\n"
                + "                                 'null' last_txn_id,\n"
                + "                                 silv.approve_type,\n"
                + "                                 silv.emp_cname,\n"
                + "                                 silv.now_activity_type,\n"
                + "                                 silv.request_level,\n"
                + "                                 silv.request_enable_date,\n"
                + "                                 silv.request_expire_type,\n"
                + "                                 service_item_id,\n"
                + "                                 silv.dept_short_cname,\n"
                + "                                 silv.item_summary,\n"
                + "                                 write_emp_cname,\n"
                + "                                 write_dept_short_cname\n"
                + "                 FROM            swf_item_list_v silv\n"
                + "                 WHERE           1 = 1\n"
                + "                 AND             attribute1 = TO_CHAR(" + empId + ")\n"
                + "                 AND             process_flag = 0\n"
                + "                 AND             approved_date IS NOT NULL) todolist\n"
                + "GROUP BY        todolist.item_txn_id,\n"
                + "                todolist.hdr_id,\n"
                + "                todolist.hdr_no,\n"
                + "                todolist.service_item_name,\n"
                + "                todolist.applied_date,\n"
                + "                todolist.request_level_code,\n"
                + "                todolist.process_status_code,\n"
                + "                todolist.emp_cname,\n"
                + "                todolist.now_activity_type,\n"
                + "                todolist.request_level,\n"
                + "                todolist.request_enable_date,\n"
                + "                todolist.request_expire_type,\n"
                + "                todolist.service_item_id,\n"
                + "                todolist.dept_short_cname,\n"
                + "                todolist.item_summary,\n"
                + "                todolist.write_emp_cname,\n"
                + "                todolist.write_dept_short_cname,\n"
                + "                todolist.approve_type";

        return this.sql;
    }

    /*SWF_ITIL_1~8 取得CHIOCE SQL*/
    public String getCreateChoiceSQL(String lookupType) {
        this.sql = "select lookup_code,meaning from swf_lookups_v where 1=1 and lookup_type = '" + lookupType
                + "' and ENABLED_FLAG = 'Y' order by lookup_code";
        return this.sql;
    }

    /*SWF_ITIL_1~8 取得表單內容 SQL*/
    public String getLineContextSQL(String hdrId) {
        this.sql = "SELECT sila.attribute1,sica.attribute5,sica.attribute8,sila.line_id FROM swf_item_line_all sila,"
                + "swf_item_comp_all sica WHERE 1 = 1 AND sila.item_comp_id = sica.item_comp_id AND sila.hdr_id = " + hdrId
                + " AND sila.attribute2 is null order by sica.item_comp_id";

        return this.sql;
    }

    /*SWF_ITIL_1~8 取得SWF_ITME_ATCH_ALL SQL*/
    public String getAtchSQL(String hdrId) {
        this.sql = "SELECT description,atch_name,atch_id from swf_item_atch_all where end_time is null and hdr_id = " + hdrId;
        return this.sql;
    }

    /*SWF_ITIL_1~8 取得當前階段 SQL*/
    public String getServiceActivitySQL(String value) {
        this.sql = "SELECT lookup_code,meaning FROM swf_lookups_v WHERE 1 = 1"
                + "AND lookup_type = 'SWF:SERVICE_ACTIVITY_TYPE' AND lookup_code = " + value
                + "ORDER BY lookup_code";

        return this.sql;
    }

    /*取得個人當前 特急件 速件 平常件 逾期件 將屆件 正常件筆數 SQL*/
    public String getTopCountSQL(String empId) {
        this.sql = "swf_list_count_pkg.run(" + empId + ")";
        return this.sql;
    }

    /*取得個人當前 特急件 速件 平常件 逾期件 將屆件 正常件 表單名稱 單號 申請日期 處理狀態 送件等級 SQL*/
    public String getTopContentSQL(String empId) {
        this.sql = "select service_item_name,hdr_no,to_char(applied_date,'YYYY/MM/DD')"
                + ",process_status_code,request_level_code from swf_item_list_v where 1=1 "
                + " AND  process_flag = 1 AND  (approver_id = " + empId + " OR deleg_id = " + empId + ") order by hdr_no";

        return this.sql;
    }

    /*PortalMainPageL 取得 本月份日期註記 SQL*/
    public String getCanlendarlistSQL() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY/MM");
        Calendar c = Calendar.getInstance();
        this.sql = "SELECT TO_CHAR (calendar_date, 'mm/dd') calendar_date,"
                + "all_day_event, SUBSTR (date_name, 1, 3) date_name"
                + " FROM sadm_portal_calendar_v"
                + " WHERE organization_code = 'SHQ' AND TO_CHAR (calendar_date, 'YYYY/MM') IN ('" + sdf.format(c.getTime()) + "') AND all_day_event IS NOT NULL";
        return this.sql;
    }

    /*PortalMainPageL 取得 本日會議行程 SQL*/
    public String getConferenceSchedulesSQL(String type, String orgId) {
        this.sql = "SELECT conf_name,time800,time830,time900,time930,time1000,time1030,"
                + "time1100,time1130,time1200,time1230,time1300,time1330,time1400,"
                + "time1430,time1500,time1530,time1600,time1630,time1700,time1730,"
                + "time1800,time1830,time1900,time1930,time2000 FROM SADM_CONF_SCHEDULE_TEMP WHERE 1=1 AND ORG_ID = " + orgId
                + " AND CONF_ROOM_TYPE = '" + type + "'";
        return this.sql;
    }

    /*PortalMainPageL 取得 跑馬燈資訊 SQL*/
    public String getMarqueeSQL() {
        this.sql = "SELECT html FROM sadm_conf_marquee2_v";
        return this.sql;
    }

    /*SWF_Mainpage 取得TODOLIST 相關資訊 SQL*/
 /* public String getTODOListSQL(String type, String start, String end, String empId) {
        this.sql = "SELECT todolist.item_txn_id,todolist.hdr_id,todolist.hdr_no,todolist.service_item_name,todolist.applied_date,todolist.request_level_code, "
                + "todolist.PROCESS_STATUS_CODE,CASE WHEN todolist.next_txn_id IS NULL AND todolist.last_txn_id = -1 THEN 'SAVE' ELSE 'APPLY' END editmode,todolist.approve_type "
                + ",todolist.emp_cname,todolist.service_activity_type_name,todolist.request_level,todolist.request_enable_date,todolist.request_expire_type,todolist.service_item_id"
                + ",todolist.dept_short_cname FROM (SELECT ROWNUM num,PROCESS_STATUS_CODE, approver_id,item_txn_id,hdr_id,hdr_no,service_item_name,TO_CHAR(applied_date, 'YYYY-MM-DD') applied_date"
                + ",request_level_code,next_txn_id,last_txn_id,approve_type,emp_cname,service_activity_type_name,request_level,request_enable_date,"
                + "request_expire_type,service_item_id,dept_short_cname FROM swf_item_list_v  WHERE  1 = 1 AND  process_flag = 1 AND  approver_id = " + empId;
        if (type.equals("E") || type.equals("R") || type.equals("U")) {
            this.sql += " AND request_level_code = '" + type + "' ";
        } else if (type.equals("NORMAL") || type.equals("OVERDUE") || type.equals("APPROACH")) {
            this.sql += " AND PROCESS_STATUS_CODE = '" + type + "' ";
        }

        this.sql += " ) todolist WHERE  1 = 1 AND todolist.num BETWEEN " + start + " AND " + end;
        return this.sql;
    }*/

 /*SWF_Mainpage 取得TODOLIST 相關資訊 SQL*/
    public String getTODOListSQL(String type, String empId) {
        this.sql = "SELECT todolist.item_txn_id,todolist.hdr_id,todolist.hdr_no,todolist.service_item_name,todolist.applied_date,"
                + " todolist.request_level_code,todolist.process_status_code,CASE WHEN todolist.next_txn_id IS NULL AND todolist.last_txn_id = -1 THEN 'SAVE'"
                + " ELSE 'APPLY' END editmode,todolist.approve_type,todolist.emp_cname,todolist.now_activity_type,todolist.request_level,"
                + " todolist.request_enable_date,todolist.request_expire_type,todolist.service_item_id,todolist.dept_short_cname,todolist.item_summary,"
                + " todolist.write_emp_cname,todolist.write_dept_short_cname,deleg_name,item_group,todolist.request_date,division_name FROM (SELECT process_status_code,"
                + " approver_id,item_txn_id,hdr_id,hdr_no,service_item_name,TO_CHAR(applied_date, 'YYYY-MM-DD') applied_date,request_level_code,"
                + " next_txn_id,last_txn_id,approve_type,emp_cname,now_activity_type,request_level,request_enable_date,request_expire_type,"
                + " service_item_id,dept_short_cname,item_summary,write_emp_cname,write_dept_short_cname,nvl(deleg_name,'null')deleg_name,item_group,"
                + " TO_CHAR(TO_DATE(request_enable_date, 'yyyy-mm-dd hh24mi'), 'yyyy-mm-dd') request_date, (SELECT dept_short_cname"
                + " FROM swf_dept_all WHERE 1 = 1 AND dept_id = TO_NUMBER(swf_dml_pkg.find_division_id(silv.dept_id))) division_name"
                + " FROM swf_item_list_v silv WHERE  1 = 1 AND process_flag = 1 AND (approver_id = " + empId + " OR DELEG_ID = " + empId + ")";
        if (type.equals("E") || type.equals("R") || type.equals("U")) {
            this.sql += " AND request_level_code = '" + type + "' ";
        } else if (type.equals("NORMAL") || type.equals("OVERDUE") || type.equals("APPROACH")) {
            this.sql += " AND PROCESS_STATUS_CODE = '" + type + "' ";
        }

        this.sql += " ) todolist ";
        return this.sql;
    }

    /*SWF_ITIL_6~8 取得公司組織別 SQL*/
    public String getCreateOrgChoiceSQL() {
        this.sql = "SELECT organization_id,organization_code FROM swf_organizations_v";
        return this.sql;
    }

    /*SWF_ITIL_1~8 取得表單單號 SQL*/
    public String getHdrNOSQL() {
        this.sql = "Select SWF_ITEM_NO_S1.nextval from dual";
        return this.sql;
    }

    /*SWF_ITIL_1~8 取得表單表頭內容*/
    public String getHdrContextSQL(String hdrId) {
        this.sql = "SELECT hdr_no,request_level,TO_CHAR(request_enable_date, 'YYYY-MM-DD') request_enable_date,"
                + "TO_CHAR(request_expire_date,'YYYY-MM-DD') request_expire_date,to_char(APPLIED_DATE,'YYYY/MM/DD hh24:mi') APPLIED_DATE,"
                + "request_expire_type,service_activity_code,sea.emp_cname,sea.emp_num,sea.dept_cname"
                + " FROM swf_item_hdr_all siha,swf_emps_all_v sea WHERE  1 = 1 AND siha.prepared_by = sea.emp_id AND hdr_id = " + hdrId;
        return this.sql;
    }

    /*取得表單的SWF_ITEM_COMP_ALL*/
    public String getSearchCompSQL(String serviceId) {
        this.sql = "SELECT   item_comp_id,attribute5,attribute8,attribute6 FROM swf_item_comp_all WHERE attribute10 <> 'Y'"
                + " AND ATTRIBUTE7 = 'Y' AND SERVICE_ITEM_ID = " + serviceId + " ORDER BY service_item_id,"
                + "TO_NUMBER(attribute3),TO_NUMBER(attribute1),TO_NUMBER(attribute2),item_comp_id";
        return this.sql;
    }

    /*從SWF_ITEM_LINE_ALL當中取得表單TABLE內容*/
    public String getTableContentSQL(String hdrId, String tableId) {
        this.sql = "SELECT   attribute2 || '-' || attribute3 tr_id,attribute1 || '***' || line_id table_value"
                + " FROM  swf_item_line_all WHERE 1 = 1 AND hdr_id = " + hdrId
                + " AND  attribute2 ='" + tableId + "' ORDER BY attribute2, attribute3,attribute4";
        return this.sql;
    }

    public String getApprovedToolSQL(String hdrId, String empId) {
        this.sql = "SELECT TO_CHAR(applied_date, 'yyyy/mm/dd') applied_date,service_item_name,hdr_no,emp_cname,service_activity_type_name,"
                + "request_level,request_enable_date,request_expire_type,item_txn_id FROM swf_item_list_v WHERE 1 = 1 AND process_flag = 1 "
                + "AND hdr_id = " + hdrId + "AND approver_id = " + empId;
        return this.sql;
    }

    public String getApprovedContentSQL(String serviceId, String hdrId) {
        this.sql = "SELECT ccc.attribute3,ccc.attribute4,ccc.attribute5,ccc.attribute6,ccc.attribute9,ccc.attribute8 object_type,ccc.objcet_value"
                + " FROM (SELECT bbb.*,NVL(sila.attribute3, '1') table_row,NVL(sila.attribute4, '0') table_column,NVL(sila.attribute1, bbb.attribute9)objcet_value FROM (SELECT aaa.item_comp_id,"
                + "aaa.attribute1,aaa.attribute2,aaa.attribute3,aaa.attribute4,aaa.attribute5,nvl(aaa.attribute6,'null') attribute6,aaa.attribute8,aaa.attribute9 FROM (SELECT *"
                + " FROM swf_item_comp_all WHERE 1 = 1 AND service_item_id = " + serviceId + " AND attribute7 = 'Y') aaa WHERE 1 = 1 START WITH aaa.attribute4 IS NULL"
                + " CONNECT BY PRIOR aaa.attribute3 = aaa.attribute4) bbb,(SELECT * FROM swf_item_line_all WHERE 1 = 1 AND hdr_id = " + hdrId + ") sila"
                + " WHERE 1 = 1 AND bbb.item_comp_id = sila.item_comp_id(+) ORDER BY bbb.attribute1,bbb.attribute2) ccc where 1=1 and ccc.attribute4 is not null"
                + " ORDER BY  ccc.attribute1,ccc.table_row,ccc.table_column,ccc.attribute2";
        return this.sql;
    }

    /*取得登入資訊*/
    public String getLoginSQL(String userId) {
        this.sql = "SELECT user_id || ',' || emp_cname || ',' || emp_num || ',' || dept_code || ',' || dept_cname || ',' || emp_id || ',' "
                + "|| dept_id || ',' || organization_id || ',' || organization_name || ',' || user_name MESSAGE "
                + "FROM   swf_emps_all_v WHERE  1 = 1 AND user_id = " + userId;
        return this.sql;
    }

    public String getTxnSQL(String hdrId) {
        this.sql = "SELECT * FROM (SELECT CASE WHEN sita.deleg_id = TO_NUMBER(sita.attribute1) THEN '(代) ' || sea1.emp_cname"
                + " WHEN sita.deleg_id IS NOT NULL AND sita.attribute1 IS NULL THEN sea.emp_cname || '   ' || sea3.emp_cname || '(代理)' WHEN sita.attribute1 IS NOT NULL THEN sea1.emp_cname"
                + " ELSE sea.emp_cname END emp_cname,sl1.meaning service_activity_type,NVL(TO_CHAR(sita.approved_date, 'yyyy/mm/dd'), 'null') approved_date,"
                + " NVL(TO_CHAR(sita.approved_date, 'hh24:mi'), 'null') approved_time,CASE WHEN approved_date IS NULL THEN '未簽核'"
                + " ELSE NVL(sita.approve_comment, '無簽核意見') END approve_comment,CASE WHEN sita.approved_date IS NULL THEN NVL(sda.dept_short_cname, 'null')"
                + " WHEN sita.deleg_id = TO_NUMBER(sita.attribute1) THEN NVL(sda1.dept_short_cname, 'null') WHEN sita.approver_id = TO_NUMBER(sita.attribute1) THEN NVL(sda.dept_short_cname, 'null')"
                + " ELSE NVL(sda.dept_short_cname, 'null') END dept_short_cname,sl2.meaning approve_type,sita.item_txn_id,last_txn_id,"
                + " next_txn_id,nvl(sita.attribute4,'N') add_flag,(SELECT NVL(attribute4, 'N') FROM swf_item_txn_all WHERE  1 = 1"
                + " AND item_txn_id = sita.next_txn_id)next_flag,nvl(sita.attribute2,'N') attribute2 FROM   swf_item_txn_all sita,swf_lookups sl1,swf_station_all ssa,"
                + " swf_emps_all sea,swf_emps_all sea1,swf_dept_all sda,swf_dept_all sda1,swf_lookups sl2,swf_emps_all sea3 WHERE 1 = 1"
                + " AND sita.station_id = ssa.station_id AND sl1.lookup_type = 'SWF:SERVICE_ACTIVITY_TYPE' AND sl2.lookup_type = 'SWF:SERVICE_APPROVE_TYPE'"
                + " AND DECODE(sea.org_id,175, sea.org_dept_id || '10',82, sea.org_dept_id || '20',217, sea.org_dept_id || '30',"
                + " sea.org_dept_id) = TO_CHAR(sda.dept_id(+)) AND ssa.service_activity_type = sl1.lookup_code AND sita.approver_id = sea.emp_id"
                + " AND sl2.lookup_code = sita.approve_type AND TO_NUMBER(sita.attribute1) = sea1.emp_id(+) and sita.deleg_id = sea3.emp_id(+)"
                + " AND DECODE(sea1.org_id,175, sea1.org_dept_id || '10',82, sea1.org_dept_id || '20',217, sea1.org_dept_id || '30',"
                + " sea1.org_dept_id) = TO_CHAR(sda1.dept_id(+)) AND hdr_id = " + hdrId + ") START WITH last_txn_id = -1 CONNECT BY PRIOR next_txn_id = item_txn_id";
        return this.sql;
    }

    public String getSpecialInterface(String empId) {
        this.sql = "SELECT todolist.item_txn_id,\n"
                + "       todolist.hdr_id,\n"
                + "       todolist.hdr_no,\n"
                + "       todolist.service_item_name,\n"
                + "       todolist.applied_date,\n"
                + "       todolist.request_level_code,\n"
                + "       todolist.process_status_code,\n"
                + "       CASE\n"
                + "          WHEN todolist.next_txn_id IS NULL\n"
                + "       AND    todolist.last_txn_id = -1 THEN 'SAVE'\n"
                + "          ELSE 'APPLY'\n"
                + "       END editmode,\n"
                + "       todolist.approve_type,\n"
                + "       todolist.emp_cname,\n"
                + "       todolist.now_activity_type,\n"
                + "       todolist.request_level,\n"
                + "       todolist.request_enable_date,\n"
                + "       todolist.request_expire_type,\n"
                + "       todolist.service_item_id,\n"
                + "       todolist.dept_short_cname,\n"
                + "       todolist.item_summary,\n"
                + "       todolist.write_emp_cname,\n"
                + "       todolist.write_dept_short_cname,\n"
                + "       deleg_name,\n"
                + "       item_group,\n"
                + "       todolist.request_date,\n"
                + "       division_name\n"
                + "FROM   (SELECT process_status_code,\n"
                + "               approver_id,\n"
                + "               item_txn_id,\n"
                + "               hdr_id,\n"
                + "               hdr_no,\n"
                + "               service_item_name,\n"
                + "               TO_CHAR(applied_date, 'YYYY-MM-DD') applied_date,\n"
                + "               request_level_code,\n"
                + "               next_txn_id,\n"
                + "               last_txn_id,\n"
                + "               approve_type,\n"
                + "               emp_cname,\n"
                + "               now_activity_type,\n"
                + "               request_level,\n"
                + "               request_enable_date,\n"
                + "               request_expire_type,\n"
                + "               service_item_id,\n"
                + "               dept_short_cname,\n"
                + "               item_summary,\n"
                + "               write_emp_cname,\n"
                + "               write_dept_short_cname,\n"
                + "               NVL(deleg_name, 'null') deleg_name,\n"
                + "               item_group,\n"
                + "               TO_CHAR(TO_DATE(request_enable_date, 'yyyy-mm-dd hh24mi'), 'yyyy-mm-dd') request_date,\n"
                + "               (SELECT dept_short_cname\n"
                + "                FROM   swf_dept_all\n"
                + "                WHERE  1 = 1\n"
                + "                AND    dept_id = TO_NUMBER(swf_dml_pkg.find_division_id(silv.dept_id))) division_name\n"
                + "        FROM   swf_item_list_v silv\n"
                + "        WHERE  1 = 1\n"
                + "        AND    approved_date IS NULL\n"
                + "        AND    service_activity_type_name = '核決'\n"
                + "        AND    now_activity_type NOT LIKE '%退回'\n"
                + "        AND    (   approver_id = " + empId + "\n"
                + "                OR deleg_id = " + empId + ")) todolist";

        return this.sql;
    }

    public String updateSwfItemTxnAllSQL(String hdrId, String itemTxnId) {
        this.sql = "SELECT     item_txn_id\n"
                + "FROM       swf_item_txn_all\n"
                + "START WITH last_txn_id = (SELECT item_txn_id\n"
                + "                          FROM   swf_item_txn_all\n"
                + "                          WHERE  1 = 1\n"
                + "                          AND    last_txn_id = -1\n"
                + "                          AND    hdr_id = " + hdrId + ")\n"
                + "CONNECT BY PRIOR next_txn_id = item_txn_id\n"
                + "AND        item_txn_id != " + itemTxnId;

        return this.sql;
    }
}
