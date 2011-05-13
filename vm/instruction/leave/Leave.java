package vm.instruction.leave;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.EBP;
import static vm.register.RegisterIndex.ESP;

public class Leave implements Instruction{
    public void execute(VM vm){
        vm.setRegisterX(ESP, vm.getRegisterX(EBP));
        vm.setRegisterX(EBP, vm.popX());

        vm.addEIP(1);
    }
}