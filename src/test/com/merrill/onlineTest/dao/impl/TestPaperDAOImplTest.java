package test.com.merrill.onlineTest.dao.impl; 

import com.merrill.onlineTest.dao.ITestPaperDAO;
import com.merrill.onlineTest.dao.impl.TestPaperDAOImpl;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.query.QueryTestPaper;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.List;

/** 
* TestPaperDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 9, 2018</pre> 
* @version 1.0 
*/ 
public class TestPaperDAOImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: save(TestPaper testPaper) 
* 
*/ 
@Test
public void testSave() throws Exception {
    ITestPaperDAO dao = new TestPaperDAOImpl();
    TestPaper testPaper = dao.getTestPaperById(new Long(4));
    testPaper.setName("new Test");
    List<Question> questions = testPaper.getQuestions();
    questions.remove(1);
    System.out.println(testPaper);
    dao.save(testPaper);
    System.out.println(dao.getTestPaperById(new Long(5)));
} 

/** 
* 
* Method: update(TestPaper testPaper) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    ITestPaperDAO dao = new TestPaperDAOImpl();
    TestPaper testPaper = dao.getTestPaperById(new Long(4));
    testPaper.setName("Test");
    testPaper.setId(new Long(5));
//    List<Question> questions = testPaper.getQuestions();
//    questions.remove(1);
    System.out.println(testPaper);
    dao.update(testPaper);
    System.out.println(dao.getTestPaperById(new Long(4)));
} 

/** 
* 
* Method: delete(Long id) 
* 
*/ 
@Test
public void testDelete() throws Exception {
    ITestPaperDAO dao = new TestPaperDAOImpl();
    System.out.println(dao.getTestPaperById(new Long(4)));
    dao.delete(new Long(4));
    System.out.println(dao.getTestPaperById(new Long(4)));
} 

/** 
* 
* Method: getTestPaperById(Long id) 
* 
*/ 
@Test
public void testGetTestPaperById() throws Exception {
    ITestPaperDAO dao = new TestPaperDAOImpl();
    System.out.println(dao.getTestPaperById(new Long(2)));
} 

/** 
* 
* Method: list()
* 
*/ 
@Test
public void testList() throws Exception {
    ITestPaperDAO dao = new TestPaperDAOImpl();
    System.out.println(dao.list());
} 


} 
