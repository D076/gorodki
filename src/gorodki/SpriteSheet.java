package gorodki;

import java.awt.image.BufferedImage;
//Класс для создания листа спрайтов
public class SpriteSheet {
   private BufferedImage sheet;
   private int spriteCount;
   private int scale; //размер спрайта
   private int spritesInWidth;//спрайтов в ширину
   
   public SpriteSheet(BufferedImage sheet, int spriteCount, int scale){
       this.sheet = sheet;
       this.spriteCount = spriteCount;
       this.scale = scale;
       this.spritesInWidth = sheet.getWidth() / scale;
   }
   
   public BufferedImage getSprite(int index){
       index = index % spriteCount;
       
       int x = index % spritesInWidth * scale;
       int y = index / spritesInWidth * scale;
       
       return sheet.getSubimage(x, y, scale, scale);
   }
}
