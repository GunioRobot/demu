package vm.instruction.comp;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class CompRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rmx = (int)modrm.getRMValue();
        int rx = (int)modrm.getRegisterX();

        long result = rmx - rx;
        FlagCheck.subCheck(rmx, rx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);

        vm.addEIP(2);
    }
}