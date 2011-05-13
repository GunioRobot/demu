package vm.instruction.ret;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;

public class NearReturn implements Instruction{
    public void execute(VM vm){
        int eip = vm.popX();
        vm.setRegisterX(EIP, eip);
    }
}