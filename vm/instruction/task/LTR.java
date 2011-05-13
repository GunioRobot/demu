package vm.instruction.task;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class LTR implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int value = (int)modrm.getMemoryAddress();
        vm.ltr(value);

        vm.addEIP(2);
    }
}