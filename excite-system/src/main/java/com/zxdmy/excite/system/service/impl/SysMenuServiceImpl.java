package com.zxdmy.excite.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zxdmy.excite.common.enums.SystemCode;

import com.zxdmy.excite.system.entity.SysMenu;
import com.zxdmy.excite.system.entity.SysRoleMenu;
import com.zxdmy.excite.system.mapper.SysMenuMapper;
import com.zxdmy.excite.system.mapper.SysRoleMenuMapper;
import com.zxdmy.excite.system.service.ISysMenuService;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单/权限表 服务实现类
 * </p>
 *
 * @author 拾年之璐
 * @since 2021-09-12
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    SysMenuMapper menuMapper;

    SysRoleMenuMapper roleMenuMapper;

//    RedisUtils redisUtils;

    /**
     * 获取一个菜单/权限
     * 使用接口：编辑接口（读取当前编辑的菜单详情）
     *
     * @param id 菜单/权限的ID
     * @return 菜单实体
     */
    @Override
    public SysMenu getMenu(Integer id) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        // 根据ID查询status正常的菜单
        wrapper.eq("id", id).ne("status", SystemCode.STATUS_N.getCode());
        return menuMapper.selectOne(wrapper);
    }

    /**
     * 添加或更新一个菜单/权限
     * 使用接口：新增接口、编辑接口
     *
     * @param menu 菜单/权限 实体
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
    //    @CacheEvict(value = "menu", key = "#cacheKey")
    public int saveMenu(SysMenu menu, String cacheKey) {
        if (null == menu) {
            return -1;
        }
        // 根据ID判断是否是新增，ID为空是新增，否则是更新
        boolean isAdd = null == menu.getId();
        // 如果是顶级菜单：上级菜单为-1，请求路径为""
        if ("N".equals(menu.getType())) {
            menu.setParentId(-1);
            menu.setPath("");
        }
        // 如果是目录：请求路径设置为空字符串（非NULL）
        else if ("C".equals(menu.getType())) {
            menu.setPath("");
        }
        // 初始化:标签
        if (!"fa ".equals(menu.getIcon().substring(0, 3)))
            menu.setIcon("fa " + menu.getIcon());
        // 如果未输入排序，则填充默认顺序：50
        if (null == menu.getSort()) {
            menu.setSort(SystemCode.MENU_DEFAULT_SORT.getCode());
        }
        // 设置时间信息
        LocalDateTime localDateTime = LocalDateTime.now();
        // 新增
        if (isAdd) {
            menu.setCreateTime(localDateTime);
            // 设置初始状态：正常
            if (null == menu.getStatus()) {
                menu.setStatus(SystemCode.STATUS_Y.getCode());
            }
            if (null == menu.getEditable()) {
                menu.setEditable(SystemCode.EDITABLE_Y.getCode());
            }
            if (null == menu.getRemovable()) {
                menu.setRemovable(SystemCode.REMOVABLE_Y.getCode());
            }
            return menuMapper.insert(menu);
        }
        // 修改
        else {
            menu.setUpdateTime(localDateTime);
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("id", menu.getId())
                    .eq("editable", SystemCode.EDITABLE_Y.getCode())
                    .ne("status", SystemCode.STATUS_N.getCode());
            return menuMapper.update(menu, wrapper);
        }
    }

    /**
     * 修改菜单的状态
     * 使用接口：修改菜单状态接口
     *
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
    public int[] changeStatus(int newStatus, Integer[] menuIds) {
        int[] result = new int[2];
        for (Integer menuId : menuIds) {
            SysMenu menu = new SysMenu();
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            menu.setId(menuId);
            menu.setStatus(newStatus);
            wrapper.eq("id", menuId)
                    .ne("status", SystemCode.STATUS_Y_BLOCK.getCode());
            if (menuMapper.update(menu, wrapper) > 0)
                result[0]++;
            else result[1]++;
        }
        return result;
    }

    /**
     * 获取菜单/权限列表
     *
     * @param isAll True:返回全部 | false:只返回目录+菜单
     * @return 菜单实体列表
     */
    @Override
    public List<SysMenu> getMenuList(Boolean isAll) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        // 查询状态status正常（0）的菜单
        wrapper.ne(!isAll, "status", SystemCode.STATUS_N.getCode())
                // 非只查询菜单，则【排除】类型（type）是按钮（B）的权限
                .ne(!isAll, "type", "B")
                // 排序
                .orderByDesc("sort");
        return menuMapper.selectList(wrapper);
    }

    /**
     * 删除一个菜单/权限
     *
     * @param id 菜单/权限的ID
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
    @CacheEvict(value = "menu", key = "#cacheKey")
    public int deleteMenu(Integer id, String cacheKey) {
        if (id == null) {
//            throw new ServiceException("菜单/权限的ID不能为null");
            return 0;
        }
        // 当删除一个菜单后，需要从【角色-权限关联表】中删除含有该菜单的记录
        QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("menu_id", id);
        roleMenuMapper.delete(wrapper1);
        // 只有removable=1的菜单才可以删除
        QueryWrapper<SysMenu> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("id", id)
                .ne("status", SystemCode.STATUS_N.getCode())
                .eq("removable", SystemCode.REMOVABLE_Y.getCode());
        return menuMapper.delete(wrapper2);
    }

    @Override
    public List<SysMenu> getMenuListForRoleEdit(int roleId) {
        // 先获取当前角色拥有的权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<Integer> roleMenuList = roleMenuMapper.selectList(wrapper).stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        // 获取全部的菜单
        QueryWrapper<SysMenu> wrapper2 = new QueryWrapper<>();
        wrapper.ne("status", SystemCode.STATUS_N.getCode())
                .orderByDesc("sort");
        List<SysMenu> menuList = menuMapper.selectList(wrapper2);
        // 使用lambda表达式，直接在菜单列表上修改checkArr字段
        return menuList.stream().peek(menu -> {
            if (roleMenuList.contains(menu.getId()))
                menu.setCheckArr("1");
        }).collect(Collectors.toList());
    }

    /**
     * 获取所有的菜单/权限列表，为权限认证框架使用
     *
     * @param cacheKey 缓存的key
     * @return 菜单实体列表
     */
    @Override
//    @Cacheable(value = "menu", key = "#cacheKey")
    public List<SysMenu> getMenuListForSaToken(String cacheKey) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        // 查询状态status正常（0）的菜单
        wrapper.ne("status", SystemCode.STATUS_N.getCode())
                // 排序
                .orderByDesc("sort");
        return menuMapper.selectList(wrapper);
    }

    /**
     * 通过用户ID查询该用户所拥有的菜单/权限
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Override
    public List<SysMenu> getMenuListByUserId(Integer userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    /**
     * 通过角色的ID查询该角色拥有的菜单/权限
     *
     * @param roleId 角色ID
     * @return 菜单/权限列表
     */
    @Override
    public List<SysMenu> getMenuListByRoleId(Integer roleId) {
        return menuMapper.selectMenusByRoleId(roleId);
    }

}
