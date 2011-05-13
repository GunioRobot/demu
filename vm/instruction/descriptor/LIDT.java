package vm.instruction.descriptor;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.ESP;

public class LIDT implements Instruction{
    public void execute(VM vm){
        int address = (int)vm.getRegister32(ESP) + 0x06;

        int size = vm.get16(address);
        int start = (int)vm.get32(address + 2);

        vm.addEIP(4);
    }
}