public class Coordinate {

    public int x;

    public int y;

    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate obj){
        this.x = obj.x;
        this.y = obj.y;
    }

    @Override
    public String toString(){
        return "(x,y) = (" + x +","+ y +")"; //(x,y) = (5,10)
    }
}
