package vm.instruction;

import vm.VM;

public interface Instruction{
    void execute(VM vm);
}