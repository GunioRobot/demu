package vm.instruction.move;

import vm.VM;
import vm.instruction.Instruction;

public class MoveR8Imm8 implements Instruction{
    private final int index;

    public MoveR8Imm8(int index){
        this.index = index;
    }

    public void execute(VM vm){
        vm.setRegister8(index, vm.getCode8(1));
        vm.addEIP(2);
    }
}