package test.com.merrill.onlineTest.dao.impl; 

import com.merrill.onlineTest.dao.ISelectionDAO;
import com.merrill.onlineTest.dao.impl.SelectionDAOImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* SelectionDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 13, 2018</pre> 
* @version 1.0 
*/ 
public class SelectionDAOImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: save(Selection selection) 
* 
*/ 
@Test
public void testSave() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: delete(Long id) 
* 
*/ 
@Test
public void testDelete() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: update(Selection selection) 
* 
*/ 
@Test
public void testUpdate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getSortById(Long id) 
* 
*/ 
@Test
public void testGetSortById() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: list() 
* 
*/ 
@Test
public void testList() throws Exception {
    ISelectionDAO dao = new SelectionDAOImpl();
    System.out.println(dao.list());
} 

/** 
* 
* Method: handler(ResultSet rs) 
* 
*/ 
@Test
public void testHandler() throws Exception { 
//TODO: Test goes here... 
} 


} 
