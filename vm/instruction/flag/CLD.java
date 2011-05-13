package vm.instruction.flag;

import vm.VM;
import vm.register.EFlags;
import vm.instruction.Instruction;

public class CLD implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();
        eflags.setDirection(false);

        vm.addEIP(1);
    }
}