package com.merrill.onlineTest.template;

import java.sql.ResultSet;
import java.sql.SQLException;

//处理结果集的规范
public interface IResultSetHandler<T> {

    T handler(ResultSet rs) throws SQLException;
}
