/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.sys;

import javax.servlet.http.*;

/**
 *
 * @author andy.dh.chen
 */
public class UserInfo {

    private HttpSession session;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public void initial(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = this.request.getSession();

    }

    /*登入時將使用者資訊儲存在SESSION上*/
    public void login(String message) {
        String par[] = message.split(",");
        this.session.setAttribute("userId", par[0]);
        this.session.setAttribute("empCname", par[1]);
        this.session.setAttribute("empNum", par[2]);
        this.session.setAttribute("deptCode", par[3]);
        this.session.setAttribute("deptName", par[4]);
        this.session.setAttribute("empId", par[5]);
        this.session.setAttribute("deptId", par[6]);
        this.session.setAttribute("orgId", par[7]);
        this.session.setAttribute("orgName", par[8]);
        this.session.setAttribute("userName", par[9]);
        System.out.println("-----使用者資訊儲存在SESSION成功-----");
        
    }

    /*登出時將使用者資訊從SESSION上移除*/
    public void loginOut() {
        this.session.removeAttribute("userId");
        this.session.removeAttribute("empCname");
        this.session.removeAttribute("empNum");
        this.session.removeAttribute("deptCode");
        this.session.removeAttribute("deptName");
        this.session.removeAttribute("empId");
        this.session.removeAttribute("deptId");
        this.session.removeAttribute("orgId");
        this.session.removeAttribute("orgName");
        this.session.removeAttribute("userName");
        System.out.println("-----將使用者資訊從SESSION移除成功-----");
    }

    /*取得SESSION上面的使用者資訊*/
    public void userMessage() {
        System.out.println(this.session.getAttribute("userId"));
        System.out.println(this.session.getAttribute("empCname"));
        System.out.println(this.session.getAttribute("empNum"));
        System.out.println(this.session.getAttribute("deptCode"));
        System.out.println(this.session.getAttribute("deptName"));
        System.out.println(this.session.getAttribute("empId"));
        System.out.println(this.session.getAttribute("deptId"));
        System.out.println(this.session.getAttribute("orgId"));
        System.out.println(this.session.getAttribute("orgName"));
        System.out.println(this.session.getAttribute("userName"));
    }

    public String checkUser() {
        if (this.session.getAttribute("userId") != null && this.session.getAttribute("userId") != "") {
            return "Y";
        } else {
            return "N";
        }
    }

    public String getUserId() {
        return this.session.getAttribute("userId").toString();
    }

    public String getEmpCname() {
        return this.session.getAttribute("empCname").toString();
    }

    public String getEmpNum() {
        return this.session.getAttribute("empNum").toString();
    }

    public String getDeptName() {
        return this.session.getAttribute("deptName").toString();
    }

    public String getDeptCode() {
        return this.session.getAttribute("deptCode").toString();
    }

    public String getEmpId() {
        return this.session.getAttribute("empId").toString();
    }

    public String getDeptId() {
        return this.session.getAttribute("deptId").toString();
    }

    public String getOrgId() {
        return this.session.getAttribute("orgId").toString();
    }

    public String getOrgName() {
        return this.session.getAttribute("orgName").toString();
    }
    
    public String getUserName() {
        return this.session.getAttribute("userName").toString();
    }

}
