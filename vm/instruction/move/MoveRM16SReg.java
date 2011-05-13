package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveRM16SReg implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int index = modrm.getRegisterIndex();
        int value = vm.getSRegisterValue(index);

        modrm.setRMValue(value);
        vm.addEIP(2);
    }
}