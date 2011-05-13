package vm.instruction.move;

import vm.VM;
import vm.instruction.Instruction;

public class MoveCR0R32 implements Instruction{
    public void execute(VM vm){
        vm.setCR0((int)vm.getModRM(false).getRegister32());
        vm.addEIP(2);
    }
}