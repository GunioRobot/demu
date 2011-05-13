package vm.instruction.pop;

import vm.VM;
import vm.instruction.Instruction;

public class PopF implements Instruction{
    public void execute(VM vm){
        vm.getEFlags().set(vm.popX());
        vm.addEIP(1);
    }
}