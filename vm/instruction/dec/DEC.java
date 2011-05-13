package vm.instruction.dec;

import vm.VM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;
import vm.register.EFlags;

import static vm.register.RegisterIndex.*;

public class DEC implements Instruction{
    private int index;

    public DEC(int index){
        this.index = index;
    }

    public void execute(VM vm){
        long register = vm.getRegisterX(index);
        long result = register - 1;

        EFlags eflags = vm.getEFlags();
        boolean isCarry = eflags.isCarry();
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(register, result, eflags, size);
        eflags.setCarry(isCarry);

        vm.setRegisterX(index,  result);
        vm.addEIP(1);
    }
}