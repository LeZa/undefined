package com.service.api.me.spring;


import com.build.pattern.single.A;
import com.build.pattern.single.SingletonBean;
import com.build.pattern.single.ConfigBean;
import com.build.thinking.in.java.net.mindview.atunit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = ConfigBean.class)
public class ConfigTest {

    @Autowired
    private SingletonBean singletonBean;

    @Autowired
    private A a;

    @Test
    public void singletonBean(){
        singletonBean.printThisStr();
    }

    @Test
    public void a(){
            a.printThisStr();
    }
}
