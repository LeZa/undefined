package PostCon;

import javax.annotation.PostConstruct;

public class PostConTest {


    @PostConstruct
    public void postConTest(){
        System.out.println("execute postConTest");
    }

}
