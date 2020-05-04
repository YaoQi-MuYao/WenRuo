package com.wenruo.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wenruo.common.Query;
import com.wenruo.common.Result;
import com.wenruo.utils.ProReflectionUtils;
import com.wenruo.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * @ClassName BaseBiz
 * @Description TODO
 * @Author MuYao
 * @Date 2mapper.2mapper./4/24 13:44
 * @Version 1.mapper.
 **/
@Slf4j
public abstract class BaseBiz<M extends BaseMapper<T>, T>{

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    private Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * 普通带参数查询
     * @param query
     * @return
     */
    public List<T> selectByQuery(Query query) {
        Example example = getExample(query);
        List<T> list = mapper.selectByExample(example);
        return list;
    }

    /**
     * 带参数分页查询
     * @param query
     * @return
     */
    public Result<List<T>> selectPageByQuery(Query query) {
        long startTime1 = System.currentTimeMillis();
        Example example = getExample(query);
        long endTime1 = System.currentTimeMillis();
        log.info("组装exampl使用：" + (endTime1 - startTime1) + "ms");
        Page<Object> result = PageHelper.startPage(query.getCurPage(), query.getPageSize());
        List<T> list = mapper.selectByExample(example);
        endTime1 = System.currentTimeMillis();
        log.info("查询使用：" + (endTime1 - startTime1) + "ms");
        Result<List<T>>  returnList = ResultUtils.successWithData(list, result.getTotal(), result.getPageNum(), result.getPageSize());
        endTime1 = System.currentTimeMillis();
        log.info("代码运行时间：" + (endTime1 - startTime1) + "ms");
        return returnList;
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int delete(T entity) {
        int refLine = mapper.delete(entity);
        return refLine;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert(T entity) {
        int refLine = mapper.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insertSelective(T entity) {
        int refLine = mapper.insertSelective(entity);
    }

    
    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }

    
    public T selectOne(T entity) {
        return (T) mapper.selectOne(entity);
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

    
    public T selectOneByExample(Example example) {
        return mapper.selectOneByExample(example);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateByExample(T entity, Example example) {
        return mapper.updateByExample(entity, example);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int updateByExampleSelective(T entity, Example example) {
        return mapper.updateByExampleSelective( entity, example);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Integer deleteById(Integer id) {
        int refLine = mapper.deleteByPrimaryKey(id);
        return refLine;
    }

    
    public Integer deleteByIds(Integer[] ids) {
        Class<T> entityClass = getEntityClass();
        Example example = new Example(entityClass);
        example.createCriteria().andIn("id", Arrays.stream(ids).collect(toList()));
        int count = mapper.deleteByExample(example);
        return count;
    }


    public T selectById(Integer id) {
        return (T) mapper.selectByPrimaryKey(id);
    }

    public List<T> selectByIds(String ids) {
        Example example = new Example(getEntityClass());
        example.createCriteria().andIn("id", Arrays.stream(ids.split(",")).collect(toList()));
        return mapper.selectByExample(example);
    }

    public List<T> selectByIds(Collection ids) {
        Example example = new Example(getEntityClass());
        example.createCriteria().andIn("id", ids);
        return mapper.selectByExample(example);
    }

    public List<T> selectByIds(Integer[] ids) {
        Example example = new Example(getEntityClass());
        example.createCriteria().andIn("id", Arrays.stream(ids).collect(toList()));
        return mapper.selectByExample(example);
    }

    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateById(T entity) {
        int refLine = mapper.updateByPrimaryKey(entity);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void updateSelectiveById(T entity) {
        int refLine = mapper.updateByPrimaryKeySelective(entity);
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public List<T> updateList(List<T> list) {
        for (T t : list) {
            mapper.updateByPrimaryKeySelective(t);
        }
        return list;
    }

    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    
    public List<T> insertList(List<T> list) {
        mapper.insertList(list);
        return list;
    }

    public Example getExample(Query query) {
        Class<T> clazz = getEntityClass();
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        if (query.entrySet().size() > 0) {
            String field = null;
            for (Map.Entry<String, Object> entry : query.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (ObjectUtils.isEmpty(value) || key.contains("searchType") || key.contains("searchField")) {
                    continue;
                }
                if (key.contains("sort")) {
                    //排序跳过
                    continue;
                }
                if (key.contains("OrLike")) {
                    //查询orLike条件,用 字段名称 + orLike 传参
                    field = key.substring(0, key.indexOf("OrLike"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        criteria.orLike(field, "%" + entry.getValue().toString() + "%");
                    }
                } else if (key.contains("Like")) {
                    //查询orLike条件,用 字段名称 + Like 传参
                    field = key.substring(0, key.indexOf("Like"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        criteria.andLike(field, "%" + entry.getValue().toString() + "%");
                    }
                } else if (key.contains("Mult")) {
                    //查询In条件，用“，”号分隔字符串
                    field = key.substring(0, key.indexOf("Mult"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        String[] split = value.toString().split(",");
                        criteria.andIn(field, Arrays.asList(split));
                    }
                } else if (key.contains("NotIn")) {
                    //查询NotIn条件,用“，”号分隔字符串
                    field = key.substring(0, key.indexOf("NotIn"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        String[] split = value.toString().split(",");
                        criteria.andNotIn(field, Arrays.asList(split));
                    }
                } else if (key.contains("Not")) {
                    //查询Not条件,用“，”号分隔字符串
                    field = key.substring(0, key.indexOf("Not"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        criteria.andNotEqualTo(field, value);
                    }
                } else if (key.contains("Greater")) {
                    //查询 时间 > 条件
                    field = key.substring(0, key.indexOf("Greater"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        criteria.andGreaterThan(field, entry.getValue());
                    }
                } else if (key.contains("Less")) {
                    //查询 时间 < 条件
                    field = key.substring(0, key.indexOf("Less"));
                    if (ProReflectionUtils.hasField(clazz, field)) {
                        criteria.andLessThan(field, entry.getValue());
                    }
                } else {
                    if (ProReflectionUtils.hasField(clazz, key)) {
                        criteria.andEqualTo(key, value);
                    }
                }
            }
        }
        return example;
    }
}
