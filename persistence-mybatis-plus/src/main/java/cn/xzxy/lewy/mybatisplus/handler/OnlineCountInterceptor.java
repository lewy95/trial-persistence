package cn.xzxy.lewy.mybatisplus.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 一个用于测试的模版拦截器
 */
@Slf4j
@Component
public class OnlineCountInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[OnlineCountInterceptor]调用了:{}", request.getRequestURI());
        request.setAttribute("requestTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        if (!request.getRequestURI().contains("/online") &&
                !request.getRequestURI().contains("/removeSession")) {
            HttpSession session = request.getSession();
            String sessionName = (String) session.getAttribute("name");
            if ("lewy".equals(sessionName)) {
                log.info("[OnlineCountInterceptor] 当前浏览器存在 session:{}", sessionName);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        long duration = (System.currentTimeMillis() - (Long) request.getAttribute("requestTime"));
        log.info("[OnlineCountInterceptor] {}]调用耗时:{}ms", request.getRequestURI(), duration);
    }
}
