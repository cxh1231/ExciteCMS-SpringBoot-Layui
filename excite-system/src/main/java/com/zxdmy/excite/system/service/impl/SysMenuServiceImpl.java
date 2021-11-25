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
import java.util.List;

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
     * 添加一个菜单/权限
     *
     * @param menu 菜单/权限 实体
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
//    @CacheEvict(value = "menu", key = "#cacheKey")
    public int addMenu(SysMenu menu, String cacheKey) {
        // 设置时间信息
        LocalDateTime localDateTime = LocalDateTime.now();
        menu.setCreateTime(localDateTime);
        // 设置初始状态：正常
        menu.setStatus(SystemCode.STATUS_Y.getCode());
        // 如果未输入排序，则填充默认顺序：50
        if (null == menu.getSort()) {
            menu.setSort(SystemCode.MENU_DEFAULT_SORT.getCode());
        }
        // 如果未输入父目录，则填充-1
        if (null == menu.getParentId()) {
            menu.setParentId(-1);
        }
//        // 执行插入操作
//        menuMapper.insert(menu);
//        // 获取当前插入的记录的ID，并返回（需要在执行menuMapper.insert 之后才能拿到）
//        return menu.getId();
        // 添加并返回
        return menuMapper.insert(menu);
    }

    /**
     * 获取一个菜单/权限
     *
     * @param id 菜单/权限的ID
     * @return 菜单实体
     */
    @Override
    public SysMenu getMenu(Integer id) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        // 根据ID查询status正常的菜单
        wrapper.eq("id", id).eq("status", SystemCode.STATUS_Y.getCode());
        return menuMapper.selectOne(wrapper);
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
        wrapper.eq("status", SystemCode.STATUS_Y.getCode())
                // 非只查询菜单，则【排除】类型（type）是按钮（B）的权限
                .ne(!isAll, "type", "B")
                // 排序
                .orderByDesc("sort");
        return menuMapper.selectList(wrapper);
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
        wrapper.eq("status", SystemCode.STATUS_Y.getCode())
                // 排序
                .orderByDesc("sort");
        return menuMapper.selectList(wrapper);
    }

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

    /**
     * 更新一个菜单/权限
     *
     * @param menu 菜单/权限 实体
     * @return 影响的行数 0-失败 | 1-成功
     */
    @Override
//    @CacheEvict(value = "menu", key = "#cacheKey")
    public int updateMenu(SysMenu menu, String cacheKey) {
        if (null == menu.getId()) {
            return -1;
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        menu.setUpdateTime(localDateTime);
        // 可编辑才能更新
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("id", menu.getId())
                .eq("editable", SystemCode.EDITABLE_Y.getCode())
                .eq("status", SystemCode.STATUS_Y.getCode());
        return menuMapper.update(menu, wrapper);
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
        }
        // 当删除一个菜单后，需要从【角色-权限关联表】中删除含有该菜单的记录
        QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("menu_id", id);
        roleMenuMapper.delete(wrapper1);
        // 只有removable=1的菜单才可以删除
        QueryWrapper<SysMenu> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("id", id)
                .eq("status", SystemCode.STATUS_Y.getCode())
                .eq("removable", SystemCode.REMOVABLE_Y.getCode());
        return menuMapper.delete(wrapper2);
    }


}
