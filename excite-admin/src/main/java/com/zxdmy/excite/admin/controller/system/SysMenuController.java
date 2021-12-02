package com.zxdmy.excite.admin.controller.system;

import com.zxdmy.excite.common.base.BaseController;
import com.zxdmy.excite.common.base.BaseResult;
import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.service.ISysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统菜单/权限表 前端控制器
 *
 * @author 拾年之璐
 * @since 2021-09-12
 */
@Controller
@AllArgsConstructor
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

    private ISysMenuService menuService;

    @RequestMapping("index")
    public String index() {
        return "system/menu/index";
    }

    @RequestMapping("goAdd")
    public String goAdd() {
        return "system/menu/add";
    }

    @RequestMapping("goEdit/{id}")
    public String goEdit(@PathVariable String id, ModelMap map) {
        try {
            SysMenu menu = menuService.getMenu(Integer.parseInt(id));
            if (null != menu) {
                if ("".equals(menu.getIcon()) && null != menu.getIcon()) {
                    menu.setIcon(menu.getIcon().substring(3));
                }
                map.put("menu", menu);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "system/menu/edit";
    }

    /**
     * 添加一个菜单
     *
     * @param menu 菜单实体
     * @return 结果
     */
    @PostMapping("/add")
    @ResponseBody
    public BaseResult addMenu(@Validated SysMenu menu) {
        System.out.println(menu);
        // 如果是顶级菜单，则将其上级菜单设置为-1
        if ("N".equals(menu.getType())) {
            menu.setParentId(-1);
            menu.setPath("");
        }
        // 如果是目录，则将其请求路径设置为空字符串（非NULL）
        else if ("C".equals(menu.getType())) {
            menu.setPath("");
        }
        menu.setIcon("fa " + menu.getIcon());
        if (menuService.addMenu(menu, "list") > 0) {
            return success("菜单添加成功");
        }
        return error(400, "菜单添加失败");
    }

    /**
     * 根据ID获取一个菜单
     *
     * @param id 菜单ID
     * @return 结果
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public BaseResult getMenu(@PathVariable String id) {
        try {
            SysMenu menu = menuService.getMenu(Integer.parseInt(id));
            if (null != menu) {
                return success("获取菜单成功", menu);
            }
            return error(400, "获取菜单失败");
        } catch (Exception e) {
            return error(400, "error：" + e.getMessage());
        }
    }

    /**
     * 获取菜单列表
     *
     * @return 菜单/权限列表
     */
    @GetMapping("/indexList")
    @ResponseBody
    public BaseResult getMenuListIndex() {

        List<SysMenu> menus = menuService.getMenuList(false);
        if (null != menus) {
            // 如果是【tree】，则需要将菜单初始化成树状结构
            menus = this.initMenu(menus);
            HashMap<String, String> map = new HashMap<>();
            map.put("title", "首页");
            map.put("href", "/system/welcome");
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("title", "ExciteCMS");
            map2.put("image", "/images/logo.png");
            map2.put("href", "/system/welcome");
            // 转换成相应的格式
            return success("获取菜单成功").put("homeInfo", map).put("logoInfo", map2).put("menuInfo", menus);

        }
        return error(400, "获取菜单失败");
    }

    /**
     * 获取菜单列表
     *
     * @param type all:查询全部数据 | menu:查询除了按钮的全部数据 | tree:查询树状菜单（不含按钮）
     * @return 菜单/权限列表
     */
    @GetMapping("/list/{type}")
    @ResponseBody
    public BaseResult getMenuList(@PathVariable String type) {
        boolean isAll = "all".equals(type);
        boolean isTree = "tree".equals(type);
        List<SysMenu> menus = menuService.getMenuList(isAll);
        if (null != menus) {
            // 如果是【tree】，则需要将菜单初始化成树状结构
            if (isTree) {
                menus = this.initMenu(menus);
                return success("获取菜单成功", menus);
            }
            return success("获取菜单成功", menus, menus.size());
        }
        return error(400, "获取菜单失败");
    }

    /**
     * 根据角色ID获取菜单/权限
     *
     * @param roleId 角色ID
     * @return 菜单/权限列表
     */
    @GetMapping("/listByRoleId/{roleId}")
    @ResponseBody
    public BaseResult getMenuListByRoleId(@PathVariable String roleId) {
        try {
            List<SysMenu> menus = menuService.getMenuListByRoleId(Integer.parseInt(roleId));
            return success("获取菜单成功", menus, menus.size());
        } catch (Exception e) {
            return error(400, "error：" + e.getMessage());
        }
    }

    /**
     * 根据用户ID获取菜单/权限
     *
     * @return 结果
     */
    @GetMapping("/listByUserId/")
    @ResponseBody
    public BaseResult getMenuListByUserId() {


        return error(400, "获取菜单失败");
    }

    /**
     * 更新菜单
     *
     * @param menu 菜单实体
     * @return 结果
     */
    @PostMapping("/update")
    @ResponseBody
    public BaseResult updateMenu(@Validated SysMenu menu) {
        // 如果是顶级菜单，则将其上级菜单设置为-1
        if ("N".equals(menu.getType())) {
            menu.setParentId(-1);
            menu.setPath("");
        }
        // 如果是目录，则将其请求路径设置为空字符串（非NULL）
        else if ("C".equals(menu.getType())) {
            menu.setPath("");
        }
        menu.setIcon("fa " + menu.getIcon());
        if (menuService.updateMenu(menu, "list") > 0) {
            return success("更新菜单成功");
        }
        return error(400, "更新菜单失败");
    }

    /**
     * 根据ID删除一个菜单
     *
     * @param id 菜单ID
     * @return BaseResult JSON
     */
    @PostMapping("/remove/{id}")
    @ResponseBody
    public BaseResult removeMenu(@PathVariable String id) {
        try {
            // 测试用
//            Integer id1 = null;
//            if (menuService.deleteMenu(id1) > 0) {
            if (menuService.deleteMenu(Integer.parseInt(id), "list") > 0) {
                return success("菜单删除成功");
            }
            return error(400, "菜单删除失败");
        } catch (Exception e) {
            return error(400, "error：" + e.getMessage());
        }
    }


    /**
     * 【注意：正常来说，这种工具应该以工具类的形式定义。但本项目中，我们只在此控制类中使用一次，所以就写在各个文件中】
     * <p>
     * 构造菜单树状列表数据
     *
     * @param menuList 查询到的菜单列表
     * @return 合并后的树状列表
     */
    private List<SysMenu> initMenu(List<SysMenu> menuList) {
        // 如果菜单为空，直接返回空
        if (menuList.isEmpty()) {
            return null;
        }
        // 定义新的菜单列表，用来返回
        List<SysMenu> retMenuList = new ArrayList<>();
        // 循环遍历：查找顶级菜单（有的项目中是顶部导航菜单）
        for (SysMenu aMenuList : menuList) {
            // 顶级菜单的特征是父级ID为 -1
            if (null != aMenuList.getParentId() && -1 == aMenuList.getParentId()) {
                retMenuList.add(aMenuList);
            }
        }
        //遍历所有菜单，将二级菜单添加至对应的一级菜单内
        for (SysMenu menu : retMenuList) {
            menu.setChild(getChild(menu.getId(), menuList));
        }
        //返回构建后的树状菜单
        return retMenuList;
    }

    /**
     * 设置子菜单（递归调用）
     *
     * @param parentId 父级菜单ID
     * @param menuList 全部菜单列表
     * @return 子菜单结果集合
     */
    private List<SysMenu> getChild(Integer parentId, List<SysMenu> menuList) {
        List<SysMenu> result = new ArrayList<>();
        // 遍历，将子菜单添加进父菜单项内
        for (SysMenu menu : menuList) {
            if (null != menu.getParentId() && parentId.equals(menu.getParentId())) {
                result.add(menu);
            }
        }
        // 继续遍历二级菜单是否有子菜单（递归）
        for (SysMenu menu : result) {
            menu.setChild(getChild(menu.getId(), menuList));
        }
        //递归终止条件
        if (result.size() == 0) {
            return null;
        }
        //返回结果集
        return result;
    }

}
