package com.bbt.framework.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 描述
 * @Author:  zhangrc
 * @CreateDate:  2018/11/7 14:10
 */
public class HttpCode {

    private static Map<Integer, String> messageMap = new HashMap<>();

    public static final int SC_OK = 200;
    public static final int SC_CREATED = 201;
    public static final int SC_ACCEPTED = 202;
    public static final int SC_NO_CONTENT = 204;

    public static final int SC_BAD_REQUEST = 400;
    public static final int SC_UNAUTHORIZED = 401;
    public static final int SC_FORBIDDEN = 403;
    public static final int SC_NOT_FOUND = 404;
    public static final int SC_NOT_ACCEPTABLE = 406;
    public static final int SC_GONE = 410;
    public static final int SC_UNPROCESABLE_ENTITY = 422;

    public static final int SC_INTERNAL_SERVER_ERROR = 500;

    // HTTP状态码与文字说明
    static {
        HttpCode.messageMap.put(HttpCode.SC_OK, "请求成功");
        HttpCode.messageMap.put(HttpCode.SC_CREATED, "新建/修改成功");
        HttpCode.messageMap.put(HttpCode.SC_ACCEPTED, "请求已接收");
        HttpCode.messageMap.put(HttpCode.SC_NO_CONTENT, "删除成功");

        HttpCode.messageMap.put(HttpCode.SC_UNAUTHORIZED, "操作未授权");
        HttpCode.messageMap.put(HttpCode.SC_FORBIDDEN, "禁止访问");
        HttpCode.messageMap.put(HttpCode.SC_NOT_ACCEPTABLE, "请求格式不正确");
        HttpCode.messageMap.put(HttpCode.SC_UNPROCESABLE_ENTITY, "验证错误");

        HttpCode.messageMap.put(HttpCode.SC_INTERNAL_SERVER_ERROR, "吖~系统出错了");

    }

    public static String get(int code) {
        return HttpCode.messageMap.get(code);
    }
}
