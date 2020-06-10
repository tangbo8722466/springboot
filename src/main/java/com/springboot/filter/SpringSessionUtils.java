package com.springboot.filter;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring Session相关操作工具类
 * 
 * @version 1.0.0
 */
public class SpringSessionUtils {

    /**
     * 获取session值
     *
     * @return
     */
    public static String getToken() {
        return getRequest().getHeader("token");
    }

    /**
     * 获取session值
     * 
     * @param name
     * @return
     */
    public static Object getSession(String name) {
        return getRequest().getSession().getAttribute(name);
    }

    /**
     * 设置session值
     * @param name
     * @param value
     */
    public static void setSession(String name, Object value) {
        getRequest().getSession().setAttribute(name, value);
    }
    
    /**
     * 移除session中的某项值
     * @param name
     */
    public static void removeSession(String name) {
        getRequest().getSession().removeAttribute(name);
    }
    
    /**
     * 清除session
     */
    public static void clearSession() {
        getRequest().getSession().invalidate();
    }

    /**
     * 获取请求对象
     * 
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    
    /**
     * 获取回复对象
     * 
     * @return
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
