package com.trendy.study.seckill.service;

import com.trendy.study.seckill.dto.Exposer;
import com.trendy.study.seckill.dto.SeckillExecution;
import com.trendy.study.seckill.exception.RepeatKillException;
import com.trendy.study.seckill.exception.SeckillCloseException;
import com.trendy.study.seckill.po.Seckill;
import com.trendy.study.seckill.service.impl.SeckillServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Date:2018/5/7
 * Author:LiZhao
 * Desc:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml",
        "classpath:spring/applicationContext-service.xml"})
public class SeckillServiceTest {
    
    @Autowired
    private SeckillService seckillService;
    
    @Test
    public void querySeckillList(){
        List<Seckill> seckills = seckillService.querySeckillList();
        for (Seckill seckill :seckills){
            System.out.println(seckill);
        }
    }

    @Test
    public void queryById(){
        Seckill seckill = seckillService.queryById(1000);
        System.out.println(seckill);
    }

    @Test
    public void exportSeckillUrl(){
        Exposer exposer = seckillService.exportSeckillUrl(1000);
        System.out.println(exposer);

    }

    @Test
    public void executeSeckill(){
        long seckillId = 1000;
        long userPhone = 13555555555L;
        SeckillServiceImpl service = new SeckillServiceImpl();
        String md5 = service.getMd5(1000);
        SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
        System.out.println(seckillExecution);
    }

    @Test
    public void executeSeckill2(){

        long seckillId = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            long userPhone = 13455555552L;
            SeckillServiceImpl service = new SeckillServiceImpl();
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
                System.out.println(seckillExecution);
            } catch (RepeatKillException e) {
                e.printStackTrace();
            }catch (SeckillCloseException e1){
                e1.printStackTrace();
            }
        }else {
            System.out.println(exposer + "秒杀没有开启");
        }

    }


}
