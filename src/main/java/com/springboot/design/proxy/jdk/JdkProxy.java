package com.springboot.design.proxy.jdk;

import com.springboot.design.proxy.TicketProxy;
import com.springboot.design.proxy.TrainSaleWindow;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ProjectName: idc-root
 * @Package: com.springboot.design.proxy.jdk
 * @ClassName: JdkProxy
 * @Author: bo.tang
 * @Description: jdk动态代理
 * 1.代理对象,不需要实现接口
 * 2.代理对象的生成,是利用JDK的API,动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)
 * 3.动态代理也叫做:JDK代理,接口代理
 * JDK中生成代理对象的API
 * 代理类所在包:java.lang.reflect.Proxy
 * JDK实现代理只需要使用newProxyInstance方法,但是该方法需要接收三个参数,完整的写法是:
 * static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h )
 * 注意该方法是在Proxy类中是静态方法,且接收的三个参数依次为:
 * • ClassLoader loader,:指定当前目标对象使用类加载器,获取加载器的方法是固定的
 * • Class<?>[] interfaces,:目标对象实现的接口的类型,使用泛型方式确认类型
 * • InvocationHandler h:事件处理,执行目标对象的方法时,会触发事件处理器的方法,会把当前执行目标对象的方法作为参数传入
 * @Date: 2020/7/9 9:45
 * @Version: 1.0
 */
@Slf4j
public class JdkProxy {

    //维护一个目标对象
    private Object target;

    public JdkProxy(Object target) {
        this.target = target;
    }

    //给目标对象生成代理对象
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        log.info("开始事务");
                        //执行目标对象方法
                        Object returnValue = method.invoke(target, args);
                        log.info("提交事务");
                        return returnValue;
                    }
                }
        );
    }

}
