package test.com.merrill.onlineTest.dao.impl; 

import com.merrill.onlineTest.dao.impl.UserDAOImpl;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.QueryUser;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* UserDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 8, 2018</pre> 
* @version 1.0 
*/ 
public class UserDAOImplTest { 

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
    UserDAOImpl dao = new UserDAOImpl();
    User user = dao.login(new Long("123"), "202CB962AC5");
    System.out.println(user);
} 

/** 
* 
* Method: getUserById(Long id) 
* 
*/ 
@Test
public void testGetUserById() throws Exception {
    UserDAOImpl dao = new UserDAOImpl();
    User user = dao.getUserById(new Long("123"));
    System.out.println(user);
} 

/** 
* 
* Method: query(QueryUser queryUser) 
* 
*/ 
@Test
public void testQuery() throws Exception {
    UserDAOImpl dao = new UserDAOImpl();
    QueryUser user = new QueryUser();
    user.setId(new Long("12"));
    System.out.println(dao.query(user));
} 

/** 
* 
* Method: save(User user) 
* 
*/ 
@Test
public void testSave() throws Exception {
    UserDAOImpl dao = new UserDAOImpl();
    User user = dao.getUserById(new Long("124"));
    System.out.println(user);
    user.setId(new Long("126"));
    dao.save(user);
    System.out.println(dao.getUserById(new Long("126")));
} 

/** 
* 
* Method: update(User user) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    UserDAOImpl dao = new UserDAOImpl();
    User user = dao.getUserById(new Long("124"));
    user.setRemark("meifengxin");
    dao.update(user);
    System.out.println(dao.getUserById(new Long("124")));
} 

/** 
* 
* Method: delete(Long id) 
* 
*/ 
@Test
public void testDelete() throws Exception {
    UserDAOImpl dao = new UserDAOImpl();
    dao.delete(new Long(127));
    QueryUser user = new QueryUser();
    user.setId(new Long(127));
    System.out.println(dao.query(user));
}

} 
