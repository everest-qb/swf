/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.item;

import java.sql.Types;
import java.sql.SQLException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Iterator;
import swf.sys.UserInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author andy.dh.chen
 */
public class ItemDao {

    private Item item;

    public void initial(HttpServletRequest request, HttpServletResponse response) {

        try {
            UserInfo userInfo = new UserInfo();
            userInfo.initial(request, response);
            this.setWritter(userInfo);

        } catch (Exception ex) {
            System.out.println(ex.getMessage() + ": Line=" + ex.getStackTrace()[0].getLineNumber());
        }
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int setWritter(UserInfo userInfo) {
        Person person = new Person();

        try {
            person.setUserId(Integer.parseInt(userInfo.getUserId()));
            person.setDeptId(Short.parseShort(userInfo.getDeptId()));
            person.setDeptName(userInfo.getDeptName());
            person.setEmpId(Integer.parseInt(userInfo.getEmpId()));
            person.setEmpName(userInfo.getEmpCname());
            person.setOrgId(Short.parseShort(userInfo.getOrgId()));
            person.setOrgName(userInfo.getOrgName());
            item.setWritter(person);
            //預設申請人為填單人
            item.setAppl(person);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

        return 0;
    }

    public int setAppl(String userId, String deptId, String deptName, String empId, String empName, String orgId, String orgName) {
        Person person = new Person();
        try {
            person.setUserId(Integer.parseInt(userId));
            person.setDeptId(Short.parseShort(deptId));
            person.setDeptName(deptName);
            person.setEmpId(Integer.parseInt(empId));
            person.setEmpName(empName);
            person.setOrgId(Short.parseShort(orgId));
            person.setOrgName(orgName);
            
            if(item.getAppls() != null){
                Iterator<Person> iterator = item.getAppls().iterator();
                
                while(iterator.hasNext()){
                    Person p = iterator.next();
                    if (p.equals(person)) {
                        return -1;
                    }
                }
            }
            item.setAppl(person);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

        return 0;

    }

    public boolean removeAppl(Person appl) {
        return item.removeAppl(appl);
    }

    public int setItemContent(int compId, String compName, int type, String data, boolean isLine) {
        ItemContent itemContent = new ItemContent();
        try {
            itemContent.setCompId(compId);
            itemContent.setCompName(compName);
            itemContent.setType(type);
            itemContent.setData(data);
            itemContent.setIsLine(isLine);

            if (item.getContent() != null) {
                Iterator<ItemContent> iterator = item.getContent().iterator();
                while (iterator.hasNext()) {
                    ItemContent content = iterator.next();
                    if (content.equals(itemContent) && !isLine) {
                        iterator.remove();
                    }
                }
            }

            item.setContent(itemContent);

        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;

        }
        return 0;
    }

    public int setItemContent(List<ItemContent> content) {

        try {
            item.setContent(content);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 0;
    }
    
    public void setTxn(List<ItemTxn> txn){
        item.setTxns(txn);
    }
    
    public void setTxn(ItemTxn txn){
        item.setTxn(txn);
    }

    public Item getItem() {
        return this.item;
    }

    public static void main(String args[]) {

        ItemDao id = new ItemDao();
        id.setItem(new Item());
        //Item item = new Item();
        //item.setContent(id.get);
        //id.item.setContent();
        id.setItemContent(0, "Name", 0, "andy", false);
        id.setItemContent(0, "Name", 0, "jay", false);
        id.setItemContent(1, "Num", 0, "andy", true);
        System.out.println(id.getItem().getContent().size());

        for (ItemContent item : id.getItem().getContent()) {
            System.out.print(item.getCompId() + "\t");
            System.out.print(item.getCompName() + "\t");
            System.out.print(item.getData()+ "\t");
            System.out.println(item.getType());
        }

    }
}
