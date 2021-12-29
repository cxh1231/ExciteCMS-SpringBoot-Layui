package com.zxdmy.excite.admin.controller.develop;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author 拾年之璐
 * @since 2021/12/29 18:50
 */
@Controller
@AllArgsConstructor
@RequestMapping("/system/develop")
public class GeneratorController extends BaseController {

    private DataSourceProperties dataSourceProperties;

    /**
     * 访问[代码生成]页面
     *
     * @return 页面跳转
     */
    @RequestMapping("generator")
    public String generatorIndex() {
        return "system/develop/generator";
    }

    @GetMapping("/generator/list")
    @ResponseBody
    public BaseResult getTableList() {
        // 获取链接数据库的基本信息，并填入配置类
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setUrl(dataSourceProperties.getUrl())//
                .setSchemaName("public")     //数据库 schema name
                .setDbType(DbType.MYSQL)
                .setDriverName(dataSourceProperties.getDriverClassName())
                .setUsername(dataSourceProperties.getUsername())
                .setPassword(dataSourceProperties.getPassword());

        // 策略配置(数据库表配置)
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setNaming(NamingStrategy.underline_to_camel)           // 数据库表映射到实体的命名策略:下划线转驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)     // 数据库表字段映射到实体的命名策略:下划线转驼峰
                .setEntityLombokModel(true)                             // 实体是否为lombok模型（默认 false）
                .setControllerMappingHyphenStyle(true);                 // 驼峰转连字符

        // 获取所有的表
        ConfigBuilder configBuilder = new ConfigBuilder(null, dsConfig, strategyConfig, null, null);
        List<TableInfo> tableInfos = configBuilder.getTableInfoList();

        return success(200, tableInfos);
    }

}
