package sunspring.tests.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import sunspring.tests.NewSessionBean;
import sunspring.tests.annotation.LogTrace;

@LogTrace
@Path("test")
public class Test {

	@Inject
	private NewSessionBean service;
	
	@Path("test")
	@GET
	public String test(){
		List<String> lits=service.test();
		String returnValue=lits.get(0);
		return returnValue;
	}
	
	@GET
	@Path("test2")	
	public int test2(){
		 return	service.findALlByType(NewSessionBean.FND_TYPE.POSITION_TYPE).size();
	}
}
