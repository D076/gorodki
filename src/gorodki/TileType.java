package gorodki;
//Вспомогательное перечисления для вырисовки уровня
public enum TileType {
    EMPTY(0), BRICK(1);
    
    private int n;
    TileType(int n){
        this.n = n;
    }
    public int numeric(){
        return n;
    }
    public static TileType fromNumeric(int n){
        switch (n){
            case 1: return BRICK;
            default: return EMPTY;
        }
    }
}
