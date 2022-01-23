package com.bootdo.common.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bootdo.common.domain.entity.SysUser;
import com.bootdo.mapper.SysUserMapper;
import com.google.gson.Gson;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		Set<String> perms = new HashSet<>();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		String password = new String((char[]) token.getCredentials());

		SysUserMapper userMapper = ApplicationContextRegister.getBean(SysUserMapper.class);
		// 查询用户信息
		SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>()
				.eq("username", username));
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		if (user.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		Gson gson = new Gson();
		return new SimpleAuthenticationInfo(gson.toJson(user), password, getName());
	}

}
