package vm.instruction.move;

import vm.VM;
import vm.view.VMView;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class MoveRM8R8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();

        modrm.setRMValue8(modrm.getRegister8());
        vm.addEIP(2);
    }
}