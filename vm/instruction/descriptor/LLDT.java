package vm.instruction.descriptor;

import vm.VM;
import vm.instruction.Instruction;

public class LLDT implements Instruction{
    public void execute(VM vm){
        vm.addEIP(3);
    }
}