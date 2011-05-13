package vm.instruction.flag;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class STC implements Instruction{
    public void execute(VM vm){
        vm.getEFlags().setCarry(true);

        vm.addEIP(1);
    }
}