package vm.modrm;

import vm.VM;

public class MemoryX extends RM{
    private int address;

    public MemoryX(VM vm, int address, int size){
        super(vm);
        this.address = address;
        super.size = size;
    }

    public void set(long value){
        vm.setDataX(address, value);
    }

    public long get(){
        return vm.getDataX(address);
    }
}