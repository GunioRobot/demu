package vm.instruction.add;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class AddRM8Imm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rm8 = modrm.getRMValue8();
        int imm = vm.getCode8(2);

        int result = rm8 + imm;
        FlagCheck.addCheck(rm8, imm, result, vm.getEFlags(), 8);
        modrm.setRMValue8(result);

        vm.addEIP(3);
    }
}