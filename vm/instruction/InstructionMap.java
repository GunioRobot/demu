package vm.instruction;

import vm.instruction.io.*;
import vm.instruction.or.*;
import vm.instruction.adc.*;
import vm.instruction.add.*;
import vm.instruction.and.*;
import vm.instruction.cwd.*;
import vm.instruction.dec.*;
import vm.instruction.inc.*;
import vm.instruction.lea.*;
import vm.instruction.pop.*;
import vm.instruction.ret.*;
import vm.instruction.sbb.*;
import vm.instruction.sub.*;
import vm.instruction.xor.*;
import vm.instruction.call.*;
import vm.instruction.ext.*;
import vm.instruction.bios.*;
import vm.instruction.comp.*;
import vm.instruction.flag.*;
import vm.instruction.imul.*;
import vm.instruction.jump.*;
import vm.instruction.move.*;
import vm.instruction.push.*;
import vm.instruction.test.*;
import vm.instruction.xchg.*;
import vm.instruction.leave.*;
import vm.instruction.prefix.*;

import static vm.register.RegisterIndex.*;

public class InstructionMap{
    private Instruction[] instructions;

    public InstructionMap(){
        instructions = new Instruction[256];
    }

    public void init(){
        instructions[0x00] = new AddRM8R8();
        instructions[0x01] = new AddRMXRX();
        instructions[0x02] = new AddR8RM8();
        instructions[0x03] = new AddRXRMX();
        instructions[0x04] = new AddALImm8();
        instructions[0x05] = new AddAXImmX();
        instructions[0x06] = new Push(ES);
        instructions[0x07] = new Pop(ES);
        instructions[0x08] = new OrRM8R8();
        instructions[0x09] = new OrRMXRX();
        instructions[0x0A] = new OrR8RM8();
        instructions[0x0B] = new OrRXRMX();
        instructions[0x0C] = new OrALImm8();
        instructions[0x0D] = new OrAXImmX();
        instructions[0x0E] = new Push(CS);
        instructions[0x0F] = new Ext0F();

        instructions[0x10] = new AdcRM8R8();
        instructions[0x11] = new AdcRMXRX();
        instructions[0x12] = new AdcR8RM8();
        instructions[0x13] = new AdcRXRMX();
        instructions[0x14] = new AdcALImm8();
        instructions[0x15] = new AdcAXImmX();
        instructions[0x16] = new Push(SS);
        instructions[0x17] = new Pop(SS);
        instructions[0x1E] = new Push(DS);
        instructions[0x1F] = new Pop(DS);

        instructions[0x18] = new SbbRM8R8();
        instructions[0x19] = new SbbRMXRX();
        instructions[0x1A] = new SbbR8RM8();
        instructions[0x1B] = new SbbRXRMX();
        instructions[0x1C] = new SbbALImm8();
        instructions[0x1D] = new SbbAXImmX();

        instructions[0x20] = new AndRM8R8();
        instructions[0x21] = new AndRMXRX();
        instructions[0x22] = new AndR8RM8();
        instructions[0x23] = new AndRXRMX();
        instructions[0x24] = new AndALImm8();
        instructions[0x25] = new AndAXImmX();
        instructions[0x26] = new SegmentPrefix(ES);
        instructions[0x28] = new SubRM8R8();
        instructions[0x29] = new SubRMXRX();
        instructions[0x2A] = new SubR8RM8();
        instructions[0x2B] = new SubRXRMX();
        instructions[0x2C] = new SubALImm8();
        instructions[0x2D] = new SubAXImmX();
        instructions[0x2E] = new SegmentPrefix(CS);

        instructions[0x30] = new XorRM8R8();
        instructions[0x31] = new XorRMXRX();
        instructions[0x32] = new XorR8RM8();
        instructions[0x33] = new XorRXRMX();
        instructions[0x34] = new XorALImm8();
        instructions[0x35] = new XorAXImmX();
        instructions[0x36] = new SegmentPrefix(DS);
        instructions[0x38] = new CompRM8R8();
        instructions[0x39] = new CompRMXRX();
        instructions[0x3A] = new CompR8RM8();
        instructions[0x3B] = new CompRXRMX();
        instructions[0x3C] = new CompALImm8();
        instructions[0x3D] = new CompAXImmX();

        for(int i = 0; i < 8; i++){
            instructions[0x40 + i] = new INC(i);
        }

        for(int i = 0; i < 8; i++){
            instructions[0x48 + i] = new DEC(i);
        }

        for(int i = 0; i < 8; i++){
            instructions[0x50 + i] = new Push(i);
        }

        for(int i = 0; i < 8; i++){
            instructions[0x58 + i] = new Pop(i);
        }

        instructions[0x60] = new PushA();
        instructions[0x61] = new PopA();
        instructions[0x64] = new SegmentPrefix(FS);
        instructions[0x65] = new SegmentPrefix(GS);
        instructions[0x66] = new OperandPrefix();
        instructions[0x67] = new AddressPrefix();
        instructions[0x68] = new PushImm16();
        instructions[0x69] = new IMulRMXImmX();
        instructions[0x6A] = new PushImm8();
        instructions[0x6B] = new IMulRMXImm8();

        instructions[0x72] = new JC();
        instructions[0x73] = new JAE();
        instructions[0x74] = new JE();
        instructions[0x75] = new JNE();
        instructions[0x76] = new JBE();
        instructions[0x77] = new JA();
        instructions[0x78] = new JS();
        instructions[0x79] = new JNS();
        instructions[0x7B] = new JPO();
        instructions[0x7C] = new JL();
        instructions[0x7D] = new JNL();
        instructions[0x7E] = new JLE();
        instructions[0x7F] = new JNLE();

        instructions[0x80] = new Ext80();
        instructions[0x81] = new Ext81();
        instructions[0x83] = new Ext83();
        instructions[0x84] = new TestRM8R8();
        instructions[0x85] = new TestRMXRX();
        instructions[0x86] = new ExchangeR8RM8();
        instructions[0x87] = new ExchangeRXRMX();
        instructions[0x88] = new MoveRM8R8();
        instructions[0x89] = new MoveRMXRX();
        instructions[0x8A] = new MoveR8RM8();
        instructions[0x8B] = new MoveRXRMX();
        instructions[0x8C] = new MoveRM16SReg();
        instructions[0x8D] = new LEA();
        instructions[0x8E] = new MoveSRegRM16();
        instructions[0x8F] = new PopMX();

        for(int i = 0; i < 8; i++){
            instructions[0x90 + i] = new ExchangeRRX(i);
        }

        instructions[0x99] = new CDQ();
        instructions[0x9C] = new PushF();
        instructions[0x9D] = new PopF();

        instructions[0xA0] = new MoveALMOffs();
        instructions[0xA1] = new MoveAXMOffs();
        instructions[0xA2] = new MoveMOffsAL();
        instructions[0xA3] = new MoveMOffsAX();
        instructions[0xA8] = new TestALImm8();
        instructions[0xA9] = new TestAXImmX();

        for(int i = 0; i < 8; i++){
            instructions[0xB0 + i] = new MoveR8Imm8(i);
        }

        for(int i = 0; i < 8; i++){
            instructions[0xB8 + i] = new MoveRXImmX(i);
        }

        instructions[0xC0] = new ExtC0();
        instructions[0xC1] = new ExtC1();
        instructions[0xC3] = new NearReturnPop();
        instructions[0xC3] = new NearReturn();
        instructions[0xC6] = new MoveRM8Imm8();
        instructions[0xC7] = new MoveRMXImmX();
        instructions[0xC9] = new Leave();
        instructions[0xCD] = new Bios();
        instructions[0xCF] = new IRET();

        instructions[0xE4] = new INALImm8();
        instructions[0xE6] = new OUTImm8AL();
        instructions[0xE8] = new RelativeNearCall16();
        instructions[0xE9] = new RelativeNearJump();
        instructions[0xEA] = new FarJump16();
        instructions[0xEB] = new ShortJump8();
        instructions[0xEC] = new INALDX();
        instructions[0xEE] = new OUTDXAL();

        instructions[0xF7] = new ExtF7();
        instructions[0xF8] = new CLC();
        instructions[0xF9] = new STC();
        instructions[0xFA] = new CLI();
        instructions[0xFB] = new STI();
        instructions[0xFC] = new CLD();
        instructions[0xFF] = new ExtFF();
    }

    public Instruction get(int code){
        return instructions[code];
    }
}