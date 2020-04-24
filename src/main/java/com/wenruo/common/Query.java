package com.wenruo.common;


import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 */
@Data
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/** 默认页码 */
    private int curPage = 1;
    /** 默认每页条数 */
    private int pageSize = 10;

    public Query() {
    }

    public Query(Map<String, Object> params){
        this.putAll(params);
        //分页参数
        if(params.get("curPage") != null) {
            this.curPage = Integer.parseInt(params.get("curPage").toString());
        }
        if(params.get("pageSize") != null) {
            this.pageSize = Integer.parseInt(params.get("pageSize").toString());
        }
        this.remove("curPage");
        this.remove("pageSize");
    }


}
