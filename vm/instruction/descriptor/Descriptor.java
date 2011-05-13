package vm.instruction.descriptor;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class Descriptor implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Descriptor(){
        instructions = new Instruction[8];

        instructions[2] = new LGDT();
        instructions[3] = new LIDT();
    }

    public void execute(VM vm){
        ModRM modrm = vm.getModRM(false);
        int opecode = modrm.getOpecode();

        instruction = instructions[opecode];
        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}