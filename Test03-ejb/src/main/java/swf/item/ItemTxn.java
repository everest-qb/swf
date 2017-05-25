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
public class ItemTxn implements Serializable {

    //private short stationId;
    private Station station;
    private int txnId;
    private short serviceAppType;
    private Person creator;
    private Person approver;
    private Person delegate;
    private int lastTxnId;
    private int nextTxnId;
    private int hdrId;
    private char processFlag;
    private String sqlCommad;
    private String approveCommend;

    public ItemTxn() {
    }

    public ItemTxn(Person creator) {
        this.creator = creator;
    }

    public int getTxnId() {
        return txnId;
    }

    public int getHdrId() {
        return hdrId;
    }

    public Station getStation() {
        return station;
    }

    public short getServiceAppType() {
        return serviceAppType;
    }

    public Person getApprover() {
        return approver;
    }

    public int getLastTxnId() {
        return lastTxnId;
    }

    public int getNextTxnId() {
        return nextTxnId;
    }

    public Person getDelegate() {
        return delegate;
    }

    public String getSqlCommad() {
        return sqlCommad;
    }

    public Person getCreator() {
        return creator;
    }

    public char getProcessFlag() {
        return processFlag;
    }

    public String getApproveCommend() {
        return approveCommend;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setServiceAppType(short serviceAppType) {
        this.serviceAppType = serviceAppType;
    }

    public void setApprover(Person approver) {
        this.approver = approver;
    }

    public void setLastTxnId(int lastTxnId) {
        this.lastTxnId = lastTxnId;
    }

    public void setNextTxnId(int nextTxnId) {
        this.nextTxnId = nextTxnId;
    }

    public void setDelegate(Person delegate) {
        this.delegate = delegate;
    }

    public void setTxnId(int txnId) {
        this.txnId = txnId;
    }

    public void setHdrId(int hdrId) {
        this.hdrId = hdrId;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public void setProcessFlag(char processFlag) {
        this.processFlag = processFlag;
    }

    public void setApproveCommend(String approveCommend) {
        this.approveCommend = approveCommend;
    }

    public void setSqlCommad(String type) {

        if (type.equals("INSERT")) {
            this.sqlCommad = " SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(" 
                    + "P_ITEM_TXN_ID=>" + this.getTxnId()
                    + ",P_STATION_Id=>" + this.getStation().getStationId()
                    + ",P_HDR_ID=>" + this.getHdrId()
                    + ",P_APPROVER_ID=>" + this.getApprover()
                    + ",P_USER_ID=>" + this.getCreator().getUserId()
                    + ",P_PROCESS_FLAG=>" + this.getProcessFlag()
                    + ",P_APPROVE_TYPE=>'" + this.getServiceAppType() + "'"
                    + ",P_NEXT_TXN_ID=>" + this.getNextTxnId()
                    + ",P_LAST_TXN_ID=>" + this.getLastTxnId() + ");";
        } else if (type.equals("UPDATE")) {
            /*this.sqlCommad = " SWF_DML_PKG.INSERT_SWF_ITEM_TXN_ALL(" 
                    + "P_ITEM_TXN_ID=>" + this.getTxnId()
                    + ",P_STATION_Id=>" + this.getStationId()
                    + ",P_HDR_ID=>" + this.getHdrId()
                    + ",P_APPROVER_ID=>" + this.getApprover()
                    + ",P_DELEGATE_ID=>" + this.getDelegate()
                    + ",P_USER_ID=>" + this.getCreator().getUserId()
                    + ",P_PROCESS_FLAG=>" + this.getProcessFlag()
                    + ",P_APPROVE_TYPE=>'" + this.getServiceAppType() + "'"
                    + ",P_NEXT_TXN_ID=>" + this.getNextTxnId()
                    + ",P_LAST_TXN_ID=>" + this.getLastTxnId() + ");";*/

        }

    }

}
