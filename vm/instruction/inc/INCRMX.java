package vm.instruction.inc;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;
import vm.register.EFlags;

import static vm.register.RegisterIndex.*;

public class INCRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rmx = modrm.getRMValue();
        long result = rmx + 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(rmx, result, eflags, size);
        eflags.setCarry(isCarry);

        modrm.setRMValue(result);
        vm.addEIP(2);
    }
}