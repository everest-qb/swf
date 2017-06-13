package sunspring.swf.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sunspring.swf.jpa.SwfEmpsAll;
import sunspring.tests.annotation.LogTrace;

/**
 * @author QB
 */
@LogTrace
@Stateless
@LocalBean
public class EmployeeService {

	@PersistenceContext(unitName = "SWFunit")
	private EntityManager em;
    
    /**
     * @param 工號
     * @return SwfEmpsAll
     */
    public SwfEmpsAll findByNu(String emplNu){
    	return em.createQuery("SELECT e FROM SwfEmpsAll e WHERE e.empNum=:EMNU", SwfEmpsAll.class)
    	.setParameter("EMNU", emplNu)
    	.getSingleResult();
    }

    public List<SwfEmpsAll> findAll(boolean isIncludeLaft){
    	List<SwfEmpsAll> returnValue;
    	if(isIncludeLaft){
    		returnValue=em.createNamedQuery("SwfEmpsAll.findAll", SwfEmpsAll.class).getResultList();
    	}else{
    		returnValue=em.createQuery("SELECT e FROM SwfEmpsAll e WHERE e.leaveOfficeDate<NOW()", SwfEmpsAll.class).getResultList();
    	}	
    	return returnValue;
    }
    
    
    public List<SwfEmpsAll> findByUpdate(Date date){
    	List<SwfEmpsAll> returnValue;
    	returnValue=em.createQuery("SELECT e FROM SwfEmpsAll e WHERE e.lastUpdateDate>=:UPDATE_DAY", SwfEmpsAll.class)
    			.setParameter("UPDATE_DAY", date)
    			.getResultList();
    	return returnValue; 
    }
    
    public void clearCache(){
    	Cache cache=em.getEntityManagerFactory().getCache();
    	cache.evict(SwfEmpsAll.class);
    }
}
