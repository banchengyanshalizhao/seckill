package com.trendy.study.seckill.service.impl;

import com.trendy.study.seckill.dao.SeckillDao;
import com.trendy.study.seckill.dao.SuccessKilledDao;
import com.trendy.study.seckill.dao.cache.RedisDao;
import com.trendy.study.seckill.dto.Exposer;
import com.trendy.study.seckill.dto.SeckillExecution;
import com.trendy.study.seckill.enums.SeckillStatEnum;
import com.trendy.study.seckill.exception.RepeatKillException;
import com.trendy.study.seckill.exception.SeckillCloseException;
import com.trendy.study.seckill.exception.SeckillException;
import com.trendy.study.seckill.po.Seckill;
import com.trendy.study.seckill.po.SuccessKilled;
import com.trendy.study.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.List;

/**
 * Date:2018/5/7
 * Author:LiZhao
 * Desc:
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    //日志输出对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //md5加盐
    String salt = "jkl7890489oqq%^&*()_$%^&*()_SDFGHJCVBNMTYUIO|&%#@!@$&";

    public List<Seckill> querySeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill queryById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    /**
     * 暴露秒杀地址url
     */
    public Exposer exportSeckillUrl(long seckillId) {

        //访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (seckill == null) {
            //访问数据库
            seckill = seckillDao.queryById(seckillId);
            if(seckill == null){
                return new Exposer(false, seckillId);
            }else {
                //放入redis中
               redisDao.putSeckill(seckill);
            }

        }

        //秒杀没有开启或者秒杀结束
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (startTime.getTime() > nowTime.getTime() || endTime.getTime() < nowTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        //秒杀开启
        String md5 = this.getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    /**
     * 执行秒杀
     * 使用注解控制事务的优点：
     * 1.开发团队达成一致约定，明确标注事务方法的编程风格。
     * 2.保证事务方法的执行时间尽可能短，不要穿插启其他的网络操作RPC/HTTTP请求或者剥离到事务方法外部
     * 3.不是所有的方法都需要事务，如只有一条修改操作，只读操作不需要事务控制。
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        if (md5 == null || !md5.equals(this.getMd5(seckillId))) {
            throw new SeckillException("秒杀数据被重写了");
        }

        //执行秒杀逻辑，减库存
        Date nowTime = new Date();
        try {
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("重复秒杀");
            } else {
                //更新了库存，插入库存明细
                //减库存
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存，说明秒杀已经结束
                    throw new SeckillCloseException("秒杀已经结束");
                } else {
                    //秒杀成功，返回秒杀结果对象
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSecKilled(seckillId, userPhone);
                    successKilled.setState(1);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SeckillException("系统异常" + e.getMessage());
        }

    }

    /**
     * 获得秒杀md5
     */
    public String getMd5(long seckillId) {
        String base = seckillId + "/" + salt;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }


}
