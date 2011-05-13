package vm.instruction.div;

import vm.VM;
import vm.instruction.Instruction;
import vm.util.VMUtil;

import static vm.register.RegisterIndex.EAX;

public class IDiv8 implements Instruction{
    public void execute(VM vm){
        int modrm = vm.getModRM();
        int value = VMUtil.getModRMData8(vm, modrm, true);

        if(value == 0){
            throw new RuntimeException("Divide Error: Divide 0!");
        }

        int ax = vm.getRegister16(EAX);

        if((ax & 0x8000) > 0){
            ax &= 0x7FFF;
            ax = -ax;
        }

        int result = ax / value;
        int mod = ax % value;

        if(result > 0x7F){
            throw new RuntimeException("Divide Error: result > 0x7FFF");
        }else if(result < -0x80){
            throw new RuntimeException("Divide Error: result < 0x8000");
        }

        vm.setRegister8Low(EAX, result);
        vm.setRegister8High(EAX, mod);

        vm.addEIP(2);
    }
}