import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

public class Snake {

    private Scene GameUI;    //遊戲頁面

    public Direction direction = Direction.RIGHT;

    private Deque<Coordinate> body = new LinkedList<>();

    private Coordinate food;

    private ScheduledExecutorService executor;

    public Snake(Scene GameUI) {
        this.GameUI = GameUI;
        Coordinate head = new Coordinate(0, 0);
        body.addFirst(head);   //將舌頭添加到身體列隊第一個位置
        produceFood();  //生成食物
        //todo 讓蛇開始移動
    }

    public Coordinate randomCoor() {  //生成隨機座標
        int rows = GameUI.height;
        int cols = GameUI.width;
        Random rand = new Random();
        Coordinate res;
        int x = rand.nextInt(cols - 1);
        int y = rand.nextInt(rows - 1);

        while (true) {
            boolean tag = false;
            for (Coordinate coor : body) {
                if (coor.x == x && coor.y == y) {  //防止食物生成在蛇身體上
                    x = rand.nextInt(cols - 1);
                    y = rand.nextInt(rows - 1);
                    tag = true;
                    break;
                }
            }

            if (!tag)
                break;   //找不到與蛇身體重疊的座標
        }

        res = new Coordinate(x, y);
        return res;
    }


    public void produceFood() {
        food = randomCoor();
    }

    public Coordinate getFood() {
        return food;
    }
}