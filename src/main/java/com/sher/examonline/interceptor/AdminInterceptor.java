package com.sher.examonline.interceptor;

import com.sher.examonline.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title AdminInterceptor
 * @Package com.sher.examonline.interceptor
 * @Description admin role
 * @Author sher
 * @Date 2021/06/30 5:03 PM
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        if (!"admin".equals(user.getRole())) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
