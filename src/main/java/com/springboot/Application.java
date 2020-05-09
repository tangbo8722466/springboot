package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Created by tangbo on 2018/1/28 0028.
 */
@SpringBootApplication
/**
 * 数据库事务管理
 */
@EnableTransactionManagement
//加上注解@EnableSwagger2 表示开启Swagger
@EnableSwagger2

@EnableConfigurationProperties
//ComponentScan(basePackages={"com.core"})
//@ComponentScan：会自动扫描指定包下的全部标有@Component的类，并注册成bean，当然包括@Component下的子注解@Service,@Repository,@Controller。
//@ComponentScan(basePackages={"com.springboot"})
//@MapperScan()

@EntityScan(basePackages = {"com.springboot.repository.entity"})
public class Application {
    //    /**
//     * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
//     *
//     *
//     */
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(Application.class);
//    }
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(Application.class, args);
    }
}
