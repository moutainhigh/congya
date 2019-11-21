package com.chauncy.web.config.bean.limit;

import com.chauncy.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import java.util.List;
import java.util.UUID;

/**
 * @Author cheng
 * @create 2019-11-20 20:05
 * 使用redis限流
 */
@Component
@Slf4j
public class RedisRaterLimiter {

    @Autowired
    private JedisPool jedisPool;

    public String acquireTokenFromBucket(String point, int limit, long timeout) {

        Jedis jedis = jedisPool.getResource();
        try{
            //UUID令牌
            String token = UUID.randomUUID().toString();
            long now = System.currentTimeMillis();
            //开启事务
            Transaction transaction = jedis.multi();

            //删除信号量 移除有序集中指定区间(score)内的所有成员 ZREMRANGEBYSCORE key min max
            transaction.zremrangeByScore((Constants.BUCKET_MONITOR + point).getBytes(), "-inf".getBytes(), String.valueOf(now - timeout).getBytes());
            //为每个有序集分别指定一个乘法因子(默认设置为 1) 每个成员的score值在传递给聚合函数之前都要先乘以该因子
            ZParams params = new ZParams();
            params.weightsByDouble(1.0, 0.0);
            //计算给定的一个或多个有序集的交集
            transaction.zinterstore(Constants.BUCKET + point, params, Constants.BUCKET + point, Constants.BUCKET_MONITOR + point);

            //计数器自增
            transaction.incr(Constants.BUCKET_COUNT);
            List<Object> results = transaction.exec();
            long counter = (Long) results.get(results.size() - 1);

            transaction = jedis.multi();
            //Zadd 将一个或多个成员元素及其分数值(score)加入到有序集当中
            transaction.zadd(Constants.BUCKET_MONITOR + point, now, token);
            transaction.zadd(Constants.BUCKET + point, counter, token);
            transaction.zrank(Constants.BUCKET + point, token);
            results = transaction.exec();
            //获取排名，判断请求是否取得了信号量
            long rank = (Long) results.get(results.size() - 1);
            if (rank < limit) {
                return token;
            } else {
                //没有获取到信号量，清理之前放入redis中垃圾数据
                transaction = jedis.multi();
                //Zrem移除有序集中的一个或多个成员
                transaction.zrem(Constants.BUCKET_MONITOR + point, token);
                transaction.zrem(Constants.BUCKET + point, token);
                transaction.exec();
            }
        }catch (Exception e){
            log.error("限流出错，请检查Redis运行状态\n"+e.toString());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return null;
    }
}