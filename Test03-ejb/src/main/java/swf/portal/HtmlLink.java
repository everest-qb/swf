/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.portal;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import swf.view.SwfView;

/**
 *
 * @author andy.dh.chen
 */
public class HtmlLink extends SwfView{
    
    private Connection conn = null;

    
    public Connection setTestConnection(){
        String url = "jdbc:oracle:thin:@192.168.12.182:1521:PTPROD";
                String username = "apps";
                String password = "apps";
                Connection con = null;
                    try{
                        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                        con = DriverManager.getConnection(url, username, password); 
                        return con;
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
                        return con;
                    }
    }
    
    
    public StringBuilder prod(){
        String sql = "SELECT   fmev.entry_sequence, "+
                     "fmev.prompt, fmev.description, "+
                     "fffv.PARAMETERS, fffv.web_html_call "+
                     "FROM     fnd_menus_vl fmv, "+
                     "fnd_menu_entries_vl fmev, "+
                     "fnd_form_functions_vl fffv "+
                     "WHERE    1 = 1 "+
                     "AND      fmv.menu_name = fnd_profile.VALUE('SWF:PROD_SYSTEM_ENTRANCE') "+
                     "AND  fmv.menu_id = fmev.menu_id "+
                     "AND      fmev.prompt IS NOT NULL "+
                     "AND      fmev.function_id = fffv.function_id "+
                     "ORDER BY entry_sequence ";
                     
        StringBuilder sb = new StringBuilder();
        ResultSetMetaData rsmd = null;
                     try{
                         rs = stmt.executeQuery(sql);
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
                     }catch(SQLException ex){
                         ex.printStackTrace();
                     }finally{                         
                             return sb;      
                     }
    }
    
    
    public StringBuilder ptst(){
        String sql = "SELECT   fmev.entry_sequence, "+
                     "fmev.prompt, fmev.description, "+
                     "fffv.PARAMETERS, fffv.web_html_call "+
                     "FROM     fnd_menus_vl fmv, "+
                     "fnd_menu_entries_vl fmev, "+
                     "fnd_form_functions_vl fffv "+
                     "WHERE    1 = 1 "+
                     "AND      fmv.menu_name = fnd_profile.VALUE('SWF:PTST_SYSTEM_ENTRANCE') "+
                     "AND  fmv.menu_id = fmev.menu_id "+
                     "AND      fmev.prompt IS NOT NULL "+
                     "AND      fmev.function_id = fffv.function_id "+
                     "ORDER BY entry_sequence ";
                     
        StringBuilder sb = new StringBuilder();
        ResultSetMetaData rsmd = null;
                     try{
                         rs = stmt.executeQuery(sql);
                         rsmd = rs.getMetaData();
                         sb.append("<table>");
                         while(rs.next()){
                             sb.append("<tr>");
                            for(int i = 1;i<=rsmd.getColumnCount();i++){
                                 sb.append("<td width='500'>"+rs.getString(i)+"</td>");
                            }
                            
                            sb.append("</tr>");
                         }
                         sb.append("</table>");
                     }catch(SQLException ex){
                         ex.printStackTrace();
                     }finally{
                         return sb;
                     }
    }

    public String getSql() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getSql(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getSql(String type, int start, int end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResultSet getResultSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResultSet getResultSet(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ResultSet getResultSet(String type, int start, int end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
