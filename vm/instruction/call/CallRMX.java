package vm.instruction.call;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;

public class CallRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long address = modrm.getRMValue();
        vm.pushX((int)vm.getRegister32(EIP) + 2);

        vm.setRegisterX(EIP, address);
    }
}