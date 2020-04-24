package com.wenruo.base;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName BaseBiz
 * @Description TODO
 * @Author MuYao
 * @Date 2mapper.2mapper./4/24 13:44
 * @Version 1.mapper.
 **/
public abstract class BaseBiz<M extends BaseMapper<T>, T>{

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    
    public int deleteByPrimaryKey(T t) {
        return mapper.deleteByPrimaryKey(t);
    }

    
    public int delete(T t) {
        return mapper.delete(t);
    }

    
    public int insert(T t) {
        return mapper.insert(t);
    }

    
    public int insertSelective(T t) {
        return mapper.insertSelective(t);
    }

    
    public boolean existsWithPrimaryKey(T t) {
        return mapper.existsWithPrimaryKey(t);
    }

    
    public List<T> selectAll() {
        return mapper.selectAll();
    }

    
    public Object selectByPrimaryKey(Object o) {
        return mapper.selectByPrimaryKey(o);
    }

    
    public int selectCount(T t) {
        return mapper.selectCount(t);
    }

    
    public List<T> select(T t) {
        return mapper.select(t);
    }

    
    public T selectOne(T t) {
        return selectOne(t);
    }

    
    public int updateByPrimaryKey(T t) {
        return mapper.updateByPrimaryKey(t);
    }

    
    public int updateByPrimaryKeySelective(T t) {
        return mapper.updateByPrimaryKeySelective(t);
    }

    
    public int deleteByCondition(T t) {
        return mapper.deleteByCondition(t);
    }

    
    public List<T> selectByCondition(T t) {
        return mapper.selectByCondition(t);
    }

    
    public int selectCountByCondition(T t) {
        return mapper.selectCountByCondition(t);
    }

    
    public int updateByCondition(T t, Object o) {
        return mapper.updateByCondition(t, o);
    }

    
    public int updateByConditionSelective(T t, Object o) {
        return mapper.updateByConditionSelective(t, o);
    }

    
    public int deleteByExample(Object o) {
        return mapper.deleteByExample(o);
    }

    
    public List<T> selectByExample(Object o) {
        return mapper.selectByExample(o);
    }

    
    public int selectCountByExample(Object o) {
        return mapper.selectCountByExample(o);
    }

    
    public T selectOneByExample(Object o) {
        return mapper.selectOneByExample(o);
    }

    
    public int updateByExample(T t, Object o) {
        return mapper.updateByExample(t, o);
    }

    
    public int updateByExampleSelective(T t, Object o) {
        return mapper.updateByExampleSelective( t, o);
    }

    
    public int deleteByIds(String s) {
        return mapper.deleteByIds(s);
    }

    
    public List<T> selectByIds(String s) {
        return mapper.selectByIds(s);
    }

    
    public int insertList(List<T> list) {
        return mapper.insertList(list);
    }

    
    public int insertUseGeneratedKeys(T t) {
        return mapper.insertUseGeneratedKeys(t);
    }
}
