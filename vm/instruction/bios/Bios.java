package vm.instruction.bios;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;

public class Bios implements Instruction{
    private Instruction[] instructions;
    private int function;

    public Bios(){
        instructions = new Instruction[256];
        instructions[0x10] = new BiosGraphics();
        instructions[0x12] = new Bios12();
        instructions[0x13] = new BiosDisk();
        instructions[0x15] = new Bios15();
        instructions[0x16] = new Bios16();
    }

    public void execute(VM vm){
        function = vm.getCode8(1);
        Instruction instruction = instructions[function];

        if(instruction == null){
            throw new RuntimeException("Not Implement BIOS function = " + function);
        }

        instruction.execute(vm);
        vm.addEIP(2);
    }

    @Override
    public String toString(){
        return "BIOS " + Integer.toHexString(function);
    }
}