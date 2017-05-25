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
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import swf.io.SwfDebugLogs;

/**
 *
 * @author andy.dh.chen
 */
public class SadmSchedulesTempTable extends Thread implements ServletContextListener {

    public boolean terminate = true;
    private static SwfDebugLogs sd = null;
    private final static SadmSchedulesTempTable sstt = new SadmSchedulesTempTable();

    public void run() {

        while (this.terminate) {
            this.sd = new SwfDebugLogs(System.getenv("CATALINA_HOME") + "/logs/SADMSchedulesTempTable.txt");
            Connection conn = null;
            Statement stmt = null;
            SimpleDateFormat sf = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
            Calendar recordDate = Calendar.getInstance();
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
                this.sd.setMessage("-----START SADMSchedulesTempTable : " + sf.format(recordDate.getTime()) + "-----\r\n");
                this.sd.setMessage("truncate table sadm.sadm_conf_schedule_temp\r\n");
                stmt.executeQuery("truncate table sadm.sadm_conf_schedule_temp");

                String sql = "SELECT scra.conf_room_id,TO_CHAR(scs.start_time, 'yyyy/mm/dd/hh24/mi') start_time,TO_CHAR(scs.end_time, 'yyyy/mm/dd/hh24/mi') end_time,"
                        + "scra.org_id,scra.conf_name,scra.attribute2 conf_room_type FROM sadm_conf_room_schedules scrs,sadm_conf_subjects scs,sadm_conf_rooms_all scra WHERE scs.conf_subject_id = scrs.conf_subject_id"
                        + " AND scrs.conf_room_id = scra.conf_room_id AND scs.Attribute3 = 'Y' AND scrs.order_status_code = 'A' AND TO_CHAR(scs.start_time, 'yyyy/mm/dd') = TO_CHAR(SYSDATE, 'yyyy/mm/dd')"
                        + " AND TO_CHAR(scs.end_time, 'yyyy/mm/dd') = TO_CHAR(SYSDATE, 'yyyy/mm/dd') UNION SELECT temp.conf_room_id,'N' start_time,"
                        + "'N' end_time,temp.org_id,temp.conf_name,temp.attribute2 conf_room_type FROM (SELECT conf_room_id,org_id,conf_name,attribute2 FROM sadm_conf_rooms_all"
                        + " WHERE 1 = 1 AND end_date IS NULL MINUS SELECT scra.conf_room_id,scra.org_id,scra.conf_name,scra.attribute2 FROM sadm_conf_room_schedules scrs,"
                        + "sadm_conf_subjects scs,sadm_conf_rooms_all scra WHERE scs.conf_subject_id = scrs.conf_subject_id AND scrs.conf_room_id = scra.conf_room_id"
                        + " AND scrs.order_status_code = 'A' AND TO_CHAR(scs.start_time, 'yyyy/mm/dd') = TO_CHAR(SYSDATE, 'yyyy/mm/dd')"
                        + " AND TO_CHAR(scs.end_time, 'yyyy/mm/dd') = TO_CHAR(SYSDATE, 'yyyy/mm/dd')) temp";

                this.sd.setMessage(sql + "\r\n");
                ResultSet rs = stmt.executeQuery(sql);

                Map room = new HashMap();
                List time = null;

                while (rs.next()) {
                    if (!room.containsKey(rs.getString(1))) {
                        room.put(rs.getString(1), new ArrayList());
                        time = (ArrayList) room.get(rs.getString(1));
                        time.add(rs.getString(4));
                        time.add("'" + rs.getString(5) + "'");
                        time.add("'" + rs.getString(6) + "'");
                    } else {
                        time = (ArrayList) room.get(rs.getString(1));
                    }
                    if (!rs.getString(2).equals("N")) {
                        //int startTime = Integer.parseInt(rs.getString(2));
                        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                        int year = Integer.parseInt(rs.getString(2).split("/")[0]);
                        int month = Integer.parseInt(rs.getString(2).split("/")[1]) - 1;
                        int day = Integer.parseInt(rs.getString(2).split("/")[2]);
                        int hour = Integer.parseInt(rs.getString(2).split("/")[3]);
                        int minute = Integer.parseInt(rs.getString(2).split("/")[4]);
                        Calendar start = Calendar.getInstance();
                        start.set(year, month, day, hour, minute, 00);

                        year = Integer.parseInt(rs.getString(3).split("/")[0]);
                        month = Integer.parseInt(rs.getString(3).split("/")[1]) - 1;
                        day = Integer.parseInt(rs.getString(3).split("/")[2]);
                        hour = Integer.parseInt(rs.getString(3).split("/")[3]);
                        minute = Integer.parseInt(rs.getString(3).split("/")[4]);

                        Calendar end = Calendar.getInstance();

                        end.set(year, month, day, hour, minute, 00);
                        boolean flag;

                        while (start.before(end)) {
                            flag = false;

                            for (int i = 0; i < time.size(); i++) {

                                if (time.get(i).equals(sdf.format(start.getTime()))) {
                                    flag = true;
                                    this.sd.setMessage("重覆時間:" + time.get(i).toString());
                                    break;
                                }
                            }
                            if (flag) {
                                this.sd.setMessage("-----有重複----\r\n");
                                this.sd.setMessage("START TIME:" + rs.getString(2) + "\r\n");
                                this.sd.setMessage("END TIME:" + rs.getString(3) + "\r\n");
                                this.sd.setMessage("會議室名稱:" + rs.getString(5) + "\r\n");
                                this.sd.setMessage("---------------\r\n");
                            } else {
                                time.add(sdf.format(start.getTime()).toString());
                            }
                            start.add(Calendar.MINUTE, 30);
                        }
                    }
                }

                //System.out.println(room.size());
                for (Object roomId : room.keySet()) {
                    sql = "insert into sadm_conf_schedule_temp(conf_room_id,org_id,conf_name,conf_room_type";
                    time = (ArrayList) room.get(roomId);
                    for (int i = 0; i < time.size(); i++) {
                        if (i == 0 || i == 1 || i == 2) {
                            continue;
                        } else {
                            sql += ",time" + Integer.parseInt(time.get(i).toString());
                        }
                    }
                    sql += ")values(" + roomId + "," + time.get(0) + "," + time.get(1) + "," + time.get(2);
                    for (int i = 0; i < time.size(); i++) {
                        if (i == 0 || i == 1 || i == 2) {
                            continue;
                        } else {
                            sql += ",1";
                        }
                    }
                    sql += ")";
                    this.sd.setMessage(sql + "\r\n");
                    stmt.executeQuery(sql);

                }

            } catch (Exception ex) {
                StackTraceElement[] ste = ex.getStackTrace();
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                    this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                }
                this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                sstt.terminate = false;
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                        stmt = null;
                    }
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                    this.sd.setMessage("-----END SADMSchedulesTempTable-----\r\n");
                    sstt.sleep(1 * 3600000);

                } catch (Exception ex) {
                    sstt.terminate = false;
                    StackTraceElement[] ste = ex.getStackTrace();
                    this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    for (int exIndex = 0; exIndex < ste.length; exIndex++) {
                        this.sd.setMessage("\t" + ste[exIndex].getClassName() + ":" + ste[exIndex].getMethodName() + " --->" + ste[exIndex].getLineNumber() + "\r\n");
                    }
                    this.sd.setMessage(this.getClass() + ex.fillInStackTrace().toString() + " Error: -------------------------------------------\r\n");
                    this.sd.setMessage("-----END SADMSchedulesTempTable-----\r\n");
                }

            }
        }
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            sstt.start();
            System.out.println("-----------------------------");
            System.out.println("SADMScHedulesTempTable開啟成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SADMScHedulesTempTable開啟失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();

        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            sstt.stop();
            System.out.println("-----------------------------");
            System.out.println("SADMScHedulesTempTable關閉成功");
            System.out.println("-----------------------------");
        } catch (Exception ex) {
            System.out.println("-----------------------------");
            System.out.println("SADMScHedulesTempTable關閉失敗");
            System.out.println("-----------------------------");
            ex.printStackTrace();
        } finally {
            sstt.stop();
        }
    }
}
