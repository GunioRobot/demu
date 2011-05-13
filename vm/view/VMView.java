package vm.view;

/**
 * このエミュレータのディスプレイ
 *
 */
public interface VMView{
    //サイズを設定する
    void setSize(int width, int height);
    
    int getRGB(int index);

    void setRGB(int index, int rgb);
    //座標x, yの色を設定する
    void setRGB(int x, int y, int rgb);
    //文字を表示する
    void putCharacter(char c);
    //文字を表示する位置を設定する
    void setCursor(int x, int y);
    //パレットの設定
    void addColorElement(int element);

    int getPaletteSize();

    void setMode(int mode);

    void repaint();
}