package vm.instruction.div;

import vm.VM;
import vm.modrm.ModRM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.EAX;
import static vm.register.RegisterIndex.EDX;

public class IDivX implements Instruction{
    public void execute(VM vm){
        ModRM modrm = vm.getModRM();
        long value = modrm.getRMValue();

        if(value == 0){
            throw new RuntimeException("Divide Error: Divide 0!");
        }

        long eax = vm.getRegisterX(EAX);
        long edx = vm.getRegisterX(EDX);
        long divsrc;

        if(vm.is32bitOperand()){
            divsrc = ((edx & 0x7FFFFFFF) << 32) | (eax & 0xFFFFFFFF);
            if((edx | 0x80000000) > 0){
                divsrc = -divsrc;
            }
        }else{
            divsrc = (edx & 0x7FFF) << 16 + (eax & 0xFFFF);
            if((edx | 0x8000) > 0){
                divsrc = -divsrc;
            }
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