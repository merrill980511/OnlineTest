package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.IUserDAO;
import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.QueryUser;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {

    public int count(QueryUser queryUser){
        queryUser.setCount(true);
        String sql = "SELECT COUNT(*) FROM userInfo " + queryUser.getQuery();
        return JdbcTemplate.query(sql, new IResultSetHandler<Integer>() {
            @Override
            public Integer handler(ResultSet rs) throws SQLException {
                if (rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
        }, queryUser.getParameters().toArray());
    }

    @Override
    public User login(Long id, String password) {
        String sql = "SELECT * FROM userInfo WHERE id = ?";
        List<User> user = JdbcTemplate.query(sql, new UserResultSetHandler(), id);
        if (user.size() != 0 && user.get(0).getPassword().equals(password)){
            return user.get(0);
        }
        return null;
    }

    @Override
    public User getUserById(Long id) {
        String sql = "SELECT * FROM userInfo WHERE id = ?";
        List<User> user = JdbcTemplate.query(sql, new UserResultSetHandler(), id);
        if (user.size() != 0){
            return user.get(0);
        }
        return null;
    }

    @Override
    public List query(QueryUser queryUser) {
        String sql = "SELECT * FROM userInfo " + queryUser.getQuery() + "LIMIT ?, ?";
        System.out.println(sql);
        System.out.println(queryUser.getParameters());
        List<User> user = JdbcTemplate.query(sql, new UserResultSetHandler(), queryUser.getParameters().toArray());
        return user;
    }

    @Override
    public int save(User user) {
        String sql = "INSERT INTO userInfo (id, password, name, sex, phone, qq, email, remark) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        return JdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getName(), user.getSex(),
                user.getPhone(), user.getQq(), user.getEmail(), user.getRemark());
    }

    @Override
    public int update(User user) {
        String sql = "UPDATE userInfo SET password = ?, name = ?, sex = ?, phone = ?," +
                " qq = ?, email = ?, remark = ? WHERE id=?";
        return JdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getSex(),
                user.getPhone(), user.getQq(), user.getEmail(), user.getRemark(), user.getId());
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM userInfo WHERE id=?";
        return JdbcTemplate.update(sql, id);
    }

    private class UserResultSetHandler implements IResultSetHandler<List<User>>{

        @Override
        public List<User> handler(ResultSet rs) throws SQLException {
            List<User> list = new ArrayList<>();
            while (rs.next()){
                User user = new User();
                list.add(user);
                user.setId(rs.getLong("id"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setSex(rs.getString("sex"));
                user.setPhone(rs.getString("phone"));
                user.setQq(rs.getString("qq"));
                user.setEmail(rs.getString("email"));
                user.setRemark(rs.getString("remark"));
            }
            return list;
        }
    }
}
