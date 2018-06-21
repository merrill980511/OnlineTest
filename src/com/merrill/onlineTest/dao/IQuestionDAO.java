package com.merrill.onlineTest.dao;

import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.query.QueryQuestion;

import java.util.List;

public interface IQuestionDAO {

    /**
     * 添加题目
     * @param question
     */
    public int save(Question question);

    /**
     * 题目更新
     * @param question
     */
    public int update(Question question);

    /**
     * 根据题目编号删除题目
     * @param id
     */
    public int delete(Long id);

    /**
     * 根据题目编号查找题目
     * @param id 传入需要查找的题目编号
     * @return 存在则返回该题目，否则返回null
     */
    public Question getQuestionById(Long id);

    /**
     * 根据查询条件查询出所有符合的题目
     * @return
     */
    public List list();

    /**
     * 总题数
     * @param queryQuestion
     * @return
     */
    public int questionCount(QueryQuestion queryQuestion);


    /**
     * 查询对应的题目的编号，题干，答案
     * @param queryQuestion
     * @return
     */
    List<Question> queryQuestion(QueryQuestion queryQuestion);

    /**
     * 根据传入的学科id和题型id确定题目的id
     * @param sortId
     * @param id
     * @return
     */
    List<Long> generateById(Long sortId, Long id);


}
