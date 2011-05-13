package vm.instruction.jump;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;

public class JumpRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long address = modrm.getRMValue();

        vm.setRegisterX(EIP, address);
    }
}