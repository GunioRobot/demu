package vm.instruction.imul;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class IMulRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long imm = vm.getSignedCode8(2);
        long result = rmx * imm;

        if(result != (int)result){
            vm.getEFlags().setOverflow(true);
            vm.getEFlags().setSign(true);
        }

        modrm.setRMValue(result);
        vm.addEIP(3);
    }
}