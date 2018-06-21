package com.merrill.onlineTest.dao;

import com.merrill.onlineTest.domain.User;
import com.merrill.onlineTest.query.QueryUser;

import java.util.List;

public interface IUserDAO {

    /**
     * 根据账号密码检测该用户的合法性，用于登录
     * @param id 用户账号
     * @param password 用户密码
     * @return 存在，则返回该用户所有的信息，否则返回null
     */
    public User login(Long id, String password);

    /**
     * 根据该账号去寻找对应的用户对象，查看用户详细信息
     * @param id 传入的用户账号
     * @return 返回当前对象
     */
    public User getUserById(Long id);

    /**
     * 查询用户列表信息，用于查询多个用户信息。
     * @param queryUser 传入查询的信息
     * @return 返回查询结果的学生列表
     */
    public List query(QueryUser queryUser);

    /**
     * 保存注册信息，添加用户
     * @param user
     */
    public int save(User user);

    /**
     * 更新用户信息
     * @param user 传入新的用户信息
     */
    public int update(User user);

    /**
     * 根据账号删除该用户
     * @param id 用户的账号
     */
    public int delete(Long id);

    /**
     * 总数据条数
     * @return
     */
    public int count(QueryUser queryUser);
}
