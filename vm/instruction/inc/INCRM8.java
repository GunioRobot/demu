package vm.instruction.inc;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;
import vm.register.EFlags;

import static vm.register.RegisterIndex.*;

public class INCRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rmx = modrm.getRMValue8();
        int result = rmx + 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        FlagCheck.check(rmx, result, eflags, 8);
        eflags.setCarry(isCarry);

        modrm.setRMValue(result);
        vm.addEIP(2);
    }
}