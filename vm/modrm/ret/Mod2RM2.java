package vm.modrm.ret;

import vm.VM;
import vm.modrm.RM;
import vm.modrm.Memory16;
import vm.modrm.Memory32;

import static vm.register.RegisterIndex.*;

public class Mod2RM2 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, (int)vm.getRegister32(EDX) + vm.getSignedCode32(2), 4);
        }else{
            return new Memory16(vm, vm.getRegister16(EBP) + vm.getRegister16(ESI) + vm.getSignedCode16(2), 2);
        }
    }
}