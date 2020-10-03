package cn.xzxy.lewy.mybatisplus.config;

import cn.xzxy.lewy.mybatisplus.handler.OnlineCountFilter;
import cn.xzxy.lewy.mybatisplus.handler.OnlineCountHttpRequestListener;
import cn.xzxy.lewy.mybatisplus.handler.OnlineCountHttpSessionListener;
import cn.xzxy.lewy.mybatisplus.handler.OnlineCountInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 实例web相关组件
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    OnlineCountInterceptor onlineCountInterceptor;

    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(onlineCountInterceptor);
        log.info("onlineCountInterceptor 注入成功...");
    }

    /**
     * 注册过滤器
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new OnlineCountFilter());
        filterRegistration.addUrlPatterns("/*");
        log.info("onlineCountFilter 注入成功...");
        return filterRegistration;
    }

    /**
     * 注册监听器
     */
    @Bean
    public ServletListenerRegistrationBean registrationBean() {
        ServletListenerRegistrationBean registrationBean = new ServletListenerRegistrationBean();
        registrationBean.setListener(new OnlineCountHttpRequestListener());
        log.info("onlineCountHttpRequestListener 注入成功...");
        registrationBean.setListener(new OnlineCountHttpSessionListener());
        log.info("onlineCountHttpSessionListener 注入成功...");
        return registrationBean;
    }

}
