package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class RelativeNearJump implements Instruction{
    public void execute(VM vm){
        int diff = vm.getSignedCodeX(1);
        vm.addEIP(diff);

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}