package vm.instruction.sbb;

import vm.VM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class SbbALImm8 implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int imm = vm.getCode8(1);

        int result = al - imm - (vm.getEFlags().isCarry() ? 1 : 0);
        FlagCheck.subCheck(al, imm, result, vm.getEFlags(), 8);
        vm.setRegister8(EAX, result);

        vm.addEIP(2);
    }
}