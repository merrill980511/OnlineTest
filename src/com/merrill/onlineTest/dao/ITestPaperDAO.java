package com.merrill.onlineTest.dao;

import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.query.QueryTestPaper;
import org.junit.Test;

import java.util.List;

public interface ITestPaperDAO {

    /**
     * 添加试卷
     * @param testPaper
     */
    public int save(TestPaper testPaper);

    /**
     * 根据传入的题目id生成试卷
     * @param id
     * @param name
     * @param sortId
     * @return
     */
    public Long save(List id, String name, Long sortId);

    /**
     * 更新试卷
     * @param testPaper
     */
    public int update(TestPaper testPaper);

    /**
     * 根据试卷编号删除试卷
     * @param id
     */
    public int delete(Long id);

    /**
     * 根据试卷编号查找试卷
     * @param id 传入需要查找的试卷编号
     * @return 存在则返回该试卷，否则返回null
     */
    public TestPaper getTestPaperById(Long id);

    /**
     * 根据查询条件查询出所有符合的试卷
     * @return
     */
    public List list();

    /**
     * 总数据条数
     * @param queryTestPaper
     * @return
     */
    public int count(QueryTestPaper queryTestPaper);

    /**
     * 查询对应的题目的编号，题干，答案
     * @param queryTestPaper
     * @return
     */
    List<TestPaper> queryTestPaper(QueryTestPaper queryTestPaper);

}
