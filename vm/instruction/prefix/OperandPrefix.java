package vm.instruction.prefix;

import vm.VM;
import vm.instruction.Instruction;

public class OperandPrefix implements Instruction{
    private Instruction instruction;

    public void execute(VM vm){
        int code = vm.getCode8(1);

        vm.setOperandPrefix(true);
        vm.addEIP(1);

        instruction = vm.getInstruction(code);
        instruction.execute(vm);

        vm.setOperandPrefix(false);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}