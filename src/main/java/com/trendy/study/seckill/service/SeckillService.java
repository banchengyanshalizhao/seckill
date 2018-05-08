package com.trendy.study.seckill.service;

import com.trendy.study.seckill.dto.Exposer;
import com.trendy.study.seckill.dto.SeckillExecution;
import com.trendy.study.seckill.exception.RepeatKillException;
import com.trendy.study.seckill.exception.SeckillCloseException;
import com.trendy.study.seckill.exception.SeckillException;
import com.trendy.study.seckill.po.Seckill;

import java.util.List;

/**
 * Date:2018/5/7
 * Author:LiZhao
 * Desc:
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> querySeckillList();

    /**
     * 根据id查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);


    /**
     * 根据id暴露秒杀地址，否则输出系统时间和秒杀时间
     * @param seckillId
     * @return
     */
    Exposer exportSeckillUrl(long seckillId);


    /**
     * 执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     */
    SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) throws SeckillException,RepeatKillException,SeckillCloseException;
}
