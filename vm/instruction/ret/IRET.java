package vm.instruction.ret;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.CS;
import static vm.register.RegisterIndex.EIP;

public class IRET implements Instruction{
    public void execute(VM vm){
        long tempEIP = vm.popX();
        long tempCS = vm.popX();
        long eflags = vm.popX();

        int cs = vm.getRegister16(CS);

        vm.setRegisterX(EIP, tempEIP);

        if((tempCS & 0x3) != (cs & 0x03)){
            vm.setRegisterX(CS, tempCS);
        }

        vm.getEFlags().set((int)eflags);
    }
}