package vm.modrm;

import vm.VM;

public class Memory32 extends RM{
    private int address;

    public Memory32(VM vm, int address, int size){
        super(vm);
        this.address = address;
        super.size = size;
    }

    @Override
    public void set8(int value){
        vm.setData8(address, value);
    }

    @Override
    public int get8(){
        return vm.getData8(address);
    }

    public void set(long value){
        vm.setData32(address, value);
    }

    public long get(){
        return vm.getData32(address);
    }

    public long getAddress(){
        return address;
    }
}