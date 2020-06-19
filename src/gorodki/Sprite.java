package gorodki;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
//Класс для создания спрайтов из листа спрайтов
public class Sprite {
    private SpriteSheet sheet;
    private float scale;//размер спрайта на экране
    private BufferedImage image;
    
    public Sprite(SpriteSheet sheet, float scale){
        this.sheet = sheet;
        this.scale = scale;
        image = sheet.getSprite(0);
        image = Utils.resize(image, (int) (image.getWidth()*scale), (int) (image.getHeight()*scale));
    }
    
    public void render(Graphics2D g, float x, float y){
        g.drawImage(image, (int) (x), (int) (y), null);
    }
}
