package com.merrill.onlineTest.dao;

import com.merrill.onlineTest.domain.TestResult;
import com.merrill.onlineTest.query.QueryTestResult;

import java.math.BigDecimal;
import java.util.List;

public interface ITestResultDAO {
    /**
     * 保存考试成绩
     * @param testResult
     */
    public int save(TestResult testResult);

    /**
     * 根据查询条件查询出所有符合的试卷
     * @param queryTestResult
     * @return
     */
    public List query(QueryTestResult queryTestResult);

    /**
     * 返回查询到的总条数
     * @param queryTestResult
     * @return
     */
    public int count(QueryTestResult queryTestResult);

    /**
     * 根据账号删除该用户
     * @param id 用户的账号
     */
    public int delete(Long id);

    /**
     *
     * @param id 传入的结果的编号
     * @return 返回当前对象
     */
    public TestResult getResultById(Long id);

    /**
     * 根据成绩存储编号修改成绩
     * @param id
     * @param mark
     * @return
     */
    public int update(Long id, BigDecimal mark);
}
