package test.com.merrill.onlineTest.dao.impl; 

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.dao.impl.SortDAOImpl;
import com.merrill.onlineTest.domain.Sort;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* SortDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 8, 2018</pre> 
* @version 1.0 
*/ 
public class SortDAOImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: save(Sort sort) 
* 
*/ 
@Test
public void testSave() throws Exception {
    ISortDAO dao = new SortDAOImpl();
    Sort sort = dao.getSortById(new Long(8));
    sort.setId(new Long(9));
    dao.save(sort);
    System.out.println(dao.getSortById(new Long(9)));
} 

/** 
* 
* Method: delete(Long id) 
* 
*/ 
@Test
public void testDelete() throws Exception {
    ISortDAO dao = new SortDAOImpl();
    dao.delete(new Long(9));
    System.out.println(dao.getSortById(new Long(9)));
} 

/** 
* 
* Method: update(Sort sort) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    ISortDAO dao = new SortDAOImpl();
    Sort sort = dao.getSortById(new Long(8));
    sort.setName("j2ee");
    dao.update(sort);
    System.out.println(dao.getSortById(new Long(8)));
} 

/** 
* 
* Method: getSortById(Long id) 
* 
*/ 
@Test
public void testGetSortById() throws Exception {
    ISortDAO dao = new SortDAOImpl();
    System.out.println(dao.getSortById(new Long("1")));
    System.out.println(dao.getSortById(new Long(2)));
} 

/**
*
* Method: list()
*
*/
@Test
public void testList() throws Exception {
    ISortDAO dao = new SortDAOImpl();
    System.out.println(dao.list());
}


} 
