package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;

public class JBX implements Instruction{
    public void execute(VM vm){
        if(vm.getEFlags().isCarry()){
            int diff = (int)vm.getCodeX(1);
            vm.addEIP(diff);
        }

        if(vm.is32bitOperand()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}