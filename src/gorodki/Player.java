package gorodki;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
//Класс игрока, управляющий битой
public class Player extends Entity{
    public static final int SPRITE_SCALE = 98;//98 пикселей
    public static final int SPRITES_PER_HEADING = 1;
    
    private enum Heading{
        D1(0, 0, 98, 98),
        D2(0, 98, 98, 98),
        D3(0, 196, 98, 98),
        D4(0, 294, 98, 98),
        D5(0, 392, 98, 98),
        D6(0, 490, 98, 98),
        D7(0, 588, 98, 98),
        D8(0, 686, 98, 98);
        
        private int x, y, h, w;
        
        Heading(int x, int y, int h, int w){
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }
        
        protected BufferedImage texture(TextureAtlas atlas){
            return atlas.cut(x, y, w, h);
        }
    }
    
    private Heading heading;
    private Map<Heading, Sprite> spriteMap;
    private float scale;
    private float speed;
    //
    private boolean launch;
    private int lives;//кол-во попыток запуска биты
    //
    
    public Player(float x, float y, float scale, float speed, TextureAtlas atlas){
        super(EntityType.Player, x, y);
        launch = false;
        lives = 24;
        this.speed = speed;
        this.scale = scale;
        heading = Heading.D1;
        spriteMap = new HashMap<Heading, Sprite>();
        
        for(Heading h : Heading.values()){
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), 1, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }
    }
    
    @Override
    public void update(Input input){
        float newX = x;
        float newY = y;

        //
        if (null != heading)
       
        switch (heading) {
            case D1:
                heading = Heading.D2;
                break;
            case D2:
                heading = Heading.D3;
                break;
            case D3:
                heading = Heading.D4;
                break;
            case D4:
                heading = Heading.D5;
                break;
            case D5:
                heading = Heading.D6;
                break;
            case D6:
                heading = Heading.D7;
                break;
            case D7:
                heading = Heading.D8;
                break;
            case D8:
                heading = Heading.D1;
                break;
            default:
                break;
        }
        
        if (input.getKey(KeyEvent.VK_UP)){
            launch = true;
        }
        if (launch)
            newY-= speed;
        //
        
        if ((input.getKey(KeyEvent.VK_LEFT)) && (!launch)){
            newX -= speed;
        } else
        if ((input.getKey(KeyEvent.VK_RIGHT)) && (!launch)){
            newX += speed;
        }
        
        if (newX <= 0){
            newX = 0;
        } else if(newX >= Game.WIDTH - SPRITE_SCALE*scale){
            newX = Game.WIDTH - SPRITE_SCALE*scale;
        }
        
        //выход биты за экран, подсчет, возврат биты на исходную позицию
        if (newY <= - 120){
            launch = false;
            newY = 700;
            newX = 350;
            //бита возвращена на место, подсчитываем оставшиеся попытки
            lives--;
            
        }
        
        //
        x = newX;
        y = newY;
    }
    
    @Override
    public void render(Graphics2D g){
        spriteMap.get(heading).render(g, x, y);
    }
    public void setLives(){
        lives = 24;
    }
    public int retLives(){
        return lives;
    }
    public int playerPositionX(){
        return (int)this.x;
    }
    public int playerPositionY(){
        return (int)this.y;
    }
    public void setDefaultPosition(){
        launch = false;
        x = 350;
        y = 700;
    }
}
