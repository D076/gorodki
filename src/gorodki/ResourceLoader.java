package gorodki;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
//Загрузка изображения из каталога для дальнейшей обработки
public class ResourceLoader {
   public static final String PATH = "res/";
   
   public static BufferedImage loadImage(String fileName){
       BufferedImage image = null;
       
       try{
           image = ImageIO.read(new File(PATH + fileName));
       }catch(IOException e){
           e.printStackTrace();
       }
       
       return image;
   }
}
