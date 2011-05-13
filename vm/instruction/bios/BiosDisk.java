package vm.instruction.bios;

import vm.VM;
import vm.instruction.Instruction;
import vm.register.EFlags;

import static vm.register.RegisterIndex.*;

public class BiosDisk implements Instruction{
    public void execute(VM vm){
        int ah = vm.getRegister8High(EAX);
        int al = vm.getRegister8Low(EAX);
        int ch = vm.getRegister8High(ECX);
        int cl = vm.getRegister8Low(ECX);
        int dh = vm.getRegister8High(EDX);
        int dl = vm.getRegister8Low(EDX);
        int ax = vm.getRegister16(EAX);
        int bx = vm.getRegister16(EBX);

        EFlags eflags = vm.getEFlags();

        if(ah == 0x00){
            eflags.setCarry(false);
        }else if(ah == 0x02){
           try{
                int cylinder = ch;
                int head = dh;
                int sector = cl;

                int readStart = cylinder * 2 * 18 * 512;
                readStart += head * 18 * 512;
                readStart += (sector - 1) * 512;

                int start = (vm.getRegister16(ES) << 4) + bx;
                vm.read(readStart, start, 512 * al);

               vm.getEFlags().setCarry(false);
            }catch(Exception e){
                e.printStackTrace();
                vm.getEFlags().setCarry(true);
            }
        }else if(ah == 0x08){
            vm.setRegister16(EAX, 0);
            vm.setRegister16(EBX, 0x02);
            vm.setRegister16(ECX, 0xFFFF);
            vm.setRegister16(EDX, 0x0101);
            vm.getEFlags().setCarry(false);
        }else if(ah == 0x41 && bx == 0x55aa){
            eflags.setCarry(false);
            vm.setRegister8(ECX, cl | 0x01);
            vm.setRegister16(EBX, 0xaa55);
        }else if(ah == 0x42){
            try{
                int esi = (int)vm.getRegisterX(ESI);
                int size = (int)vm.getData16(esi);
                int sector = (int)vm.getData16(esi + 2);
                int buffStart = (int)vm.getData16(esi + 4);
                int selector = (int)vm.getData16(esi + 6);

                int readSector = (int)vm.getData32(esi + 8);
                int start = (selector << 4) + buffStart;

                vm.read(readSector * 2048, start, sector * 2048);

                vm.setRegister8High(EAX, 0);
                vm.getEFlags().setCarry(false);
            }catch(Exception e){
                e.printStackTrace();
                vm.getEFlags().setCarry(true);
            }
        }else if(ax == 0x4B01){
            int esi = (int)vm.getRegisterX(ESI);
            vm.setData8(esi  + 0x00, 0x13);  //パケットのサイズ
            vm.setData8(esi  + 0x01, 0x07);  //メディアタイプ
            vm.setData8(esi  + 0x02, 0x00);  //ドライブ番号
            vm.setData8(esi  + 0x03, 0x00);  //CD-ROMコントローラー番号
            vm.setData32(esi + 0x04, 0x00);  //Logical Block Address of disk image to emulate
            vm.setData16(esi + 0x08, 0x00);
            vm.setData16(esi + 0x0A, 0x00);
            vm.setData16(esi + 0x0C, 0x00);
            vm.setData16(esi + 0x0E, 0x00);
            vm.setData8(esi  + 0x10, 0xFF);
            vm.setData8(esi  + 0x11, 0xFF);
            vm.setData8(esi +  0x12, 0x01);

            vm.getEFlags().setCarry(false);
        }else{
            throw new RuntimeException("Not Implement BiosDisk AH = " + Integer.toHexString(ah));
        }
    }
}