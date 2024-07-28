import javax.swing.*;
import java.awt.*;

public class Scene extends JFrame {

    private JPanel paintPanel;    //畫板 幫助使用者介面

    public final int width = 20;

    public final int height = 20;

    public final int pixel_per_unit = 22; //每個網格的像素(pixel)

    public final int pixel_rightBar = 100; //右邊訊息藍的寬度 單位是像素(pixel)




    private Snake snake;


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
                super.paint(g1);

                // todo 其他的override
            }
        };

        paintPanel.setOpaque(false);    //設定畫板為透明
        paintPanel.setBounds(0, 0, 900, 480);  //設定畫板邊界
        add(paintPanel);   //添加畫板到遊戲視窗
    }


    public void run(){
        System.out.println("game running");
        //  todo 遊戲執行細節
        setFocusable(true);   //設置焦點
        setVisible(true);     //顯示遊戲視窗
        snake = new Snake();  //初始化貪吃蛇物件
    }   //  UI init


    public static void main (String[] args){
        // 遊戲程式的起始點
        System.out.println("Application starting...");
        Scene game = new Scene();  //建立遊戲畫面的物件
        game.initUI();             //初始化遊戲畫面
        game.run();                //開始遊戲
        System.out.println("Game starting...");
    }
}