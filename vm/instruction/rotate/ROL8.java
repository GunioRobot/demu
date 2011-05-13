package vm.instruction.rotate;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class ROL8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int imm = vm.getCode8(2);

        value <<= imm;
        value |= value >> 8;

        modrm.setRMValue8(value);
        vm.addEIP(3);
    }
}