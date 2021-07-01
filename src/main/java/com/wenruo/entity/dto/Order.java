package com.wenruo.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @description: 数据实体
 * @author: MuYao
 * @date: Created in 2020/6/19 16:31
 * @version: 1.0.0
 */
@Data
@Accessors(chain = true)
public class Order {

    /* 主键ID */
    @Id
    private Integer id;

    /* 地区号 */
    private String areaNo;

    /* 时间戳 */
    private String localDateTime;

    /* 统计周期表示: 小时：1 yyyyMMddHH、天：2 yyyyMMdd、月：3 yyyyMM*/
    private Integer logo;

    /* 总交易量 */
    private BigDecimal countDeal;

    /* 峰值交易量 */
    private BigDecimal maxDeal;

    /* 平均交易金额 */
    private BigDecimal avgDelMoney;
}
