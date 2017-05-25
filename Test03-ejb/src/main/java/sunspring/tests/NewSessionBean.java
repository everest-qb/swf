/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sunspring.tests;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

/**
 *
 * @author QB
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class NewSessionBean {

 @PersistenceContext(unitName="SWFunit")
	private EntityManager em;

    public void businessMethod() {

    }

    
    public String test() {
      
        return "ABC";
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
