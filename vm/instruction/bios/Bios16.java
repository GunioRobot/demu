package vm.instruction.bios;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.*;

public class Bios16 implements Instruction{
    public void execute(VM vm){
        int ax = vm.getRegister16(EAX);

        if(vm.getRegister8High(EAX) == 0x10){
            vm.setRegister8High(EAX, 0);
            vm.setRegister8Low(EAX, 'A');
        }else if(vm.getRegister8High(EAX) == 0x11){
            vm.getEFlags().setZero(false);
        }else if(ax == 0x0200){
            vm.setRegister16(EAX, 0x0700);
        }else{
            throw new RuntimeException("Bios16 not implement function AX = " + Integer.toHexString(ax));
        }
    }
}