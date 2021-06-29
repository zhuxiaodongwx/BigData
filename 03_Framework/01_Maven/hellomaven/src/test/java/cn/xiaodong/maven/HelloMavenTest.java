package cn.xiaodong.maven;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 测试类
 */
public class HelloMavenTest {

    @Test
    public void sayHello() {
        HelloMaven hello = new HelloMaven();
        String maven = hello.sayHello("Maven");
        System.out.println(maven);
    }
}