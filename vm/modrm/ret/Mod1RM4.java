package vm.modrm.ret;

import vm.VM;
import vm.modrm.RM;
import vm.modrm.Memory16;
import vm.modrm.Memory32;

import static vm.register.RegisterIndex.*;

public class Mod1RM4 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, SIB.getSIB(vm, vm.getCode8(2)) + vm.getSignedCode8(3), 2);
        }else{
            return new Memory16(vm, vm.getRegister16(ESI) + vm.getSignedCode8(2), 1);
        }
    }
}