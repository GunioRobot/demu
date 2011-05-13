package vm.instruction.rotate;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class ROR8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int imm = vm.getCode8(2);

        value = (value >> imm) | (value & ((1 << imm) - 1)) << (8 - imm);

        modrm.setRMValue8(value);
        vm.addEIP(3);
    }
}