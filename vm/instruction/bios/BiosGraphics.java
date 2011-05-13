package vm.instruction.bios;

import vm.VM;
import vm.view.VMView;
import vm.instruction.Instruction;

import static vm.register.RegisterIndex.*;

public class BiosGraphics implements Instruction{
    private static int[] PLANE_PALETTE = {
        0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xAA5500,
        0xAAAAAA, 0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF,
        0xFFFF55, 0xFFFFFF
    }; 

    public void execute(VM vm){
        int al = vm.getRegister8Low(EAX);
        int ah = vm.getRegister8High(EAX);
        int ax = vm.getRegister16(EAX);
        int es = vm.getRegister16(ES);
        int di = vm.getRegister16(EDI);

        if(ah == 0){
            VMView view = vm.getView();

            if(al == 0x03){
                view.setSize(80 * 8, 25 * 16);
            }else if(al == 0x12){
                view.setSize(640, 480);
                view.setMode(1);
            }else{
                throw new RuntimeException("Not Implemented Bios Graphics AH = 0, AL = " + Integer.toHexString(al));
            }
        }else if(ah == 0x02){
            VMView view = vm.getView();
            int dl = vm.getRegister8Low(EDX);
            int dh = vm.getRegister8High(EDX);

            if(dl == 0){
                view.setCursor(dl, dh + 1);
            }
        }else if(ah == 0x03){
            vm.setRegister16(EAX, 0);
            vm.setRegister16(ECX, 0);
            vm.setRegister16(EDX, 0);
        }else if(ah == 0x09){
            char c = (char)al;
            int count = vm.getRegister16(ECX);

            VMView view = vm.getView();

            for(int i = 0; i < count; i++){
                view.putCharacter(c);
            }
        }else if(ah == 0x0C){
            VMView view = vm.getView();
            int color = PLANE_PALETTE[al];
            view.setRGB(vm.getRegister16(ECX), vm.getRegister16(EDX), color);
            view.repaint();
        }else if(ah == 0x0e){
            char c = (char)al;

            VMView view = vm.getView();
            view.putCharacter(c);
        }else if(ah == 0x0f){
            vm.setRegister8High(EAX, 24);
            vm.setRegister8Low(EAX, 0x12);
            vm.setRegister8High(EBX, 1);
        }else if(ax == 0x4f00){
            vm.setRegister16(EAX, 0x4f);
            vm.set16(es * 16 + di + 4, 0x0200);
        }else if(ax == 0x4f01){
            vm.setRegister16(EAX, 0x4f);

            int address = es * 16 + di;
            vm.set8(address, 0x80);
            vm.set16(address + 0x12, 1024);
            vm.set16(address + 0x14, 768);
            vm.set8(address + 0x19, 0x08);
            vm.set8(address + 0x1b, 0x04);
            vm.set32(address + 0x28, 0xa000000);
        }else if(ax == 0x4f02 && vm.getRegister16(EBX) == 0x4105){
            VMView view = vm.getView();
            view.setSize(1024, 768);
        }else if(ax == 0x4f02 && vm.getRegister16(EBX) == 0x4112){
            VMView view = vm.getView();
            view.setSize(640, 480);
        }else{
            throw new RuntimeException("Not Implemented Bios Graphics AH = " + Integer.toHexString(ah));
        }
    }
}