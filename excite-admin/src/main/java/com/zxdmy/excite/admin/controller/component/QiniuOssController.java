package com.zxdmy.excite.admin.controller.component;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.storage.model.FileInfo;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.component.qiniu.QiniuOssService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 七牛云文件存储服务控制类
 *
 * @author 拾年之璐
 * @since 2022/1/24 21:49
 */
@Controller
@AllArgsConstructor
@RequestMapping("/component/qiniuOss")
public class QiniuOssController extends BaseController {

    QiniuOssService qiniuOssService;

    @RequestMapping("index")
    public String index() {
        return "component/qiniuOss/index";
    }

    @GetMapping(value = "/fileList")
    @ResponseBody
    public BaseResult getList(Integer page, Integer limit) {
        Page<FileInfo> fileInfoList = qiniuOssService.getQiniuFileListPage(page, limit, "", "");
        return success("获取成功！", fileInfoList.getRecords(), (int) fileInfoList.getTotal());
    }



}
