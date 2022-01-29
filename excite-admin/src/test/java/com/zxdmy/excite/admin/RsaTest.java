package com.zxdmy.excite.admin;

import cn.hutool.crypto.asymmetric.RSA;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * RAS加密公钥、私钥生成测试类
 *
 * @author 拾年之璐
 * @since 2022/1/28 23:20
 */
@SpringBootTest
public class RsaTest {

    @Test
    void createKey() {
        // 定义对象
        RSA rsa = new RSA();

        //获得私钥
        System.out.println("私钥如下：");
        String privateKey = rsa.getPrivateKeyBase64();
        System.out.println(privateKey);

        //获得公钥
        System.out.println("公钥如下：");
        String publicKey = rsa.getPublicKeyBase64();
        System.out.println(publicKey);
    }
}
