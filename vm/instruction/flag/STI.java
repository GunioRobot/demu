package vm.instruction.flag;

import vm.VM;
import vm.register.EFlags;
import vm.instruction.Instruction;

public class STI implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();
        eflags.setInterrupt(true);

        vm.addEIP(1);
    }
}