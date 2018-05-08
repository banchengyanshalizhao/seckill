package com.trendy.study.seckill.dto;

import com.trendy.study.seckill.enums.SeckillStatEnum;
import com.trendy.study.seckill.po.SuccessKilled;

/**
 * Date:2018/5/7
 * Author:LiZhao
 * Desc:封装执行秒杀后的结果:是否秒杀成功
 */
public class SeckillExecution {

    private long seckillId;

    //秒杀状态
    private int state;

    //秒杀状态描述
    private String stateInfo;

    //秒杀成功后返回秒杀成功对象
    private SuccessKilled successKilled;

    /**
     * 秒杀成功，返回所有信息
     */
    public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getInfo();
        this.successKilled = successKilled;
    }

    /**
     * 秒杀失败，返回状态信息
     */
    public SeckillExecution(long seckillId,SeckillStatEnum statEnum) {
        this.seckillId = seckillId;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getInfo();
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
