package vm.instruction.sub;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class SubRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rx = modrm.getRegisterX();
        long rmx = modrm.getRMValue();

        long result = rx - rmx;
        FlagCheck.subCheck(rx, rmx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRegisterX(result);

        vm.addEIP(2);
    }
}