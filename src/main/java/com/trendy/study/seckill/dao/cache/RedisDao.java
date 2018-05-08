package com.trendy.study.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.trendy.study.seckill.po.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Date:2018/5/8
 * Author:LiZhao
 * Desc:
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private static final String SECKILL_REDIS_KEY = "seckill:";

    private RuntimeSchema<Seckill> schema  =RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip,int port){
        jedisPool = new JedisPool(ip,port);
    }


    /**
     * 从redis中获取数据seckill
     */
    public Seckill getSeckill(long seckillId){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = SECKILL_REDIS_KEY + seckillId;
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes != null){
                    //空对象
                    Seckill seckill = schema.newMessage();
                    //反序列化
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    return  seckill;
                }
            } finally {
                if(jedis != null){
                    jedis.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }


    /**
     * 存入Seckill到redis中
     */
    public String putSeckill(Seckill seckill){
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = SECKILL_REDIS_KEY + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeOut = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeOut, bytes);
                return result;
            } finally {
                if(jedis != null){
                    jedis.close();
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }




}
