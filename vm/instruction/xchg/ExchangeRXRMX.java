package vm.instruction.xchg;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class ExchangeRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long temp = modrm.getRegisterX();
        modrm.setRegisterX(modrm.getRMValue());
        modrm.setRMValue(temp);

        vm.addEIP(2);
    }
}