import javax.swing.*;
import java.awt.*;

public class Scene extends JFrame {

    private JPanel paintPanel;    //畫板 幫助使用者介面畫上線條

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
                //todo 劃出食物
                Coordinate food = snake.getFood();   //取得食物的座標
                g.setColor(Color.green);    //設定食物的顏色
                g.fillOval(getPixel(food.x, padding, pixel_per_unit), getPixel(food.y, padding, pixel_per_unit), 20,20);

                //todo 劃出蛇類

                //todo 劃出蛇的身體
            }
        };

        paintPanel.setOpaque(false);    //設定畫板為透明
        paintPanel.setBounds(0, 0, 900, 480);  //設定畫板邊界
        add(paintPanel);   //添加畫板到遊戲視窗

        // 選項欄位
        JMenuBar bar = new JMenuBar();  //創建選項欄位
        bar.setBackground(Color.white);  //設定此物件顏色
        setJMenuBar(bar);    //設定到遊戲視窗中
        JMenu Settings = new JMenu("Setting");  Settings.setFont(f); bar.add(Settings);  //創建並添加"設置"選項
        JMenu Help = new JMenu("Help");   Help.setFont(f);  bar.add(Help);     //創建並添加"幫助"選項
        JMenuItem remove_net = new JMenuItem("Remove net"); remove_net.setFont(f2); Settings.add(remove_net);
        JMenuItem remove_padding = new JMenuItem("Remove padding"); remove_padding.setFont(f2); Settings.add(remove_padding);

        //todo 按鈕的觸發事件
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
    }


    public void run(){
        System.out.println("game running");
        snake = new Snake(this);
        setFocusable(true);   //設置焦點
        setVisible(true);     //顯示遊戲視窗
    }


    public static void main (String[] args){
        // 遊戲程式的起始點
        System.out.println("Application starting...");
        Scene game = new Scene();  //建立遊戲畫面的物件
        game.initUI();             //初始化遊戲畫面
        game.run();                //開始遊戲
        System.out.println("Game starting...");
    }
}