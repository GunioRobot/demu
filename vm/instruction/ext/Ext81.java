package vm.instruction.ext;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import vm.instruction.or.OrRMXImmX;
import vm.instruction.adc.AdcRMXImmX;
import vm.instruction.add.AddRMXImmX;
import vm.instruction.and.AndRMXImmX;
import vm.instruction.comp.CompRMXImmX;
import vm.instruction.sbb.SbbRMXImmX;
import vm.instruction.sub.SubRMXImmX;
import vm.instruction.xor.XorRMXImmX;

public class Ext81 implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Ext81(){
        instructions = new Instruction[8];

        instructions[0] = new AddRMXImmX();
        instructions[1] = new OrRMXImmX();
        instructions[2] = new AdcRMXImmX();
        instructions[3] = new SbbRMXImmX();
        instructions[4] = new AndRMXImmX();
        instructions[5] = new SubRMXImmX();
        instructions[6] = new XorRMXImmX();
        instructions[7] = new CompRMXImmX();
    }

    public void execute(VM vm){
        int code = vm.getModRM(false).getOpecode();
        instruction = instructions[code];
        instruction.execute(vm);
    }

    public String toString(){
        return instruction.toString();
    }
}