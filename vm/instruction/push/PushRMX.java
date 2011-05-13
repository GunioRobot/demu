package vm.instruction.push;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class PushRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        vm.pushX((int)modrm.getRMValue());
        vm.addEIP(2);
    }
}