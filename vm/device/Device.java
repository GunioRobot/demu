package vm.device;

public interface Device{
    public void add(byte data);
    public byte poll();
}