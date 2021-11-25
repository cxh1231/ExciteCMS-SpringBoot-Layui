package com.zxdmy.excite.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.system.entity.SysUser;
import com.zxdmy.excite.system.mapper.SysUserMapper;
import com.zxdmy.excite.system.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-01 0001 19:19
 */
@SpringBootTest
class ISysUserServiceTest {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysUserService userService;

    /**
     * 测试添加一个用户
     */
    @Test
    void createOneTest() {
        SysUser user = new SysUser();
        user.setNickname("张三");
        user.setEmail("1000@zxdmy.com");
        user.setPassword("sfdnfjdsfhdsjk");
        if (userService.save(user)) {
            System.out.println("新增用户成功");
        } else {
            System.out.println("新增用户失败");
        }
    }

    /**
     * 测试添加一系列数据
     */
    @Test
    void createListTest() {
        // 定义用户列表
        List<SysUser> userList = new ArrayList<>();
        // 随机生成 5个用户
        for (int i = 1; i <= 5; i++) {
            // 定义用户实体
            SysUser user = new SysUser();
            // 随机填充数据
            user.setNickname("用户" + i);
            user.setEmail("1000@zxdmy.com" + i);
            user.setPassword(DigestUtils.md5DigestAsHex(("password" + i).getBytes()));
            // 添加至列表
            userList.add(user);
        }
        // 打印用户列表
        System.out.println(userList);
        // 执行添加
        if (userService.saveBatch(userList)) {
            System.out.println("新增用户成功");
        } else {
            System.out.println("新增用户失败");
        }
    }

    /**
     * 测试根据ID修改一个用户的姓名
     */
    @Test
    void updateOneTest() {
        // 定义用户实体
        SysUser user = new SysUser();
        // 随机填充数据
        user.setId(10006);
        user.setNickname("李四");
        // 执行修改
        if (userService.updateById(user)) {
            System.out.println("修改用户信息成功");
        } else {
            System.out.println("修改用户信息失败");
        }
    }

    /**
     * 测试根据ID查询一个用户的信息
     */
    @Test
    void getOneTest() {
        SysUser user = userService.getById(100007);
        if (null != user) {
            System.out.println(user);
        } else {
            System.out.println("查无此用户");
        }
    }

    /**
     * 测试根据ID删除一个用户的信息
     */
    @Test
    void logicDeleteTest() {
        if (userService.removeById(10010)) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    /**
     * 分页查询测试
     */
    @Test
    void pageTest(){
        // 进行分页查询
        Page<SysUser> userPage = userService.page(new Page<>(2,2),null);
        // 获取查询的用户列表
        List<SysUser> userList = userPage.getRecords();
        System.out.println(userList);
        System.out.println("当前页："+userPage.getCurrent());
        System.out.println("当前页显示条数："+userPage.getSize());
        System.out.println("总数："+userPage.getTotal());
    }

}