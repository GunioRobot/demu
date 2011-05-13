package vm.instruction.push;

import vm.VM;
import vm.instruction.Instruction;

public class PushImm8 implements Instruction{
    public void execute(VM vm){
        int imm = vm.getSignedCode8(1);
        vm.pushX(imm);

        vm.addEIP(2);
    }
}