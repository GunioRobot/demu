package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;

public class JA implements Instruction{
    public void execute(VM vm){
        if(!vm.getEFlags().isCarry() && !vm.getEFlags().isZero()){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}