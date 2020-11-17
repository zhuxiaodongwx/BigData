package cn.xiaodong;

import cn.xiaodong.maven.HelloMaven;

/**
 * @description:
 * @author: xiaodong
 * @create: 2020-11-17 05:00
 **/
public class HelloFriend  {
    public String sayHelloToFriend(String name){
        HelloMaven hello = new HelloMaven();
        String str = hello.sayHello(name)+" I am "+this.getMyName();
        return str;
    }
    public String getMyName(){
        return "Idea";
    }

}
