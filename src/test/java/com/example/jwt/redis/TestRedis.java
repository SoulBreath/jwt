package com.example.jwt.redis;

import com.example.jwt.modules.user.entity.SysUserEntity;
import com.example.jwt.common.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Redis单元测试
 *
 * @author xuyang
 * @version 1.0
 * @date 2019/5/9 11:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void testString() throws IOException {
        SysUserEntity user1 = new SysUserEntity();
        user1.setId(1L);
        user1.setMobile("11111111111");
        user1.setPassword("999999");
        user1.setNickname("jack");
        SysUserEntity user2 = new SysUserEntity();
        user2.setId(2L);
        user2.setMobile("22222222222");
        user2.setPassword("666666");
        user2.setNickname("lucy");

        List<SysUserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        redisUtil.set("users", users);

        List<SysUserEntity> str = (List<SysUserEntity>) redisUtil.get("users");
        System.out.println(str);
    }
}