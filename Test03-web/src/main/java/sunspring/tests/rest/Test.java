package sunspring.tests.rest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import sunspring.swf.ejb.DepartmentService;
import sunspring.swf.ejb.EmployeeService;
import sunspring.swf.ejb.ProcessService;
import sunspring.swf.jpa.SwfDeptAll;
import sunspring.swf.jpa.SwfEmpsAll;
import sunspring.swf.jpa.SwfItemHdrAll;
import sunspring.swf.jpa.SwfItemLineAll;
import sunspring.swf.jpa.SwfItemTxnAll;
import sunspring.tests.NewSessionBean;
import sunspring.tests.annotation.LogTrace;
import sunspring.tests.jpa.FndLookupValue;
import sunspring.tests.jpa.ShrEmployeesAll;

@LogTrace
@Path("test")
public class Test {

	@Inject
	private NewSessionBean service;
	@Inject
	private EmployeeService empService;
	@Inject
	private DepartmentService deptservice;
	@Inject
	private ProcessService processService;
	
	@Path("test")
	@GET
	public String test(){
		List<String> lits=service.test();
		String returnValue=lits.get(0);
		return returnValue;
	}
	
	@GET
	@Path("test2")	
	@Produces(MediaType.APPLICATION_JSON)
	public FndLookupValue test2(){
		 return	service.findALlByType(NewSessionBean.FND_TYPE.POSITION_TYPE).get(0);
	}
	@GET
	@Path("test3")
	@Produces(MediaType.APPLICATION_JSON)
	public ShrEmployeesAll findEmpl(@NotNull @Size(min=1) @QueryParam("id") String EmplNU){
		ShrEmployeesAll empl=null;
		try {
			empl = service.findEmplByNumber(EmplNU);
		} catch (Exception e) {
			
		}
		return empl;
	}
	
	@GET
	@Path("test5")
	@Produces(MediaType.APPLICATION_JSON)
	public SwfEmpsAll findByEmpNU(@NotNull @Size(min=1) @QueryParam("id") String EmplNU){
		return empService.findByNu(EmplNU);
	}
	
	@GET
	@Path("test6")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SwfEmpsAll> findALL(){
		return empService.findAll(true);
	}
	
	@GET
	@Path("test7")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SwfEmpsAll> findAll_01(){
		return empService.findAll(false);
	}
	
	@GET
	@Path("test8")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SwfEmpsAll> findAll_02(@NotNull @Size(max=8,min=8) @QueryParam("date") String date) throws ParseException{
		SimpleDateFormat f=new SimpleDateFormat("yyyyMMdd");
		Date d=f.parse(date);
		return empService.findByUpdate(d);
	}
	
	@GET
	@Path("test9")
	@Produces(MediaType.APPLICATION_JSON)//code=21610
	public List<SwfDeptAll> findDepByCode(@NotNull @QueryParam("code") String code){
		return deptservice.findHierarchy(code);
	}
	
	@GET
	@Path("test10")
	@Produces(MediaType.APPLICATION_JSON)//code=79430
	public List<SwfEmpsAll> findEmplByDept(@NotNull @QueryParam("code") String code){
		return deptservice.findEmplOfDeptCode(code);
	}
	
	@GET
	@Path("test11")
	public String genSWF_NO(){
		return processService.genSWF_NO();
	}
	
	@GET
	@Path("test12")
	@Produces(MediaType.APPLICATION_JSON)//id=586
	public SwfItemHdrAll findProcess(@NotNull @QueryParam("id") int hdrId){
		return processService.findProcessById(new BigDecimal(hdrId));
	}	
	
	@GET
	@Path("test13")
	@Produces(MediaType.APPLICATION_JSON)//id=586
	public List<SwfItemTxnAll> findTask(@NotNull @QueryParam("id") int hdrId){
		return processService.findAllTaskByPid(new BigDecimal(hdrId));
	}
	
	@GET
	@Path("test14")
	@Produces(MediaType.APPLICATION_JSON)//id=586
	public List<SwfItemLineAll> findVariables(@NotNull @QueryParam("id") int hdrId){
		return processService.findAllVariavlesByPid(new BigDecimal(hdrId));
	}
	
	@GET
	@Path("test15")
	@Produces(MediaType.APPLICATION_JSON)
	public void del(@NotNull @QueryParam("id") int hdrId){
		processService.delProcess(new BigDecimal(hdrId));
	}
}
