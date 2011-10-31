package vm.instruction.move;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class MoveAXMOffs implements Instruction{
    public void execute(VM vm){
        long data = vm.getDataX((int)vm.getCodeX(1));
        vm.setRegisterX(EAX, data);

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }


    }
}