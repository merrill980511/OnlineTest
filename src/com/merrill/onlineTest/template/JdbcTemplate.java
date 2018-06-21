package com.merrill.onlineTest.template;

import com.merrill.onlineTest.utils.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcTemplate {

    //DML操作模板
    public static int update(String sql, Object... params){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConn();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, ps, null);
        }
        return 0;
    }

    //查询操作模板
    public static <T>T query(String sql, IResultSetHandler<T> rsh, Object... params){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConn();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i+1, params[i]);
            }
            rs = ps.executeQuery();
            return rsh.handler(rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, ps, rs);
        }
        return null;
    }

//    public static Long getMaxId(String sql){
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn = JdbcUtil.getConn();
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            rs.next();
//            return rs.getLong(1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtil.close(conn, ps, rs);
//        }
//        return new Long(0);
//    }
}
