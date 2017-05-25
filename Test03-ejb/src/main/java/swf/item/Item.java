/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.item;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;
import swf.core.SwfFlowEngine;

/**
 *
 * @author andy.dh.chen
 */
public class Item implements Serializable {

    private short serviceItemId;
    private String nowTxnId;
    private Person writter;
    private File attachment;
    private List<Person> appls;
    private List<ItemContent> content;
    private StringBuilder notation;
    private List<ItemTxn> txns;
    private Station station;

    private int hdrId;
    private Date startDate;
    private Date endDate;
    private boolean isEndAtJobExpire;
    private String hdrNo;
    private String requestLevel;
    private String processStatus;

    public Item() {

    }

    public Item(short itemId) {
        this.serviceItemId = itemId;
    }

    public short getServiceItemId() {
        return serviceItemId;
    }

    public Person getWritter() {
        return writter;
    }

    public File getAttachment() {
        return attachment;
    }

    public List<Person> getAppls() {
        return appls;
    }

    public List<ItemContent> getContent() {
        return content;
    }

    public StringBuilder getNotation() {
        return notation;
    }

    public List<ItemTxn> getTxns() {
        return txns;
    }

    public Station getStation() {
        return station;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isIsEndAtJobExpire() {
        return isEndAtJobExpire;
    }

    public String getHdrNo() {
        return hdrNo;
    }

    public String getRequestLevel() {
        return requestLevel;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public int getHdrId() {
        return hdrId;
    }

    public String getNowTxnId() {
        return nowTxnId;
    }

    public void setServiceItemId(short serviceItemId) {
        this.serviceItemId = serviceItemId;
    }

    public void setWritter(Person writter) {
        this.writter = writter;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public void setAppls(List<Person> appls) {
        this.appls = appls;
    }

    public void setAppl(Person appl) {
        if (this.appls == null) {
            this.appls = new ArrayList<Person>();
        }
        this.appls.add(appl);
    }

    public void setNowTxnId(String nowTxnId) {
        this.nowTxnId = nowTxnId;
    }

    public boolean removeAppl(Person appl) {
        for (Person a : this.appls) {
            if (a.equals(appl)) {
                return this.appls.remove(a);
            }
        }
        return false;
    }

    public void setContent(List<ItemContent> content) {
        this.content = content;
    }

    public void setContent(ItemContent content) {
        if (this.content == null) {
            this.content = new ArrayList<ItemContent>();
        }
        this.content.add(content);
    }

    public void setNotation(StringBuilder notation) {
        this.notation = notation;
    }

    //ItemDriver用
    public void setTxns(List<ItemTxn> txns) {
        this.txns = txns;
    }

    public void setTxn(ItemTxn txn) {
        txn.setCreator(writter);
        this.txns.add(txn);
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setIsEndAtJobExpire(boolean isEndAtJobExpire) {
        this.isEndAtJobExpire = isEndAtJobExpire;
    }

    public void setHdrNo(String hdrNo) {
        this.hdrNo = hdrNo;
    }

    public void setRequestLevel(String requestLevel) {
        this.requestLevel = requestLevel;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public void setHdrId(int hdrId) {
        this.hdrId = hdrId;
    }

    public static void main(String args[]) {
        Item item = new Item();
        /*Person p1 = new Person();
        p1.setUserId(1);
        p1.setEmpId(1);
        p1.setEmpName("高偉強");

        Person p2 = new Person();
        p2.setUserId(2);
        p2.setEmpId(2);
        p2.setEmpName("施韋男");

        Person p3 = new Person();
        p3.setUserId(3);
        p3.setEmpId(3);
        p3.setEmpName("劉仁杰");

        Person p4 = new Person();
        p4.setUserId(4);
        p4.setEmpId(4);
        p4.setEmpName("陳德鴻");

        Person p5 = new Person();
        p5.setUserId(5);
        p5.setEmpId(5);
        p5.setEmpName("陳彥呂");

        item.setAppl(p1);
        item.setAppl(p2);
        item.setAppl(p3);
        item.setAppl(p4);
        item.setAppl(p5);

        item.setAppls(item.appls);

        Person p6 = new Person();
        p6.setUserId(2);
        p6.setEmpId(2);
        p6.setEmpName("施韋男");
        System.out.println(item.getAppls().size());
        System.out.println(item.removeAppl(p6));
        System.out.println(item.getAppls().size());*/

    }

}
