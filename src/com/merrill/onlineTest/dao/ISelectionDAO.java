package com.merrill.onlineTest.dao;

import com.merrill.onlineTest.domain.Selection;

import java.util.List;

public interface ISelectionDAO {
    /**
     * 添加新的分类
     * @param selection
     */
    public int save(Selection selection);

    /**
     * 删除分类
     * @param id
     */
    public int delete(Long id);

    /**
     * 更新分类信息
     * @param selection
     */
    public int update(Selection selection);

    /**
     * 根据分类编号查找对应分类信息
     * @param id
     * @return 存在则返回该分类，否则返回null
     */
    public Selection getSortById(Long id);

    /**
     * 查询所有分类信息
     * @return 返回所有分类的集合
     */
    public List list();
}
