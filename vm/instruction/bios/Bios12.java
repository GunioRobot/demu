package vm.instruction.bios;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.*;

public class Bios12 implements Instruction{
    public void execute(VM vm){
        vm.setRegister16(EAX, 0x27F);
    }
}