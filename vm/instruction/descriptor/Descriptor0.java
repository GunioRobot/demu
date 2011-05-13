package vm.instruction.descriptor;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import vm.instruction.task.LTR;

public class Descriptor0 implements Instruction{
    private Instruction[] instructions;

    public Descriptor0(){
        instructions = new Instruction[8];

        instructions[2] = new LLDT();
        instructions[3] = new LTR();
    }

    public void execute(VM vm){
        int opecode = vm.getModRM().getOpecode();

        instructions[opecode].execute(vm);
    }
}