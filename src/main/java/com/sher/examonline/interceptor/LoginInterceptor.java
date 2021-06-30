package com.sher.examonline.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title LoginInterceptor
 * @Package com.sher.examonline.interceptor
 * @Description login interceptor
 * @Author sher
 * @Date 2021/06/30 4:59 PM
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
