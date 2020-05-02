package com.wenruo;

import com.wenruo.entity.BaseUser;
import com.wenruo.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @description: 测试
 * @author: MuYao
 * @date: Created in 2020/5/2 13:01
 * @version: 1.0.0
 */
@SpringBootTest
public class RedisTest {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisUtils redisUtils;

    @Test
    public void redisTest() {

        BaseUser baseUser = new BaseUser();
        baseUser.setId(1);
        baseUser.setName("木尧");

        /*
           redisTemplate 操作不同的数据类型，api和我们的指令是一样的
           opsForValue   操作字符串 类似String
           opsForList    操作List 类似List
           opsFotSet     操作Set
           opsForHash    操作Hash
           opsForZSet    操作ZSet

         */
        redisUtils.set("baseUser", baseUser);
//        redisTemplate.opsForValue().set("baseUser", baseUser);
//        System.out.println(redisTemplate.opsForValue().get("baseUser"));
        System.out.println(redisUtils.get("baseUser"));
    }
}
