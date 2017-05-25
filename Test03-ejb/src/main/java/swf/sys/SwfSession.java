/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.sys;

import javax.servlet.http.*;
import javax.naming.*;
//import org.apache.tomcat.jdbc.pool.*;
import java.sql.*;
import java.util.concurrent.*;
import javax.sql.DataSource;

/**
 *
 * @author User
 */
public class SwfSession extends HttpServlet implements java.io.Serializable{
    private HttpSession session ;
    private Connection sessionCon = null;
    private Boolean sessionConFlag = false;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private transient  Connection conn = null;
    
    public SwfSession()
    {
    
    }
    
    public void initial(HttpServletRequest request ,HttpServletResponse response){
        this.request = request;
        this.response = response;
        this.session = this.request.getSession();
        if(this.session.getAttribute("dbcon") != null)
        {
           this.sessionCon = (Connection)this.session.getAttribute("dbcon");
            try
            {
                this.sessionConFlag = this.sessionCon.isClosed();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        
    }
    
    
    public void checkConnection(){
        System.out.println("SESSION ID:" + this.session.getId());
        System.out.println("SESSION CONNECTION:" + session.getAttribute("dbcon"));
        System.out.println("CONNECTION CLOSE 狀態:" + sessionConFlag);
        if(session.getAttribute("dbcon") == null || sessionConFlag)
        {
                Context inictx = null;
                DataSource ds = null;
                Future<Connection> future = null;
                Object tempObj = null;
           try
            {
                inictx = new InitialContext();
                tempObj = inictx.lookup("java:comp/env/jdbc/PORTAL");
                if(tempObj != null)
                {
                    ds = (DataSource)tempObj;
                    /*future = ds.getConnectionAsync();
                    while (!future.isDone()) {
                    System.out.println("Connection is not yet available. Do some background work");
                        try {
                          Thread.sleep(100); //simulate work
                        }catch (InterruptedException x) {
                          Thread.currentThread().interrupt();
                        }
                  }*/
                }
                //conn = future.get();
                conn = ds.getConnection();
                System.out.println("JDBC POOL:" + ds.toString());
             
                session.setAttribute("dbcon",conn);
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }finally
                {
                    inictx = null;
                    ds = null;
                    conn = null;
                    future = null;
                    tempObj = null;
                }
        }
    }
}
