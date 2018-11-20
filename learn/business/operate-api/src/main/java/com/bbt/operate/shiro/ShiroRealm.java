package com.bbt.operate.shiro;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/20 10:37
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    /**
     * 为用户添加权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##################执行Shiro权限认证##################");
        ////获取当前登录输入的用户名
        String account = (String) super.getAvailablePrincipal(principalCollection);

        //到数据库查是否有此对象
        //ShiroUser user = authService.find(account);

        //模仿数据
        ShiroUser user = new ShiroUser();
        List<ShiroRole> roles = new ArrayList<>();
        ShiroRole role = new ShiroRole();
        role.setRoleKey("admin");
        role.setPermissionsKey(Arrays.asList("user:add","user:delete"));
        roles.add(role);
        user.setRoles(roles);
        Set<String> rolesKey = new HashSet<>();
        rolesKey.add("admin");

        if (user != null) {
            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            // 用户的角色集合
            info.setRoles(user.getRolesKey());
            // 用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
            List<ShiroRole> roleList = user.getRoles();
            for (ShiroRole shiroRole : roleList) {
                if(shiroRole.getPermissionsKey() !=null){
                    info.addStringPermissions(shiroRole.getPermissionsKey());
                }
            }
            return info;
        }

        return null;
    }

    /**
     * 用户认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        logger.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));

        // 查出是否有此用户
        //ShiroUser user = authService.find(token.getUsername(), new String(token.getPassword()));

        ShiroUser user = null;
        if(("zhangrc").equals(token.getUsername())){
            user = new ShiroUser();
            user.setAccount("zhangrc");
            user.setPassword("123456");
        }

        if (user != null) {
            SecurityUtils.getSubject().getSession().setTimeout(3600000);
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getAccount(), token.getPassword(), getName());
        }

        return null;
    }
}
