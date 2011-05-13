package vm.device;

import java.util.LinkedList;

public class KeyBoardManager implements Device{
    public static final int NO_ACTION = 0;
    public static final int WAIT = 1;

    private int state;
    private final LinkedList<Byte> buf;

    public KeyBoardManager(){
        buf = new LinkedList<Byte>();
    }

    public void setState(int state){
        this.state = state;
    }

    public boolean isWait(){
        return state == KeyBoardManager.WAIT;
    }

    public void add(byte data){
        buf.add(data);
    }

    public byte poll(){
        return buf.poll();
    }
}