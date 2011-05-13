package vm.instruction.call;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;
import static vm.register.RegisterIndex.CS;

public class FarCallMX implements Instruction{
    public void execute(VM vm){
        int address = 0;
        int cs = 0;

        if(vm.is32bitOperand()){
            address = vm.getCode32(1);
            cs = vm.getCode16(5);
        }else{
            address = vm.getCode16(1);
            cs = vm.getCode16(3);
        }

        int addeip = vm.is32bitOperand() ? 7 : 5;
        vm.pushX((int)vm.getRegister32(EIP) + addeip);

        vm.setRegisterX(CS, cs);
        vm.setRegisterX(EIP, address);
    }
}