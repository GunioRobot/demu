package vm.instruction.jump;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class FarJump16 implements Instruction{
    public void execute(VM vm){
        int address = 0;
        int cs = 0;

        if(vm.is32bitOperand()){
            address = vm.getCode32(1);
            cs = vm.getCode16(5);
        }else{
            address = vm.getCode16(1);
            cs = vm.getCode16(3);
        }

        vm.setRegister32(EIP, address);
        vm.setRegister16(CS, cs);
    }
}