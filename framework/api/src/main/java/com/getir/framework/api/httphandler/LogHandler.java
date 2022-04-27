package com.getir.framework.api.httphandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogHandler implements HandlerInterceptor {

    private static final String REQUEST_START_TIME = "requestStartTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                request.setAttribute(REQUEST_START_TIME, System.currentTimeMillis());
                log.info("Starting controller method for {}.{}",
                        ClassUtils.getClassFileName(handlerMethod.getBean().getClass()),
                        handlerMethod.getMethod().getName());
            }
        } catch (Exception e) {
            log.error("Caught an exception while executing handler method", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                log.info("Completed controller method for {}.{} takes {} ms",
                        ClassUtils.getClassFileName(handlerMethod.getBean().getClass()),
                        handlerMethod.getMethod().getName(),
                        System.currentTimeMillis() - (Long) request.getAttribute(REQUEST_START_TIME));
            }
        } catch (Exception e) {
            log.error("Caught an exception while executing handler method", e);
        }
    }
}
