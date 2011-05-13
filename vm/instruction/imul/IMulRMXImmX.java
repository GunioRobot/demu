package vm.instruction.imul;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class IMulRMXImmX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long imm = vm.getCodeX(2);
        long result = rmx * imm;

        if(result != (int)result){
            vm.getEFlags().setOverflow(true);
            vm.getEFlags().setSign(true);
        }

        modrm.setRMValue(result);
        vm.addEIP(vm.is32bitOperand() ? 6 : 4);
    }
}