package sunspring.swf.jpa;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class JpaAllTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class)				
				.addPackage(SwfEmpsAll.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
        insertData();
        startTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}
	

	@Test
	public void testFindEmployee() {
		SwfEmpsAll employee=em.find(SwfEmpsAll.class, new BigDecimal(253));
		assertNotNull(employee);
	}

	@Test
	public void testFindDepartment(){
		SwfDeptAll dept=em.find(SwfDeptAll.class, new BigDecimal(270730));
		assertNotNull(dept);
	}
	
	@Test
	public void testProcess(){
		SwfItemHdrAll hdr=em.find(SwfItemHdrAll.class, new BigDecimal(586));
		assertNotNull(hdr);
	}
	
    private void clearData() throws Exception {
        utx.begin();
        em.joinTransaction();
        
        utx.commit();
    }

    private void insertData() throws Exception {
        utx.begin();
        em.joinTransaction();
        //System.out.println("Inserting records...");
       
        utx.commit();
        // clear the persistence context (first-level cache)
        em.clear();
    }

    private void startTransaction() throws Exception {
        utx.begin();
        em.joinTransaction();
    }
}
