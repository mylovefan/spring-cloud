package com.bbt.operate.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 11:00
 */
public class ShiroUser {

    private String account;

    private String password;

    private List<ShiroRole> roles = new ArrayList<>();

    private Set<String> rolesKey = new HashSet<>();

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ShiroRole> getRoles() {
        return roles;
    }

    public void setRoles(List<ShiroRole> roles) {
        this.roles = roles;
    }

    public Set<String> getRolesKey() {
        return rolesKey;
    }

    public void setRolesKey(Set<String> rolesKey) {
        this.rolesKey = rolesKey;
    }
}
