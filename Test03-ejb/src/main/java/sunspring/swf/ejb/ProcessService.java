package sunspring.swf.ejb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import sunspring.swf.jpa.SwfItemHdrAll;
import sunspring.swf.jpa.SwfItemLineAll;
import sunspring.swf.jpa.SwfItemTxnAll;
import sunspring.tests.annotation.LogTrace;

/**
 * @author QB
 */
@LogTrace
@Stateless
@LocalBean
public class ProcessService {


	@PersistenceContext(unitName = "SWFunit")
	private EntityManager em;
	
	public String genSWF_NO(){
		String returnValue;
		SimpleDateFormat swfHoFormat = new SimpleDateFormat("YYYYMMdd");
		Calendar date = Calendar.getInstance();
		Object no=em.createNativeQuery("Select lpad(SWF.SWF_ITEM_NO_S1.nextval,3,0) from dual")
				.getSingleResult();
		returnValue="SWF" + swfHoFormat.format(date.getTime())+no;
		return returnValue;
	}
	
	public SwfItemHdrAll findProcessById(BigDecimal hdrId){
		SwfItemHdrAll p= em.find(SwfItemHdrAll.class, hdrId);		
		return p;
	}
	
	public List<SwfItemTxnAll> findAllTaskByPid(BigDecimal hdrId){
		SwfItemHdrAll p= em.find(SwfItemHdrAll.class, hdrId);
		return p.getItemTxn();
	}
	
	public List<SwfItemLineAll> findAllVariavlesByPid(BigDecimal hdrId){
		SwfItemHdrAll p= em.find(SwfItemHdrAll.class, hdrId);
		return p.getItemLine();
	}
	
	public void delProcess(BigDecimal hdrId){
		SwfItemHdrAll p= em.find(SwfItemHdrAll.class, hdrId);
		em.remove(p);
	}
}
