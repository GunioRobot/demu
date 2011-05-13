package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;
import vm.register.EFlags;;
import static vm.register.RegisterIndex.EIP;

public class JLEX implements Instruction{
    public void execute(VM vm){
        EFlags eflags = vm.getEFlags();

        if(eflags.isSign() != eflags.isOverflow() || eflags.isZero()){
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