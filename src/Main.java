public class Main{
    public static void main(String[] args){
        System.out.println("Hello world");

        Direction d1 = Direction.LEFT;
        Direction d2 = Direction.RIGHT;
        Direction d3 = Direction.UP;
        Direction d4 = Direction.DOWN;

        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(d4);
        //  ========================================
        Coordinate c1 = new Coordinate(5,10);
        System.out.println(c1);
        Coordinate c2 = new Coordinate(c1);
        System.out.println(c2);
    }
}