package vm.modrm.ret;

import vm.VM;
import vm.modrm.RM;
import vm.modrm.Memory16;
import vm.modrm.Memory32;

import static vm.register.RegisterIndex.*;

public class Mod0RM4 implements RMReturn{
    public RM getRM(VM vm){
        if(vm.is32bitAddress()){
            int count = 1;
            int sib = vm.getCode8(2);

            if((sib & 0x07) == 0x05){
                count += 4;
            }

            return new Memory32(vm, SIB.getSIB(vm, sib), count);
        }else{
            return new Memory16(vm, vm.getRegister16(ESI), 0);
        }
    }
}