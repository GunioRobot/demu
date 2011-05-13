package vm.interrupt;

public class PIC{
    private int master;
    private int slave;

    public void setMaster(int master){
        this.master = master;
    }

    public void setSlave(int slave){
        this.slave = slave;
    }
}