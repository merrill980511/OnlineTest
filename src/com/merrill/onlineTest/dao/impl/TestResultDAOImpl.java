package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.ITestResultDAO;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.domain.TestPaper;
import com.merrill.onlineTest.domain.TestResult;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.QueryTestResult;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestResultDAOImpl implements ITestResultDAO {
    @Override
    public int save(TestResult testResult) {
        String sql = "INSERT INTO test_result(t_id, u_id, mark) VALUES(?, ?, ?)";
        return JdbcTemplate.update(sql, testResult.getTestPaper().getId(),
                testResult.getUser().getId(), testResult.getMark());
    }

    @Override
    public List query(QueryTestResult queryTestResult) {
        String sql = "SELECT * FROM test_result tr LEFT JOIN test t " +
                " on t.id = tr.t_id LEFT JOIN userInfo u on tr.u_id = u.id LEFT JOIN sort s on s.id=t.s_id " +
                queryTestResult.getQuery() + " LIMIT ?, ?";
        System.out.println(sql);
        System.out.println(queryTestResult.getParameters());
        return JdbcTemplate.query(sql, new ResultResultSetHandler(), queryTestResult.getParameters().toArray());
    }

    @Override
    public int count(QueryTestResult queryTestResult) {
        queryTestResult.setCount(true);
        String sql = "SELECT count(*) FROM test_result tr LEFT JOIN test t " +
                " on t.id = tr.t_id LEFT JOIN userInfo u on tr.u_id = u.id " + queryTestResult.getQuery();
        return JdbcTemplate.query(sql, new IResultSetHandler<Integer>() {
            @Override
            public Integer handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        }, queryTestResult.getParameters().toArray());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM test_result WHERE id=?";
        return JdbcTemplate.update(sql, id);
    }

    @Override
    public TestResult getResultById(Long id) {
        String sql = "SELECT * FROM test_result tr LEFT JOIN test t " +
            " on t.id = tr.t_id LEFT JOIN userInfo u on tr.u_id = u.id LEFT JOIN sort s on s.id=t.s_id WHERE tr.id = ?";
        System.out.println(sql);
        return JdbcTemplate.query(sql, new ResultResultSetHandler(), id).get(0);
    }

    @Override
    public int update(Long id, BigDecimal mark) {
        String sql = "UPDATE test_result SET mark = ? WHERE id = ?";
        return JdbcTemplate.update(sql, mark, id);
    }

    private class ResultResultSetHandler implements IResultSetHandler<List<TestResult>>{
        @Override
        public List<TestResult> handler(ResultSet rs) throws SQLException {
            List<TestResult> list = new ArrayList<>();
            while (rs.next()) {
                TestResult testResult = new TestResult();
                list.add(testResult);
                testResult.setId(rs.getLong("tr.id"));
                testResult.setMark(rs.getBigDecimal("mark"));
                User user = new User();
                user.setId(rs.getLong("u_id"));
                user.setName(rs.getString("u.name"));
                testResult.setUser(user);
                TestPaper testPaper = new TestPaper();
                testPaper.setId(rs.getLong("t_id"));
                testPaper.setName(rs.getString("t.name"));
                testPaper.setStartTime(rs.getTimestamp("starttime"));
                testPaper.setEndTime(rs.getTimestamp("endtime"));
                testResult.setTestPaper(testPaper);
                Sort sort = new Sort();
                sort.setId(rs.getLong("s.id"));
                sort.setName(rs.getString("s.name"));
                testPaper.setSort(sort);
            }
            return list;
        }
    }
}
