package com.wenruo.base;

import com.wenruo.common.Query;
import com.wenruo.common.Result;
import com.wenruo.constant.MsgConstansts;
import com.wenruo.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseController
 * @Description controller公用父类
 * @Author MuYao
 * @Date 2020/4/24 13:56
 * @Version 1.0
 **/
public class BaseController<Biz extends BaseBiz, Entity> {

    @Autowired
    protected Biz baseBiz;


    /**
     * 新增一条数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public Result<Entity> add(@RequestBody Entity entity){
        baseBiz.insertSelective(entity);
        return ResultUtils.success(MsgConstansts.OPERATE_SUCCESS);
    }

    /**
     * 更新一条数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.PUT)
    @ResponseBody
    public Result<Entity> update(@RequestBody Entity entity){
        baseBiz.updateSelectiveById(entity);
        return ResultUtils.success(MsgConstansts.OPERATE_SUCCESS);
    }

    /**
     * 更新多条数据
     * @param entities
     * @return
     */
    @RequestMapping(value = "/batchUpdate",method = RequestMethod.PUT)
    @ResponseBody
    public Result<List<Entity>> batchUpdate(@RequestBody List<Entity> entities){
        baseBiz.updateList(entities);
        return ResultUtils.success(MsgConstansts.OPERATE_SUCCESS);
    }

    /**
     * 插入多条数据
     * @param entities
     * @return
     */
    @RequestMapping(value = "/batchInsert",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> batchInsert(@RequestBody List<Entity> entities){
        baseBiz.insertList(entities);
        return ResultUtils.success(MsgConstansts.OPERATE_SUCCESS);
    }

    /**
     * 删除多条数据
     * @param ids
     * @return
     */
    @ResponseBody
    public Result<Entity> remove(@RequestBody Integer[] ids){
        baseBiz.deleteByIds(ids);
        return ResultUtils.success(MsgConstansts.OPERATE_SUCCESS);
    }

    /**
     * 删除单条数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Entity> remove(@PathVariable Integer id){
        baseBiz.deleteById(id);
        return ResultUtils.success(MsgConstansts.OPERATE_SUCCESS);
    }

    /**
     * 获取全部数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Entity>> all(@RequestParam(required = false) Map<String, Object> params){
        Query query = new Query(params);
        return ResultUtils.successWithData(baseBiz.selectByQuery(query));
    }

    /**
     * 分页查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Entity>> page(@RequestParam(required = false) Map<String, Object> params){
        Query query = new Query(params);
        return baseBiz.selectPageByQuery(query);
    }

    /**
     * 查询一条数据
     * @param params
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public Result<Entity> selectOne(@RequestParam(required = false) Map<String, Object> params){
        Query query = new Query(params);
        return ResultUtils.successWithData((Entity) baseBiz.selectOne(query));
    }

    /**
     * 根据{id}获取一条数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Result<Entity> get(@PathVariable Integer id){
        Object entity = baseBiz.selectById(id);
        return ResultUtils.successWithData((Entity)entity);
    }
}
