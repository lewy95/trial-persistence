package cn.xzxy.lewy.mybatisplus.controller;

import cn.xzxy.lewy.mybatisplus.handler.OnlineCountHttpSessionListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用于测试 handler 包下各个 web 组件是否生效
 */
@RestController
@RequestMapping("/oc")
public class OnlineCountController {

    @GetMapping("/addSession")
    public String addSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("name", "lewy");
        return "当前在线人数" + session.getServletContext().getAttribute("sessionCount") + "人";
    }

    @GetMapping("/removeSession")
    public String removeSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "当前在线人数" + session.getServletContext().getAttribute("sessionCount") + "人";
    }

    @GetMapping("/online")
    public String online() {
        return "当前在线人数" + OnlineCountHttpSessionListener.userCount.get() + "人";
    }
}
