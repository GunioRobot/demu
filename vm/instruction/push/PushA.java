package vm.instruction.push;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class PushA implements Instruction{
    public void execute(VM vm){
        int esp = (int)vm.getRegisterX(ESP);

        vm.pushX((int)vm.getRegisterX(EAX));
        vm.pushX((int)vm.getRegisterX(ECX));
        vm.pushX((int)vm.getRegisterX(EDX));
        vm.pushX((int)vm.getRegisterX(EBX));
        vm.pushX(esp);
        vm.pushX((int)vm.getRegisterX(EBP));
        vm.pushX((int)vm.getRegisterX(ESI));
        vm.pushX((int)vm.getRegisterX(EDI));

        vm.addEIP(1);
    }
}