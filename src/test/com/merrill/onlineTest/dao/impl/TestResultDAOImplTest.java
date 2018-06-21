package test.com.merrill.onlineTest.dao.impl; 

import com.merrill.onlineTest.dao.ITestResultDAO;
import com.merrill.onlineTest.dao.impl.TestResultDAOImpl;
import com.merrill.onlineTest.query.QueryTestResult;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* TestResultDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 9, 2018</pre> 
* @version 1.0 
*/ 
public class TestResultDAOImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: save(TestResult testResult) 
* 
*/ 
@Test
public void testSave() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: query(QueryTestResult queryTestResult) 
* 
*/ 
@Test
public void testQuery() throws Exception {
    ITestResultDAO dao = new TestResultDAOImpl();
    QueryTestResult queryTestResult = new QueryTestResult();
    System.out.println(dao.query(queryTestResult));
} 


} 
