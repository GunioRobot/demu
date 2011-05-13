package vm.instruction.imul;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class IMulRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rx = modrm.getRegisterX();
        long rmx = modrm.getRMValue();
        long result = rx * rmx;
        
        if(result != (int)result){
            vm.getEFlags().setOverflow(true);
            vm.getEFlags().setSign(true);
        }

        modrm.setRegisterX(result);
        vm.addEIP(2);
    }
}