package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.ISortDAO;
import com.merrill.onlineTest.domain.Sort;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SortDAOImpl implements ISortDAO {
    @Override
    public int save(Sort sort) {
        String sql = "INSERT INTO sort (name) VALUES ( ?)";
        return JdbcTemplate.update(sql, sort.getName());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM sort WHERE id = ?";
        return JdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Sort sort) {
        String sql = "UPDATE sort SET name = ? WHERE id = ?";
        return JdbcTemplate.update(sql, sort.getName(), sort.getId());
    }

    @Override
    public Sort getSortById(Long id) {
        String sql = "SELECT * FROM sort WHERE id = ?";
        List<Sort> sort = JdbcTemplate.query(sql, new SortResultSetHandler(), id);
        if (sort.size() != 0){
            return sort.get(0);
        }
        return null;
    }

    @Override
    public List list() {
        String sql = "SELECT * FROM sort";
        List<Sort> sort = JdbcTemplate.query(sql, new SortResultSetHandler());
        return sort;
    }

    private class SortResultSetHandler implements IResultSetHandler<List<Sort>>{
        @Override
        public List<Sort> handler(ResultSet rs) throws SQLException {
            List<Sort> list = new ArrayList<>();
            while (rs.next()){
                Sort sort = new Sort();
                list.add(sort);
                sort.setId(rs.getLong("id"));
                sort.setName(rs.getString("name"));
            }
            return list;
        }
    }
}
