package vm.instruction.xchg;

import vm.VM;
import vm.instruction.Instruction;
import vm.util.VMUtil;

import static vm.register.RegisterIndex.EAX;

public class ExchangeRRX implements Instruction{
    private int index;

    public ExchangeRRX(int index){
        this.index = index;
    }

    public void execute(VM vm){
        long temp = vm.getRegisterX(EAX);

        vm.setRegisterX(EAX, vm.getRegisterX(index));
        vm.setRegisterX(index, temp);

        vm.addEIP(1);
    }
}