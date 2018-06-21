package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.ISelectionDAO;
import com.merrill.onlineTest.domain.Selection;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectionDAOImpl implements ISelectionDAO {
    @Override
    public int save(Selection selection) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public int update(Selection selection) {
        return 0;
    }

    @Override
    public Selection getSortById(Long id) {
        return null;
    }

    @Override
    public List list() {
        String sql = "SELECT * FROM selection";
        List<Selection> selections = JdbcTemplate.query(sql, new SelectionResultHandler());
        return selections;
    }

    private class SelectionResultHandler implements IResultSetHandler<List<Selection>> {
        @Override
        public List<Selection> handler(ResultSet rs) throws SQLException {
            List<Selection> list = new ArrayList<>();
            while (rs.next()){
                Selection selection = new Selection();
                list.add(selection);
                selection.setId(rs.getLong("id"));
                selection.setName(rs.getString("name"));
            }
            return list;
        }
    }
}
