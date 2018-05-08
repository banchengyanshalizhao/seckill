package com.trendy.study.seckill.dao;

import com.trendy.study.seckill.po.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * Date:2018/4/27
 * Author:LiZhao
 * Desc:
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);


    /**
     * 查询秒杀成功过明细
     * @param seckillId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSecKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);


}
