/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.item;
import java.io.Serializable;
/**
 *
 * @author andy.dh.chen
 */
public class Person implements Serializable{
    private int empId;
    private int userId;
    private String userName;
    
    private String empName;
    private String empNum;
    
    private short deptId;
    private String deptName;
    
    private short orgId;
    private String orgName;
    
   
    

    public Person() {
    }

    public int getEmpId() {
        return empId;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpNum() {
        return empNum;
    }

    public short getDeptId() {
        return deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public short getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpNum(String empNum) {
        this.empNum = empNum;
    }

    public void setDeptId(short deptId) {
        this.deptId = deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setOrgId(short orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.empId != other.empId) {
            return false;
        }
        if (this.userId != other.userId) {
            return false;
        }
        return true;
    }

}
