package com.springboot.controller.shiro;

import com.springboot.Utils.encrypt.Md5.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 不能使用@RestController，会将所有响应加上ResponseBody
@Controller
public class ShiroController {

    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequiresPermissions(value = {"user:add", "admin"}, logical = Logical.OR)
    @GetMapping("/add")
    public String add(){
        return "/add";
    }

    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequiresPermissions(value = {"user:update", "admin"}, logical = Logical.OR)
    @GetMapping("/update")
    public String update(){
        return "/update";
    }

    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequiresPermissions(value = {"user:delete", "admin"}, logical = Logical.OR)
    @GetMapping("/delete")
    public String delete(){
        return "/delete";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    @GetMapping("/noAuth")
    public String noAuth(){
        return "/noAuth";
    }

    @GetMapping("/main")
    public String main(){
        return "/main";
    }

    /**
     * 登录逻辑处理
     */
    @PostMapping("/login")
    public String login(String userName, String password, Model model){
        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();

        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5Utils.MD5Encode(password, "UTF-8"));

        //3.执行登录方法
        try {
            subject.login(token);

            //登录成功
            //跳转到test.html
            model.addAttribute("userName", userName);
            return "main";
        } catch (UnknownAccountException e) {
            //e.printStackTrace();
            //登录失败:用户名不存在，UnknownAccountException是Shiro抛出的找不到用户异常
            model.addAttribute("msg", "用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e) {
            //e.printStackTrace();
            //登录失败:密码错误，IncorrectCredentialsException是Shiro抛出的密码错误异常
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}
