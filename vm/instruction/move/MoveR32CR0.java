package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveR32CR0 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM(false);
        modrm.setRegister32(vm.getCR0());

        vm.addEIP(2);
    }
}