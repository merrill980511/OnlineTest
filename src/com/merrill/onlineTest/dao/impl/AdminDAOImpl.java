package com.merrill.onlineTest.dao.impl;

import com.merrill.onlineTest.dao.IAdminDAO;
import com.merrill.onlineTest.domain.Admin;
import com.merrill.onlineTest.template.IResultSetHandler;
import com.merrill.onlineTest.template.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements IAdminDAO {
    @Override
    public Admin login(Long id, String password) {
        String sql = "SELECT * FROM adminInfo WHERE id = ?";
        Admin admin = JdbcTemplate.query(sql, new AdminResultSetHandler(), id);
        if (admin != null && admin.getPassword().equals(password)){
            return admin;
        }
        return null;
    }

    @Override
    public Admin getAdminById(Long id) {
        String sql = "SELECT * FROM adminInfo WHERE id = ?";
        Admin admin = JdbcTemplate.query(sql, new AdminResultSetHandler(), id);
        return admin;
    }

    @Override
    public int update(Admin admin) {
        String sql = "UPDATE adminInfo SET password = ?, name = ?, sex = ?, phone = ?," +
                " email = ?, remark = ? WHERE id=?";
        return JdbcTemplate.update(sql, admin.getPassword(), admin.getName(), admin.getSex(),
                admin.getPhone(), admin.getEmail(), admin.getRemark(), admin.getId());
    }

    private class AdminResultSetHandler implements IResultSetHandler<Admin> {
        @Override
        public Admin handler(ResultSet rs) throws SQLException {
            if (rs.next()){
                Admin admin = new Admin();
                admin.setId(rs.getLong("id"));
                admin.setPassword(rs.getString("password"));
                admin.setName(rs.getString("name"));
                admin.setSex(rs.getString("sex"));
                admin.setPhone(rs.getString("phone"));
                admin.setEmail(rs.getString("email"));
                admin.setRemark(rs.getString("remark"));
                return admin;
            }
            return null;
        }
    }
}
