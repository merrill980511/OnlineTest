package test.com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.IAdminDAO;
import com.merrill.onlineTest.dao.impl.AdminDAOImpl;
import com.merrill.onlineTest.domain.Admin;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
* AdminDAOImpl Tester.
*
* @author <Authors name>
* @since <pre>���� 10, 2018</pre>
* @version 1.0
*/
public class AdminDAOImplTest {

@Before
public void before() throws Exception {
}

@After
public void after() throws Exception {
}

/**
*
* Method: login(Long id, String password)
*
*/
@Test
public void testLogin() throws Exception {
    IAdminDAO dao = new AdminDAOImpl();
    System.out.println(dao.login(new Long(123), "202CB962AC5"));
    System.out.println(dao.login(new Long(124), "202CB962AC5"));
}

/**
*
* Method: getAdminById(Long id)
*
*/
@Test
public void testGetAdminById() throws Exception {
    IAdminDAO dao = new AdminDAOImpl();
    System.out.println(dao.getAdminById(new Long(123)));
    System.out.println(dao.getAdminById(new Long(124)));
}

/**
*
* Method: update(Admin admin)
*
*/
@Test
public void testUpdate() throws Exception {
    IAdminDAO dao = new AdminDAOImpl();
    Admin admin = dao.getAdminById(new Long("123"));
    admin.setName("merrill0511");
    dao.update(admin);
    System.out.println(dao.getAdminById(new Long("123")));
}


}
