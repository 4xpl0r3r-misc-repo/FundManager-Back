package com.project.fundmanager.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(5)
@Component
public class ApiAuthUserFilterBean extends FilterRegistrationBean<Filter> {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        setFilter(new ApiAuthUserFilter());
        setUrlPatterns(List.of("/user/update/*","/user/getList"));
    }

    class ApiAuthUserFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession session=req.getSession();
            Long id= (Long) session.getAttribute("id");
            if (id==null){
                logger.warn("Unauthorized user,from ip: {}", request.getRemoteAddr());
                resp.setStatus(403);
                resp.getWriter().println("Unauthorized user");
//                return;
            }else {
                logger.info("Authorized user id {}.", id);
                chain.doFilter(request, response);
            }
        }
    }
}
