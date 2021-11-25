package com.zxdmy.excite.admin;

import com.power.doc.builder.HtmlApiDocBuilder;
import com.power.doc.constants.DocGlobalConstants;
import com.power.doc.constants.DocLanguage;
import com.power.doc.model.ApiConfig;
import com.power.doc.model.ApiErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * SmartDoc 接口文档生成工具
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-10 0010 23:55
 */

@SpringBootTest
public class SmartDocGenerator {

    @Test
    public void generatorDoc() {
        // 定义API配置对象
        ApiConfig config = new ApiConfig();
        // 设置项目根路径，将与控制类的路径对接
        config.setServerUrl("http://localhost:8181");
        // 当AllInOne为true，则将所有接口保存至一个文档中
        config.setAllInOne(true);
        // 生成HTML文档保存路径。
        config.setOutPath(DocGlobalConstants.HTML_DOC_OUT_PATH);
        // Smart-doc提供的常量HTML_DOC_OUT_PATH，是将文档保存至项目的 src/main/resources/static/doc 路径下。
        // 保存至此路径的好处是，生成文档之后，重新运行项目，访问 http://localhost:8181/doc/index.html 可以访问接口文档。

        config.setLanguage(DocLanguage.CHINESE);
        // 设置接口包扫描路径过滤，默认扫描所有的接口类。多个包名用英文逗号隔开。
        // config.setPackageFilters("com.zxdmy.excite.user");


        // 设置错误码列表（即遍历自己的错误码列表，赋值给接口文档）
        List<ApiErrorCode> errorCodeList = new ArrayList<>();
        // 这里先使用HttpStatus的状态码
        for (HttpStatus httpStatus : HttpStatus.values()) {
            ApiErrorCode errorCode = new ApiErrorCode();
            errorCode.setValue(String.valueOf(httpStatus.value())).setDesc(httpStatus.getReasonPhrase());
            errorCodeList.add(errorCode);
        }

        // 设置错误码。如果不需要显示状态码，则不需要设置。
        config.setErrorCodes(errorCodeList);
        // 生成HTML格式文档
        HtmlApiDocBuilder.buildApiDoc(config);

    }
}
