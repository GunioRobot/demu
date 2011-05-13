package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveSRegRM16 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        vm.setSRegisterValue(modrm.getRegisterIndex(), (int)modrm.getRMValue() & 0xFFFF);
        vm.addEIP(2);
    }
}