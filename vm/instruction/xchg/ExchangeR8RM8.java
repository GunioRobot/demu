package vm.instruction.xchg;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

public class ExchangeR8RM8 implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        int temp = modrm.getRegister8();
        modrm.setRegister8(modrm.getRMValue8());
        modrm.setRMValue8(temp);

        vm.addEIP(2);
    }
}