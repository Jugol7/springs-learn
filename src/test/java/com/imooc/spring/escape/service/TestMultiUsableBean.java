package com.imooc.spring.escape.service;

import com.imooc.spring.escape.multi_usable_bean.ITemplateManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Autowired: Spring提供
 * 1. 默认根据类型匹配，多个情况下，根据名称匹配，找不到则报错
 * 2. 与@Qualifier一起使用，找到唯一的名称的bean
 * @Resource：JDK提供
 * 1. 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
 * 2. 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
 * 3. 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
 * 4. 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配
 *
 * @author zlp
 * @date 2022/11/15
 */
@SuppressWarnings("all")
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMultiUsableBean {

    //    @Autowired
//    @Qualifier("qinyiRedisTemplate")
//    @Resource
//    private RedisTemplate redisTemplate;
    @Autowired
    @Qualifier("qinyiRedisTemplate")
    private RedisTemplate imoocRedisTemplate;

    @Autowired
    @Qualifier("qinyiTemplateManagerService")
    private ITemplateManagerService templateManagerService;

    @Test
    public void testAutowire() {

        assert imoocRedisTemplate != null;
        imoocRedisTemplate.getConnectionFactory().getConnection().flushAll();

        imoocRedisTemplate.opsForValue().set("name", "qinyi");
    }

    @Test
    public void testTemplateManagerService() {

//        assert templateManagerService == null;

        templateManagerService.print();
    }

    @Test
    public void testUseRedisPipeline() {

        // 清空 Redis 服务器中的数据, 方便校验测试
        imoocRedisTemplate.getConnectionFactory().getConnection().flushAll();

        List<Object> resultList = imoocRedisTemplate.executePipelined(
                new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection)
                            throws DataAccessException {

                        // 1. 通过 connection 打开 pipeline
                        connection.openPipeline();

                        // 2. 给本次 pipeline 添加一次性要执行的多条命令
                        // 2.1 一个 set key value 的操作
                        byte[] key = "name".getBytes();
                        byte[] value = "qinyi".getBytes();
                        connection.set(key, value);

                        // 2.2 执行一个错误的命令
                        connection.lPop("xyzabc".getBytes());

                        // 2.3 mset 操作
                        Map<byte[], byte[]> tuple = new HashMap<>();
                        tuple.put("id".getBytes(), "1".getBytes());
                        tuple.put("age".getBytes(), "19".getBytes());
                        connection.mSet(tuple);

                        // 3. 关闭 pipeline
//                        connection.closePipeline();

                        return null;
                    }
                }
        );

        resultList.forEach(System.out::println);
    }
}
