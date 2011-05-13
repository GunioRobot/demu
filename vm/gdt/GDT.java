package vm.gdt;

public class GDT{
    SegmentDescriptor[] descriptors;

    public GDT(){
    }

    public void lgdt(byte[] buff, int start, int count){
        count++;

        descriptors = new SegmentDescriptor[count];
        for(int  i = 0; i < count; i++){
            descriptors[i] = new SegmentDescriptor(buff, start + (i * 8));
        }
    }

    public SegmentDescriptor get(int index){
        return descriptors[index];
    }
}