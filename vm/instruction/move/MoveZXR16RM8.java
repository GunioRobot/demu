package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveZXR16RM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value =  modrm.getRMValue8();
        modrm.setRegister16(value);

        vm.addEIP(2);
    }
}
