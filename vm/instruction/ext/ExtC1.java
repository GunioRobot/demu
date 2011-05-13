package vm.instruction.ext;

import vm.VM;
import vm.instruction.Instruction;
import vm.instruction.rotate.ROLRMXImm8;
import vm.instruction.rotate.SARRMXImm8;
import vm.instruction.rotate.SHLRMXImm8;
import vm.instruction.rotate.SHRRMXImm8;

import vm.modrm.ModRM;

public class ExtC1 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public ExtC1(){
        instructions = new Instruction[8];

        instructions[0x00] = new ROLRMXImm8();
        instructions[0x04] = new SHLRMXImm8();
        instructions[0x05] = new SHRRMXImm8();
        instructions[0x07] = new SARRMXImm8();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();

        instruction = instructions[code];
        if(instruction == null){
            throw new RuntimeException("Not Implement Code = " + code);
        }

        instruction.execute(vm);
    }

    public String toString(){
        return instruction.toString();
    }
}