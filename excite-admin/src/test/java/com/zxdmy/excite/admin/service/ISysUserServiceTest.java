package com.zxdmy.excite.admin.service;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.ECIES;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power.common.util.RSAUtil;
import com.zxdmy.excite.framework.oshi.Server;
import com.zxdmy.excite.system.entity.SysUser;
import com.zxdmy.excite.system.mapper.SysUserMapper;
import com.zxdmy.excite.system.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
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


    @Test
    void ServerTest() throws Exception {
        Server server = new Server();
        server.copyTo();
        System.out.println(server);
    }

    /**
     * 生成密钥对
     */
    @Test
    void RSA() {


        String en = "EH5BLHO6HQGERUoJGzJxE4rX8SRC5j9fDkgN2lu7sSWHv5fHpIjg9Mct6Qbe3NO9nUlmpjsb0j+veiebK3g5so8EXkM71uUvCiq3zVX0hrLOWFBIi9/ikzrXFkxtFLfUIVhchuRncozQTZOux7QyOTBPFUdR2s3A8A80fWLZkfg=";
        String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALwcyvYIGmhk+be320JWWsq1OYjiM0lzv8eHGMgSIOMLxzM/g9X7jguNe8thxJXR/CLqcTgsfZzk8E8Sc9+qnSDxNl5f5tga93vRxd5713zAeAGqLiTQnRffdzRmdbsmu5+0/K8mj056VhKh8FdBNzAj7e4iX9i+uBBG/oDmZbTVAgMBAAECgYEAmgNU5NTDkj9B+Pnt6UU8doSjw3+3j+bV2K2yS3QUOvAUus/Ax7x6ktjWxzCXvDY9IfUil2RNv9vtKEAqYLCWjc+lf8PV/yH1b7NEgyeAPBXtAJRoOnmYL2bdPW92kP9KgxJruF6Dz/C5AmMOncsvq8ABD+9Darn4p8dwj2ZC4O0CQQDf/AHmZsQokEItfCy4mHS9UbxbfIhEUv1ApPh/+Sr7NkJkHWYCtBQo+8jKO6zurAZQgWBPD1XX2UE4R+VIiZazAkEA1wAqtMvGhccyRZr+6kpkpDIa8+9jOE+nGUzqTDvgCID6as8AzOONFVVK6m/UUqkhcJ8Qu1pF36BGojy5BX2KVwJBAJSFpbji0hXXupowqfLp3RcgmNbNWAp+QUJZYhJx5cdYbmO2fssyH+AhPT6knYJR/YnqkDM8hv6vKCkqu2YDHjMCQAOA8TE5EOclM+CGghj3VWSHnIDVKdzFD4gOBNNxNlltIKeU8AJmwunSFgJ0CBXAw9a+ANvMwM7AIeaK7sj0HskCQAvxfDCq7gaNx+pfu0FHG8Gix08A/A6foggBl1fVu+L9sr9ZuOQ3HbXnl28F9ewuB9xdjnLUDjp7W7U0pB+vKoQ=";

        System.out.println(RSAUtil.decryptString(en, PRIVATE_KEY));


        // 原始字符串
//        String origin = "哈哈哈哈哈啊啊";
//        System.out.println("原始字符串：");
//        System.out.println(origin);

//        // 定义对象
//        RSA rsa = new RSA();
//
//        //获得私钥
////        System.out.println(rsa.getPrivateKey());
//        System.out.println("私钥如下：");
//        String privateKey = rsa.getPrivateKeyBase64();
//        System.out.println(privateKey);
//
//        //获得公钥
////        System.out.println(rsa.getPublicKey());
//        System.out.println("公钥如下：");
//        String publicKey = rsa.getPublicKeyBase64();
//        System.out.println(publicKey);
//
//        // 使用公钥进行加密
//        byte[] encrypt = rsa.encrypt(StrUtil.bytes(origin, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
//        // 加密结果
//        System.out.println("加密后的密文：");
//        String en = StrUtil.str(encrypt, CharsetUtil.CHARSET_UTF_8);
//        System.out.println(en);
//
//        // 使用私钥解密
//        byte[] decrypt = rsa.decrypt(en, KeyType.PrivateKey);

//        String en = "EH5BLHO6HQGERUoJGzJxE4rX8SRC5j9fDkgN2lu7sSWHv5fHpIjg9Mct6Qbe3NO9nUlmpjsb0j+veiebK3g5so8EXkM71uUvCiq3zVX0hrLOWFBIi9/ikzrXFkxtFLfUIVhchuRncozQTZOux7QyOTBPFUdR2s3A8A80fWLZkfg=";
//        String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALwcyvYIGmhk+be320JWWsq1OYjiM0lzv8eHGMgSIOMLxzM/g9X7jguNe8thxJXR/CLqcTgsfZzk8E8Sc9+qnSDxNl5f5tga93vRxd5713zAeAGqLiTQnRffdzRmdbsmu5+0/K8mj056VhKh8FdBNzAj7e4iX9i+uBBG/oDmZbTVAgMBAAECgYEAmgNU5NTDkj9B+Pnt6UU8doSjw3+3j+bV2K2yS3QUOvAUus/Ax7x6ktjWxzCXvDY9IfUil2RNv9vtKEAqYLCWjc+lf8PV/yH1b7NEgyeAPBXtAJRoOnmYL2bdPW92kP9KgxJruF6Dz/C5AmMOncsvq8ABD+9Darn4p8dwj2ZC4O0CQQDf/AHmZsQokEItfCy4mHS9UbxbfIhEUv1ApPh/+Sr7NkJkHWYCtBQo+8jKO6zurAZQgWBPD1XX2UE4R+VIiZazAkEA1wAqtMvGhccyRZr+6kpkpDIa8+9jOE+nGUzqTDvgCID6as8AzOONFVVK6m/UUqkhcJ8Qu1pF36BGojy5BX2KVwJBAJSFpbji0hXXupowqfLp3RcgmNbNWAp+QUJZYhJx5cdYbmO2fssyH+AhPT6knYJR/YnqkDM8hv6vKCkqu2YDHjMCQAOA8TE5EOclM+CGghj3VWSHnIDVKdzFD4gOBNNxNlltIKeU8AJmwunSFgJ0CBXAw9a+ANvMwM7AIeaK7sj0HskCQAvxfDCq7gaNx+pfu0FHG8Gix08A/A6foggBl1fVu+L9sr9ZuOQ3HbXnl28F9ewuB9xdjnLUDjp7W7U0pB+vKoQ=";
//
//
//        // 根据私钥生成新的对象
//        RSA rsa2 = new RSA(PRIVATE_KEY, null);
//        byte[] decrypt = rsa2.decrypt(en, KeyType.PrivateKey);
//
//        System.out.println("解密后的密文：");
//        System.out.println(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));


    }

    /**
     * 测试添加一个用户
     */
//    @Test
//    void createOneTest() {
//        SysUser user = new SysUser();
//        user.setUsername("张三");
//        user.setEmail("1000@zxdmy.com");
//        user.setPassword("sfdnfjdsfhdsjk");
//        if (userService.save(user)) {
//            System.out.println("新增用户成功");
//        } else {
//            System.out.println("新增用户失败");
//        }
//    }
    @Test
    void tokenTest() {
        // 查询value包括1000的所有token，结果集从第0条开始，返回10条
        System.out.println();
        List<String> tokenList = StpUtil.searchTokenValue("", 0, 10);
        for (String token : tokenList) {
            System.out.println(token);
        }
        System.out.println();
        List<String> tokenList2 = StpUtil.searchTokenSessionId("", -1, 10000);
        for (String token : tokenList2) {
            System.out.println(token);
        }
        System.out.println();
        List<String> tokenList3 = StpUtil.searchSessionId("", 0, 10000);
        for (String token : tokenList3) {

            System.out.println(token);
        }

//        List<String> sessionIds = StpUtil.searchTokenValue(null, -1, 1000);
//        MySaTokenListener.ONLINE_USERS.sort((o1, o2) -> DateUtil.compare(o2.getLoginTime(), o1.getLoginTime()));
//        PageDtoUtil pageDto = PageDtoUtil.getPageDto(MySaTokenListener.ONLINE_USERS, page, limit);
//
//        List<OnlineUser> pageList = (List<OnlineUser>) pageDto.getPageList();
//        pageList.forEach(onlineUser -> {
//            String keyLastActivityTime = StpUtil.stpLogic.splicingKeyLastActivityTime(onlineUser.getTokenValue());
//            String lastActivityTimeString = SaManager.getSaTokenDao().get(keyLastActivityTime);
//            if (lastActivityTimeString != null) {
//                long lastActivityTime = Long.parseLong(lastActivityTimeString);
//                onlineUser.setLastActivityTime(DateUtil.date(lastActivityTime));
//            }
//        });
//        return PageResponse.ok(pageList, (long) pageDto.getTotal());

    }

    /**
     * 测试添加一系列数据
     */
//    @Test
//    void createListTest() {
//        // 定义用户列表
//        List<SysUser> userList = new ArrayList<>();
//        // 随机生成 5个用户
//        for (int i = 1; i <= 5; i++) {
//            // 定义用户实体
//            SysUser user = new SysUser();
//            // 随机填充数据
//            user.setUsername("用户" + i);
//            user.setEmail("1000@zxdmy.com" + i);
//            user.setPassword(DigestUtils.md5DigestAsHex(("password" + i).getBytes()));
//            // 添加至列表
//            userList.add(user);
//        }
//        // 打印用户列表
//        System.out.println(userList);
//        // 执行添加
//        if (userService.saveBatch(userList)) {
//            System.out.println("新增用户成功");
//        } else {
//            System.out.println("新增用户失败");
//        }
//    }

    /**
     * 测试根据ID修改一个用户的姓名
     */
//    @Test
//    void updateOneTest() {
//        // 定义用户实体
//        SysUser user = new SysUser();
//        // 随机填充数据
//        user.setId(10006);
//        user.setUsername("李四");
//        // 执行修改
//        if (userService.updateById(user)) {
//            System.out.println("修改用户信息成功");
//        } else {
//            System.out.println("修改用户信息失败");
//        }
//    }

    /**
     * 测试根据ID查询一个用户的信息
     */
//    @Test
//    void getOneTest() {
//        SysUser user = userService.getById(100007);
//        if (null != user) {
//            System.out.println(user);
//        } else {
//            System.out.println("查无此用户");
//        }
//    }

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

//    /**
//     * 分页查询测试
//     */
//    @Test
//    void pageTest() {
//        // 进行分页查询
//        Page<SysUser> userPage = userService.page(new Page<>(2, 2), null);
//        // 获取查询的用户列表
//        List<SysUser> userList = userPage.getRecords();
//        System.out.println(userList);
//        System.out.println("当前页：" + userPage.getCurrent());
//        System.out.println("当前页显示条数：" + userPage.getSize());
//        System.out.println("总数：" + userPage.getTotal());
//    }

}