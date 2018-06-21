package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.ITestPaperDAO;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.query.QueryTestPaper;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;
import com.merrill.onlineTest.template.impl.QuestionResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestPaperDAOImpl implements ITestPaperDAO {
    @Override
    public int save(TestPaper testPaper) {
        String sql = "INSERT INTO test(name, starttime, endtime, time, s_is) VALUES(?, ?, ?, ?, ?)";
        int num = 0;
        num = JdbcTemplate.update(sql, testPaper.getName(), testPaper.getStartTime(),
                testPaper.getEndTime(), testPaper.getTime(), testPaper.getSort().getId());
        sql = "SELECT MAX(id) FROM test";
        Long id = JdbcTemplate.query(sql, new IResultSetHandler<Long>() {
            @Override
            public Long handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
        sql = "INSERT INTO test_question(t_id, q_id) VALUES(?, ?)";
        for (Iterator<Question> it = testPaper.getQuestions().iterator(); it.hasNext();) {
            Question question = it.next();
            JdbcTemplate.update(sql, id, question.getId());
        }
        return num;
    }

    @Override
    public Long save(List id,String name,Long sortId) {
        String sql = "INSERT INTO test(name, s_id) VALUES(?, ?)";
        JdbcTemplate.update(sql, name, sortId);
        sql = "SELECT MAX(id) FROM test";
        Long maxId = JdbcTemplate.query(sql, new IResultSetHandler<Long>() {
            @Override
            public Long handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
        sql = "INSERT INTO test_question(t_id, q_id) VALUES(?, ?)";
        for (Iterator<Long> it = id.iterator(); it.hasNext();) {
            Long temp = it.next();
            JdbcTemplate.update(sql, maxId, temp);
        }
        return maxId;
    }

    @Override
    public int update(TestPaper testPaper) {
        String sql = "UPDATE test SET name = ?, starttime = ?, endtime = ?, time = ? WHERE id = ?";
        int num = 0;

        JdbcTemplate.update(sql, testPaper.getName(), testPaper.getStartTime(),
                testPaper.getEndTime(), testPaper.getId(), testPaper.getTime());
        sql = "DELETE FROM test_question WHERE t_id = ?";
        JdbcTemplate.update(sql, testPaper.getId());
        for (Iterator<Question> it = testPaper.getQuestions().iterator(); it.hasNext(); ){
            Question question = it.next();
            sql = "INSERT INTO test_question(t_id, q_id) VALUES(?, ?)";
            JdbcTemplate.update(sql, testPaper.getId(), question.getId());
        }
        return num;
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM test_question WHERE t_id = ?";
        JdbcTemplate.update(sql, id);
        sql = "DELETE FROM test WHERE id = ?";
        return JdbcTemplate.update(sql, id);
    }

    @Override
    public TestPaper getTestPaperById(Long id) {
        String sql = "SELECT * FROM test_question tq left join test t on t.id = tq.t_id left join " +
                "sort s on s.id = t.s_id WHERE t.id = ?";
        List<TestPaper> list = JdbcTemplate.query(sql, new TestPaperResultSetHandler(), id);
        TestPaper testPaper = null;
        if (list.size() != 0) {
            testPaper = finish(list).get(0);
        }
        return testPaper;
    }

    @Override
    public List list() {
        String sql = "SELECT * FROM test_question tq left join test t on t.id = tq.t_id left join " +
                "sort s on s.id = t.s_id ";
        System.out.println(sql);
        List<TestPaper> list = JdbcTemplate.query(sql, new TestPaperResultSetHandler());
        return finish(list);
    }

    @Override
    public int count(QueryTestPaper queryTestPaper) {
        queryTestPaper.setCount(true);
        String sql = "SELECT COUNT(*) FROM test t left join sort s on s.id=t.s_id  " + queryTestPaper.getQuery();
        return JdbcTemplate.query(sql, new IResultSetHandler<Integer>() {
            @Override
            public Integer handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        }, queryTestPaper.getParameters().toArray());
    }

    @Override
    public List<TestPaper> queryTestPaper(QueryTestPaper queryTestPaper) {
        String sql = "SELECT * FROM test t left join sort s on s.id=t.s_id " + queryTestPaper.getQuery() + "LIMIT ?, ?";
        System.out.println(sql);
        System.out.println(queryTestPaper.getParameters());
        List<TestPaper> testPapers = JdbcTemplate.query(sql, new PaperResultSetHandler(), queryTestPaper.getParameters().toArray());
        return testPapers;
    }

    private List<TestPaper> finish(List<TestPaper> list) {
        for (int i = 0; i < list.size(); i++) {
            List<Question> questions = list.get(i).getQuestions();

            for (int j = 0; j < questions.size(); j++) {
                String sql = "SELECT * FROM questions q LEFT JOIN sort s on q.s_id=s.id LEFT JOIN " +
                        "options o on o.q_id=q.id LEFT JOIN selection se on q.selection=se.id  where q.id = ?";
                List<Question> questionList = JdbcTemplate.query(sql, new QuestionResultSetHandler(),
                        questions.get(j).getId());

                if (questions.size() != 0) {
                    questions.set(j, questionList.get(0));
                }
            }

        }
        return list;
    }

    private class PaperResultSetHandler implements IResultSetHandler<List<TestPaper>> {
        @Override
        public List<TestPaper> handler(ResultSet rs) throws SQLException {
            List<TestPaper> list = new ArrayList<>();
            while (rs.next()){
                TestPaper testPaper = new TestPaper();
                list.add(testPaper);
                testPaper.setId(rs.getLong("t.id"));
                testPaper.setName(rs.getString("t.name"));
                testPaper.setStartTime(rs.getTimestamp("starttime"));
                testPaper.setEndTime(rs.getTimestamp("endtime"));
                testPaper.setTime(rs.getInt("time"));
                Sort sort = new Sort();
                sort.setId(rs.getLong("s.id"));
                sort.setName(rs.getString("s.name"));
                testPaper.setSort(sort);
            }
            return list;
        }
    }

    private class TestPaperResultSetHandler implements IResultSetHandler<List<TestPaper>> {
        @Override
        public List<TestPaper> handler(ResultSet rs) throws SQLException {
            List<TestPaper> list = new ArrayList<>();
            List<Question> questions = new ArrayList<>();
            Long id = new Long("-1");
            while (rs.next()) {
                if (rs.getLong("t.id") != id) {
                    TestPaper testPaper = new TestPaper();
                    list.add(testPaper);
                    Sort sort = new Sort();
                    sort.setName(rs.getString("s.name"));
                    sort.setId(rs.getLong("s.id"));
                    testPaper.setSort(sort);
                    questions = new ArrayList<>();
                    Question question = new Question();
                    questions.add(question);
                    question.setId(rs.getLong("q_id"));
                    id = rs.getLong("t.id");
                    testPaper.setId(id);
                    testPaper.setQuestions(questions);
                    testPaper.setName(rs.getString("t.name"));
                    testPaper.setStartTime(rs.getTimestamp("starttime"));
                    testPaper.setEndTime(rs.getTimestamp("endtime"));
                    testPaper.setTime(rs.getInt("time"));
                } else {
                    Question question = new Question();
                    id = rs.getLong("t.id");
                    question.setId(rs.getLong("q_id"));
                    questions.add(question);
                }
            }
            return list;
        }
    }
}
