import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.nio.file.Files.move;

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
        run();   //snake start moving
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

    public void move(){  //蛇身體移動
        Coordinate head, next_coor = new Coordinate(0,0);  //定義蛇頭和下一個時間點的座標
        head = body.getFirst();
        if(direction == Direction.UP)
            next_coor = new Coordinate(head.x, head.y -1);
        else if(direction == Direction.DOWN)
            next_coor = new Coordinate(head.x, head.y +1);
        else if(direction == Direction.LEFT)
            next_coor = new Coordinate(head.x-1, head.y );
        else if(direction == Direction.RIGHT)
            next_coor = new Coordinate(head.x+1, head.y);

        if(checkDeath(next_coor)){
            // todo 設置pause,die : 執行repaint
        }
        else{
            Coordinate next_node = new Coordinate(next_coor);  //創建獨立的新節點
            body.addFirst(next_node);  //新節點添加到蛇頭
            if(!checkEat(next_coor)){   //有吃到食物就去除尾巴
                body.pollLast();   //remove tail
            }
            else{
                GameUI.updateLength(body.size());
                produceFood();  //create new food
            }
        }
    }

    public boolean checkDeath(Coordinate coor){  //判斷一個座標是否超出邊界代表蛇死亡
        int rows = GameUI.height;  //獲取區域大小的高
        int cols = GameUI.width;  //獲取區域大小的寬
        // 0 to 19

        boolean result = coor.x < 0 || coor.x >= cols || coor.y < 0 ||coor.y >= rows;

        return result;
    }

    public boolean checkEat(Coordinate coor) {
        return food.x == coor.x && food.y == coor.y;
    }


    public void show(){
        GameUI.repaint();  //重新繪製畫面 =刷新
    }


    public void produceFood() {
        food = randomCoor();
    }

    public Coordinate getFood() {
        return food;
    }

    public Deque<Coordinate> getBody() {
        return body;
    }

    public void run() {
        executor = Executors.newSingleThreadScheduledExecutor();  //創建單線程調度執行器
        executor.scheduleAtFixedRate(() -> {
            move();  //moving snake
            show();   //刷新顯示
        }, 0, 500, TimeUnit.MILLISECONDS);   //初始延遲為0毫秒每500毫秒執行一次
    }
}