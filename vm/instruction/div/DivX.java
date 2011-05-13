package vm.instruction.div;

import vm.VM;
import vm.instruction.Instruction;
import vm.util.VMUtil;

import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EDX;

public class DivX implements Instruction{
    public void execute(VM vm){
        int modrm = vm.getModRM();
        long value = VMUtil.getModRMDataX(vm, modrm, true);

        if(value == 0){
            throw new RuntimeException("Divide Error: Divide 0!");
        }

        long eax = vm.getRegisterX(EAX);
        long edx = vm.getRegisterX(EDX);
        long divsrc;

        if(vm.is32bitOperand()){
            divsrc = ((edx & 0xFFFFFFFF) << 32) | (eax & 0xFFFFFFFF);
        }else{
            divsrc = (edx & 0xFFFF) << 16 + (eax & 0xFFFF);
        }

        long result = divsrc / value;
        long mod = divsrc % value;

        if(vm.is32bitOperand()){
            if(result > 0xFFFFFFFFL){
                throw new RuntimeException("Divide Error: result > 0xFFFFFFFF");
            }
        }else{
            if(result > 0xFFFF){
                throw new RuntimeException("Divide Error: result > 0xFFFF");
            }
        }

        vm.setRegisterX(EAX, result);
        vm.setRegisterX(EDX, mod);

        vm.addEIP(2);
    }
}