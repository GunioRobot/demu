package vm.instruction.pop;

import vm.VM;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class PopA implements Instruction{
    public void execute(VM vm){
        vm.setRegisterX(EDI, vm.popX());
        vm.setRegisterX(ESI, vm.popX());
        vm.setRegisterX(EBP, vm.popX());
        vm.popX();
        vm.setRegisterX(EBX, vm.popX());
        vm.setRegisterX(EDX, vm.popX());
        vm.setRegisterX(ECX, vm.popX());
        vm.setRegisterX(EAX, vm.popX());

        vm.addEIP(1);
    }
}