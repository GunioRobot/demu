package vm.instruction.push;

import vm.VM;
import vm.instruction.Instruction;

public class Push implements Instruction{
    private final int index;

    public Push(int index){
        this.index = index;
    }

    public void execute(VM vm){
        vm.pushX((int)vm.getRegisterX(index));

        vm.addEIP(1);
    }
}