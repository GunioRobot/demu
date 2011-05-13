package vm.instruction.descriptor;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.ESP;

public class LGDT implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM(false);
        long address = 0;

        if(vm.isProtectMode()){
            address = modrm.getRMValue();

            address = vm.getRegister32(ESP) + 0x06;
        }else{
            address = vm.getCode16(2);
        }

        int size = vm.is32bitOperand() ? vm.get16((int)address - 2) : (int)vm.getData16((int)address);
        int start = (int)vm.get32((int)address + 2);

        vm.setGDTR(start, size);
        vm.addEIP(vm.isProtectMode() ? 2 : 4);
        vm.lgdt();
    }
}