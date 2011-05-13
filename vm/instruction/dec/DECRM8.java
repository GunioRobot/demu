package vm.instruction.dec;

import vm.VM;
import vm.util.FlagCheck;
import vm.modrm.ModRM;
import vm.instruction.Instruction;
import vm.register.EFlags;

import static vm.register.RegisterIndex.*;

public class DECRM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int rmx = modrm.getRMValue8();
        int result = rmx - 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        FlagCheck.check(rmx, result, eflags, 8);
        eflags.setCarry(isCarry);

        modrm.setRMValue8(result);
        vm.addEIP(2);
    }
}