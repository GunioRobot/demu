package vm.instruction.move;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EIP;

public class MoveALMOffs implements Instruction{
    public void execute(VM vm){
        int data = (int)vm.getDataX((int)vm.getCodeX(1));
        vm.setRegister8(EAX, data);

        if(vm.is32bitAddress()){
            vm.addEIP(5);
        }else{
            vm.addEIP(3);
        }
    }
}