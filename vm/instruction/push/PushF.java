package vm.instruction.push;

import vm.VM;
import vm.instruction.Instruction;

public class PushF implements Instruction{
    public void execute(VM vm){
        vm.pushX(vm.getEFlags().get());
        vm.addEIP(1);
    }
}