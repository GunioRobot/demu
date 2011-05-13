package vm.instruction.ret;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;
import static vm.register.RegisterIndex.ESP;

public class NearReturnPop implements Instruction{
    public void execute(VM vm){
        int eip = vm.popX();
        int count = vm.getCode16(1);

        vm.setRegisterX(EIP, eip);
        vm.setRegisterX(ESP, vm.getRegisterX(ESP) + count);
    }
}