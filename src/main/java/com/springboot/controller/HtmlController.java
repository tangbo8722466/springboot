package com.springboot.controller;

import org.apache.commons.math3.util.ArithmeticUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HtmlController {

    @GetMapping("/templates")
    String test(HttpServletRequest request) {
        //逻辑处理
        request.setAttribute("key", "hello world");
        return "index";
    }
}
