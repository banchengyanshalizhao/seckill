package com.trendy.study.seckill.dao;

import com.trendy.study.seckill.po.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Date:2018/4/27
 * Author:LiZhao
 * Desc:
 */
public interface SeckillDao {

    /**
     * 根据偏移量查询秒杀商品列表
     * @param off
     * @param limit
     * @return
     */
    public List<Seckill> queryAll(@Param("off") int off, @Param("limit") int limit);


    /**
     * 根据id查询秒杀商品
     * @param seckillId
     * @return
     */
    public Seckill queryById(long seckillId);

    /**
     * 减库存
     * 如果int > 1表示更新库存成功
     * @param seckillId
     * @param killTime
     * @return
     */
    public int reduceNumber(@Param("seckillId") long seckillId,@Param("killTime") Date killTime);

}
