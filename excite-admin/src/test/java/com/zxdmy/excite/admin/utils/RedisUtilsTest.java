package com.zxdmy.excite.admin.utils;


import com.zxdmy.excite.framework.config.ExciteConfig;
import com.zxdmy.excite.framework.service.RedisService;
import com.zxdmy.excite.system.entity.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * Redis使用测试类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-30 0030 20:14
 */
@SpringBootTest
class RedisUtilsTest {

    @Autowired
    private RedisService redisUtils;

    @Autowired
    private ExciteConfig exciteConfig;

    @Test
    void MyConfigTest() {
        System.out.println(exciteConfig.getName());
    }

    @Test
    void redisTest() {
        // 写入Redis：普通测试
        redisUtils.set(UUID.randomUUID().toString(), UUID.randomUUID());
        // 写入redis：普通测试，带过期时间
        redisUtils.set(UUID.randomUUID().toString(), UUID.randomUUID(), 100L);

        // 写入Redis：永久的值
        redisUtils.set("10000", "这个值是永久的");
        // 写入Redis：有过期时间
        redisUtils.set("10001", "这个值是有过期时间的，过期时间120S", 120L);

        // 获取存在的值
        String name = (String) redisUtils.get("10000");
        System.out.println(name);  // 输出：这个值是永久的
        // 获取不存在的值
        String name2 = (String) redisUtils.get("sdfdsfsdfsdfdsf");
        System.out.println(name2);  //输出：null

        // 获取过期时间：永久
        Long time = redisUtils.getExpire("10000");
        System.out.println(time);  // 输出：-1
        // 获取过期时间：定时
        Long time2 = redisUtils.getExpire("10001");
        System.out.println(time2); // 输出：（一个正数）
        // 获取过期时间：不存在
        Long time3 = redisUtils.getExpire("534564564564");
        System.out.println(time3); // 输出：-2

        // 测试存在
        if (redisUtils.hasKey("10001")) {
            System.out.println("10001存在");
        }
        // 测试不存在
        if (redisUtils.hasKey("11111")) {
            System.out.println("11111存在");
        } else {
            System.out.println("11111不存在");  // 输出：11111不存在
        }

        // 测试实体
        SysUser user = new SysUser();
        user.setId(10006);
        user.setNickname("这是昵称");
        redisUtils.set("10006", user);
        // 测试获取实体
        SysUser user2 = (SysUser) redisUtils.get("10006");
        System.out.println(user2.getNickname()); // 输出：这是昵称

        // 测试列表
        System.out.println("测试保存列表");
        List<SysUser> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user);
        redisUtils.set("userList", (Serializable) userList);
        List<SysUser> userList2 = (List<SysUser>) redisUtils.get("userList");
        for (SysUser user1 : userList2) {
            System.out.println(user1);
        }
    }
}