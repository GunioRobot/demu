package vm.instruction.ext;

import vm.VM;
import vm.instruction.Instruction;

import vm.instruction.test.TestX;
import vm.instruction.not.NotX;
import vm.instruction.neg.NegX;
import vm.instruction.imul.IMulX;
import  vm.instruction.div.DivX;
import  vm.instruction.div.IDivX;

public class ExtF7 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtF7(){
        instructions = new Instruction[8];

        instructions[0] = new TestX();
        instructions[2] = new NotX();
        instructions[3] = new NegX();

        //instructions[4] = new MulX();
        instructions[5] = new IMulX();

        instructions[6] = new DivX();
        instructions[7] = new IDivX();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();

        instruction = instructions[code];
        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}