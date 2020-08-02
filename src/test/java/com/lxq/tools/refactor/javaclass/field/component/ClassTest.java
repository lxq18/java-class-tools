package com.lxq.tools.refactor.javaclass.field.component;

import org.junit.Test;

/**
 * @author lixiaoqiang
 * @create 2020/8/2 18:27
 */
public class ClassTest {
    @Test
    public  void test() throws ClassNotFoundException {
        /*Class<?> intClass = Class.forName("int");
        System.out.println(intClass.getName());*/

        Class<?> intergerClass = Class.forName("java.lang.Integer");
        System.out.println(intergerClass.getName());
    }
}
