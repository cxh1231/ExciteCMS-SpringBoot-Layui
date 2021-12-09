package com.zxdmy.excite.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * SpringDoc API配置文档
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-10 0010 21:57
 */
// 全局只能定义一个，主要配置文档信息和安全配置
@OpenAPIDefinition(
        // 配置接口文档基本信息，用于网页显示
        info = @Info(
                title = "ExciteCMS", //标题
                version = "1.0", //版本号
                description = "An integrated content management system based on Spring Boot (v2.5.4)" //描述信息
        )
        // 当然还可以添加其他的安全配置。
)
@Configuration // 如果需要在此处配置分组信息，则需要添加此注解。
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi guestApi() {
        return GroupedOpenApi.builder()
                // 分组名
                .group("guest")
                // 扫描的包路径。可选项。
                .packagesToScan("com.zxdmy.cms.excite.guest")
                // 匹配的路径。可选项，可以与包路径二选一，也可以都用。
                .pathsToMatch("/guest/**")
                .build();
    }

//        @Bean
//        public GroupedOpenApi thirdApi() {
//                return GroupedOpenApi.builder()
//                        // 分组名
//                        .group("third")
//                        // 扫描的包路径。可选项。
//                        .packagesToScan("com.zxdmy.cms.excite.third")
//                        // 匹配的路径。可选项，可以与包路径二选一，也可以都用。
//                        .pathsToMatch("/third/**")
//                        .build();
//        }

}





