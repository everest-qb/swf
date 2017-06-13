/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sunspring.tests;

import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sunspring.tests.annotation.LogTrace;
import sunspring.tests.jpa.FndLookupValue;
import sunspring.tests.jpa.ShrEmployeesAll;

/**
 *
 * @author QB
 */
@LogTrace
@Stateless
@LocalBean
public class NewSessionBean {
	
	public enum FND_TYPE{POSITION_TYPE,POSITION,JOB_LEVEL,JOB_GRADE};
	
	@PersistenceContext(unitName = "SWFunit")
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<String> test() {
		List<String> returnValue=new ArrayList<>();
		
		List<Object[]> list=(List<Object[]>)em.createNativeQuery("SELECT sea.employee_id,sea.employee_number "
				+ "FROM  shr.shr_employees_all sea,shr.shr_emp_txn_summaries_all setsa "
				+ "WHERE sea.employee_id = setsa.employee_id").getResultList();
		for(Object[] o:list){
			returnValue.add((String)o[1]);
		}
		return returnValue;
	}

	public List<FndLookupValue> findALlByType(FND_TYPE type){
		List<FndLookupValue> returnValue=null;
		returnValue=em.createQuery("SELECT v FROM FndLookupValue v WHERE v.id.lookupType=:ID", FndLookupValue.class)
				.setParameter("ID", "SHR:"+type.name())
				.getResultList();
		
		
		return returnValue;
	}
	
	public ShrEmployeesAll findEmplByNumber(String emplNumber) throws Exception{
		return em.createQuery("SELECT e FROM ShrEmployeesAll e JOIN FETCH e.summary WHERE e.employeeNumber=:EmplNU", ShrEmployeesAll.class)
				.setParameter("EmplNU", emplNumber)
				.getSingleResult();
	}
	
}
