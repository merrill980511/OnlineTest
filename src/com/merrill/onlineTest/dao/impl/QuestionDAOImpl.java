package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.IQuestionDAO;
import com.merrill.onlineTest.domain.Option;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.Selection;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.query.QueryQuestion;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;
import com.merrill.onlineTest.template.impl.QuestionResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuestionDAOImpl implements IQuestionDAO {

    @Override
    public int save(Question question) {
        String sql = "INSERT INTO questions(s_id, stem, answer, selection) VALUES(?, ?, ?, ?) ";
        int num = 0;
        num = JdbcTemplate.update(sql, question.getSort().getId(), question.getStem(),
                question.getAnswer(), question.getSelection().getId());
        sql = "SELECT MAX(id) FROM questions";
        Long id = JdbcTemplate.query(sql, new IResultSetHandler<Long>() {
            @Override
            public Long handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getLong(1);
                }
                return 0L;
            }
        });
        if (id != 0){
            sql = "INSERT INTO options(item, content, q_id) VALUES(?, ?, ?)";
            for (Iterator<Option> it = question.getOptions().iterator(); it.hasNext();){
                Option option = it.next();
                JdbcTemplate.update(sql, option.getItem(), option.getContent(), id);
            }
        }
        return num;
    }

    @Override
    public int update(Question question) {
        String sql = "UPDATE questions SET s_id = ?, selection = ?, stem = ?, answer = ? WHERE id = ?";
        int num = 0;
        num = JdbcTemplate.update(sql, question.getSort().getId(), question.getSelection().getId(), question.getStem(),
                question.getAnswer(), question.getId());
        sql = "DELETE FROM options WHERE q_id = ?";
        JdbcTemplate.update(sql, question.getId());
        sql = "INSERT INTO options(item, content, q_id) VALUES(?, ?, ?)";
        for (Iterator<Option> it = question.getOptions().iterator(); it.hasNext();){
            Option option = it.next();
            JdbcTemplate.update(sql, option.getItem(), option.getContent(), question.getId());
        }
        return num;
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM options WHERE q_id=?";
        JdbcTemplate.update(sql, id);
        sql = "DELETE FROM questions WHERE id=?";
        return JdbcTemplate.update(sql, id);
    }

    @Override
    public Question getQuestionById(Long id) {
        String sql = "SELECT * FROM questions q LEFT JOIN sort s on q.s_id=s.id LEFT JOIN " +
                " selection se on se.id=q.selection LEFT JOIN options o on o.q_id=q.id where q.id = ?";
        System.out.println(sql);
        List<Question> questions = JdbcTemplate.query(sql, new QuestionResultSetHandler(), id);
        if (questions.size() != 0){
            return questions.get(0);
        }
        return null;
    }

    @Override
    public List list() {
        String sql = "SELECT * FROM questions q LEFT JOIN sort s on q.s_id=s.id LEFT JOIN " +
                " selection se on se.id=q.selection LEFT JOIN options o on o.q_id=q.id ";
        List<Question> questions = JdbcTemplate.query(sql, new QuestionResultSetHandler());
        return questions;
    }

    @Override
    public int questionCount(QueryQuestion queryQuestion) {
        queryQuestion.setCount(true);
        String sql = "SELECT COUNT(*) FROM questions q LEFT JOIN sort s on q.s_id=s.id " +
                "LEFT JOIN selection se on q.selection = se.id " + queryQuestion.getQuery();
        System.out.println("question" + sql);
        return JdbcTemplate.query(sql, new IResultSetHandler<Integer>() {
            @Override
            public Integer handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        }, queryQuestion.getParameters().toArray());
    }

    @Override
    public List<Question> queryQuestion(QueryQuestion queryQuestion) {
        String sql = "SELECT * FROM questions q LEFT JOIN sort s on q.s_id=s.id LEFT JOIN " +
                "selection se on se.id=q.selection " +
                queryQuestion.getQuery() + "LIMIT ?, ?";
        List<Question> questions = JdbcTemplate.query(sql, new QueryQuestionResultSetHandler(), queryQuestion.getParameters().toArray());
        return questions;
    }

    @Override
    public List<Long> generateById(Long sortId, Long id) {
        String sql = "SELECT * FROM questions WHERE s_id = ? AND selection = ?";
        return JdbcTemplate.query(sql, new IResultSetHandler<List<Long>>() {
            @Override
            public List<Long> handler(ResultSet rs) throws SQLException {
                List<Long> list = new ArrayList<>();
                while (rs.next()){
                    list.add(rs.getLong("id"));
                }
                return list;
            }
        },sortId, id);
    }

    private class QueryQuestionResultSetHandler implements IResultSetHandler<List<Question>>{
        @Override
        public List<Question> handler(ResultSet rs) throws SQLException {
            List<Question> list = new ArrayList<>();
            while (rs.next()){
                Question question = new Question();
                list.add(question);
                question.setId(rs.getLong("id"));
                question.setStem(rs.getString("stem"));
                Sort sort = new Sort();
                sort.setId(rs.getLong("s.id"));
                sort.setName(rs.getString("s.name"));

                Selection selection = new Selection();
                selection.setId(rs.getLong("se.id"));
                selection.setName(rs.getString("se.name"));
                question.setSort(sort);
                question.setSelection(selection);
                question.setAnswer(rs.getString("answer"));
            }
            return list;

        }
    }

//    @Override
//    public int optionCount(QueryQuestion queryQuestion) {
//        queryQuestion.setCount(true);
//        String sql = "SELECT COUNT(*) FROM questions q LEFT JOIN options o on q.id=o.q_id " + queryQuestion.getQuery();
//        return JdbcTemplate.query(sql, new IResultSetHandler<Integer>() {
//            @Override
//            public Integer handler(ResultSet rs) throws SQLException {
//                if (rs.next()){
//                    return rs.getInt(1);
//                }
//                return 0;
//            }
//        }, queryQuestion.getParameters().toArray());
//    }
//    private class QuestionResultSetHandler implements IResultSetHandler<List<Question>>{
//        @Override
//        public List<Question> handler(ResultSet rs) throws SQLException {
//            List<Question> list = new ArrayList<>();
//            Set<Option> options = new HashSet<>();
//            Long id = new Long("-1");
//            while (rs.next()){
//                if (rs.getLong("q_id") != id){
//                    Question question = new Question();
//                    list.add(question);
//                    question.setId(rs.getLong("id"));
//                    question.setStem(rs.getString("stem"));
//                    Sort sort = new Sort();
//                    sort.setId(rs.getLong("s.id"));
//                    sort.setName(rs.getString("name"));
//                    question.setSort(sort);
//                    question.setAnswer(rs.getString("answer"));
//                    Option option = new Option();
//                    options = new HashSet<>();
//                    question.setOptions(options);
//                    id = rs.getLong("id");
//                    option.setId(rs.getLong("o.id"));
//                    option.setItem(rs.getString("item"));
//                    option.setContent(rs.getString("content"));
//                    options.add(option);
//                } else {
//                    Option option = new Option();
//                    id = rs.getLong("id");
//                    option.setId(rs.getLong("o.id"));
//                    option.setItem(rs.getString("item"));
//                    option.setContent(rs.getString("content"));
//                    options.add(option);
//                }
//
//            }
//            return list;‘
//        }
//    }
}
