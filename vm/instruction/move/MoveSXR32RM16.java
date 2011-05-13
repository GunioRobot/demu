package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveSXR32RM16 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rm16 = (modrm.getRMValue() & 0xFFFF);
        long r32 = ((rm16 & 0x8000) > 0) ? -(rm16 & 0x7FFF) : rm16;

        modrm.setRegister32(r32);
        vm.addEIP(2);
    }
}