package com.merrill.onlineTest.dao;

import com.merrill.onlineTest.domain.Admin;

public interface IAdminDAO {

    /**
     * 根据账号密码检测该管理员的合法性，用于登录
     * @param id 用户账号
     * @param password 用户密码
     * @return 存在，则返回该管理员所有的信息，否则返回null
     */
    public Admin login(Long id, String password);

    /**
     * 根据该账号去寻找对应的管理员对象，管理员登录
     * @param id 传入管理员账号
     * @return 返回当前对象
     */
    public Admin getAdminById(Long id);

    /**
     * 用于管理员修改信息后保存信息
     * @param admin 传入修改后的信息
     */
    public int update(Admin admin);
}
