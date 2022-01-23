package com.bootdo.common.utils;

import com.bootdo.common.domain.entity.SysUser;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public class ShiroUtils {
    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }

    public static SysUser getUser() {
        //UserRealm中存入时已经转成 json 了
        Object object = getSubjct().getPrincipal();
        return new Gson().fromJson(object + "", SysUser.class);
    }

    public static Long getUserId() {
        SysUser user = getUser();
        if (user == null) {
            return null;
        }
        return getUser().getId();
    }

    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }
}
