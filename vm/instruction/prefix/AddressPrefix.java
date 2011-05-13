package vm.instruction.prefix;

import vm.VM;
import vm.instruction.Instruction;

public class AddressPrefix implements Instruction{
    Instruction instruction;

    public void execute(VM vm){
        int code = vm.getCode8(1);

        vm.setAddressPrefix(true);
        vm.addEIP(1);

        instruction = vm.getInstruction(code);
        instruction.execute(vm);

        vm.setAddressPrefix(false);
    }

    @Override
    public String toString(){
        return instruction.toString();
    }
}