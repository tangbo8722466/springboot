package com.springboot.design.clone;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;

/**
 * 原型（Prototype）模式的定义如下：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象
 */
//具体原型类
@Slf4j
public class Realizetype implements Cloneable
{
    Realizetype()
    {
        log.info("具体原型创建成功！");
    }
    public Object clone() throws CloneNotSupportedException
    {
        log.info("具体原型复制成功！");
        return (Realizetype)super.clone();
    }

    public static void main(String[] args)throws CloneNotSupportedException
    {
        Realizetype obj1=new Realizetype();
        Realizetype obj2=(Realizetype)obj1.clone();
        System.out.println("obj1==obj2?"+(obj1==obj2));
    }

}