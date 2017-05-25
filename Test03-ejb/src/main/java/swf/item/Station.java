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
public class Station implements Serializable{

    private int stationId;
    private char serviceActivityType;
    private short seq;
    private char topLevel;
    private short jobId;
    private int empId;
    private short deptId;
    private boolean autoFlag;

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setServiceActivityType(char serviceActivityType) {
        this.serviceActivityType = serviceActivityType;
    }

    public void setSeq(short seq) {
        this.seq = seq;
    }

    public void setTopLevel(char topLevel) {
        this.topLevel = topLevel;
    }

    public void setJobId(short jobId) {
        this.jobId = jobId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setDeptId(short deptId) {
        this.deptId = deptId;
    }

    public void setAutoFlag(boolean autoFlag) {
        this.autoFlag = autoFlag;
    }

    public int getStationId() {
        return stationId;
    }

    public char getServiceActivityType() {
        return serviceActivityType;
    }

    public short getSeq() {
        return seq;
    }

    public char getTopLevel() {
        return topLevel;
    }

    public short getJobId() {
        return jobId;
    }

    public int getEmpId() {
        return empId;
    }

    public short getDeptId() {
        return deptId;
    }

    public boolean isAutoFlag() {
        return autoFlag;
    }

}
