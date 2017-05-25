/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * 此 Class 主要提供 Select 之後，將訊息回傳至HTML端 將ResultSet 轉換成 HTML 可閱讀的格式 若有其他需要請在下方自行增加
 * Methods
 *
 * @author SWF Team
 */
public class SwfUI {

    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private HashMap<String, Object> map;

    /**
     * 此Class中的第一步驟，將 Select 後的 ResultSet 傳入
     *
     * @param rs Select 後的 ResultSet
     */
    public void init(ResultSet rs) {
        this.rs = rs;
    }

    /**
     * 此Class中的最後步驟，移除掉所有使用的物件參照
     */
    public void destory() {
        this.rs = null;
        this.rsmd = null;
        this.map = null;
    }

    /**
     * 取得 ResultSet 的所有欄位標籤
     *
     * @return ArrayList 的方式回傳，並不帶有任何 HTML 標籤
     */
    public ArrayList<String> getHeader() {
        ArrayList<String> list = new ArrayList<String>();

        try {
            rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) //Table Column 由 1 開始計算
            {
                if (!rsmd.getColumnName(i).equalsIgnoreCase("ROWNUM")) {
                    list.add(rsmd.getColumnName(i));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
        return list;
    }

    /**
     * 取得 ResultSet 的所有值，並不包含欄位名稱 Map<K, V>
     * K = Key = Row Index V = Value = Row Data Value 目前使用 ArrayList 實作
     *
     * @return Map 的方式回傳，並不帶有任何 HTML 標籤
     */
    public HashMap<String, Object> getData() {
        map = new HashMap<String, Object>();
        ArrayList<String> list;
        try {
            int rowIndex = 1;
            while (rs.next()) {
                list = new ArrayList<String>();
                for (int i = 1; i <= this.getColumnCount(); i++) //Table Column 由 1 開始計算
                {
                    if (!rs.getMetaData().getColumnName(i).equalsIgnoreCase("ROWNUM")) {
                        list.add(rs.getString(i));
                    }
                }
                map.put(String.valueOf(rowIndex), list);
                rowIndex += 1;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        } finally {
            list = null;
        }

        return map;
    }

    /**
     * 回傳欄位數量
     *
     * @return 欄位數量
     */
    public int getColumnCount() {
        int i;
        try {
            i = this.rs.getMetaData().getColumnCount();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
            i = -1;
        }
        return i;
    }

    /**
     * 以ResultSet重組成 HTML 且帶有 Table 標籤的格式
     *
     * @param hasHeader Y/建立標頭 ; N/不建立標頭
     * @return 帶有HTML標籤的
     */
    public StringBuilder getHTML(boolean hasHeader) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = this.getHeader();
        this.map = this.getData();
        sb.append("<table>");

        if (hasHeader) {
            sb.append("<tr>");
            for (int i = 0; i < list.size(); i++) {
                sb.append("<th>").append(list.get(i)).append("</th>");
            }
            sb.append("</tr>");
        }

        for (String key : map.keySet()) {
            sb.append("<tr>");

            list = (ArrayList<String>) map.get(key);
            for (int i = 0; i < list.size(); i++) {
                sb.append("<td>").append(list.get(i)).append("</td>");
            }

            sb.append("</tr>");
        }

        sb.append("</table>");
        System.out.println(sb.toString());
        return sb;
    }

    /**
     * 以ResultSet重組成 HTML 且帶有 Table 標籤的格式 但此方法不包含建立 <table> 與 </table> 標籤
     *
     * @param hasHeader Y/建立標頭 ; N/不建立標頭
     * @return 帶有HTML標籤的
     */
    public StringBuilder getNonTableTagHTML(boolean hasHeader) {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> list = this.getHeader();
        this.map = this.getData();

        if (hasHeader) {
            sb.append("<tr>");
            for (int i = 0; i < list.size(); i++) {
                sb.append("<th>").append(list.get(i)).append("</th>");
            }
            sb.append("</tr>");
            System.out.print(sb);
        }

        for (String key : map.keySet()) {
            sb.append("<tr>");

            list = (ArrayList<String>) map.get(key);
            for (int i = 0; i < list.size(); i++) {
                sb.append("<td>").append(list.get(i)).append("</td>");
            }

            sb.append("</tr>");
        }

        System.out.println(sb);
        return sb;
    }

    /*PortalMainPageL 會議行程*/
    public StringBuilder conferenceSchedule(String id) {
        StringBuilder sb = new StringBuilder();
        int i;
        try {
            /*this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            System.out.println(sql);
            this.rs = this.stmt.executeQuery(sql);*/
            ResultSetMetaData rsmd = rs.getMetaData();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Calendar start = Calendar.getInstance();
            start.set(Calendar.HOUR_OF_DAY, 8);
            start.set(Calendar.MINUTE, 00);
            List record = new ArrayList();
            /*rs 為需建立的會議室集合*/
            while (rs.next()) {
                /*rs 為第一筆的時候執行*/
                if (rs.isFirst()) {
                    /*建立第一行 建立空th 跟時間區段 並且建立ArrayList去紀錄每個tr的html*/
                    for (i = 0; i < rsmd.getColumnCount(); i++) {
                        if (i == 0) {
                            record.add("<tr><th width='40px'></th>");
                        } else {
                            record.add("<tr><td>" + sdf.format(start.getTime()) + "</td>");
                            start.add(Calendar.MINUTE, 30);
                        }
                    }
                }

                /*建立每間會議室的預約情況*/
                for (i = 0; i < rsmd.getColumnCount(); i++) {
                    /*判斷rs是否為最後一筆*/
                    if (!rs.isLast()) {
                        /*i等於0 建立的是會議室名稱*/
                        if (i == 0) {
                            record.set(i, record.get(i) + "<th width='50px' style='font-size:smaller;'><center>" + rs.getString(i + 1) + "</center></th>");
                            /*建立會議室預約時間段*/
                        } else if (i < rsmd.getColumnCount()) {
                            /*等於1 代表該時段有會議*/
                            if (rs.getString(i + 1).equals("1")) {
                                record.set(i, record.get(i) + "<td class='conferenceTableTd'><center><img src='../resource/dist/img/red.png' class='business-meeting' title='meeting'/></td></center></td>");
                            } else {
                                record.set(i, record.get(i) + "<td class='conferenceTableTd'><center><img src='../resource/dist/img/green.png' class='business-meeting' title='No meeting'/></td></center></td>");
                            }
                        }
                    } else if (rs.isLast()) {
                        /*i等於0 建立的是會議室名稱 因為rs是最後一筆 加入tr結束標簽 並在最前面加入div 跟 table的開始標簽*/
                        if (i == 0) {
                            record.set(i, record.get(i) + "<th width='50px' style='font-size:smaller;'><center>" + rs.getString(i + 1) + "</center></th></tr>");
                            sb.append(id + "&<div class='post'><table style='border-collapse: collapse;'>" + record.get(i));
                            /*建立會議室預約時間段 因為rs為最後一筆 在最後加入tr結束標簽*/
                        } else if (i < rsmd.getColumnCount()) {
                            /*等於1 代表該時段有會議*/
                            if (rs.getString(i + 1).equals("1")) {
                                record.set(i, record.get(i) + "<td class='conferenceTableTd'><center><img src='../resource/dist/img/red.png' class='business-meeting' title='meeting'/></td></center></td></tr>");
                            } else {
                                record.set(i, record.get(i) + "<td class='conferenceTableTd'><center><img src='../resource/dist/img/green.png' class='business-meeting' title='No meeting'/></td></center></td></tr>");
                            }
                            sb.append(record.get(i));
                        }
                        

                    }
                }

            }
            sb.append("</table></div>");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sb;
    }

}
