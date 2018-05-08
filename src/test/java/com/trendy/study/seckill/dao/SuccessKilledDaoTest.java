package com.trendy.study.seckill.dao;

import com.trendy.study.seckill.po.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Date:2018/5/7
 * Author:LiZhao
 * Desc:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled(){
        long seckillId = 1000;
        long userPhone = 1344444444;
        int number = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        System.out.println(number);
    }

    @Test
    public void queryByIdWithSecKilled(){
        long seckillId = 1000;
        long userPhone = 1344444444;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSecKilled(seckillId, userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
