package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveZXR32RM16 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value =  modrm.getRMValue() & 0xFFFF;
        modrm.setRegister32(value);

        vm.addEIP(2);
    }
}