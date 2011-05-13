package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;
import vm.register.EFlags;;

public class JNL implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();

        if(eflags.isSign() == eflags.isOverflow()){
            int diff = vm.getSignedCode8(1);
            vm.addEIP(diff);
        }

        vm.addEIP(2);
    }
}