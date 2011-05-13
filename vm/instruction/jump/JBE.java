package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;

public class JBE implements Instruction{
    public void execute(VM vm){
        if(vm.getEFlags().isZero() || vm.getEFlags().isCarry()){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}