package vm.instruction.not;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class NotX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = ~modrm.getRMValue();
        modrm.setRMValue(value);

        vm.addEIP(2);
    }
}