package vm.instruction.pop;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.ESI;

public class PopMX implements Instruction{
    public void execute(VM vm){
        vm.setDataX(vm.getRegisterX(ESI), vm.popX());

        vm.addEIP(2);
    }
}