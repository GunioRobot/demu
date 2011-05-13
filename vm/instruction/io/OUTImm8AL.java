package vm.instruction.io;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.EAX;

public class OUTImm8AL implements Instruction{
    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int port = vm.getCode8(1);
        if(port == 0x21 || port == 0xa1){

        }else if(port == 0x60){
            if(al != 0xdf){
                throw new RuntimeException("port = " + Integer.toHexString(port) + ", value = " + Integer.toHexString(al));
            }
        }else if(port == 0x64){
            if(al != 0xd1){
                throw new RuntimeException("port = " + Integer.toHexString(port) + ", value = " + Integer.toHexString(al));
            }
        }else{
            throw new RuntimeException("port = " + Integer.toHexString(port) + ", value = " + Integer.toHexString(al));
        }

        vm.addEIP(2);
    }
}