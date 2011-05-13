package vm.instruction.prefix;

import vm.VM;
import vm.instruction.Instruction;
import static vm.register.RegisterIndex.*;

public class RepeatNE implements Instruction{
    public void execute(VM vm){
        int code = vm.getCode8(1);
        vm.addEIP(1);
        int eip = (int)vm.getRegisterX(EIP);

        Instruction instruction = vm.getInstruction(code);

        if(instruction == null){
            throw new RuntimeException("Not Implement Opecode = " + code);
        }

        while(vm.getRegisterX(ECX) > 0 && !vm.getEFlags().isZero()){
            vm.setRegisterX(EIP, eip);
            instruction.execute(vm);
            vm.setRegisterX(ECX, vm.getRegisterX(ECX) - 1);
        }
    }
}