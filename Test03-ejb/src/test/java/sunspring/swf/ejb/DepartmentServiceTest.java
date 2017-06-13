package sunspring.swf.ejb;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import sunspring.swf.jpa.SwfDeptAll;
import sunspring.swf.jpa.SwfEmpsAll;

@RunWith(Arquillian.class)
public class DepartmentServiceTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)				
				.addPackage(SwfEmpsAll.class.getPackage())
				.addClass(DepartmentService.class)
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	DepartmentService service;
	
	
	@Test
	public void testFindByCode() {
		assertNotNull(service.findByCode("21610"));
	}

	@Test
	public void testFindEmplOfDeptCode() {
		List<SwfEmpsAll> list=service.findEmplOfDeptCode("79430");
		assertTrue(list.size()>0);
	}

	@Test
	public void testFindHierarchy() {
		List<SwfDeptAll> list =service.findHierarchy("21610");
		assertTrue(list.size()>0);
	}

}
