package vm.instruction.comp;

import vm.VM;
import vm.modrm.ModRM;
import vm.util.FlagCheck;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EIP;

public class CompRXRMX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long rx = modrm.getRegisterX() & 0xFFFFFFFFL;
        long rmx = modrm.getRMValue();
/*
        if(vm.getFlag()){
            System.out.printf("rx = %x, rmx = %x; ", rx, rmx);
        }
*/
        long result = rx - rmx;
        FlagCheck.subCheck(rx, rmx, result, vm.getEFlags(), vm.is32bitOperand() ? 32 : 16);

        vm.addEIP(2);
    }
}