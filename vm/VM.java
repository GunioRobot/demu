package vm;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;
import java.io.RandomAccessFile;
import java.io.IOException;

import vm.view.VMView;
import vm.register.GDTR;
import vm.register.EFlags;
import vm.register.RegisterIndex;
import vm.interrupt.PIC;
import vm.gdt.GDT;

import vm.device.Device;
import vm.device.KeyBoardManager;

import vm.instruction.Instruction;
import vm.instruction.InstructionMap;
import vm.instruction.NotImplementException;

import vm.modrm.ModRM;
import static vm.register.RegisterIndex.*;

public class VM{
    //レジスタを表す配列
    private long[] registers;

    //メモリを表す配列
    private byte[] memory;

    //コントロールレジスタ0
    private int cr0;

    //プロテクトモードかどうか
    private boolean isProtect;

    //32ビットモードかどうか
    private boolean is32bit;

    //オペランドプリフィックスがついているかどうか
    private boolean isOperandPrefix;

    //アドレスプリフィックスがついているかどうか
    private boolean isAddressPrefix;

    //データをアクセスするときに使うセグメントレジスタのインデックス
    private int sindex;

    //ディスプレイ用のビュー
    private VMView view;

    //EFLAGSレジスタ
    private EFlags eflags;

    //GDT
    private GDT gdt;

    //GDTR
    private GDTR gdtr;

    //PIC
    private PIC pic;

    //命令を格納するマップ
    private InstructionMap instMap;

    //ディスクエミュレート用のランダムアクセスファイル
    private RandomAccessFile disk;

    //Task Register
    private int tr;

    //デバイスマップ
    private Map<String, Device> deviceMap;

    public static final String KEYBOARD = "KeyBoard";

    //レジスタ群を初期化するコンストラクタ
    public VM(int memorySize, VMView view){
        registers = new long[RegisterIndex.SIZE];
        memory = new byte[memorySize];
        this.view = view;
        eflags = new EFlags();
        gdt = new GDT();
        gdtr = new GDTR();
        pic = new PIC();

        instMap = new InstructionMap();
        instMap.init();
        deviceMap = initDeviceMap();

        eflags.set(0x02);
        cr0 = 0x10;
        is32bit = false;
        isOperandPrefix = false;
        isAddressPrefix = false;
        sindex = DS;
    }

    //命令の実行を行うメソッド
    public void execute() throws NotImplementException{
        int eip = (int)getRegister32(EIP);
        int code = getCode8(0);

        Instruction instruction = instMap.get(code);
        instruction.execute(this);
    }

    //ブートセクタの読み込みを行う。El Torito専用
    public void load(String fileName, int mode) throws IOException{
        disk = new RandomAccessFile(fileName, "r");

        if(mode == 0){
            disk.read(memory, 0x7C00, 0x200);
        }else if(mode == 1){
           //Boot Record Volume Descriptorを読み込む
           disk.seek(2048 * 17 + 0x47);
           disk.read(memory, 0, 4);
           //Boot Catalogへのポインタ
           int catalog = getCode32(0);

           //Boot Catalogを読み込む
           disk.seek(catalog * 2048 + 0x20);
           disk.read(memory, 0, 2048);

           //ブートセクタと読み込むセクタ数
           int sector = getCode16(6);
           int boot = getCode32(8);

            //ブートセクタから指定されたセクタ数分だけ読み込む
            disk.seek(boot * 2048);
            disk.read(memory, 0x7C00, 2048 * sector);
        }

        //EIPをブートセクタの初期位置の0x7C00にする
        setRegister32(EIP, 0x7C00);
    }

    //ディスクのdiskStartからsizeぶんだけmemoryStartに読み込む
    public void read(int diskStart, int memoryStart, int size) throws IOException{
        disk.seek(diskStart);
        disk.read(memory, memoryStart, size);
    }

    //実行する命令を返す
    public Instruction getInstruction(int index){
        return instMap.get(index);
    }

    public Map<String, Device> initDeviceMap(){
        Map<String, Device> deviceMap = new HashMap<String, Device>();
        deviceMap.put(VM.KEYBOARD, new KeyBoardManager());

        return deviceMap;
    }

    public void putDeviceData(String name, byte data){
        deviceMap.get(name).add(data);
    }

    public byte getDeviceData(String name){
        return deviceMap.get(name).poll();
    }

    //セグメントレジスタの番号をセットする
    public void setSegmentIndex(int sindex){
        this.sindex = sindex;
    }

    //indexから実際にアクセスするアドレスを求める
    public int getCodeAddress(int index){
         int address = 0;

         if(isProtectMode()){
             int gindex = (int)registers[CS] >> 3;
             address = getGDT().get(gindex).getBase() + (int)registers[EIP] + index;
         }else{
             address = (int)registers[CS] * 16 + (int)registers[EIP] + index;
         }

         return address;
    }

    //コードセグメント上の符号付きの1byteのコードを返す
    public int getSignedCode8(int index){
         int address = getCodeAddress(index);

         return memory[address];
    }

    //コードセグメント上の符号無しの1byteのコードを返す
    public int getCode8(int index){
         int address = getCodeAddress(index);

         return memory[address] & 0xFF;
    }

    //コードセグメント上の符号付きの2byteのコードを返す
    public int getSignedCode16(int index){
         int address = getCodeAddress(index);

         int data = memory[address] & 0xFF;
         data |= memory[address + 1] << 8;

         return data;
    }

    //コードセグメント上の符号無し2byteのコードを返す
    public int getCode16(int index){
         int address = getCodeAddress(index);

         int data = memory[address] & 0xFF;
         data |= (memory[address + 1] & 0xFF) << 8;

         return data;
    }

    //コードセグメント上の符号付きの4byteのコードを返す
    public int getSignedCode32(int index){
         int address = getCodeAddress(index);

         int data = memory[address] & 0xFF;
         data |= (memory[address + 1] & 0xFF) << 8;
         data |= (memory[address + 2] & 0xFF) << 16;
         data |= memory[address + 3] << 24;

         return data;
    }

    //コードセグメント上の符号無し4byteのコードを返す
    public int getCode32(int index){
         int address = getCodeAddress(index);

         return get32(address);
    }

    //コードセグメント上の符号付きの2byte又は4byteのコードを返す
    public int getSignedCodeX(int index){
        if(is32bitOperand()){
            return getSignedCode32(index);
        }else{
            return getSignedCode16(index);
        }
    }

    //コードセグメント上の符号無し2byte又は4byteのコードを返す
    public long getCodeX(int index){
        if(is32bitOperand()){
            return getCode32(index);
        }else{
            return getCode16(index);
        }
    }

    //ModRMを返す
    public ModRM getModRM(){
        return new ModRM(getCode8(1), this, true);
    }

    public ModRM getModRM(boolean addeip){
        return new ModRM(getCode8(1), this, addeip);
    }

    //データセグメント上のaddressを返す
    public int getDataAddress(int address){
         if((cr0 & 0x01) == 0x01 && getRegister16(DS) > 0){
            int base = getGDT().get((int)registers[sindex] >> 3).getBase();
            address = base + address;
        }else{
            address = (int)registers[sindex] * 16 + address;
        }

        return address;
    }

    //addressの1byteのデータを返す
    public int get8(int address){
        return (memory[address] & 0xFF);
    }

    //addressの2byteのデータを返す
    public int get16(int address){
        int data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;

        return data;
    }

    //addressの4byteのデータを返す
    public int get32(int address){
        int data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;
        data |= (memory[address + 2] & 0xFF) << 16;
        data |= (memory[address + 3] & 0xFF) << 24;

        return data;
    }

    //addressの4byteのデータを返す
    public long getu32(int address){
        long data = memory[address] & 0xFF;
        data |= (memory[address + 1] & 0xFF) << 8;
        data |= (memory[address + 2] & 0xFF) << 16;
        data |= (long)(memory[address + 3] & 0xFF) << 24;

        return data;
    }

    //現在のデータセグメントにあるaddressの2byte又は4byteのデータを返す
    public long getDataX(int address){
        if(is32bitAddress()){
            return getData32(address);
        }else{
            return getData16(address);
        }
    }

    public int getData8(int address){
        address = getDataAddress(address);

        if(address >= 0xa000000 && address <= 0xb000000){
            VMView view = getView();
            return view.getRGB(address - 0xa000000) & 0xFF;
        }

        return get8(address);
    }

    //現在のデータセグメントにあるaddressの2byteのデータを返す
    public long getData16(int address){
        address = getDataAddress(address & 0xFFFF);

        if(is32bitOperand()){
            return getu32(address);
        }

        return get16(address);
    }

    //現在のデータセグメントにあるaddressの4byteのデータを返す
    public long getData32(int address){
        address = getDataAddress(address);

        if(address >= 0xa000000 && address <= 0xb000000){
            VMView view = getView();
            return view.getRGB(address - 0xa000000);
        }

        if(address >= memory.length){
            return -1;
        }

        return getu32(address);
    }

    //現在のデータセグメントにあるaddressにdataを1byte格納する
    public void set8(int address, int data){
        memory[address] = (byte)(data & 0xFF);
    }

    //現在のデータセグメントにあるaddressにdataを2byte格納する
    public void set16(int address, int data){
        memory[address] = (byte)(data & 0xFF);
        memory[address + 1] = (byte)((data  >> 8) & 0xFF);
    }

    //現在のデータセグメントにあるaddressにdataを4byte格納する
    public void set32(int address, long data){
        memory[address] = (byte)(data & 0xFF);
        memory[address + 1] = (byte)((data  >> 8) & 0xFF);
        memory[address + 2] = (byte)((data  >> 16) & 0xFF);
        memory[address + 3] = (byte)((data  >> 24) & 0xFF);
    }

    //現在のデータセグメントにあるaddressに1byteのdataを設定する
    public void setData8(int address, int data){
        address = getDataAddress(address);

        if(address >= 0xa000000 && address <= 0xb000000){
            VMView view = getView();
            view.setRGB(address - 0xa000000, (int)data);
        }

        if(memory.length > address){
            set8(address, data);
        }
    }

    //現在のデータセグメントにあるaddressに2byteのdataを設定する
    public void setData16(int address, int data){
        address = getDataAddress(address);

        if(address < memory.length){
            if(is32bitMode()){
                set32(address, data);
            }else{
                set16(address, data);
            }
        }
    }

    //現在のデータセグメントにあるaddressに4byteのdataを設定する
    public void setData32(long address, long data){
        address = getDataAddress((int)address);

        if(address >= 0xa000000 && address <= 0xb000000){
            VMView view = getView();

            for(int i = 0; i < 4; i++){
                int value = (int)((data >> (i * 8)) & 0xFF);
                view.setRGB((int)address - 0xa000000 + i, value);
            }
        }

        if(address < memory.length){
            set32((int)address, data);
        }
    }

    //現在のデータセグメントにあるaddressに2byte又は4byteのdataを設定する
    public void setDataX(long address, long data){
        if(is32bitAddress()){
            setData32(address, data);
        }else{
            setData16((int)(address & 0xFFFF), (int)data);
        }
    }

    //8bitレジスタの値を返す。indexによって上位か下位かを決める
    public int getRegister8(int index){
        if(index < 4){
            return (int)registers[index] & 0xFF;
        }else{
            return (int)(registers[index - 4] >> 8) & 0xFF;
        }
    }

    //8bitレジスタ下位の値を返す
    public int getRegister8Low(int index){
        return (int)registers[index] & 0xFF;
    }

    //8bitレジスタ上位の値を返す
    public int getRegister8High(int index){
        return (int)(registers[index] >> 8) & 0xFF;
    }

    //16bit又は32bitレジスタの値を返す
    public long getRegisterX(int index){
        if(is32bitOperand()){
            return getRegister32(index);
        }else{
            return getRegister16(index);
        }
    }

    //16bitレジスタの値を返す
    public int getRegister16(int index){
        return (int)registers[index] & 0xFFFF;
    }

    //32bitレジスタの値を返す
    public long getRegister32(int index){
        return registers[index] & 0xFFFFFFFFL;
    }

    //16bit又は32bitレジスタに値を設定する
    public void setRegisterX(int index, long data){
        if(is32bitOperand()){
            setRegister32(index, data);
        }else{
            setRegister16(index, (int)(data & 0xFFFF));
        }
    }

    //8bitレジスタに値を設定する。indexによって上位か下位かを決める
    public void setRegister8(int index, int data){
        if(index < 4){
            setRegister8Low(index, data);
        }else{
            setRegister8High(index - 4, data);
        }
    }

    //8bitレジスタの下位に値を設定する
    public void setRegister8Low(int index, int data){
        registers[index] &= 0xFFFFFF00;
        registers[index] |= (data & 0xFF);
    }

    //8bitレジスタの上位に値を設定する
    public void setRegister8High(int index, int data){
        registers[index] &= 0xFFFF00FF;
        registers[index] |= (data & 0xFF) << 8;
    }

    //16bitレジスタに値を設定する
    public void setRegister16(int index, int data){
        registers[index] &= 0xFFFF0000;
        registers[index] |= (data & 0xFFFF);
    }

    //32bitレジスタに値を設定する
    public void setRegister32(int index, long data){
        registers[index] = data;
    }

    //セグメントレジスタの値を返す
    public int getSRegisterValue(int index){
        return (int)registers[index + 9];
    }

    //セグメントレジスタに値を設定する
    public void setSRegisterValue(int index, int data){
        registers[index + 9] = (data & 0xFFFF);
    }

    //スタックに1byteデータを積む
    public void push8(int value){
        setRegisterX(ESP, getRegisterX(ESP) - (is32bitOperand() ? 4 : 2));

        if(is32bitOperand()){
            set32((int)registers[ESP], value & 0xFF);
        }else{
            set16((int)registers[ESP], value & 0xFF);
        }
    }

    //スタックに2byteデータを積む
    public void push16(int value){
        setRegisterX(ESP, getRegisterX(ESP) - (is32bitOperand() ? 4 : 2));

        if(is32bitOperand()){
            set32((int)registers[ESP], value & 0xFFFF);
        }else{
            set16((int)registers[ESP], value);
        }
    }

    //スタックに4byteデータを積む
    public void push32(int value){
        setRegisterX(ESP, getRegisterX(ESP) - 4);
        set32((int)registers[ESP], value);
    }

    //スタックに2byte又は4byteデータを積む
    public void pushX(int value){
        if(is32bitOperand()){
            push32(value);
        }else{
            push16(value);
        }
    }

    //スタックから2byteデータを取り出す
    public int pop16(){
        int data = get16((int)registers[ESP]);
        setRegisterX(ESP, getRegisterX(ESP) + (is32bitOperand() ? 4 : 2));

        return data;
    }

    //スタックから4byteデータを取り出す
    public int pop32(){
        int data = get32((int)registers[ESP]);
        setRegisterX(ESP, getRegisterX(ESP) + 4);

        return data;
    }

    //スタックから2byte又は4byteデータを取り出す
    public int popX(){
        if(is32bitOperand() || isProtectMode()){
            return pop32();
        }else{
            return pop16();
        }
    }

    //レジスタ群を返す
    public long[] getRegisters(){
        return registers;
    }

    //メモリの配列を返す
    public byte[] getMemory(){
        return memory;
    }

    //ディスプレイを返す
    public VMView getView(){
        return view;
    }

    //EFlagsを返す
    public EFlags getEFlags(){
        return eflags;
    }

   //PICを返す
    public PIC getPIC(){
        return pic;
    }

    //GDTを返す
    public GDT getGDT(){
        return gdt;
    }

    public void setProtectMode(boolean isProtect){
        this.isProtect = isProtect;
    }

    //プロテクトモードかどうかを返す
    public boolean isProtectMode(){
        return (cr0 & 0x01) == 0x01 && (registers[CS] >= 8 ) && (registers[CS] <= 0x100);
    }

    //32bitモードかどうかを設定する
    public void set32bitMode(boolean is32bit){
        this.is32bit = is32bit;
    }

    //32bitモードかどうかを返す
    public boolean is32bitMode(){
        return is32bit;
    }

    //オペランドプリフィックスがついているかどうかを設定する
    public void setOperandPrefix(boolean isOperandPrefix){
        this.isOperandPrefix = isOperandPrefix;
    }

    //オペランドプリフィックスがついているかどうかを返す
    public boolean isOperandPrefix(){
        return isOperandPrefix;
    }

    //アドレスプリフィックスがついているかどうかを設定する
    public void setAddressPrefix(boolean isAddressPrefix){
        this.isAddressPrefix = isAddressPrefix;
    }

    //アドレスプリフィックスがついているかどうかを返す
    public boolean isAddressPrefix(){
        return isAddressPrefix;
    }

    //オペランドが32bitかどうかを返す
    public boolean is32bitOperand(){
        return isProtectMode() ^ isOperandPrefix();
    }

    //アドレスが32bitかどうかを返す
    public boolean is32bitAddress(){
        return isProtectMode() ^ isAddressPrefix();
    }

    //EIPの値をcountぶんだけ増やす
    public void addEIP(int count){
        int next = (int)getRegister32(EIP) + count;
        setRegister32(EIP, next);
    }

    public void setGDTR(int start, int size){
        gdtr.setGDTR(start, size);
    }

    //CR0を返す
    public int getCR0(){
        return cr0;
    }

    public void lgdt(){
        gdt.lgdt(memory, gdtr.getStart(), gdtr.getSize() / 8);
    }

    //CR0を設定する
    public void setCR0(int cr0){
        if((cr0 & 0x01) == 0x01){
            if((this.cr0 & 0x01) != 0x01){
                lgdt();
            }
        }else{
            is32bit = false;
        }

        this.cr0 = cr0;
    }

    public void ltr(int tr){
        this.tr = tr;
    }

    public void addColorElement(int element){
        view.addColorElement(element);
    }

    //レジスタ群とメモリの一部をダンプする
    public void dump(){
        System.out.println("EAX = " + Long.toHexString(registers[EAX]));
        System.out.println("EBX = " + Long.toHexString(registers[EBX]));
        System.out.println("ECX = " + Long.toHexString(registers[ECX]));
        System.out.println("EDX = " + Long.toHexString(registers[EDX]));
        System.out.println("ESI = " + Long.toHexString(registers[ESI]));
        System.out.println("EDI = " + Long.toHexString(registers[EDI]));
        System.out.println();

        System.out.println("EIP = " + Long.toHexString(registers[EIP]));
        System.out.println("ESP = " + Long.toHexString(registers[ESP]));
        System.out.println("EBP = " + Long.toHexString(registers[EBP]));
        System.out.println();

        System.out.println("CS = " + Long.toHexString(registers[CS]));
        System.out.println("DS = " + Long.toHexString(registers[DS]));
        System.out.println("ES = " + Long.toHexString(registers[ES]));
        System.out.println("FS = " + Long.toHexString(registers[FS]));
        System.out.println("GS = " + Long.toHexString(registers[GS]));
        System.out.println("SS = " + Long.toHexString(registers[SS]));
        System.out.println();

        System.out.print("Next:");

        for(int i = 0; i < 10; i++){
            System.out.print(" " + Integer.toHexString(getCode8(i)));
        }

        System.out.println();
    }
}