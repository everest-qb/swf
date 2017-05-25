/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swf.item;

import java.util.ArrayList;
import java.util.List;
import swf.core.SwfFlowEngine;

/**
 *
 * @author andy.dh.chen
 */
public class ItemTester {

    public static void main(String args[]) {
        Item item = new Item();
        Person person = new Person();
        person.setUserName("SHQ-ANDY.DH.CHEN");
        item.setWritter(person);
        item.setHdrId(1);
        ItemTxn itemTxn = new ItemTxn();
        itemTxn.setTxnId(994);

        Station station = new Station();
        char type = 10;
        station.setServiceActivityType(type);
        List<ItemTxn> txns = new ArrayList<ItemTxn>();
        item.setTxns(txns);

    }

    /*public void test() {
        Item item = new Item();
        Person person = new Person();
        person.setUserName("SHQ-ANDY.DH.CHEN");
        item.setWritter(person);
        item.setHdrId(1);
        SwfFlowEngine.pushItem(item);
        ItemTxn itemTxn = new ItemTxn();
        itemTxn.setTxnId(994);

        Station station = new Station();

        char type = 10;
        station.setServiceActivityType(type);
        itemTxn.setStation(station);
        List<ItemTxn> txns = new ArrayList<ItemTxn>();
        item.setTxns(txns);

    }*/
}
