package vm.instruction.ext;

import vm.VM;
import vm.instruction.Instruction;
import vm.instruction.rotate.RCL8;
import vm.instruction.rotate.RCR8;
import vm.instruction.rotate.ROL8;
import vm.instruction.rotate.ROR8;
import vm.instruction.rotate.SAL8;
import vm.instruction.rotate.SAR8;
import vm.instruction.rotate.SHR8;

public class ExtC0 implements Instruction{
    private Instruction[] instructions;

    public ExtC0(){
        instructions = new Instruction[8];

        instructions[0x00] = new ROL8();
        instructions[0x01] = new ROR8();
        instructions[0x02] = new RCL8();
        instructions[0x03] = new RCR8();
        instructions[0x04] = new SAL8();
        instructions[0x05] = new SHR8();
        instructions[0x07] = new SAR8();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();

        Instruction instruction = instructions[code];
        if(instruction == null){
            throw new RuntimeException("Not Implement Code = " + code);
        }

        instruction.execute(vm);
    }
}