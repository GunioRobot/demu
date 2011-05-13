package vm.instruction.ext;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import vm.instruction.or.OrRM8Imm8;
import vm.instruction.adc.AdcRM8Imm8;
import vm.instruction.add.AddRM8Imm8;
import vm.instruction.and.AndRM8Imm8;
import vm.instruction.comp.CompRM8Imm8;
import vm.instruction.sbb.SbbRM8Imm8;
import vm.instruction.sub.SubRM8Imm8;
import vm.instruction.xor.XorRM8Imm8;

public class Ext80 implements Instruction{
    private Instruction instruction;
    private Instruction[] instructions;

    public Ext80(){
        instructions = new Instruction[8];

        instructions[0] = new AddRM8Imm8();
        instructions[1] = new OrRM8Imm8();
        instructions[2] = new AdcRM8Imm8();
        instructions[3] = new SbbRM8Imm8();
        instructions[4] = new AndRM8Imm8();
        instructions[5] = new SubRM8Imm8();
        instructions[6] = new XorRM8Imm8();
        instructions[7] = new CompRM8Imm8();
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