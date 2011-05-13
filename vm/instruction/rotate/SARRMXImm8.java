package vm.instruction.rotate;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class SARRMXImm8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = (int)modrm.getRMValue();
        int imm = vm.getCode8(2);
        long result = value >> imm;
        
        modrm.setRMValue(result);
        vm.addEIP(3);
    }
}