package vm.instruction.rotate;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class RCL8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = modrm.getRMValue8();
        int imm = vm.getCode8(2);

        value |= vm.getEFlags().isCarry() ? 1 << 8 : 0;
        value <<= imm;
        value |= value >> 9;

        if((value & (1 << 8)) > 0){
            vm.getEFlags().setCarry(true);
        }

        modrm.setRMValue8(value);
        vm.addEIP(3);
    }
}