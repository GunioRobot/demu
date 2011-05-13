package vm.instruction.xor;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class XorR8RM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int r8 = modrm.getRegister8();
        int rm8 = modrm.getRMValue8();

        int result = r8 ^ rm8;
        FlagCheck.check(r8, result, vm.getEFlags(), 8);
        modrm.setRegister8(result);

        vm.addEIP(2);
    }
}