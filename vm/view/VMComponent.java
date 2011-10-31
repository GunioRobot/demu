package vm.view;

import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.image.BufferedImage;

import java.io.IOException;

import static vm.register.RegisterIndex.*;

public class VMComponent extends JComponent implements VMView{
    private BufferedImage image;

    private boolean isTextMode;
    private int cx = 0;
    private int cy = 0;
    private StringBuilder[] lines;
    private Palette palette;

    public VMComponent(){
        lines = new StringBuilder[24];

        for(int i = 0; i < lines.length; i++){
            lines[i] = new StringBuilder();
        }

        isTextMode = false;
        palette = new Palette();
    }

    public void setSize(final int width, final int height){
        Runnable runnable = new Runnable(){
            public void run(){
                image = new BufferedImage(width + 1, height + 1, BufferedImage.TYPE_INT_RGB);

                setPreferredSize(new Dimension(width, height));
                Container container = VMComponent.this;

                while((container = container.getParent()) != null){
                    if(container instanceof JFrame){
                        JFrame frame = (JFrame)container;
                        frame.pack();

                        break;
                    }
                }
            }
        };

        if(SwingUtilities.isEventDispatchThread()){
            runnable.run();
        }else{
            try{
                SwingUtilities.invokeAndWait(runnable);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public int getPaletteSize(){
        return palette.getSize();
    }

    public void setRGB(int index, int rgb){
        index /= 4;

        int x = index % getWidth();
        int y = index / getWidth();

        if(x >= getWidth() || y >= getHeight()){
            return;
        }

        setRGB(x, y, rgb);
    }

    public int getRGB(int index){
        index /= 4;

        int x = index % getWidth();
        int y = index / getWidth();

        if(x >= getWidth() || y >= getHeight()){
            return 0;
        }

        return image.getRGB(x, y);
    }

    private int rgbCount = 0;
    private int rgbElement = 0;

    public void setRGB(int x, int y, int rgb){
        if(palette.getSize() > 0){
            image.setRGB(x, y, palette.getColor(rgb).getInt());
            repaint();
        }else{
            if(rgbCount == 0){
                rgbElement = rgb;
            }else if(rgbCount == 1){
                rgbElement = rgbElement | (rgb << 8);
            }else if(rgbCount == 2){
                rgbElement = rgbElement | rgb << 16;
            }

            rgbCount = (rgbCount + 1) % 3;

            if(rgbCount == 0){
                image.setRGB(x, y, rgbElement);
                repaint();
            }
        }
    }

    public void putCharacter(char c){
        if(c == 0x0a){
            cy++;
            cx = 0;
        }else{
            lines[cy].insert(cx, c);
            cx++;
        }

        repaint();
    }

    public void setCursor(int x, int y){
        cx = x;
        cy = y;
    }

    public void addColorElement(int element){
        palette.addColorElement(element);
        isTextMode = false;
    }

    @Override
    public void paintComponent(Graphics g){
        if(isTextMode){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);

            for(int y = 0; y < lines.length; y++){
                g.drawString(lines[y].toString(), 0, y * 16 + 16);
            }

        }else{
            g.drawImage(image, 0, 0, this);
        }
    }

    public void setMode(int mode){
        if(mode > 0){
            isTextMode = false;
        }
    }
}