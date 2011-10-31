package vm.instruction.add;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class AddRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long rx = modrm.getRegisterX();

        long result = rmx + rx;
        FlagCheck.addCheck(rmx, rx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        modrm.setRMValue(result);

        vm.addEIP(2);
    }
}