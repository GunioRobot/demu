package vm.instruction.test;

import vm.VM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class TestAXImmX implements Instruction{
    public void execute(VM vm){
        long eax = vm.getRegisterX(EAX);
        long value = vm.getCodeX(1);

        long result = eax & value;
        FlagCheck.check(eax, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);

        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}