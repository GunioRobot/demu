package vm.instruction.prefix;

import vm.VM;
import vm.instruction.Instruction;

public class Lock implements Instruction{
    public void execute(VM vm){
        vm.addEIP(1);

        Instruction instruction = vm.getInstruction(vm.getCode8(0));
        instruction.execute(vm);
    }
}