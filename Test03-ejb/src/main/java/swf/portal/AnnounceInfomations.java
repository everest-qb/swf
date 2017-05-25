/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.portal;

import swf.view.SwfView;
import java.sql.*;
import javax.servlet.http.*;
/**
 *
 * @author User
 */
public class AnnounceInfomations extends SwfView{
    private String tableName = "SADM_ANNOUNCE_PUSH_V";
    /**
     * 
     * @return 取得近七天內的公告訊息
     */
    public StringBuilder getAnnounce(){
        rs = null;
        ResultSetMetaData rsmd = null;
        StringBuilder sb = new StringBuilder();
        try{
            rsmd = rs.getMetaData();
            sb.append("<table width='100%'>");
            while(rs.next()){
                sb.append("<tr>");
                for(int i = 1;i<=rsmd.getColumnCount();i++){
                    sb.append("<td>"+rs.getString(i)+"</td>");
                }
                sb.append("</tr>");
            }
            sb.append("</table>");
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            this.closeStatement();
            this.closeConnection();
            return sb;
        }
        
    }
}
