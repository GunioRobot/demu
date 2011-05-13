package vm.instruction.move;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class MoveMOffsAX implements Instruction{
    public void execute(VM vm){
        vm.setDataX((int)vm.getCodeX(1), vm.getRegisterX(EAX));

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}