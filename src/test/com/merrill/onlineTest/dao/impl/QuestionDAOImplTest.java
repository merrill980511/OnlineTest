package test.com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.IQuestionDAO;
import com.merrill.onlineTest.dao.impl.QuestionDAOImpl;
import com.merrill.onlineTest.domain.Option;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.query.QueryQuestion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

/** 
* QuestionDAOImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 8, 2018</pre> 
* @version 1.0 
*/ 
public class QuestionDAOImplTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception {
} 

/** 
* 
* Method: save(Question question) 
* 
*/ 
@Test
public void testSave() throws Exception {
    IQuestionDAO dao = new QuestionDAOImpl();
    Question question = dao.getQuestionById(new Long(1));
    System.out.println(question);
    question.setAnswer("ABC");
    System.out.println(question);
    dao.save(question);
} 

/** 
* 
* Method: update(Question question) 
* 
*/ 
@Test
public void testUpdate() throws Exception {
    IQuestionDAO dao = new QuestionDAOImpl();
    Question question = dao.getQuestionById(new Long(4));
    Option option = new Option();
//    Set<Option> set = question.getOptions();for (Iterator<Option> it = question.getOptions().iterator(); it.hasNext();){
//        option = it.next();
//        break;
//    }
    question.setAnswer("fdfdsf");
    option.setContent("dnaskdhasj");
    dao.update(question);
} 

/** 
* 
* Method: delete(Long id) 
* 
*/ 
@Test
public void testDelete() throws Exception {
    IQuestionDAO dao = new QuestionDAOImpl();
    dao.delete(new Long(10));
} 

/** 
* 
* Method: getQuestionById(Long id) 
* 
*/ 
@Test
public void testGetQuestionById() throws Exception {
    IQuestionDAO dao = new QuestionDAOImpl();
    System.out.println(dao.getQuestionById(new Long(3)));
} 

/** 
*
 * Method: list()
* 
*/ 
@Test
public void testList() throws Exception {
    IQuestionDAO dao = new QuestionDAOImpl();
    QueryQuestion queryQuestion = new QueryQuestion();
//    queryQuestion.setId(new Long(2));
    System.out.println(dao.list());
} 


} 
