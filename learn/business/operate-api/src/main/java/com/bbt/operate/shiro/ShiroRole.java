package com.bbt.operate.shiro;

import java.util.List;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 11:00
 */
public class ShiroRole {

    private String roleKey;

    private List<String> permissionsKey;

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public List<String> getPermissionsKey() {
        return permissionsKey;
    }

    public void setPermissionsKey(List<String> permissionsKey) {
        this.permissionsKey = permissionsKey;
    }
}
