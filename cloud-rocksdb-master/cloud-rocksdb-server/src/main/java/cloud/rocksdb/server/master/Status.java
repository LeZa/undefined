package cloud.rocksdb.server.master;

/**
 * Created by fafu on 2017/7/11.
 */
public enum Status {
    INIT(0),STARTING(1),STARTED(2),STOPPING(3),STOPPED(4),
    UP(11),DOWN(12);

    private int value;
    private Status(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
