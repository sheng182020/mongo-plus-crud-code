package com.crud.code.svc.base.utils;


import com.crud.code.svc.base.constant.UserContext;

public class UserContextUtils {

    private static final ThreadLocal<UserContext> USER_CONTEXT = new ThreadLocal<>();

    public static UserContext initUserContext() {
        UserContext userContext = USER_CONTEXT.get();
        if (userContext == null) {
            userContext = new UserContext();
        }
        return userContext;
    }

    public static void removeContext() {
        USER_CONTEXT.remove();
    }

    public static void setUserId(String userId) {
        UserContext userContext = initUserContext();
        userContext.setUserId(userId);
        USER_CONTEXT.set(userContext);
    }

    public static void setUser(String userId,String email) {
        UserContext userContext = initUserContext();
        userContext.setUserId(userId);
        userContext.setEmail(email);
        USER_CONTEXT.set(userContext);
    }

    public static String getUserId() {
        UserContext userContext = USER_CONTEXT.get();
        // 可能存在没有token请求的情况
        String userId = "";
        if (userContext != null) {
            userId = userContext.getUserId();
        }
        return userId;
    }
}
