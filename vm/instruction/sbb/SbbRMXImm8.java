package vm.instruction.sbb;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class SbbRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        int imm = vm.getSignedCode8(2);

        long result = rmx - imm - (vm.getEFlags().isCarry() ? 1 : 0);
        FlagCheck.subCheck(rmx, imm, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRMValue(result);

        vm.addEIP(3);
    }
}