package vm.instruction.test;

import vm.VM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class TestALImm8 implements Instruction{
    public void execute(VM vm){
        int src = vm.getRegister8(EAX);
        int dest = vm.getCode8(1);

        int result = src & dest;
        FlagCheck.check(src, result, vm.getEFlags(), 8);

        vm.addEIP(2);
    }
}