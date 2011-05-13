package vm.instruction.test;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class TestRM8R8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int src = modrm.getRMValue8();
        int dest = modrm.getRegister8();

        int result = src & dest;
        FlagCheck.check(src, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}