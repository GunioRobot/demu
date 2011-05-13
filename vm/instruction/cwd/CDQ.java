package vm.instruction.cwd;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EDX;

public class CDQ implements Instruction{
    public void execute(VM vm){
        int eax = (int)vm.getRegister32(EAX);
        if(eax < 0){
            vm.setRegister32(EDX, 0xFFFFFFFF);
        }else{
            vm.setRegister32(EDX, 0);
        }

        vm.addEIP(1);
    }
}