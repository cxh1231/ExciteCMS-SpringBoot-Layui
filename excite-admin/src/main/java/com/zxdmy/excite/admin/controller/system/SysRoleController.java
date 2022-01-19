package com.zxdmy.excite.admin.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.framework.aop.AnnotationSaveReLog;
import com.zxdmy.excite.system.entity.SysRole;
import com.zxdmy.excite.system.service.ISysRoleService;
import com.zxdmy.excite.system.service.ISysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色表 前端控制器
 *
 * @author 拾年之璐
 * @since 2021-09-23
 */
@Controller
@AllArgsConstructor
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    ISysRoleService roleService;

    ISysUserRoleService userRoleService;

    /**
     * @return 页面：【角色管理】页面入口
     */
    @RequestMapping("index")
    public String index() {
        return "system/role/index";
    }

    /**
     * @return 页面：【角色管理-添加角色】页面入口
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        return "system/role/add";
    }

    /**
     * @param id  角色ID
     * @param map map
     * @return 页面：【角色管理-编辑角色】页面入口
     */
    @RequestMapping("goEdit/{id}")
    public String goEdit(@PathVariable String id, ModelMap map) {
        try {
            SysRole role = roleService.getRole(Integer.parseInt(id));
            if (null != role) {
                map.put("role", role);
                return "system/role/edit";
            } else {
                map.put("code", 500);
                map.put("msg", "可能原因：该角色禁止编辑，该角色已被禁用！");
                return "error/error";
            }
        } catch (Exception e) {
            // 发生异常，一般就是数字转换出错
            System.err.println(e.getMessage());
            return "error/500";
        }
    }

    @RequestMapping("goShowUser/{id}")
    public String showUser(@PathVariable String id, ModelMap map) {
        try {
            SysRole role = roleService.getRole(Integer.parseInt(id));
            if (null == role) {
                map.put("code", 500);
                map.put("msg", "可能原因：该角色禁止编辑，该角色已被禁用！");
                return "error/error";
            } else {
                map.put("role", role);
                return "system/role/authUser";
            }
        } catch (Exception e) {
            // 发生异常，一般就是数字转换出错
            System.err.println(e.getMessage());
            return "error/500";
        }
    }

    /**
     * 接口功能：添加角色，并支持同时分配权限
     *
     * @param role    角色实体
     * @param menuIds 该角色拥有的菜单ID，数组类型：[1,2,3,4,5,6]，前端传递字符串格式：1,2,3,4
     * @return 添加结果
     */
    @PostMapping("/add")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult addRole(@Validated SysRole role, Integer[] menuIds) {
        try {
            if (roleService.saveRole(role, menuIds) > 0) {
                return success("菜单角色成功");
            }
            return error(400, "菜单角色失败");
        } catch (Exception e) {
            return error(500, "发生错误:" + e.getMessage());
        }
    }

    /**
     * 接口功能：通过角色ID获取角色信息
     *
     * @param id 角色ID
     * @return 指定角色的信息
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult getRole(@PathVariable String id) {
        try {
            SysRole role = roleService.getRole(Integer.parseInt(id));
            if (null != role) {
                return success("获取角色成功", role);
            }
            return error(400, "获取角色失败");
        } catch (Exception e) {
            return error(500, "发生错误:" + e.getMessage());
        }
    }

    /**
     * 接口功能：获取所有角色，支持条件搜索
     *
     * @param page       页号
     * @param limit      当前页数目
     * @param name       搜索字段：名称
     * @param permission 搜索字段：权限字符
     * @return 符合要求的角色列表
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult getRoleList(Integer page, Integer limit, String name, String permission) {
        try {
            Page<SysRole> rolePage = roleService.getPage(page, limit, name, permission);
            return success("获取角色列表成功", rolePage.getRecords(), (int) rolePage.getTotal());
        } catch (Exception e) {
            return error(500, "发生错误:" + e.getMessage());
        }
    }

    /**
     * 接口功能：获取获取所有正常的角色，为用户模块所用（添加、编辑用户）
     * 其中：如果指定ID（即ID不为0），则该用户的角色列表中，已分配的角色属性中的checkArr=1
     *
     * @param userId 用户ID。如果为0，则表示获取所有列表
     * @return JSON结果
     */
    @GetMapping("/listForUser/{userId}")
    @ResponseBody
    public BaseResult getRoleListForUser(@PathVariable String userId) {
        try {
            List<SysRole> roleList = roleService.getListByUserId(Integer.parseInt(userId));
            return success("获取角色列表成功", roleList, roleList.size());
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }

    /**
     * 接口功能：更新角色接口
     *
     * @param role    角色实体
     * @param menuIds 该角色拥有的菜单ID，数组类型：[1,2,3,4,5,6]，前端发送数据格式为字符串：1,2,3,4
     * @return 结果
     */
    @PostMapping("/update")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult updateRole(@Validated SysRole role, Integer[] menuIds) {
        try {
            if (roleService.saveRole(role, menuIds) > 0) {
                return success("更新角色成功");
            }
            return error(400, "更新角色失败，请重试");
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }

    /**
     * 接口功能：更新角色状态
     *
     * @param status  角色新状态：1-正常 0-禁用
     * @param roleIds 角色ID列表，数组类型：[1,2,3,4,5,6]
     * @return 修改结果
     */
    @PostMapping("/changeStatus/{status}")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult changeStatus(@PathVariable String status, Integer[] roleIds) {
        try {
            int[] result = roleService.changeStatus(Integer.parseInt(status), roleIds);
            if (result[0] > 0) {
                return success(result[0] + "个角色状态更新成功，" + result[1] + "个失败！");
            } else
                return error(400, "更新角色状态失败，请重试");
        } catch (Exception e) {
            return error(500, "发生错误:" + e.getMessage());
        }
    }

    /**
     * 接口功能：删除指定角色，包含该角色的权限信息，以及撤销分配给某些用户的角色
     *
     * @param id 角色ID
     * @return 结果
     */
    @PostMapping("/remove/{id}")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult removeRole(@PathVariable String id) {
        try {
            if (roleService.deleteRoleById(Integer.parseInt(id)) > 0) {
                return success("角色删除成功");
            }
            return error(400, "角色删除失败，请重试");
        } catch (Exception e) {
            return error(400, "error:" + e.getMessage());
        }
    }

    /**
     * 接口功能：将指定角色分配给系列用户，或者取消某角色分配给的用户
     *
     * @param type    操作类型：auth-添加授权 | revoke-取消授权
     * @param roleId  用户ID
     * @param userIds 角色信息列表
     * @return 分配结果
     */
    @PostMapping("/authUser/{type}")
    @ResponseBody
    @AnnotationSaveReLog
    public BaseResult authForUser(@PathVariable("type") String type, String roleId, Integer[] userIds) {
        try {
            // 添加授权
            if (type.equals("auth")) {
                if (roleService.authRoleForUsers(Integer.parseInt(roleId), userIds))
                    return success(200, "添加授权成功！");
                else
                    return error("授权失败，请重试！");
            }
            // 取消授权
            else if (type.equals("revoke")) {
                int[] result = roleService.revokeRoleForUsers(Integer.parseInt(roleId), userIds);
                if (result[1] == 0)
                    return success(200, "取消授权成功！");
                else if (result[0] == 0)
                    return error("取消授权失败，请重试！");
                else
                    return success(result[0] + "个用户取消授权成功，" + result[1] + "个失败！");
            }
            return error("访问错误！");
        } catch (Exception e) {
            return error("发生错误：" + e.getMessage());
        }
    }


}
