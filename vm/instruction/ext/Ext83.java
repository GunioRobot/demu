package vm.instruction.ext;

import vm.VM;
import vm.instruction.Instruction;

import vm.instruction.or.OrRMXImm8;
import vm.instruction.adc.AdcRMXImm8;
import vm.instruction.add.AddRMXImm8;
import vm.instruction.and.AndRMXImm8;
import vm.instruction.comp.CompRMXImm8;
import vm.instruction.sbb.SbbRMXImm8;
import vm.instruction.sub.SubRMXImm8;
import vm.instruction.xor.XorRMXImm8;

public class Ext83 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Ext83(){
        instructions = new Instruction[8];

        instructions[0] = new AddRMXImm8();
        instructions[1] = new OrRMXImm8();
        instructions[2] = new AdcRMXImm8();
        instructions[3] = new SbbRMXImm8();
        instructions[4] = new AndRMXImm8();
        instructions[5] = new SubRMXImm8();
        instructions[6] = new XorRMXImm8();
        instructions[7] = new CompRMXImm8();
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