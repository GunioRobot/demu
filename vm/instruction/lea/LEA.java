package vm.instruction.lea;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class LEA implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        modrm.setRegisterX(modrm.getMemoryAddress());

        vm.addEIP(2);
    }
}