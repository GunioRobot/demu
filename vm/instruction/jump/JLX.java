package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;
import vm.register.EFlags;;

public class JLX implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();

        if(eflags.isSign() ^ eflags.isOverflow()){
            int diff = (int)vm.getSignedCodeX(1);
            vm.addEIP(diff);
        }

        if(vm.is32bitOperand()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}