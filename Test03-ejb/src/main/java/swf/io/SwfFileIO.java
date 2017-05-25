/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import swf.sys.SwfSession;
import java.net.URLDecoder;

/**
 *
 * @author andy.dh.chen
 */
public class SwfFileIO extends SwfSession {

    private Connection conn = null;

    public void initial(HttpServletRequest request, HttpServletResponse response) {

        try {
            super.initial(request, response);
            super.checkConnection();
            this.conn = (Connection) request.getSession().getAttribute("dbcon");
            System.out.println("Connection Initial ok...");
        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    /*將表單的附件資訊存入SWF_ITEM_ATCH_ALL 並且將檔案刪除*/
    public void setActh(String path, String hdrId, String description, String name, String size, String userId) {
        File file = new File(path);
        InputStream fis = null;
        CallableStatement cStmt = null;
        PreparedStatement pstmt = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);

                pstmt = this.conn.prepareStatement("INSERT INTO swf_item_atch_all (atch_id,hdr_id,ATCH_DATA,description,atch_name,atch_size,start_time,"
                        + "CREATED_BY,LAST_UPDATED_BY,LAST_UPDATE_LOGIN,LAST_UPDATE_DATE,CREATION_DATE)"
                        + "values(swf_item_atch_all_s1.nextval," + hdrId + ",?,?,?,?,sysdate,?,?,?,sysdate,sysdate)");
                pstmt.setBinaryStream(1, fis, (int) file.length());
                pstmt.setString(2, description);
                pstmt.setString(3, URLDecoder.decode(name.split("\\\\")[name.split("\\\\").length - 1], "UTF-8"));
                pstmt.setString(4, size);
                pstmt.setInt(5, Integer.parseInt(userId));
                pstmt.setInt(6, Integer.parseInt(userId));
                pstmt.setInt(7, Integer.parseInt(userId));
                pstmt.executeUpdate();
                pstmt.clearParameters();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    pstmt.close();
                    fis.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        }
    }

    /*傳入路徑 及 atchId 將檔案建立在路徑下*/
    public void downloadTemp(String url, String atchId) {
        File path = new File(url);
        Statement st = null;
        ResultSet rs = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedOutputStream bw = null;
        String sql = "SELECT ATCH_NAME,ATCH_DATA FROM SWF_ITEM_ATCH_ALL WHERE ATCH_ID = " + atchId;

        try {
            //如果路徑不存在就建立路徑
            if (!path.exists()) {
                path.mkdir();
            }
            System.out.println(sql);
            st = this.conn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            File file = new File(url, rs.getString(1));
            //檔案不存在建立檔案
            if (!file.exists()) {
                file.createNewFile();
            }
            //取得BLOB資料
            java.sql.Blob blob = rs.getBlob(2);
            //將BLOB裡的binaryStream給 InputStream
            is = blob.getBinaryStream();
            //建立FileOutputStream
            os = new FileOutputStream(file);
            //建立BufferedOutputStream
            bw = new BufferedOutputStream(os);
            int data = is.read();
            //-1為結束 沒結束就將binary寫入檔案
            while (data != -1) {
                bw.write(data);
                data = is.read();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
                bw.close();
                os.close();
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /*傳入資料夾路徑 刪除資料夾及裡面的檔案*/
    public void deleteAllFile(String url) {
        System.out.println("-----" + url);
        File path = new File(url);

        //路徑不存在return
        try {
            if (!path.exists()) {
                System.out.println("-----路徑不存在-----");
                return;
            } //如果是檔案刪除後return
            else if (path.isFile()) {
                path.delete();
                System.out.println("-----傳入路徑為檔案 已刪除檔案-----");
                return;
            } else {
                /*找出資料夾下所有檔案一一刪除 最後將資料夾刪除*/
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
                path.delete();
                System.out.println("-----檔案刪除完成-----");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public long getFileSize(String path) {
        long size = 0;
        FileInputStream fis = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                fis = new FileInputStream(file);
                size = fis.available();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception ex) {

            }
            return size;
        }

    }

}
