package cn.xiaodong;

import org.junit.Test;

import static org.junit.Assert.*;

public class HelloFriendTest {

    @Test
    public void sayHelloToFriend() {
        HelloFriend helloFriend = new HelloFriend();
        String results = helloFriend.sayHelloToFriend("Maven");
        System.out.println(results);
    }
}