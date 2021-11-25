package com.zxdmy.excite.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目启动入口
 *
 * @author 拾年之璐
 */
@RestController
@SpringBootApplication(scanBasePackages = {"com.zxdmy.excite.system.service", "com.zxdmy.excite"})
@MapperScan(basePackages = {"com.zxdmy.excite.system.mapper", ""})
public class ExciteAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExciteAdminApplication.class, args);
    }

}
