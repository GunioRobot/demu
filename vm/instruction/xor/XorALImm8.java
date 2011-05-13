package vm.instruction.xor;

import vm.VM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class XorALImm8 implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int imm = vm.getCode8(1);

        int result = al ^ imm;
        FlagCheck.check(al, result, vm.getEFlags(), 8);
        vm.setRegister8(EAX, result);

        vm.addEIP(2);
    }
}