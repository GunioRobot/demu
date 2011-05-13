package vm.instruction.neg;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class NegX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();

        if(value == 0){
            vm.getEFlags().setCarry(false);
        }else{
            vm.getEFlags().setCarry(true);
        }

        modrm.setRMValue(value);

        vm.addEIP(2);
    }
}