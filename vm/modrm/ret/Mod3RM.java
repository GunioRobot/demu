package vm.modrm.ret;

import vm.VM;
import vm.modrm.RM;
import vm.modrm.Register16;
import vm.modrm.Register32;

public class Mod3RM implements RMReturn{
    private int rm;

    public Mod3RM(int rm){
        this.rm = rm;
    }

    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Register32(vm, rm);
        }else{
            return new Register16(vm, rm);
        }
    }
}