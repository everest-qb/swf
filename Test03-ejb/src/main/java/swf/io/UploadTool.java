package swf.io;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author andy.dh.chen
 */
import java.io.File;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import org.apache.commons.fileupload.FileUploadException;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.util.*;

public class UploadTool {

    private int buffersize = 4096;

    private int SizeMax = 1024 * 1024 * 10;// 10Mbyte最大檔案大小

    private File tempfile = null;

    private String def_upload_dir = null;

    private List items = null;

    // 用來存parameter
    private Map map = null;

    private Map uploadlist = null;

    // 處始化時把給request把所有的值取出,存入map
    //HttpServletRequest是用來存放由使用者傳過來的資料
    public UploadTool(HttpServletRequest request) throws FileUploadException, UnsupportedEncodingException {

        map = new HashMap();

        uploadlist = new HashMap();

        // 建立一個以disk-base的檔案物件
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 初始化內容
        // 傳送所用的buffer空間
        factory.setSizeThreshold(buffersize);

        // The directory in which temporary files will be located.
        factory.setRepository(tempfile);

        // 建立一個檔案上傳的物件
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 最大檔案大小
        upload.setSizeMax(SizeMax);

        // 每一個Fileitem代表一個form上傳的物件內容ex input type="text"
        items = null; // 會產生 FileUploadException

        // 把資料從request取出
        items = upload.parseRequest(request); // Parse the request
        System.out.println(items);

        Iterator iter = items.iterator();

        while (iter.hasNext()) {//  先把所有參數取得而不先write to file

            FileItem item = (FileItem) iter.next();

            // 一般文字欄位
            if (item.isFormField()) {//isFormField 用來分別是檔案欄位還是一般的表單欄位

                map.put(item.getFieldName(), item.getString("UTF-8"));

                System.out.println("上傳檔案的其它參數:" + item.getFieldName() + "="
                        + item.getString("UTF-8"));

            } else// 上傳檔案欄位
            // or it's a file upload request
             if (item.getSize() > 0) {

                    uploadlist.put(item.getFieldName(), item);

                    System.out.println("上傳檔案:" + item.getFieldName());

                }

        }

    }

    // 設定檔案上傳後存放的地方
    public void setUploadDir(String upload_dir) {

        this.def_upload_dir = upload_dir;

    }

    //取得檔案路徑
    public String getUploadDir() {
        return this.def_upload_dir;
    }

    // 取得所有欄位,包含一般欄位及上傳的欄位
    public List getAllParameter() {

        List rvalue = new ArrayList();

        rvalue.add(map);

        rvalue.add(uploadlist);

        return rvalue;

    }

    // 取得某一欄位的值,一般欄位
    public String getParameter(String FieldName) {

        if (map.containsKey(FieldName)) {
            return String.valueOf(map.get(FieldName));
        } else {
            return null;
        }

    }

    // 取得某一欄位的值,上傳欄位
    public FileItem getUploadParameter(String FieldName) {

        if (uploadlist.containsKey(FieldName)) {
            return (FileItem) uploadlist.get(FieldName);
        } else {
            return null;
        }

    }

    // 檢查上傳資料是否正確
    public String checkUpload() {

        Iterator iter = uploadlist.keySet().iterator();

        while (iter.hasNext()) {

            Object Name = iter.next();

            FileItem item = (FileItem) uploadlist.get(Name);

            String itename = item.getName();

            System.out.println("上傳的檔案為:" + itename);

            if (item.getSize() > SizeMax) {
                return "檔案太大!";
            }

        }

        return "";

    }

    // 開始上傳
    public String doUpload(FileItem item) {

        String str = "";

        String fileName;

        fileName = item.getName();//取得檔案名稱

        long sizeInBytes = item.getSize();

        // 碓認上傳資料是否有誤
        if (sizeInBytes > SizeMax) {
            return "檔案太大!";
        }

        if (sizeInBytes > 0) {

            int index = -1;

            String itename = null;
            if ((index = fileName.lastIndexOf(".")) != -1) {
                itename = fileName.substring(0,
                        index);
                System.out.println(itename);
            } else {
                itename = item.getName();
            }

            // 副檔名
            String formatName = fileName.substring(fileName.lastIndexOf("."),
                    fileName.length());

            // 會產生 Exception
            try {
                fileName = (itename + formatName);

                System.out.println("上傳檔案檔案名稱:" + fileName);

                File uploadedFile = new File(def_upload_dir);
                System.out.println(def_upload_dir);
                if (!uploadedFile.exists()) {
                    uploadedFile.mkdir();
                }
                
                uploadedFile = new File(def_upload_dir + "//" + fileName.split("\\\\")[fileName.split("\\\\").length - 1]);
                item.write(uploadedFile);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("上傳失敗!" + e.toString());

                str = "上傳失敗!";

            }

            // 會產生 Exception
        }

        return str;

    }

    // 是否存在此上傳欄位資料
    public boolean isExtUpload(String fileName) {

        return uploadlist.containsKey(fileName);

    }

    public Map getuploadlist() {
        return uploadlist;
    }

}
