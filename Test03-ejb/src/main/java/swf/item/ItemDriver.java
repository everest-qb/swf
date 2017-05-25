/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.item;

import swf.sys.SwfSession;
import java.sql.Connection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import swf.view.SwfView;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import swf.sys.UserInfo;

/**
 *
 * @author andy.dh.chen
 */
public class ItemDriver extends SwfSession {

    private Item item;

    private UserInfo userInfo;
    private Connection conn;

    public void initiail(HttpServletRequest request, HttpServletResponse response) {
        super.initial(request, response);
        super.checkConnection();
        userInfo = new UserInfo();
        userInfo.initial(request, response);
        try {
            this.conn = (Connection) request.getSession().getAttribute("dbcon");
            System.out.println("Connection Initial ok...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    //從HTML開表單 傳入 hdrId 進DB取得資料 建立ITEM
    public Item creatItem(int hdrId) {
        item = new Item();

        item.setWritter(this.creatPerson(hdrId));
        item.setAppls(this.creatAppls(hdrId));
        item.setContent(this.createItemContent(hdrId));
        item.setTxns(this.createTxns(hdrId));

        return item;

    }

    //開新表單 serviecItemId是以後如果需要在此建立表單的元件時 建立表單的所有comp
    public Item creatItem(String serviceItemId) {
        item = new Item();

        Person person = new Person();

        person.setUserId(Integer.parseInt(userInfo.getUserId()));
        person.setDeptId(Short.parseShort(userInfo.getDeptId()));
        person.setDeptName(userInfo.getDeptName());
        person.setEmpId(Integer.parseInt(userInfo.getEmpId()));
        person.setEmpName(userInfo.getEmpCname());
        person.setOrgId(Short.parseShort(userInfo.getOrgId()));
        person.setOrgName(userInfo.getOrgName());
        item.setWritter(person);

        return item;

    }

    private Person creatPerson(int hdrId) {
        Person person = new Person();
        String sql = " SELECT sea.emp_id, sea.user_id,sea.emp_cname,sea.emp_num,sea.dept_id,"
                + " sea.dept_cname,sea.organization_id,sea.organization_name,sea.user_name "
                + " FROM swf_item_hdr_all sida,swf_emp_all_v sea WHERE  1 = 1 "
                + " AND sida.prepared_by = sea.emp_id AND sida.hdr_id = " + hdrId;

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                person.setEmpId(Integer.parseInt(rs.getString("EMP_ID")));
                person.setUserId(Integer.parseInt(rs.getString("USER_ID")));
                person.setEmpName(rs.getString("EMP_CNAME"));
                person.setEmpNum(rs.getString(rs.getString("EMP_NUM")));
                person.setDeptId(Short.parseShort(rs.getString("DEPT_ID")));
                person.setDeptName(rs.getString("DEPT_CNAME"));
                person.setOrgId(Short.parseShort(rs.getString("ORGANIZATION_ID")));
                person.setOrgName(rs.getString("ORGANIZATION_NAME"));
                person.setUserName(rs.getString("USER_NAME"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return person;
    }

    private List<Person> creatAppls(int hdrId) {
        String sql = " SELECT sea.emp_id, sea.user_id,sea.emp_cname,sea.emp_num,sea.dept_id,"
                + " sea.dept_cname,sea.organization_id,sea.organization_name,sea.user_name "
                + " FROM swf_item_hdr_all sida,swf_item_appl_all siaa,swf_emp_all_v sea"
                + " WHERE 1=1 AND sida.hdr_id = siaa.hdr_id AND siaa.emp_id = sea.emp_id"
                + " AND sida.hdr_id = " + hdrId;

        List<Person> appls = new ArrayList<Person>();

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Person person = new Person();
                person.setEmpId(rs.getInt("EMP_ID"));
                person.setUserId(rs.getInt("USER_ID"));
                person.setEmpName(rs.getString("EMP_CNAME"));
                person.setEmpNum(rs.getString(rs.getString("EMP_NUM")));
                person.setDeptId(rs.getShort("DEPT_ID"));
                person.setDeptName(rs.getString("DEPT_CNAME"));
                person.setOrgId(rs.getShort("ORGANIZATION_ID"));
                person.setOrgName(rs.getString("ORGANIZATION_NAME"));
                person.setUserName(rs.getString("USER_NAME"));
                appls.add(person);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appls;

    }

    private List<ItemContent> createItemContent(int hdrId) {
        String sql = "SELECT sica.item_comp_id, sica.attribute5,item_comp_name,sila.line_id,sila.attribute1 item_comp_data,"
                + "sila.attribute2 is_line FROM swf_item_hdr_all siha,swf_item_comp_all sica,swf_item_line_all sila "
                + "WHERE  1 = 1 AND siha.service_item_id = sica.service_item_id "
                + "AND sica.item_comp_id = sila.item_comp_id AND siha.hdr_id = " + hdrId;

        List<ItemContent> itemContent = new ArrayList<ItemContent>();

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemContent item = new ItemContent();
                item.setCompId(rs.getInt("ITEM_COMP_ID"));
                item.setCompName(rs.getString("ITEM_COMP_NAME"));
                item.setData(rs.getString("ITEM_COMP_DATA"));
                if ("Y".equals(rs.getString("IS_LINE"))) {
                    item.setIsLine(true);
                } else {
                    item.setIsLine(false);
                }

                item.setLineId(rs.getLong("LINE_ID"));

                itemContent.add(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return itemContent;
    }

    private List<ItemTxn> createTxns(int hdrId) {

        String sql = "SELECT sita.station_id,sita.item_txn_id,sita.approve_type,sita.approver_id,sita.deleg_id, "
                + "sita.last_txn_id,sita.next_txn_id,sita.hdr_id,sita.process_flag,sita.approve_comment "
                + "FROM swf_item_hdr_all siha,swf_item_txn_all sita WHERE 1 = 1 "
                + "AND siha.hdr_id = sita.hdr_id AND siha.hdr_id = " + hdrId;

        List<ItemTxn> txns = new ArrayList<ItemTxn>();

        try {
            Statement stmt = this.conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ItemTxn txn = new ItemTxn();
                //txn.setStationId(rs.getShort("STATION_ID"));
                txn.setTxnId(rs.getInt("ITEM_TXN_ID"));
                txn.setServiceAppType(rs.getShort("APPROVE_TYPE"));
                txn.setApprover(this.creatApproverAndAgent(rs.getString("APPROVER_ID")));
                txn.setDelegate(this.creatApproverAndAgent(rs.getString("DELEG_ID")));
                txn.setLastTxnId(rs.getInt("LAST_TXN_ID"));
                txn.setNextTxnId(rs.getInt("NEXT_TXN_ID"));
                txn.setHdrId(rs.getInt("HDR_ID"));
                txn.setProcessFlag((char) rs.getInt("PROCESS_FLAG"));
                txn.setApproveCommend("APPROVE_COMMENT");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return txns;
    }

    private Person creatApproverAndAgent(String id) {
        Person person = new Person();
        if (id != null && !"".equals(id)) {
            String sql = "SELECT sea.emp_id,sea.user_id,sea.emp_cname,sea.emp_num,sea.dept_id, "
                    + "sea.dept_cname,sea.organization_id,sea.organization_name,sea.user_name "
                    + "from swf_emp_all_v sea and sea.emp_id =" + id;

            try {
                Statement stmt = this.conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    person.setEmpId(Integer.parseInt(rs.getString("EMP_ID")));
                    person.setUserId(Integer.parseInt(rs.getString("USER_ID")));
                    person.setEmpName(rs.getString("EMP_CNAME"));
                    person.setEmpNum(rs.getString(rs.getString("EMP_NUM")));
                    person.setDeptId(Short.parseShort(rs.getString("DEPT_ID")));
                    person.setDeptName(rs.getString("DEPT_CNAME"));
                    person.setOrgId(Short.parseShort(rs.getString("ORGANIZATION_ID")));
                    person.setOrgName(rs.getString("ORGANIZATION_NAME"));
                    person.setUserName(rs.getString("USER_NAME"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return person;
    }
   

}
