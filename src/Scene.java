import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Deque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scene extends JFrame {

    private JPanel paintPanel;    //畫板 幫助使用者介面畫上線條

    private final JLabel label1 = new JLabel("當前長度");

    private final JLabel label2 = new JLabel("所花時間:");

    private final JLabel Length = new JLabel("1");

    private final JLabel  Time = new JLabel("");

    private Timer timer;    //計時器

    public boolean pause = false;  //暫停標籤

    public boolean quit = false;  //退出標籤

    public boolean die = false;   //死亡標籤

    private boolean show_padding = true;  //是否出邊框線

    private boolean show_grid = true;    //是否出網格線

    public final int padding = 5;   //內邊寬的寬度

    private final Font f = new Font("微軟雅黑", Font.PLAIN, 15);  //字體設置

    private final Font f2 = new Font("微軟雅黑", Font.PLAIN, 12); //

    public final int width = 20;

    public final int height = 20;

    public final int pixel_per_unit = 22; //每個網格的像素(pixel)

    public final int pixel_rightBar = 100; //右邊訊息藍的寬度 單位是像素(pixel)


    private Snake snake;

    public void restart(){
        quit = true;
        Length.setText("1");  //重啟長度
        Time.setText("");  //重設時間

        snake.quit();
        snake = null;
        snake = new Snake(this);    //創建新的蛇物件

        timer.reset();   //重製計時器

        die = false;    //重製死亡標籤
        quit = false;   //重製退出標籤
        pause = false;  //重製暫停標籤

        System.out.println("\nGame restart....");
    }

    public void updateLength(int length){
        Length.setText("" + length);
    }

    public int getPixel(int i, int padding, int pixels_per_unit){
        return 1+padding+i*pixels_per_unit;
    }


    public void initUI() {
        System.out.println("init UI");
        //  todo 初始化遊戲畫面

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();  //獲取package中的系統外觀樣式

        try {
            UIManager.setLookAndFeel(lookAndFeel); //設置系統外觀樣式
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Image img = Toolkit.getDefaultToolkit().getImage("image//title.png"); //獲取遊戲的logo icon
        setIconImage(img);            //設定遊戲視窗的icon
        setTitle("Snake");            //設定遊戲視窗的標題
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //設置關閉畫面時的操作
        setSize(width * pixel_per_unit + 100, height * pixel_per_unit + 75);  //設定遊戲視窗大小
        setResizable(false);          //禁用遊戲視窗大小調整
        setLayout(null);              //設定布局為null
        setLocationRelativeTo(null);  //視窗居中

        paintPanel =  new JPanel(){
            @Override
            public void paint(Graphics g1){
                super.paint(g1); // 呼叫並使用父類別的功能，確保畫板正常顯示
                Graphics2D g = (Graphics2D) g1; // 將Graphics轉Graphics2D，以便使用高級繪圖功能
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); /// 設置抗鋸齒，使繪圖更平滑
                g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE); // 正常化描邊，使線條更平滑

                // 邊框線
                if(show_padding){
                    g.setPaint(new GradientPaint(115,135,Color.CYAN,230,135,Color.MAGENTA,true)); // 設置漸變顏色
                    g.setStroke( new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL)); // 設置邊框線的粗細和樣式
                    g.drawRect(2, 2, padding*2-4+width*pixel_per_unit, height*pixel_per_unit+6);  // 繪製矩形邊框
                }

                // 網格線
                if(show_grid) {
                    for(int i = 0; i <= width; i++) {
                        g.setStroke( new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, new float[] { 15, 10, }, 0f)); // 設置虛線樣式
                        g.setColor(Color.black); // 設置線條顏色
                        g.drawLine(padding+i*pixel_per_unit, padding, padding+i*pixel_per_unit,padding+height*pixel_per_unit); /// 畫直線
                    }

                    for(int i = 0;i <= height; i++){
                        g.drawLine(padding,padding+i*pixel_per_unit, padding+width*pixel_per_unit,padding+i*22); // 畫橫線
                    }
                }
                //劃出食物
                Coordinate food = snake.getFood();   //取得食物的座標
                g.setColor(Color.green);    //設定食物的顏色
                g.fillOval(getPixel(food.x, padding, pixel_per_unit), getPixel(food.y, padding, pixel_per_unit), 20,20);

                //劃出蛇頭
                Deque<Coordinate> body = snake.getBody();   //get snake body's location
                Coordinate head = body.getFirst();  // get snake head's location
                g.setColor(Color.red);         // color of snake head
                g.fillRoundRect(getPixel(head.x, padding, pixel_per_unit), getPixel(head.y, padding, pixel_per_unit), 20, 20, 10,10);  //繪製蛇頭

                //劃出蛇的身體
                g.setPaint(new GradientPaint(115,135,Color.CYAN,230,135,Color.MAGENTA,true));
                for (Coordinate coor: body){
                    if(head.x == coor.x && head.y == coor.y) continue;  //跳過蛇頭
                    g.fillRoundRect(getPixel(coor.x, padding, pixel_per_unit), getPixel(coor.y, padding, pixel_per_unit), 20,20,10,10);
                }

                //當死亡時畫面所顯示訊息
                if(die){
                    g.setFont(new Font("微軟雅黑", Font.BOLD | Font.ITALIC,30 ));
                    g.setColor(Color.black);
                    g.setStroke(new BasicStroke(10,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));

                    int x = this.getWidth() / 2, y = this.getHeight() / 2;
                    g.drawString("Sorry, you die", x - 350, y -50);
                    g.drawString("Press ESC to restart", x -350, y + 50);
                }
            }
        };

        paintPanel.setOpaque(false);    //設定畫板為透明
        paintPanel.setBounds(0, 0, 900, 480);  //設定畫板邊界
        add(paintPanel);   //添加畫板到遊戲視窗



        //右側遊戲資訊
        int info_x = padding * 3 + width * pixel_per_unit;
        add(label1); label1.setBounds(info_x,10,80,20); label1.setFont(f);
        label1.setForeground(Color.black);   //添加並設置標籤
        add(Length); Length.setBounds(info_x,35,80,20); Length.setFont(f); Length.setForeground(Color.black);
        add(label2); label2.setBounds(info_x,70,80,20); label2.setFont(f); label2.setForeground(Color.black);
        add(Time);  Time.setBounds(info_x, 95,80,20); Time.setFont(f); Time.setForeground(Color.black);

        // 選項欄位
        JMenuBar bar = new JMenuBar();  //創建選項欄位
        bar.setBackground(Color.white);  //設定此物件顏色
        setJMenuBar(bar);    //設定到遊戲視窗中
        JMenu Settings = new JMenu("Setting");  Settings.setFont(f); bar.add(Settings);  //創建並添加"設置"選項
        JMenu Help = new JMenu("Help");   Help.setFont(f);  bar.add(Help);     //創建並添加"幫助"選項
        JMenuItem remove_net = new JMenuItem("Remove net"); remove_net.setFont(f2); Settings.add(remove_net);
        JMenuItem remove_padding = new JMenuItem("Remove padding"); remove_padding.setFont(f2); Settings.add(remove_padding);
        JMenuItem help = new JMenuItem("Guide....");  help.setFont(f2); Help.add(help);

        this.addKeyListener(new MyKeyListener());  //添加鍵盤的監聽器
        remove_net.addActionListener(e -> {
            if(!show_grid){
                show_grid = true;
                remove_net.setText("hide grid");
            }
            else{
                show_grid = false;
                remove_net.setText("show grid");
            }
            paintPanel.repaint();     //重新繪製畫面
        });
        remove_padding.addActionListener(e ->{
            if(!show_padding){
                show_padding = true;
                remove_padding.setText("hide padding");
            }
            else{
                show_padding = false;
                remove_padding.setText("show padding");
            }
            paintPanel.repaint();
        });
        //todo 完成"幫助"的觸發事件
        help.addActionListener(e  -> new Help());  //顯示幫助
    }


    public void run(){
        System.out.println("game running");
        snake = new Snake(this);
        setFocusable(true);   //設置焦點
        setVisible(true);     //顯示遊戲視窗
        timer = new Timer();
    }


    public static void main (String[] args){
        // 遊戲程式的起始點
        System.out.println("Application starting...");
        Scene game = new Scene();  //建立遊戲畫面的物件
        game.initUI();             //初始化遊戲畫面
        game.run();                //開始遊戲
        System.out.println("Game starting...");
    }

    //todo 計時器的class

    private class Timer{

        private int hour = 0;

        private int min = 0;

        private int sec = 0;



        public Timer(){
            this.run();
        }


        public void run(){
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();  //創建一個單線程執行器
            executor.scheduleAtFixedRate(() ->{
                if(!quit && !pause){  //如果遊戲沒有退出或暫停
                    sec += 1;
                    if(sec >=60){   //秒進位分
                        sec = 0;
                        min += 1;
                    }
                    if (min >= 60){   //分進位小時
                        min = 0;
                        hour += 1;
                    }
                    showTime();
                }
            }, 0,1000, TimeUnit.MILLISECONDS);
        }

        public void reset() {
            hour = 0;
            min = 0;
            sec = 0;
        }

        public void showTime(){
            String strTime;
            if(hour < 10) strTime = "0" + hour + ":";
            else strTime = hour + ":";

            if(min < 10) strTime = strTime + "0" + min +":";
            else strTime = "" +strTime + min +":";

            if(sec< 10) strTime = strTime + "0" + sec;
            else strTime = "" + strTime + sec;

            Time.setText(strTime);
        }
    }


    private class MyKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();   //獲取按鍵代碼
            Direction direction = snake.direction;   //獲取當前蛇的方向

            if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
                if(!quit && direction != Direction.LEFT)
                    snake.direction = Direction.RIGHT;
            }
            else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
                if(!quit && direction != Direction.RIGHT)
                    snake.direction = Direction.LEFT;
            }
            else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
                if(!quit && direction != Direction.DOWN)
                    snake.direction = Direction.UP;
            }
            else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S){
                if(!quit && direction != Direction.UP)
                    snake.direction = Direction.DOWN;
            }
            else if(key == KeyEvent.VK_ESCAPE){
                restart();   //restart the game
            }
            else if(key == KeyEvent.VK_SPACE){
                if(!pause){  //如果沒有暫停
                    pause = true;  //設置為暫停
                    System.out.println("Paused...");
                }
                else{  //如果已暫停
                    pause = false;  //取消暫停
                    System.out.println("Start...");
                }
            }
        }


        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}