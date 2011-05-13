package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class JSX implements Instruction{
    public void execute(VM vm){
        if(vm.getEFlags().isSign()){
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