package vm.modrm.ret;

import vm.VM;
import vm.modrm.RM;
import vm.modrm.Memory16;
import vm.modrm.Memory32;

import static vm.register.RegisterIndex.*;

public class Mod1RM1 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, (int)vm.getRegister32(ECX) + vm.getSignedCode8(2), 1);
        }else{
            return new Memory16(vm, vm.getRegister16(EBX) + vm.getRegister16(EDI) + vm.getSignedCode8(2), 1);
        }
    }
}