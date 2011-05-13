package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveRMXImmX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRMValue(vm.getCodeX(2));
        vm.addEIP(vm.is32bitOperand() ? 6 : 4);
    }
}