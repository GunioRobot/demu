package vm.instruction.test;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

public class TestX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long src = modrm.getRMValue();
        long dest = vm.getCodeX(2);

        long result = src & dest;
        int size = vm.is32bitOperand() ? 32 : 16;
        FlagCheck.check(src, result, vm.getEFlags(), size);

        if(vm.is32bitOperand()){
            vm.addEIP(6);
        }else{
            vm.addEIP(4);
        }
    }
}