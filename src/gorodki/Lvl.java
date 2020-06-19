package gorodki;

import java.awt.Graphics2D;
import static java.lang.Math.sqrt;
import java.util.HashMap;
import java.util.Map;
//Класс управления уровнем
public class Lvl {
    public static final int TILE_SCALE = 8;
    public static final int TILE_IN_GAME_SCALE = 2;
    public static final int TILES_IN_WIDTH = Game.WIDTH / (TILE_SCALE * TILE_IN_GAME_SCALE);
    public static final int TILES_IN_HIGHT = Game.HEIGHT / (TILE_SCALE * TILE_IN_GAME_SCALE);    
    
    private Integer[][] tileMap;
    private Map<TileType, Tile> tiles;
    private boolean directionR;
    private boolean figureExist;
    private int numOfCurLvl;
    
    public Lvl(TextureAtlas atlas){
        tileMap = new Integer[TILES_IN_WIDTH][TILES_IN_HIGHT];
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.BRICK, new Tile(atlas.cut(0,0,8,8), TILE_IN_GAME_SCALE, TileType.BRICK));
        tiles.put(TileType.EMPTY, new Tile(atlas.cut(8,0,8,8), TILE_IN_GAME_SCALE, TileType.BRICK));
        
        numOfCurLvl = 1;
        figureExist = true;
        directionR = true;
        tileMap = Utils.lvlParser("res/lvl1.lvl");
    }
    
    public void update(int playerX, int playerY){
        double playerRad = 50;
        double brickRad = 4;
        int numOfBrick = 0;
        //обработка столкновения биты с блоком
        for(int j = 0; j < 50; j++){
            for(int i = 0; i < 50; i++){
                if (tileMap[i][j] == 1){
                    numOfBrick++;
                    int brickX = j*16;
                    int brickY = i*16;
                    double radius = sqrt(((brickX - playerX)*(brickX - playerX)) + ((brickY - playerY)*(brickY - playerY)));
                    if (radius <= playerRad + brickRad){//Если бита и блок столкнулись
                        tileMap[i][j] = 0;
                        numOfBrick--;
                    }
                }
            }
        }
        if (numOfBrick == 0)
            figureExist = false;
        //обработка движения фигуры
        if(directionR){
            for(int j = 49; j>=0; j--){
                for(int i = 0; i<50; i++){
                    if (!directionR)
                        return;
                    if ((j == 49)&&(tileMap[i][49] == 1)){
                        directionR = false;
                        continue;
                    }
                    if(tileMap[i][j] == 1){
                        tileMap[i][j] = 0;
                        tileMap[i][j+1] = 1;
                    }
                }
            }
        }else{
            for(int j = 0; j<50; j++){
                for(int i = 0; i<50; i++){
                    if (directionR)
                        return;
                    if ((j == 0)&&(tileMap[i][0] == 1)){
                        directionR = true;
                        continue;
                    }
                    if(tileMap[i][j] == 1){
                        tileMap[i][j] = 0;
                        tileMap[i][j-1] = 1;
                    }
                }
            }
        }
        
    }
    
    public void render(Graphics2D g){
        
        for (int i=0; i < tileMap.length; i++){
            for(int j=0; j < tileMap[i].length; j++){
                Tile tile = tiles.get(TileType.fromNumeric(tileMap[i][j]));
                
                tile.render(g, j * TILE_SCALE * TILE_IN_GAME_SCALE, i * TILE_SCALE * TILE_IN_GAME_SCALE);
            }
        }
    }
    
    public boolean retFigureExist(){
        return figureExist;
    }
    public int retCurLvl(){
        return numOfCurLvl;
    }
    public void setFirstLvl(){
        numOfCurLvl = 1;
        figureExist = true;
        tileMap = Utils.lvlParser("res/lvl1.lvl");
    }
    
    public void setNextLvl(){
        numOfCurLvl++;
        
        figureExist = true;
        //Загрузка уровня из файла
        switch(numOfCurLvl){
            case 1: tileMap = Utils.lvlParser("res/lvl1.lvl");
                break;
            case 2: tileMap = Utils.lvlParser("res/lvl2.lvl");
                break;
            case 3: tileMap = Utils.lvlParser("res/lvl3.lvl");
                break;
            case 4: tileMap = Utils.lvlParser("res/lvl4.lvl");
                break;
            case 5: tileMap = Utils.lvlParser("res/lvl5.lvl");
                break;
            case 6: tileMap = Utils.lvlParser("res/lvl6.lvl");
                break;
            case 7: tileMap = Utils.lvlParser("res/lvl7.lvl");
                break;
            case 8: tileMap = Utils.lvlParser("res/lvl8.lvl");
                break;
            case 9: tileMap = Utils.lvlParser("res/lvl9.lvl");
                break;
            case 10: tileMap = Utils.lvlParser("res/lvl10.lvl");
                break;
            case 11: tileMap = Utils.lvlParser("res/lvl11.lvl");
                break;
            case 12: tileMap = Utils.lvlParser("res/lvl12.lvl");
                break;
            case 13: tileMap = Utils.lvlParser("res/lvl13.lvl");
                break;
            case 14: tileMap = Utils.lvlParser("res/lvl14.lvl");
                break;
            case 15: tileMap = Utils.lvlParser("res/lvl15.lvl");
                break;
            default: figureExist = false;
                break;
        }
        
        
    }
}
