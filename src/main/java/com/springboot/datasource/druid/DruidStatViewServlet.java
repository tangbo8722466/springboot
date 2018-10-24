package com.springboot.datasource.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class DruidStateViewServlet
 */
@WebServlet(
        urlPatterns = {"/druid/*"},
        initParams = {
                @WebInitParam(name = "allow", value = "127.0.0.1"),
                @WebInitParam(name = "loginUsername", value = "root"),
                @WebInitParam(name = "loginPassword", value = "root"),
                @WebInitParam(name = "resetEnable", value = "true")// 允许HTML页面上的“Reset All”功能

        }
)
public class DruidStatViewServlet extends StatViewServlet implements Servlet {
    private static final long serialVersionUID = 1L;
}