package vm.instruction.io;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EDX;

public class OUTDXAL implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int port = vm.getRegister16(EDX);

        if(port == 0x20 || port == 0x21 || port == 0xa0 || port == 0xa1){

        }else if(port == 0x40 || port == 0x43){

        }else if(port == 0x60){
            if(al == 0x47){
            }else if(al == 0xdf){
            }else if(al == 0xf4){
            }else{
                throw new RuntimeException("port = " + Integer.toHexString(port) + ", value = " + Integer.toHexString(al));
            }
        }else if(port == 0x64){
            if(al == 0x60){
            }else if(al == 0xd1){
            }else if(al == 0xd4){
            }else{
                throw new RuntimeException("port = " + Integer.toHexString(port) + ", value = " + Integer.toHexString(al));
            }
        }else if(port == 0x3c8){
        }else if(port == 0x3c9){
            vm.addColorElement(al);
        }else{
            throw new RuntimeException("port = " + Integer.toHexString(port) + ", value = " + Integer.toHexString(al));
        }

        vm.addEIP(1);
    }
}