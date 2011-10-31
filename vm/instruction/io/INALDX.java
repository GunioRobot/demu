package vm.instruction.io;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EDX;

public class INALDX implements Instruction{
    public void execute(VM vm){
        int port = vm.getRegister16(EDX);

        System.out.printf("port = %x\n", port);

        if(port == 6){
            vm.setRegister8Low(EAX, 0);
        }else if(port == 0x60){
            vm.setRegister8Low(EAX, 0);
        }else if(port == 0x64){

        }else{
            throw new RuntimeException("IN port = " + Integer.toHexString(port));
        }

        vm.addEIP(1);
    }
}