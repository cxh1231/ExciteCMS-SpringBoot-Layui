package com.zxdmy.excite.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * Mybatis Plus 配置信息
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-01 0001 18:34
 */
@Configuration
@MapperScan({"com.zxdmy.excite.system.mapper", ""})
public class MybatisPlusConfig {

    /**
     * 分页插件配置
     *
     * @return MP拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
