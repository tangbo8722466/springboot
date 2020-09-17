package com.springboot.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
import java.util.HashMap;
import java.util.Map;
 
/**
 * @author tangbo
 */
@Component
public class LoggerBuilder {

    private static final Map<String, Logger> container = new HashMap<>();

    /**
     *@描述 实例化日志对象
     *@参数 日志对象名称
     *@返回值
     */
    public Logger getLogger(String name) {
        Logger logger;
        synchronized (LoggerBuilder.class) {
            logger = container.get(name);
            if (logger != null) {
                return logger;
            }
            logger = build(name);
            container.put(name, logger);
        }
        return logger;
    }

    /**
     *@描述 销毁日志对象
     *@参数 日志对象名称
     *@返回值
     */
    public void destroyLogger(String name) {
        synchronized (LoggerBuilder.class) {
            container.remove(name);
        }
    }


    private static Logger build(String name) {
        RollingFileAppender errorAppender = new Appender().getAppender(name, Level.ERROR);
        RollingFileAppender infoAppender = new Appender().getAppender(name, Level.INFO);
        RollingFileAppender warnAppender = new Appender().getAppender(name, Level.WARN);
        RollingFileAppender debugAppender = new Appender().getAppender(name, Level.DEBUG);
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger(name);
        //设置不向上级打印信息
        logger.setAdditive(false);
        logger.addAppender(errorAppender);
        logger.addAppender(infoAppender);
        logger.addAppender(warnAppender);
        logger.addAppender(debugAppender);

        return logger;
    }
}
