package PostCon;

import javax.annotation.PreDestroy;

public class PreDestTest {

    @PreDestroy
    public void preDestTest(){
        System.out.println("execute preDestTest");
    }
}
