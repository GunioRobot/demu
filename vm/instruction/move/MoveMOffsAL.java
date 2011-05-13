package vm.instruction.move;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class MoveMOffsAL implements Instruction{
    public void execute(VM vm){
        vm.setData8((int)vm.getCodeX(1), vm.getRegister8(EAX));

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}