package vm.instruction.sbb;

import vm.VM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class SbbAXImmX implements Instruction{
    public void execute(VM vm){
        long ax = vm.getRegisterX(EAX);
        long imm = vm.getCodeX(1);

        long result = ax - imm  - (vm.getEFlags().isCarry() ? 1 : 0);
        FlagCheck.subCheck(ax, imm, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);
        vm.setRegisterX(EAX, result);

        vm.addEIP(vm.is32bitOperand() ? 5 : 3);
    }
}