package vm.instruction.call;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;

public class RelativeNearCall16 implements Instruction{
    public void execute(VM vm){
        int address = vm.getSignedCodeX(1);
        int addeip = vm.is32bitOperand() ? 5 : 3;
        vm.pushX((int)vm.getRegister32(EIP) + addeip);

        vm.addEIP(address);
        vm.addEIP(addeip);
    }
}