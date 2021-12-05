package com.zxdmy.excite.admin.controller.system;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.entity.SysRole;
import com.zxdmy.excite.system.service.ISysRoleService;
import com.zxdmy.excite.system.service.ISysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 角色管理 列表页面
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "system/role/index";
    }

    /**
     * 添加角色 页面
     * @return
     */
    @RequestMapping("goAdd")
    public String goAdd() {
        return "system/role/add";
    }
    /**
     * 编辑角色 页面
     * @return
     */
    @RequestMapping("goEdit/{id}")
    public String goEdit(@PathVariable String id, ModelMap map) {
        try {
            SysRole role = roleService.getRole(Integer.parseInt(id));
            if (null != role) {
                map.put("role", role);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "system/role/edit";
    }


    /**
     * 添加角色接口
     *
     * @param role    角色实体
     * @param menuIds 该角色拥有的菜单ID，数组类型：[1,2,3,4,5,6]
     * @return 结果
     */
    @PostMapping("/add")
    @ResponseBody
    public BaseResult addRole(@Validated SysRole role, Integer[] menuIds) {
        try {
            if (roleService.addRole(role, menuIds) > 0) {
                return success("菜单角色成功");
            }
            return error(400, "菜单角色失败");
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }

    /**
     * 通过ID获取一个角色
     *
     * @param id 角色ID
     * @return 结果
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public BaseResult getRole(@PathVariable String id) {
        try {
            SysRole role = roleService.getRole(Integer.parseInt(id));
            if (null != role) {
                return success("获取角色成功", role);
            }
            return error(400, "获取角色失败");

        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }

    /**
     * 获取所有角色
     *
     * @return 结果
     */
    @GetMapping("/list")
    @ResponseBody
    public BaseResult getRoleList() {
        try {
            List<SysRole> roleList = roleService.getList();
            return success("获取角色列表成功", roleList, roleList.size());
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }
    }

    /**
     * 更新角色接口
     *
     * @param role    角色实体
     * @param menuIds 该角色拥有的菜单ID，数组类型：[1,2,3,4,5,6]
     * @return 结果
     */
    @PostMapping("/update")
    @ResponseBody
    public BaseResult updateRole(@Validated SysRole role, Integer[] menuIds) {
        try {
            if (roleService.updateRole(role, menuIds) > 0) {
                return success("更新角色成功");
            }
            return error(400, "更新角色失败，请重试");
        } catch (Exception e) {
            return error(500, "error:" + e.getMessage());
        }

    }

    /**
     * 删除指定角色（包含该角色的权限信息，但是已经分配给用户的角色不允许删除）
     *
     * @param id 角色ID
     * @return 结果
     */
    @PostMapping("/remove/{id}")
    @ResponseBody
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


}
