package gorodki;


import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

//Класс, содержащий игровой движок
public class Game implements Runnable{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final String TITLE = "Городки";
    public static final int CLEAR_COLOR = 0xffffffff;
    public static final int NUM_BUFFERS = 3;
    
    public static final float UPDATE_RATE = 30.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;//в наносек
    public static final long IDLE_TIME = 1;//в миллисек
    
    public static final String ATLAS_FILE_NAME = "bit_atlas.png";
    public static final String BRICK_FILE_NAME = "brick.png";
    
    
    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;
    private TextureAtlas atlas;
    private TextureAtlas brick;
    private Player player;
    private Lvl lvl;
    
    public Game(){
        running = false;
        Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.AddInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        brick = new TextureAtlas(BRICK_FILE_NAME);
        player = new Player(350, 700, 1, 18, atlas);
        lvl = new Lvl(brick);
    }
    //Запуск игры
    public synchronized void start(){
        if (running)
            return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();//запускает метод run
    }
    
    public synchronized void stop(){//остановка игры
        if (!running)
            return;
        running = false;
        
        try{
         gameThread.join();//ожидание завершения параллельного кода
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        cleanUp();
    }
    
    private void update(){//физика и геометрия
        player.update(input);//обновление биты
        int playerX = player.playerPositionX();
        int playerY = player.playerPositionY();
        lvl.update(playerX, playerY);//обновление уровня игры
        
        boolean figureExist = lvl.retFigureExist();
        if (!figureExist){//если игрок выбил фигуру
            player.setDefaultPosition();
            //создание след уровня
            lvl.setNextLvl();
        }
    }
    
    private void render(){//перерисовка экрана
        Display.clear();
        lvl.render(graphics);
        player.render(graphics);
        Display.swapBuffers();
    }
    
    @Override
    public void run(){//цикл с игрой
        int fps = 0;
        int upd = 0;//кол-во апдейтов всего
        int updl = 0;//кол-во апдейтов подряд
        
        long count = 0;
        
        float delta = 0;
        long lastTime = Time.get();
        while(running){
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;
            
            count += elapsedTime;
            
            boolean render = false;
            delta+= (elapsedTime / UPDATE_INTERVAL);//на каждую единицу delta - 1 апдейт
            while(delta > 1){
                update();
                upd++;
                delta--;
                if (render)
                    updl++;
                else
                    render = true;
            }
            if(render){
                render();
                fps++;
            } else{
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (count >= Time.SECOND){
                int lives = player.retLives();
                int curLvl = lvl.retCurLvl();
                if(curLvl > 15){
                    JOptionPane.showMessageDialog(null, "Поздравляем!\nВы прошли все 15 уровней!");
                    newGame();
                }
                if (lives == 0){
                    JOptionPane.showMessageDialog(null, "У вас кончились броски\nВы дошли до уровня №"+curLvl);
                    newGame();
                }
                Display.setTitle(TITLE + " || Осталось бросков: "+lives +" | Текущий уровень: №" +curLvl);
                upd = 0;
                updl = 0;
                fps = 0;
                count = 0;
            }
                
        }
    }
    private void newGame(){
        player.setLives();
        player.setDefaultPosition();
        lvl.setFirstLvl();
    }
    
    private void cleanUp(){
        Display.destroy();
    }
}
