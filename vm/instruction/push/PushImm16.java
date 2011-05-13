package vm.instruction.push;

import vm.VM;
import vm.instruction.Instruction;

public class PushImm16 implements Instruction{
    public void execute(VM vm){
        int imm = (int)vm.getCodeX(1);
        vm.pushX(imm);

        if(vm.is32bitOperand()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}