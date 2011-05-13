package vm.instruction.move;

import vm.VM;
import vm.view.VMView;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveRMXRX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();

        modrm.setRMValue(modrm.getRegisterX());
        vm.addEIP(2);
    }
}