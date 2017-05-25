/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.io;

import java.io.*;

/**
 *
 * @author andy.dh.chen
 */
public class SwfDebugLogs {

    private File file;

    public SwfDebugLogs(String path) {
        this.file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void setMessage(String msg) {
        if (this.file.exists()) {
            FileOutputStream fos = null;
            OutputStreamWriter osw = null;
            BufferedWriter bw = null;
            try {
                //this.fw = new FileWriter(this.file, true);
                fos = new FileOutputStream(this.file, true);
                osw = new OutputStreamWriter(fos, "UTF-8");
                bw = new BufferedWriter(osw);
                bw.write(msg);
                bw.newLine();
                bw.flush();
            } catch (IOException ex) {
                osw = null;
                fos = null;
                bw = null;
            } finally {
                try {
                    bw.close();
                    osw.close();
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }
            }
        }
    }

}
