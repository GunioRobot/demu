package vm.instruction.imul;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EDX;

public class IMulX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long eax = vm.getRegisterX(EAX);
        long rmx = modrm.getRMValue();
        long result = eax * rmx;

        if(result != (int)result){
            vm.getEFlags().setOverflow(true);
            vm.getEFlags().setSign(true);
        }

        vm.setRegisterX(EAX, result & 0xFFFFFFFF);
        vm.setRegisterX(EDX, (result >> 32) & 0xFFFFFFFF);
        vm.addEIP(2);
    }
}