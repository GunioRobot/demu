package vm.modrm;

import vm.VM;

public class Memory16 extends RM{
    private int address;

    public Memory16(VM vm, int address, int size){
        super(vm);
        this.address = address;
        super.size = size;
    }
    
    @Override
    public void set8(int value){
        vm.setData32(address, (int)value);
    }
    
    @Override
    public int get8(){
        return vm.getData8(address);
    }

    public void set(long value){
        vm.setData16(address, (int)value);
    }

    public long get(){
        return vm.getData16(address);
    }
}