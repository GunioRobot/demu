package vm.modrm.ret;

import vm.VM;
import vm.modrm.RM;
import vm.modrm.Memory16;
import vm.modrm.Memory32;

import static vm.register.RegisterIndex.*;

public class Mod0RM5 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            return new Memory32(vm, vm.getSignedCode32(2), 4);
        }else{
            return new Memory16(vm, vm.getRegister16(EDI), 0);
        }
    }
}