package vm.instruction.move;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveSXRXRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rm8 = modrm.getRMValue8();
        long rx = ((rm8 & 0x80) > 0) ? -(rm8 & 0x7F) : rm8;

        modrm.setRegisterX(rx);
        vm.addEIP(2);
    }
}