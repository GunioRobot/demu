package vm.instruction.ext;

import vm.VM;
import vm.instruction.Instruction;
import vm.instruction.inc.INCRMX;
import vm.instruction.dec.DECRMX;
import vm.instruction.call.CallRMX;
import vm.instruction.call.FarCallMX;
import vm.instruction.jump.JumpRMX;
import vm.instruction.jump.FarJumpMX;
import vm.instruction.push.PushRMX;

public class ExtFF implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtFF(){
        instructions = new Instruction[8];

        instructions[0] = new INCRMX();
        instructions[1] = new DECRMX();
        instructions[2] = new CallRMX();
        instructions[3] = new FarCallMX();
        instructions[4] = new JumpRMX();
        instructions[5] = new FarJumpMX();
        instructions[6] = new PushRMX();
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