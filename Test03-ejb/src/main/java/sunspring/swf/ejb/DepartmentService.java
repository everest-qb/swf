package sunspring.swf.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sunspring.swf.jpa.SwfDeptAll;
import sunspring.swf.jpa.SwfEmpsAll;


@Stateless
@LocalBean
public class DepartmentService {

	@PersistenceContext(unitName = "SWFunit")
	private EntityManager em;

	public SwfDeptAll findByCode(String code){
		return em.createQuery("SELECT d FROM SwfDeptAll d WHERE d.deptCode=:DEP_CODE", SwfDeptAll.class)
		.setParameter("DEP_CODE", code)
		.getSingleResult();
	}
	
	public List<SwfEmpsAll> findEmplOfDeptCode(String code){
		return findByCode(code).getEmployees();
	}
	
	public List<SwfDeptAll> findHierarchy(String code){
		return findHierarchy(findByCode(code).getDeptId());
	}
	
	private List<SwfDeptAll> findHierarchy(BigDecimal depId){
		if(depId==null)
			return new ArrayList<SwfDeptAll>();
		SwfDeptAll dep=em.find(SwfDeptAll.class, depId);
		if(dep==null){
			return new ArrayList<SwfDeptAll>();
		}else{
			List<SwfDeptAll> r=findHierarchy(dep.getParentDeptId());
			r.add(dep);
			return r;
		}	
	}
	
    public void clearCache(){
    	Cache cache=em.getEntityManagerFactory().getCache();
    	cache.evict(SwfEmpsAll.class);
    }
}
