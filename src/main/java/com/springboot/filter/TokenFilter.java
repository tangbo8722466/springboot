package com.springboot.filter;

//import com.framework.utils.core.RequestUtil;
//import com.framework.utils.core.api.ApiConst;
//import com.framework.utils.core.api.ApiResponse;
import com.springboot.Utils.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录拦截
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "TokenFilter")
@Slf4j
public class TokenFilter implements Filter {

    private List<String> noFilters = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        noFilters.add("/*swagger*");
        noFilters.add("/v2/api-docs");
        noFilters.add("/*doc*");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        if (!httpServletRequest.getMethod().equals("OPTIONS")) {
            log.info("uri is [{}]", httpServletRequest.getRequestURI());
            if (noFilters.stream().anyMatch(s -> PatternMatchUtils.simpleMatch(s, httpServletRequest.getRequestURI()))) {
                log.info("uri [{}] not check token", httpServletRequest.getRequestURI());
                filterChain.doFilter(httpServletRequest, httpResponse);
                return;
            }
            String token = SpringSessionUtils.getToken();
            log.info("token is [{}]", token);

            //RequestUtil.writeJsonToResponseCos(RestResult.buildFailResponse("token失效"), httpServletRequest.getHeader("origin"), httpResponse);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        }
    }

    @Override
    public void destroy() {

    }
}
