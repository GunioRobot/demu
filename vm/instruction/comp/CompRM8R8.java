package vm.instruction.comp;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class CompRM8R8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rm8 = modrm.getRMValue8();
        int r8 = modrm.getRegister8();

        int result = rm8 - r8;
        FlagCheck.subCheck(rm8, r8, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}