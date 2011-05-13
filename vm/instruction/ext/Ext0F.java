package vm.instruction.ext;

import vm.VM;
import vm.instruction.Instruction;
import vm.instruction.descriptor.Descriptor0;
import vm.instruction.descriptor.Descriptor;
import vm.instruction.imul.IMulRXRMX;
import vm.instruction.jump.JBX;
import vm.instruction.jump.JEX;
import vm.instruction.jump.JLX;
import vm.instruction.jump.JSX;
import vm.instruction.jump.JBEX;
import vm.instruction.jump.JLEX;
import vm.instruction.jump.JNEX;
import vm.instruction.jump.JNLX;
import vm.instruction.jump.JNLEX;
import vm.instruction.move.MoveCR0R32;
import vm.instruction.move.MoveR32CR0;
import vm.instruction.move.MoveZXR16RM8;
import vm.instruction.move.MoveZXR32RM16;
import vm.instruction.move.MoveSXRXRM8;
import vm.instruction.move.MoveSXR32RM16;
import vm.instruction.push.Push;
import vm.instruction.pop.Pop;
import vm.instruction.set.SetBE;
import vm.instruction.set.SetC;
import vm.instruction.set.SetNG;
import vm.instruction.set.SetNZ;
import vm.instruction.set.SetNLE;
import vm.instruction.set.SetTL;

import static vm.register.RegisterIndex.*;

public class Ext0F implements Instruction{
    private Instruction[] instructions;
    private Instruction instruction;

    public Ext0F(){
        instructions = new Instruction[256];

        instructions[0x00] = new Descriptor0();
        instructions[0x01] = new Descriptor();
        instructions[0x20] = new MoveR32CR0();
        instructions[0x22] = new MoveCR0R32();
        instructions[0x82] = new JBX();
        instructions[0x84] = new JEX();
        instructions[0x86] = new JBEX();
        instructions[0x85] = new JNEX();
        instructions[0x88] = new JSX();
        instructions[0x8C] = new JLX();
        instructions[0x8D] = new JNLX();
        instructions[0x8E] = new JLEX();
        instructions[0x8F] = new JNLEX();
        instructions[0x92] = new SetC();
        instructions[0x95] = new SetNZ();
        instructions[0x96] = new SetBE();
        instructions[0x9C] = new SetTL();
        instructions[0x9E] = new SetNG();
        instructions[0x9F] = new SetNLE();
        instructions[0xA0] = new Push(FS);
        instructions[0xA1] = new Pop(FS);
        instructions[0xA8] = new Push(GS);
        instructions[0xA9] = new Pop(GS);
        instructions[0xAF] = new IMulRXRMX();
        instructions[0xB6] = new MoveZXR16RM8();
        instructions[0xB7] = new MoveZXR32RM16();
        instructions[0xBE] = new MoveSXRXRM8();
        instructions[0xBF] = new MoveSXR32RM16();
    }

    public void execute(VM vm){
        int code = vm.getCode8(1);
        vm.addEIP(1);

        instruction = instructions[code];
        if(instruction == null){
            throw new RuntimeException("Not Implement Code = " + Integer.toHexString(code));
        }

        instruction.execute(vm);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}