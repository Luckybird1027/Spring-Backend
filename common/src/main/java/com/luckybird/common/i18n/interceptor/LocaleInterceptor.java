package com.luckybird.common.i18n.interceptor;

import com.luckybird.common.context.utils.ContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Set;

/**
 * 用户鉴权拦截器
 *
 * @author 新云鸟
 */
@RequiredArgsConstructor
@Component
public class LocaleInterceptor implements HandlerInterceptor {

    // TODO: 安全性问题、性能问题
    private final static Set<String> LOCALE_LIST = Set.of("zh_CN", "en_US");

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        // TODO: 2024-09-19T11:05:56.014+08:00 ERROR 26232 --- [starter] [nio-8080-exec-4] c.l.c.e.handler.GlobalExceptionHandler   : Exception: ErrorResult(code=null, message=Cannot invoke "Object.equals(Object)" because "o" is null), URL: /v1/dept/tree
        //
        //java.lang.NullPointerException: Cannot invoke "Object.equals(Object)" because "o" is null
        //	at java.base/java.util.ImmutableCollections$Set12.contains(ImmutableCollections.java:817) ~[na:na]
        //	at com.luckybird.common.i18n.interceptor.LocaleInterceptor.preHandle(LocaleInterceptor.java:32) ~[classes/:na]
        //	at org.springframework.web.servlet.HandlerExecutionChain.applyPreHandle(HandlerExecutionChain.java:146) ~[spring-webmvc-6.1.11.jar:6.1.11]
        //	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1084) ~[spring-webmvc-6.1.11.jar:6.1.11]
        //	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:979) ~[spring-webmvc-6.1.11.jar:6.1.11]
        //	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1014) ~[spring-webmvc-6.1.11.jar:6.1.11]
        //	at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.1.11.jar:6.1.11]
        //	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:527) ~[jakarta.servlet-api-6.0.0.jar:6.0.0]
        //	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.1.11.jar:6.1.11]
        //	at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:614) ~[jakarta.servlet-api-6.0.0.jar:6.0.0]
        //	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:195) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.1.11.jar:6.1.11]
        //	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.11.jar:6.1.11]
        //	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.1.11.jar:6.1.11]
        //	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.11.jar:6.1.11]
        //	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.1.11.jar:6.1.11]
        //	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.1.11.jar:6.1.11]
        //	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:164) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:140) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:483) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:344) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:389) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:904) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1190) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:63) ~[tomcat-embed-core-10.1.26.jar:10.1.26]
        //	at java.base/java.lang.Thread.run(Thread.java:1583) ~[na:na]

        // 从请求头中获取语言设置并存入上下文
        String stringLocale = request.getHeader("Accept-Language");
        if (LOCALE_LIST.contains(stringLocale)) {
            String[] split = stringLocale.split("_");
            Locale locale = Locale.of(split[0], split[1]);
            ContextUtils.setLocale(locale);
        } else {
            ContextUtils.setLocale(Locale.getDefault());
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
        ContextUtils.removeLocale();
    }
}
