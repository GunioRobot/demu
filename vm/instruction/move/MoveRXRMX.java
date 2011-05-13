package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();

        modrm.setRegisterX(modrm.getRMValue());
        vm.addEIP(2);
    }
}