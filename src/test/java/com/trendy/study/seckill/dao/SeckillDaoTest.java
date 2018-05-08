package com.trendy.study.seckill.dao;

import com.trendy.study.seckill.po.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Date:2018/5/4
 * Author:LiZhao
 * Desc:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring/applicationContext-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        int number = seckillDao.reduceNumber(1000, new Date());
        System.out.println(number);
    }

    @Test
    public void queryById() throws Exception {
        Seckill seckill = seckillDao.queryById(1000);
        System.out.println(seckill);

    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckills = seckillDao.queryAll(0, 2);
        for (Seckill seckill : seckills ){
            System.out.println(seckill);
        }
    }
}
