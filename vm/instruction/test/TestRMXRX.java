package vm.instruction.test;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class TestRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRegisterX();
        long target = modrm.getRMValue();

        long result = target & value;
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(target, result, vm.getEFlags(), size);

        vm.addEIP(2);
    }
}