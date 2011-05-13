package vm.instruction.add;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class AddR8RM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int r8 = modrm.getRegister8();
        int rm8 = modrm.getRMValue8();

        int result = r8 + rm8;
        modrm.setRegister8(result);
        FlagCheck.addCheck(r8, rm8, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}